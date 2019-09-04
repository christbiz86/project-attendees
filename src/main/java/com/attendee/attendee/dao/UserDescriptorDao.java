package com.attendee.attendee.dao;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.attendee.attendee.model.UserDescriptorPojo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Repository
public class UserDescriptorDao extends ParentDao {
	private Gson gson = new Gson();
	
	public void save(String id, JsonObject json) {
		String query = "INSERT INTO users_descriptors(user_id, descriptors) VALUES('"+id+"','"+json.toString()+"')";
		super.entityManager.createNativeQuery(query)
		.executeUpdate();
	}
	
	@Transactional
	public UserDescriptorPojo load(String id) throws JsonProcessingException {
		String query = "SELECT descriptors FROM users_descriptors WHERE user_id LIKE '"+id+"'";
		String json = super.entityManager.createNativeQuery(query).getSingleResult().toString();
		UserDescriptorPojo output = gson.fromJson(json, UserDescriptorPojo.class);
		return output;
	}
}
