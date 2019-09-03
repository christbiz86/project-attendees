package com.attendee.attendee.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSSClient;
import com.attendee.attendee.dao.UserDescriptorDao;
import com.attendee.attendee.model.UserDescriptorPojo;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@Service
public class UserDescriptorService {
//	private JsonObject jsonObject = new JsonObject();
	private Gson gson = new Gson();
	
	private final String BUCKET_NAME = "attendee-storage";
	String endpoint = "oss-ap-southeast-5.aliyuncs.com";
	String accessKeyId = "LTAIgIip5nffvLF5";
	String accessKeySecret = "HhLtxTftFss1tEevTDMQRmbxyFwJue";
	
	private OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
	
	@Autowired
	private UserDescriptorDao usdDao;

	@Transactional
	public void addJson(UserDescriptorPojo userDescriptor) throws Exception {
		JsonObject inputObj  = gson.fromJson(gson.toJson(userDescriptor), JsonObject.class);
		usdDao.save(userDescriptor.getName(), inputObj);
	}
	
	@Transactional
	public void addImage(MultipartFile file, String id) throws Exception {
		ossClient.putObject(BUCKET_NAME, id+".jpg",
				file.getInputStream());
	}
	
	public UserDescriptorPojo getDescriptor(String id) throws Exception {
		return usdDao.load(id);
	}
}
