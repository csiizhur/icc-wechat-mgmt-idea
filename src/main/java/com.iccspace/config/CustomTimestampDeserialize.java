package com.iccspace.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.sql.Timestamp;

public class CustomTimestampDeserialize extends JsonDeserializer<Timestamp> {
    
    @Override    
    public Timestamp deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
    
        Timestamp timestamp = null;
        try {    
            timestamp = Timestamp.valueOf(jp.getText());
        } catch (Exception e) {
            e.printStackTrace();    
        }
        return timestamp;
    }
}