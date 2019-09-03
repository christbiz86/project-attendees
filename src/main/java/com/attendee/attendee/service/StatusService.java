package com.attendee.attendee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendee.attendee.dao.StatusDao;
import com.attendee.attendee.model.Status;

@Service
public class StatusService {
	@Autowired
	private StatusDao stDao;
	
	public Status findByStatus(String status) {
		return stDao.findByStatus(status);
	}

}
