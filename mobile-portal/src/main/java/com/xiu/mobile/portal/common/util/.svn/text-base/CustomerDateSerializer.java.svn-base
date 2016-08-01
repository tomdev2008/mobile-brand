package com.xiu.mobile.portal.common.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomerDateSerializer extends JsonSerializer<Date>{

	@Override
	public void serialize(Date dateValue, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//这里可以换成你想要的时间格式
         String formattedDate = formatter.format(dateValue);
         jgen.writeString(formattedDate);	
	}

}
