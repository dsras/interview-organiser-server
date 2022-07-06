package com.accolite.intervieworganiser.utility;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Provides custom date deserialization for JSON (DD MM YYYY)
 */
public class DateHandler extends StdDeserializer<LocalDate> {

    public DateHandler() {
        this(null);
    }

    public DateHandler(Class<?> clazz) {
        super(clazz);
    }

    /**
     * Formats date in pattern DD-MM-YYYY
     *
     * @param jsonParser JSON parser
     * @param context deserialization context
     * @return formatted date
     * @throws IOException thrown by parser getText
     */
    @Override
    public LocalDate deserialize(
            JsonParser jsonParser,
            DeserializationContext context
    )
            throws IOException {
        String date = jsonParser.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDate.parse(date, formatter);
    }
}
