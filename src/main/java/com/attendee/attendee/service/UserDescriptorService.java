package com.attendee.attendee.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendee.attendee.dao.UserDescriptorDao;
import com.attendee.attendee.model.PojoUserDescriptor;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Service
public class UserDescriptorService {
	private Gson gson = new Gson();
	
	@Autowired
	private UserDescriptorDao usdDao;

	@Transactional
	public void addJson(PojoUserDescriptor userDescriptor) throws Exception {
		JsonObject inputObj  = gson.fromJson(gson.toJson(userDescriptor), JsonObject.class);
		usdDao.save(userDescriptor.getName(), inputObj);
	}
	
	public PojoUserDescriptor getDescriptor(String id) throws Exception {
		return usdDao.load(id);
	}
}
