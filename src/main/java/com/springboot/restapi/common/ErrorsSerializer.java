package com.springboot.restapi.common;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.validation.Errors;

import java.io.IOException;

/**
 * Error 객체를 json으로 serialization하기 위한 class
 * @JsonComponent을 사용하여 ObjectMapper에 등록
 */
@JsonComponent
public class ErrorsSerializer extends JsonSerializer<Errors> {

    @Override
    public void serialize(Errors errors, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeFieldName("errors");
        jsonGenerator.writeStartArray();
        errors.getFieldErrors().stream().forEach(e -> {

            try {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField("field", e.getField());
                jsonGenerator.writeStringField("objectName", e.getObjectName());
                jsonGenerator.writeStringField("code", e.getCode());
                jsonGenerator.writeStringField("defaultMessage", e.getDefaultMessage());

                Object rejectedValue = e.getRejectedValue();
                if (rejectedValue != null) {
                    jsonGenerator.writeStringField("rejectedValue", e.getRejectedValue().toString());
                }

                jsonGenerator.writeEndObject();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        errors.getGlobalErrors().forEach(e -> {
            try {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField("objectName", e.getObjectName());
                jsonGenerator.writeStringField("code", e.getCode());
                jsonGenerator.writeStringField("defaultMessage", e.getDefaultMessage());

                jsonGenerator.writeEndObject();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        jsonGenerator.writeEndArray();
    }
}
