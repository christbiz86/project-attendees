package com.attendee.attendee.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendee.attendee.dao.TipeUserDao;
import com.attendee.attendee.model.TipeUser;

@Service
public class TipeUserService {
	@Autowired
	private TipeUserDao tuDao;
	
	public TipeUser findByTipe(String tipe) {
		return tuDao.findByTipe(tipe);
	}

}
