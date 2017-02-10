package com.fr.chain.web.bind;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.DeserializerProvider;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.type.JavaType;

public class RestObjectMapper extends ObjectMapper {

	public static class NullSerializer extends JsonSerializer<Object> {
		public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
			// any JSON value you want...
			jgen.writeString("");
		}
	}

	@Override
	public <T> T readValue(InputStream src, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
		// TODO Auto-generated method stub
		return super.readValue(src, valueType);
	}

	@Override
	public <T> T readValue(JsonNode root, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
		// TODO Auto-generated method stub
		return super.readValue(root, valueType);
	}

	@Override
	public <T> T readValue(Reader src, JavaType valueType) throws IOException, JsonParseException, JsonMappingException {
		// TODO Auto-generated method stub
		return super.readValue(src, valueType);
	}

	@Override
	public <T> T readValue(String content, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
		// TODO Auto-generated method stub
		return super.readValue(content, valueType);
	}

	@Override
	public <T> T readValue(byte[] src, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
		// TODO Auto-generated method stub
		return super.readValue(src, valueType);
	}

	@Override
	public <T> T readValue(Reader src, Class<T> valueType) throws IOException, JsonParseException, JsonMappingException {
		// TODO Auto-generated method stub
		return super.readValue(src, valueType);
	}

	public RestObjectMapper() {
		super();
		getSerializerProvider().setNullValueSerializer(new NullSerializer());
		configure(Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		configure(Feature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, false);
		configure(Feature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
		setVisibilityChecker(getSerializationConfig().getDefaultVisibilityChecker().withFieldVisibility(JsonAutoDetect.Visibility.ANY)
				.withGetterVisibility(JsonAutoDetect.Visibility.NONE).withSetterVisibility(JsonAutoDetect.Visibility.NONE)
				.withCreatorVisibility(JsonAutoDetect.Visibility.NONE));

	}

	@Override
	public void writeValue(JsonGenerator jgen, Object value) throws IOException, JsonGenerationException, JsonMappingException {
		super.writeValue(jgen, value);
	}

	@Override
	public void writeValue(OutputStream out, Object value) throws IOException, JsonGenerationException, JsonMappingException {
		super.writeValue(out, value);
	}

	public RestObjectMapper(JsonFactory jf, SerializerProvider sp, DeserializerProvider dp, SerializationConfig sconfig, DeserializationConfig dconfig) {
		super(jf, sp, dp, sconfig, dconfig);
	}

	public RestObjectMapper(JsonFactory jf, SerializerProvider sp, DeserializerProvider dp) {
		super(jf, sp, dp);
	}

	public RestObjectMapper(JsonFactory jf) {
		super(jf);
	}

}
