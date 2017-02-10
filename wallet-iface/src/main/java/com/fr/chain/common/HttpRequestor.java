package com.fr.chain.common;

import static org.apache.http.HttpVersion.HTTP_1_1;
import static org.apache.http.params.CoreConnectionPNames.SO_TIMEOUT;
import static org.apache.http.params.CoreConnectionPNames.STALE_CONNECTION_CHECK;
import static org.apache.http.params.CoreProtocolPNames.HTTP_CONTENT_CHARSET;
import static org.apache.http.params.CoreProtocolPNames.HTTP_ELEMENT_CHARSET;
import static org.apache.http.params.CoreProtocolPNames.PROTOCOL_VERSION;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import lombok.extern.slf4j.Slf4j;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.springframework.stereotype.Component;

@Component("httpRequestor")
@Slf4j
public class HttpRequestor {

	@PostConstruct
	public void reload() {
		lock.writeLock().lock();
		try {
			cm = new ThreadSafeClientConnManager();
			httpclient = new DefaultHttpClient(cm);
			DefaultHttpClient httpclient = new DefaultHttpClient();
			httpclient.setKeepAliveStrategy(new ConnectionKeepAliveStrategy() {
				public long getKeepAliveDuration(HttpResponse response,
						HttpContext context) {
					// Honor 'keep-alive' header
					HeaderElementIterator it = new BasicHeaderElementIterator(
							response.headerIterator(HTTP.CONN_KEEP_ALIVE));
					while (it.hasNext()) {
						HeaderElement he = it.nextElement();
						String param = he.getName();
						String value = he.getValue();
						if (value != null && param.equalsIgnoreCase("timeout")) {
							try {
								return Long.parseLong(value) * 1000;
							} catch (NumberFormatException ignore) {
							}
						}
					}
					return httpKeepAlivesSecs * 1000;
				}
			});

			httpclient.getParams().setParameter(PROTOCOL_VERSION, HTTP_1_1);
			httpclient.getParams().setParameter(HTTP_CONTENT_CHARSET, "UTF-8");
			httpclient.getParams().setParameter(HTTP_ELEMENT_CHARSET, "UTF-8");
			httpclient.getParams().setParameter(STALE_CONNECTION_CHECK, false);
		} finally {

			lock.writeLock().unlock();
		}
	}

	public void changeMaxTotal(int size) {
		lock.writeLock().lock();
		try {
			cm.setMaxTotal(size);
		} finally {
			lock.writeLock().unlock();
		}
	}

	public void changeMaxPerRoute(int size) {
		lock.writeLock().lock();
		try {
			cm.setDefaultMaxPerRoute(size);
		} finally {
			lock.writeLock().unlock();
		}
	}

	public String post(String txt, String address)
			throws ClientProtocolException, IOException {
		lock.readLock().lock();
		HttpPost httppost = null;
		try {
			log.debug("postdata:" + address + ":" + txt);
			httpclient.getParams().setParameter(SO_TIMEOUT,
					defaultHttpTimeoutMillis);
			StringEntity entity = new StringEntity(txt, "UTF-8");
			httppost = new HttpPost(address);
			httppost.setEntity(entity);
			httppost.getParams().setParameter(SO_TIMEOUT,
					defaultHttpTimeoutMillis);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			return httpclient.execute(httppost, responseHandler);
		} finally {
			lock.readLock().unlock();
		}
	}

	public String postAsParameter(List<NameValuePair> urlParameters, String address)
			throws ClientProtocolException, IOException {
		lock.readLock().lock();
		HttpPost httppost = null;
		try {
			
			log.debug("postdata:" + address + ":" + urlParameters);
			httpclient.getParams().setParameter(SO_TIMEOUT,
					defaultHttpTimeoutMillis);
			httppost = new HttpPost(address);
			httppost.setEntity(new UrlEncodedFormEntity(urlParameters, "UTF-8"));
			httppost.getParams().setParameter(SO_TIMEOUT,
					defaultHttpTimeoutMillis);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			return httpclient.execute(httppost, responseHandler);
		} finally {
			lock.readLock().unlock();
		}
	}
	public String postAsParameterForHttps(List<NameValuePair> urlParameters,
			String address) throws ClientProtocolException, IOException,
			NoSuchAlgorithmException, KeyStoreException,
			KeyManagementException, UnrecoverableKeyException {
		lock.readLock().lock();
		HttpPost httppost = null;
		try {
			TrustStrategy acceptingTrustStrategy = new TrustStrategy() {
				@Override
				public boolean isTrusted(X509Certificate[] certificate,
						String authType) {
					return true;
				}
			};
			SSLSocketFactory sf = new SSLSocketFactory(acceptingTrustStrategy,
					SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("https", 443, sf));
			ClientConnectionManager ccm = new PoolingClientConnectionManager(
					registry);

			DefaultHttpClient httpClient = new DefaultHttpClient(ccm);

			log.debug("postdata:" + address + ":" + urlParameters);
			httpclient.getParams().setParameter(SO_TIMEOUT,
					defaultHttpTimeoutMillis);
			httppost = new HttpPost(address);
			httppost.setEntity(new UrlEncodedFormEntity(urlParameters, "UTF-8"));
			httppost.getParams().setParameter(SO_TIMEOUT,
					defaultHttpTimeoutMillis);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			return httpClient.execute(httppost, responseHandler);
		} finally {
			lock.readLock().unlock();
		}
	}

	@PreDestroy
	public void destroy() {
		lock.writeLock().lock();
		try {
			cm.shutdown();
		} finally {
			lock.writeLock().unlock();
		}
	}

	public int defaultHttpTimeoutMillis = 20000;// http超时时间
	private int httpKeepAlivesSecs = 30;
	private ThreadSafeClientConnManager cm;
	private DefaultHttpClient httpclient;
	private final ReadWriteLock lock = new ReentrantReadWriteLock();

}
