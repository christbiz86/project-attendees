package com.attendee.attendee.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendee.attendee.dao.TipeUserDao;
import com.attendee.attendee.exception.ValidationException;
import com.attendee.attendee.model.TipeUser;

@Service
public class TipeUserService {
	@Autowired
	private TipeUserDao tuDao;
	
	public TipeUser findByTipe(String tipe) {
		return tuDao.findByTipe(tipe);
	}
	
	public void valIdExist(UUID id)throws ValidationException{
		if(!tuDao.isExist(id)) {
			throw new ValidationException("Data tidak ada");
		}
	}
	
	public void valIdNotNull(TipeUser tipeUser)throws ValidationException {
		if(tipeUser.getId()==null) {
			throw new ValidationException("Id tidak boleh kosong");
		}
	}

	public void valBkNotExist(TipeUser tipeUser)throws ValidationException{
		if(tuDao.isBkExist(tipeUser.getTipe())) {
			throw new ValidationException("Data sudah ada");
		}
	}	

	public void valBkNotNull(TipeUser tipeUser) throws ValidationException{
		if(tipeUser.getTipe()==null) {

			throw new ValidationException("tipe tidak boleh kosong");
		}
	}

	public TipeUser findName(String tipeUser) throws ValidationException{
		return tuDao.findName(tipeUser);
	}

	public List<TipeUser> findAll() throws ValidationException{
		return tuDao.findAll();
	}
	
	public List<TipeUser> findByFilter(TipeUser tipeUser) throws ValidationException{
		return tuDao.findByFilter(tipeUser);
	}
	
	public void save(TipeUser tipeUser) throws ValidationException{
		valBkNotNull(tipeUser);
		valBkNotExist(tipeUser);
		tuDao.save(tipeUser);
	}
	
	public void update(TipeUser tipeUser) throws ValidationException{
		valIdNotNull(tipeUser);
		valIdExist(tipeUser.getId());
		valBkNotNull(tipeUser);
		valBkNotExist(tipeUser);
		tuDao.save(tipeUser);
	}
	
	public void update(UUID id) throws ValidationException{
		valIdExist(id);
		tuDao.delete(id);
	}
	public TipeUser findById(UUID id)throws ValidationException{
		return tuDao.findById(id);
	}
}