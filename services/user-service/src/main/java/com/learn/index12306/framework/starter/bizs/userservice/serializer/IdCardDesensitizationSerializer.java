package com.learn.index12306.framework.starter.bizs.userservice.serializer;

import cn.hutool.core.util.DesensitizedUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @author: cyy
 * @create: 2025-03-23 18:14
 * @description: 身份证号脱敏反序列化
 **/
public class IdCardDesensitizationSerializer extends JsonSerializer<String>{

    @Override
    public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String idCardDesensitization  = DesensitizedUtil.idCardNum(s, 4, 4);
        jsonGenerator.writeString(idCardDesensitization);
    }
}
