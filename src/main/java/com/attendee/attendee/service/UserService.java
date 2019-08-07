package com.attendee.attendee.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.List;
import java.util.Random;
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
	
	//buat nyoba doang
	public User findPassword(String pass) throws NoSuchAlgorithmException {
		return userDao.findPassword(pass);
	}
	
	private Timestamp getTime() {
		return new Timestamp(System.currentTimeMillis());
	}
	
	public void valIdNotNull(User user) throws ValidationException {
		if(user.getId()==null) {
			throw new ValidationException("ID tidak boleh kosong!");
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
			throw new ValidationException("Kode tidak boleh kosong!");
		}
	}
	
	public void valBkNotExist(User user) throws ValidationException {
		if(userDao.isBkExist(user.getKode())) {
			throw new ValidationException("Kode sudah ada!");
		}
	}
	
	public void valBkNotChange(User user) throws ValidationException {
		String s = findById(user.getId()).getKode();
		if(!user.getKode().toString().equals(s.toString())) {
			throw new ValidationException("Kode tidak boleh berubah!");
		}
	}
	
	private void valNonBk(User user)throws ValidationException{
		StringBuilder sb=new StringBuilder();
		int error=0;
				
		if(user.getNama() == null) {
			sb.append("Alamat tidak boleh kosong!");
			error++;
		}
		if(user.getAlamat() == null) {
			sb.append("Alamat tidak boleh kosong!");
			error++;
		}
		if(user.getTglLahir() == null) {
			sb.append("Tanggal lahir tidak boleh kosong!");
			error++;
		}
		if(user.getTelp() == null) {
			sb.append("Nomor telepon tidak boleh kosong!");
			error++;
		}if(user.getEmail() == null) {
			sb.append("Email tidak boleh kosong!");
			error++;
		}
//		if(user.getFoto()==null) {
//			sb.append("foto tidak boleh kosong \n");
//			error++;
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
	
	public String generatePassword(User user) throws NoSuchAlgorithmException {
		String email = user.getEmail();
		
		Random RANDOM = new SecureRandom();
	    String letters = "1234567890";
	    String pw = "";
	    for (int i=0; i<5; i++) {
	        int index = (int)(RANDOM.nextDouble()*letters.length());
	        pw += letters.substring(index, index+1);
	    }
	    
	    String pwd = email.substring(0,3).toLowerCase()+pw;
//	    System.out.println(pwd);
		
		//MD5
		MessageDigest alg = MessageDigest.getInstance("MD5");
        alg.reset(); 
        alg.update(pwd.getBytes());
        byte[] digest = alg.digest();

        StringBuffer hashedpwd = new StringBuffer();
        String hx;
        for (int i=0;i<digest.length;i++){
            hx =  Integer.toHexString(0xFF & digest[i]);
            //0x03 is equal to 0x3, but we need 0x03 for our md5sum
            if(hx.length() == 1){hx = "0" + hx;}
            hashedpwd.append(hx);
        }
//        System.out.println(hashedpwd.toString());
        
        return hashedpwd.toString();
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

	public void save(User user) throws ValidationException, NoSuchAlgorithmException{
		user.setCreatedAt(getTime());
		valBkNotNull(user);
		valBkNotExist(user);
		valNonBk(user);
		user.setPassword(generatePassword(user));
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
