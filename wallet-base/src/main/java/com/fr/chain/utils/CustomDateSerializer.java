package com.fr.chain.utils;

import java.io.IOException;
import java.util.Date;
	 
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
	 
	/**
	 * 自定义返回JSON 数据格中日期格式化处理
	 *
	 * @author <a href="http://www.micmiu.com">Michael Sun</a>
	 */
public class CustomDateSerializer extends JsonSerializer<Date> {
    @Override
    public void serialize(Date value, JsonGenerator jgen,
            SerializerProvider provider) throws IOException,
            JsonProcessingException {
        String formattedDate = DateUtil.format(value, DateStyle.YYYY_MM_DD_HH_MM_SS);
        jgen.writeString(formattedDate); 
    }
 
}
