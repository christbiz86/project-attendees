package com.attendee.attendee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendee.attendee.dao.AnnualLeaveDao;
import com.attendee.attendee.model.AnnualLeave;

@Service
public class AnnualLeaveService {
	@Autowired
	private AnnualLeaveDao anLevDao;
	
	public List<AnnualLeave> findAll() {
		return anLevDao.findAll();
	}
}
