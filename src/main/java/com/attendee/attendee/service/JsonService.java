package com.attendee.attendee.service;

import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

@Service
public class JsonService {
	
	public void save() throws JsonIOException, IOException {
		Gson gson = new Gson();
		
		gson.toJson("123.45", new FileWriter("gson.json"));
		}
}