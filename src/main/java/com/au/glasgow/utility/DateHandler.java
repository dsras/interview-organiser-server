package com.au.glasgow.utility;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/* Custom Date Deserializer for JSON */
public class DateHandler extends StdDeserializer<LocalDate> {
    public DateHandler() {
        this(null);
    }

    public DateHandler(Class<?> clazz) {
        super(clazz);
    }

    @Override
    public LocalDate deserialize(JsonParser jsonparser, DeserializationContext context) throws IOException {
        String date = jsonparser.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDate.parse(date, formatter);
    }
}
