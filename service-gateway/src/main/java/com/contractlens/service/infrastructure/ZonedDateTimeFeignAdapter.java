package com.contractlens.service.infrastructure;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZonedDateTimeFeignAdapter extends TypeAdapter<ZonedDateTime> {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ISO_ZONED_DATE_TIME;


    @Override
    public void write(JsonWriter out, ZonedDateTime value) throws IOException {
        if (value== null){
            out.nullValue();
        }else {
            out.value(value.format(FORMATTER));
        }
    }

    @Override
    public ZonedDateTime read(JsonReader in) throws IOException {
        String dateTime = in.nextString();
        return ZonedDateTime.parse(dateTime,FORMATTER);
    }
}
