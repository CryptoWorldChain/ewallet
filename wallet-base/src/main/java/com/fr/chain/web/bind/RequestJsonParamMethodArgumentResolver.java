package com.fr.chain.web.bind;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.MapType;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.codehaus.jackson.type.JavaType;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.AbstractNamedValueMethodArgumentResolver;

import com.fr.chain.utils.JsonUtil;
import com.fr.chain.web.bean.FieldsMapperBean;
import com.fr.chain.web.bean.QueryMapperBean;

public class RequestJsonParamMethodArgumentResolver extends
		AbstractNamedValueMethodArgumentResolver implements WebArgumentResolver {

	private ObjectMapper mapper = new ObjectMapper();

	public RequestJsonParamMethodArgumentResolver() {
		super(null);
	}

	public boolean supportsParameter(MethodParameter parameter) {

		if (parameter.hasParameterAnnotation(RequestJsonParam.class)) {
			return true;
		}
		return false;
	}

	@Override
	protected NamedValueInfo createNamedValueInfo(MethodParameter parameter) {
		RequestJsonParam annotation = parameter
				.getParameterAnnotation(RequestJsonParam.class);
		return new RequestJsonParamNamedValueInfo(annotation);

	}

	public QueryMapperBean buildQueryMapper(String json) {
		if (StringUtils.isBlank(json)) {
			return new QueryMapperBean();
		}
		ObjectNode node;
		try {
			node = JsonUtil.toObjectNode(json);
			
//			String where = QueryMapperResolver
//					.genQueyDirectory("", node, "and");
			QueryMapperBean mb = new QueryMapperBean();
			mb.setNode(node);
			return mb;

		} catch (Exception e) {
			e.printStackTrace();
			return new QueryMapperBean();
		}
	}

	public Object buildFieldsMapper(String json) {
		return FieldsMapperResolver.genQueryMapper(json);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected Object resolveName(String name, MethodParameter parameter,
			NativeWebRequest webRequest) throws Exception {
		String[] paramValues = webRequest.getParameterValues(name);
		Class<?> paramType = parameter.getParameterType();
		if (paramValues == null) {
			return null;
		}

		try {
			if (paramValues.length == 1) {
				String text = paramValues[0];
				if (StringUtils.isBlank(text)) {
					return null;
				}
				if (QueryMapperBean.class.equals(paramType)) {
					return buildQueryMapper(text);
				}
				if (FieldsMapperBean.class.equals(paramType)) {
					return buildFieldsMapper(text);
				}

				Type type = parameter.getGenericParameterType();
				if (MapWapper.class.isAssignableFrom(paramType)) {
					MapWapper<?, ?> jsonMap = (MapWapper<?, ?>) paramType
							.newInstance();

					MapType mapType = (MapType) getJavaType(HashMap.class);

					if (type instanceof ParameterizedType) {
						mapType = (MapType) mapType
								.narrowKey((Class<?>) ((ParameterizedType) type)
										.getActualTypeArguments()[0]);
						mapType = (MapType) mapType
								.narrowContentsBy((Class<?>) ((ParameterizedType) type)
										.getActualTypeArguments()[1]);
					}
					jsonMap.setInnerMap(mapper.<Map> readValue(text, mapType));
					return jsonMap;
				}

				JavaType javaType = getJavaType(paramType);

				if (Collection.class.isAssignableFrom(paramType)) {
					javaType = javaType
							.narrowContentsBy((Class<?>) ((ParameterizedType) type)
									.getActualTypeArguments()[0]);
				}

				mapper.configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
				mapper.configure(Feature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
				mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
				return mapper.readValue(paramValues[0], javaType);
			}

		} catch (Exception e) {
			throw new JsonMappingException(
					"Could not read request json parameter", e);
		}

		throw new UnsupportedOperationException(
				"too many request json parameter '" + name
						+ "' for method parameter type [" + paramType
						+ "], only support one json parameter");
	}

	@SuppressWarnings("deprecation")
	protected JavaType getJavaType(Class<?> clazz) {
		return TypeFactory.type(clazz);
	}

	@Override
	protected void handleMissingValue(String paramName,
			MethodParameter parameter) throws ServletException {
		String paramType = parameter.getParameterType().getName();
		throw new ServletRequestBindingException(
				"Missing request json parameter '" + paramName
						+ "' for method parameter type [" + paramType + "]");
	}

	private class RequestJsonParamNamedValueInfo extends NamedValueInfo {

		private RequestJsonParamNamedValueInfo() {
			super("", false, null);
		}

		private RequestJsonParamNamedValueInfo(RequestJsonParam annotation) {
			super(annotation.value(), annotation.required(), null);
		}
	}

	/**
	 * spring 3.1之前
	 */
	@Override
	public Object resolveArgument(MethodParameter parameter,
			NativeWebRequest request) throws Exception {
		if (!supportsParameter(parameter)) {
			return WebArgumentResolver.UNRESOLVED;
		}
		return resolveArgument(parameter, null, request, null);
	}
}