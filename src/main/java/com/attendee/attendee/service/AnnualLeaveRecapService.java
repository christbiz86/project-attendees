package com.attendee.attendee.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendee.attendee.dao.AnnualLeaveRecapDao;
import com.attendee.attendee.model.AnnualLeaveRecap;

@Service
public class AnnualLeaveRecapService {
	@Autowired
	private AnnualLeaveRecapDao alrDao;
	
	public List<AnnualLeaveRecap> getAll(String company, Date startDate, Date endDate) {
		List<AnnualLeaveRecap> list = alrDao.getAllRecap(company, startDate, endDate);
		return list;
	}
}
