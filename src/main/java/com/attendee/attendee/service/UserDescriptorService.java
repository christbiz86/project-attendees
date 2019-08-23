package com.attendee.attendee.service;

import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Paths;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.attendee.attendee.model.UserDescriptor;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class UserDescriptorService {
	@Transactional
	public void add(UserDescriptor userDescriptor) throws Exception {
		JsonParser parser = new JsonParser();  
		Gson gson = new Gson();
		JsonArray jsonObject = new JsonArray();
		
		//load
		Object obj = parser.parse(new FileReader(Paths.get("upload-dir").resolve("Employee.json").toString()));
		jsonObject = (JsonArray) obj;
		
		JsonObject inputObj  = gson.fromJson(gson.toJson(userDescriptor), JsonObject.class);
		jsonObject.add(inputObj);
		
		FileWriter writer = new FileWriter(Paths.get("upload-dir").resolve("Employee.json").toString(),false);
		writer.write(jsonObject.toString());
		writer.close();
	}
}
