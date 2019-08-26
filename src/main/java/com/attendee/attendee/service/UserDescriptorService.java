package com.attendee.attendee.service;

import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Paths;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.attendee.attendee.model.UserDescriptor;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
public class UserDescriptorService {
	private JsonParser parser = new JsonParser();
	private JsonObject jsonObject = new JsonObject();
	private JsonArray jsonArray = new JsonArray();
	private Gson gson = new Gson();
	
	@Transactional
	public void add(UserDescriptor userDescriptor) throws Exception {
		//load
		Object obj = parser.parse(new FileReader(Paths.get("upload-dir").resolve("Employee.json").toString()));
		jsonArray = (JsonArray) obj;
		
		JsonObject inputObj  = gson.fromJson(gson.toJson(userDescriptor), JsonObject.class);
		jsonArray.add(inputObj);
		
		FileWriter writer = new FileWriter(Paths.get("upload-dir").resolve("Employee.json").toString(),false);
		writer.write(jsonArray.toString());
		writer.close();
	}
	
	public UserDescriptor getDescriptor(UserDescriptor userDescriptor) throws Exception {
		//load
		Object obj = parser.parse(new FileReader(Paths.get("upload-dir").resolve("Employee.json").toString()));
		jsonArray = (JsonArray) obj;
		
		for (JsonElement jsonElement : jsonArray) {
			System.out.println(jsonElement.toString());
			if(jsonElement.getAsJsonObject().get("name").toString().contains(userDescriptor.getName())) {
				jsonObject = (JsonObject) jsonElement;
				break;
			}
		}
		UserDescriptor output = gson.fromJson(jsonObject, UserDescriptor.class);
		return output;
	}
}