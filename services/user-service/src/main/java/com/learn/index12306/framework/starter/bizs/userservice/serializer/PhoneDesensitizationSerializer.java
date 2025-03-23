package com.learn.index12306.framework.starter.bizs.userservice.serializer;

import cn.hutool.core.util.DesensitizedUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @author: cyy
 * @create: 2025-03-23 18:13
 * @description: 电话号码脱敏反序列化
 **/
public class PhoneDesensitizationSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String s, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        String phoneDesensitization = DesensitizedUtil.mobilePhone(s);
        jsonGenerator.writeString(phoneDesensitization);
    }
}
