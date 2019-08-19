package com.attendee.attendee.storage;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

import com.attendee.attendee.model.PojoUser;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ConverterJson implements Converter<String, PojoUser> {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public PojoUser convert(String source) {
        try {
			return objectMapper.readValue(source, PojoUser.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    }
}
