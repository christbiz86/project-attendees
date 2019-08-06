package com.attendee.attendee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendee.attendee.dao.CompanyDao;

@Service
public class CompanyService {
	@Autowired
	private CompanyDao companyDao;

}
