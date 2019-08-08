package com.attendee.attendee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendee.attendee.dao.CompanyUnitPosisiDao;
import com.attendee.attendee.model.CompanyUnitPosisi;

@Service
public class CompanyUnitPosisiService {
	@Autowired
	private CompanyUnitPosisiDao cupDao;
	
	public void insert(CompanyUnitPosisi cup) {
		cupDao.save(cup);
	}
	
	public void update(CompanyUnitPosisi cup) {
		cupDao.save(cup);
	}

}
