package com.attendee.attendee.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendee.attendee.dao.AttendeeRecapDao;
import com.attendee.attendee.model.AttendeeRecap;

@Service
public class AttendeeRecapService {
	@Autowired
	private AttendeeRecapDao attendeeRecapDao;
	
	public List<AttendeeRecap> getAll(Date startDate, Date endDate) {
		List<AttendeeRecap> list = attendeeRecapDao.getAllRecap(startDate, endDate);
		return list;
	}
}