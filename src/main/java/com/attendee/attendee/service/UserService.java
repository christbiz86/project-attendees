package com.attendee.attendee.service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.attendee.attendee.dao.StatusDao;
import com.attendee.attendee.dao.UserDao;
import com.attendee.attendee.exception.ValidationException;
import com.attendee.attendee.model.User;
import com.attendee.attendee.model.UserPrinciple;

@Service
public class UserService{
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private StatusDao staDao;
	
	public String kodeUser() {
		return "USER"+userDao.countRows();
	}
	
	public User findByName(String nama) {
		return userDao.findByName(nama);
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
	    
	    String pwd = (email.substring(0,3).toLowerCase()+pw).toString();
	    System.out.println(pwd);
		
		//MD5
//		MessageDigest alg = MessageDigest.getInstance("MD5");
//        alg.reset(); 
//        alg.update(pwd.getBytes());
//        byte[] digest = alg.digest();
//
//        StringBuffer hashedpwd = new StringBuffer();
//        String hx;
//        for (int i=0;i<digest.length;i++){
//            hx =  Integer.toHexString(0xFF & digest[i]);
//            //0x03 is equal to 0x3, but we need 0x03 for our md5sum
//            if(hx.length() == 1){hx = "0" + hx;}
//            hashedpwd.append(hx);
//        }
        
        return pwd;
	}
	
	private void valIdExist(UUID id)throws ValidationException{
		if(!userDao.isExist(id)) {
			throw new ValidationException("Data tidak ada");
		}
	}
	
	private void valIdNotNull(User user)throws ValidationException {
		if(user.getId()==null) {
			throw new ValidationException("Id tidak boleh kosong");
		}
	}
	
	private void valNonBk(User user)throws ValidationException{
		StringBuilder sb=new StringBuilder();
		int error=0;

		if(user.getNama() == null) {
			sb.append("Nama tidak boleh kosong!");
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
		
		if(error>0) {
			throw new ValidationException(sb.toString());
		}
	}
	
	private void valBkNotExist(User user)throws ValidationException{
		if(userDao.isBkExist(user.getKode())) {
			throw new ValidationException("Data sudah ada");
		}
	}	
	
	private void valBkNotChange(User user)throws ValidationException{
		String s=findById(user.getId()).getKode();
		if(!user.getKode().toString().equals(s.toString())) {
			throw new ValidationException("Kode tidak boleh berubah");
		}
	}
	
	private void valBkNotNull(User user) throws ValidationException{
		if(user.getKode()==null) {
			throw new ValidationException("Kode tidak boleh kosong");
		}
	}
	
	private void valCreatedNotChange(User user)throws ValidationException {
		User tempUser=findById(user.getId());
					System.out.println(tempUser.getCreatedAt());
					System.out.println(user.getCreatedAt());
		if(tempUser.getCreatedAt()!=user.getCreatedAt() && tempUser.getCreatedBy().getId()!=user.getCreatedBy().getId()) {
			throw new ValidationException("created tidak boleh berubah");
		}
	}
	
	public void save(User user)throws ValidationException, NoSuchAlgorithmException{
		user.setCreatedAt(getTime());
		user.setPassword(encoder.encode(user.getPassword()));
		user.setKode(kodeUser());
		user.setIdStatus(staDao.findByStatus("Active"));		
		user.setUpdatedAt(null);
		user.setUpdatedBy(null);
		
		valEmailNotExist(user);
		valBkNotNull(user);
		valBkNotExist(user);
		valNonBk(user);

		userDao.save(user);
	}
	
	public void update(User user)throws ValidationException{
		User tempUser=findById(user.getId());
		user.setCreatedAt(tempUser.getCreatedAt());
		user.setCreatedBy(tempUser.getCreatedBy());
		user.setUpdatedAt(getTime());
		valCreatedNotChange(user);
		
		valIdNotNull(user);
		valIdExist(user.getId());
		valBkNotNull(user);
		valBkNotChange(user);
		valNonBk(user);
		userDao.save(user);
	}
	
	public void delete(User user)throws ValidationException{
		valIdExist(user.getId());
		user.setUpdatedAt(getTime());
		user.setUpdatedBy(((UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
		user.setIdStatus(staDao.findByStatus("Inactive"));
		
		userDao.save(user);
	}
	
	public User findById(UUID id)throws ValidationException{
		System.out.println("get id");
		return userDao.findById(id);
	}
	
	public User findByBk(User user) {
		return userDao.findByBk(user.getKode());
	}
	
	public List<User> findAll( )throws ValidationException{
		return userDao.findAll();
	}
	
	public List<User> findByFilter(User user )throws ValidationException{
		return userDao.findByFilter(user);
	}
	
	private Timestamp getTime() {
		return  new Timestamp(System.currentTimeMillis());
	}

	public User findUsername(String username) {
		return userDao.userLogin(username);
	}

	private void valEmailNotExist(User user) throws ValidationException{
		if(userDao.findByEmail(user).getId()!=null) {
			System.out.println("email sudah digunakan");
			throw new ValidationException("Email sudah digunakan");
		}
	}

}
