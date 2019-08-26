package com.attendee.attendee.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.attendee.attendee.dao.NotificationDao;
import com.attendee.attendee.exception.ValidationException;
import com.attendee.attendee.model.Notification;

@Service
public class NotificationService {
	
	@Autowired
	private NotificationDao notifDao;
	
	private void valIdExist(UUID id)throws ValidationException {
		if(!notifDao.isIdExist(id)) {
			throw new ValidationException("Data tidak ada");
		}
	}
	
	private void valIdNotNull(Notification notification)throws ValidationException {
		if(notification.getId()==null) {
			throw new ValidationException("Id tidak boleh kosong");
		}
	}
	
	private void valNonBk(Notification notification)throws ValidationException {
		StringBuilder sb=new StringBuilder();
		int error=0;

		if(notification.getStatus()==null) {
			sb.append("tipe user tidak boleh kosong \n");
			error++;
		}
		
		if(error>0) {
			throw new ValidationException(sb.toString());
		}
	}
	
	private void valBkNotNull(Notification notification) throws ValidationException {
		if(notification.getRequest().getId()==null) {
			throw new ValidationException("request tidak boleh kosong");
		}
	}
	
	private void valBkNotExist(Notification notification)throws ValidationException {
		if(notifDao.isBkExist(notification.getRequest().getId())) {
			throw new ValidationException("Data sudah ada");
		}
	}
	
	private void valBkNotChange(Notification notification)throws ValidationException{
		String s = findById(notification.getId()).getRequest().getId().toString();
		if(!notification.getRequest().getId().toString().equals(s.toString())) {
			throw new ValidationException("request tidak boleh berubah");
		}
	}
	
	@Transactional
	public void insert(Notification notification)throws ValidationException {
		valBkNotNull(notification);
		valBkNotExist(notification);
		valNonBk(notification);
		notifDao.save(notification);
	}
	
	@Transactional
	public void update(Notification notification)throws ValidationException {
		valIdNotNull(notification);
		valIdExist(notification.getId());
		valBkNotNull(notification);
		valBkNotChange(notification);
		valNonBk(notification);
		notifDao.save(notification);
	}
	
	public Notification findById(UUID id)throws ValidationException{
		return notifDao.findById(id);
	}
	
	public List<Notification> findAll()throws ValidationException {
		return notifDao.findAll();
	}
	
	public List<Notification> findByFilter(Notification notification )throws ValidationException{
		return notifDao.findByFilter(notification);
	}
}
