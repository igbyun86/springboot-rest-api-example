package com.springboot.restapi.accounts;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class AccountSerializer extends JsonSerializer<Account> {

    @Override
    public void serialize(Account account, JsonGenerator jen, SerializerProvider serializerProvider) throws IOException {
        jen.writeStartObject();
        jen.writeNumberField("id", account.getId());
        jen.writeEndObject();
    }
}
