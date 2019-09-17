package com.attendee.attendee.service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.OSSClient;

@Service
public class CloudService {
	private final String BUCKET_NAME = "attendee-storage";
	private final String endpoint = "oss-ap-southeast-5.aliyuncs.com";
	private final String accessKeyId = "*********";
	private final String accessKeySecret = "************";
	private OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
	
	@Transactional
	public void save(MultipartFile file, String filename) throws Exception {
		ossClient.putObject(BUCKET_NAME, filename,
				file.getInputStream());
	}
	
	@Transactional
	public byte[] load(String filename) throws Exception {
		InputStream is = ossClient.getObject(BUCKET_NAME, filename).getObjectContent();
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	    int nRead;
	    byte[] data = new byte[1024];
	    while ((nRead = is.read(data, 0, data.length)) != -1) {
	        buffer.write(data, 0, nRead);
	    }
	 
	    buffer.flush();
	    byte[] byteArray = buffer.toByteArray();
		return byteArray;
	}
	
	@Transactional
	public InputStream loadReport(String filename) throws Exception {
		return ossClient.getObject(BUCKET_NAME, filename).getObjectContent();
	}
}
