package com.attendee.attendee.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendee.attendee.dao.UserDao;
import com.attendee.attendee.exception.ValidationException;
import com.attendee.attendee.model.User;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	
	private Timestamp getTime() {
		return  new Timestamp(System.currentTimeMillis());
	}
	
	public void valIdNotNull(User user) throws ValidationException {
		if(user.getId()==null) {
			throw new ValidationException("Id tidak boleh kosong");
		}
	}
	
	public void valIdNotExist(UUID id) {
		if(userDao.isIdExist(id)) {
			System.out.println("Data sudah ada!");
		}
	}
	
	public void valIdExist(UUID id) {
		if(!userDao.isIdExist(id)) {
			System.out.println("Data tidak ada!");
		}
	}
	
	public void valBkNotNull(User user) throws ValidationException {
		if(user.getKode()==null) {
			throw new ValidationException("Kode tidak boleh kosong");
		}
	}
	
	public void valBkNotExist(User user) throws ValidationException {
		if(userDao.isBkExist(user.getKode())) {
			throw new ValidationException("Data sudah ada");
		}
	}
	
	public void valBkNotChange(User user) throws ValidationException {
		String s = findById(user.getId()).getKode();
		if(!user.getKode().toString().equals(s.toString())) {
			throw new ValidationException("Kode tidak boleh berubah");
		}
	}
	
	private void valNonBk(User user)throws ValidationException{
		
		StringBuilder sb=new StringBuilder();
		int error=0;

		if(user.getAlamat()==null) {
			sb.append("alamat tidak boleh kosong \n");
			error++;
		}
		if(user.getTglLahir()==null) {
			sb.append("tanggal lahir tidak boleh kosong \n");
			error++;
		}
		if(user.getTelp()==null) {
			sb.append("telepon tidak boleh kosong \n");
			error++;
		}
//		if(user.getFoto()==null) {
//			sb.append("foto tidak boleh kosong \n");
//			error++;
//		}
		if(user.getNama()==null) {
			sb.append("nama tidak boleh kosong \n");
			error++;
		}
//		if(user.getPassword()==null) {
//			sb.append("password tidak boleh kosong \n");
//			error++;
//		}
//		if(user.getCreatedAt()==null) {
//			sb.append("tanggal dibuat tidak boleh kosong \n");
//			error++;
//		}
//		if(user.getCreatedBy()==null) {
//			sb.append("pembuat tidak boleh kosong \n");
//			error++;
//		}
//		if(user.getUpdatedAt()==null) {
//			sb.append("tanggal diedit tidak boleh kosong \n");
//			error++;
//		}
//		if(user.getUpdatedBy()==null) {
//			sb.append("pengedit tidak boleh kosong \n");
//			error++;
//		}
		
		if(error>0) {
			throw new ValidationException(sb.toString());
		}
	}

	private User findById(UUID id) {
		return userDao.findById(id);
	}
	
	private User findByBk(String kode) {
		return userDao.findByBk(kode);
	}
	
	public List<User> findByFilter(User user) {
		return userDao.findByFilter(user);
	}
	
	public List<User> findAll() {
		return userDao.findAll();
	}

	public void save(User user) throws ValidationException{
		user.setCreatedAt(getTime());
		valBkNotNull(user);
		valBkNotExist(user);
		valNonBk(user);
		userDao.save(user);
	}
	
	public void update(User user)throws ValidationException{
		user.setUpdatedAt(getTime());
		valIdNotNull(user);
		valIdExist(user.getId());
		valBkNotNull(user);
		valBkNotChange(user);
		valNonBk(user);
		userDao.save(user);
	}
	
	

}
