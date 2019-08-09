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
import com.attendee.attendee.model.CompanyUnitPosisi;
import com.attendee.attendee.model.PojoUser;
import com.attendee.attendee.model.TipeUser;
import com.attendee.attendee.model.User;
import com.attendee.attendee.model.UserCompany;

@Service
public class UserService{
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserCompanyService ucService;
	
	@Autowired
	private TipeUserService tuService;
	
	@Autowired
	private CompanyService comService;
	
	@Autowired
	private UnitService unitService;
	
	@Autowired
	private PosisiService posisiService;
	
	@Autowired
	private CompanyUnitPosisiService cupService;
		
	private void valIdExist(UUID id) throws ValidationException{
		if(!userDao.isExist(id)) {
			throw new ValidationException("Data tidak ada!");
		}
	}
	
	private void valIdNotNull(User user) throws ValidationException {
		if(user.getId()==null) {
			throw new ValidationException("ID tidak boleh kosong!");
		}
	}
	
	private void valNonBk(User user) throws ValidationException{
		StringBuilder sb=new StringBuilder();
		int error=0;

		if(user.getNama() == null) {
			sb.append("Nama tidak boleh kosong!");
			error++;
		}
		if(user.getAlamat()==null) {
			sb.append("Alamat tidak boleh kosong! \n");
			error++;
		}
		if(user.getTglLahir()==null) {
			sb.append("Tanggal lahir tidak boleh kosong! \n");
			error++;
		}
		if(user.getTelp()==null) {
			sb.append("Nomor telepon tidak boleh kosong! \n");
			error++;
		}
		if(user.getFoto()==null) {
			sb.append("foto tidak boleh kosong! \n");
			error++;
		}
		if(user.getNama()==null) {
			sb.append("Nama tidak boleh kosong! \n");
			error++;
		}
		if(user.getPassword()==null) {
			sb.append("Password tidak boleh kosong! \n");
			error++;
		}
		
		if(error>0) {
			throw new ValidationException(sb.toString());
		}
	}
	
	private void valBkNotExist(User user) throws ValidationException{
		if(userDao.isBkExist(user.getKode())) {
			throw new ValidationException("Kode sudah ada!");
		}
	}	
	
	private void valBkNotChange(User user) throws ValidationException{
		String s=findById(user.getId()).getKode();
		if(!user.getKode().toString().equals(s.toString())) {
			throw new ValidationException("Kode tidak boleh berubah!");
		}
	}
	
	private void valBkNotNull(User user) throws ValidationException{
		if(user.getKode()==null) {
			throw new ValidationException("Kode tidak boleh kosong!");
		}
	}
	
	private void valCreatedNotChange(User user) throws ValidationException {
		User tempUser=findById(user.getId());
			
		if(tempUser.getCreatedAt()!=user.getCreatedAt() && tempUser.getCreatedBy()!=user.getCreatedBy()) {
			throw new ValidationException("created tidak boleh berubah");
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
	    System.out.println(pwd);
		
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
        
        return hashedpwd.toString();
	}
	
	public void save(User user) throws ValidationException{
		user.setCreatedAt(getTime());
		user.setPassword(generatePassword(user));
		
		user.setUpdatedAt(null);
		user.setUpdatedBy(null);
		
		valBkNotNull(user);
		valBkNotExist(user);
		valNonBk(user);
		userDao.save(user);
	}
	
	public void update(User user) throws ValidationException{
		user.setUpdatedAt(getTime());
		valCreatedNotChange(user);
		
		valIdNotNull(user);
		valIdExist(user.getId());
		valBkNotNull(user);
		valBkNotChange(user);
		valNonBk(user);
		userDao.save(user);
	}
	
	public User findById(UUID id) throws ValidationException{

		return userDao.findById(id);
	}
	
	public User findByBk(User user) {

		return userDao.findByBk(user.getKode());
	}
	
	public List<User> findAll() throws ValidationException{
		return userDao.findAll();
	}
	
	public List<User> findByFilter(User user) throws ValidationException{
		return userDao.findByFilter(user);
	}
	
	private Timestamp getTime() {
		return new Timestamp(System.currentTimeMillis());
	}

	public User findUsername(String username) {
		return userDao.userLogin(username);
	}

	public void saveWithTipeUser(User signUpRequest) throws ValidationException{
		try {
			save(signUpRequest);
	        UserCompany userCompany=new UserCompany();
	        TipeUser tu=tuService.findName("ROLE_SUPERADMIN");
	        userCompany.setIdUser(findByBk(signUpRequest));
	        userCompany.setIdTipeUser(tu);
	        ucService.save(userCompany);
	      
		}catch(Exception e) {
			delete(findByBk(signUpRequest).getId());
			throw new ValidationException("error");
		}
        		
	}

	public void saveWithCompanyUnitPosisi(PojoUser user) throws ValidationException {
		try {
			save(user.getUser());
			
			UserCompany userCompany = new UserCompany();
	        CompanyUnitPosisi companyunitPosisi=new CompanyUnitPosisi();
	        
	        companyunitPosisi.setIdCompany(comService.findById(user.getCompany().getId()));
	        companyunitPosisi.setIdPosisi(posisiService.findById(user.getPosisi().getId()));
	        companyunitPosisi.setIdUnit(unitService.findById(user.getUnit().getId()));
	        
	        cupService.save(companyunitPosisi);
	 
	        userCompany.setIdUser(findByBk(user.getUser()));
	        userCompany.setIdTipeUser(user.getTipeUser());
	        userCompany.setIdCompanyunitPosisi(cupService.findByBk(companyunitPosisi.getIdCompany().getId(),companyunitPosisi.getIdUnit().getId(),companyunitPosisi.getIdPosisi().getId()));
	        
	        ucService.save(userCompany);
		}catch (Exception e) {
			System.out.println(e);
			delete(findByBk(user.getUser()).getId());
			throw new ValidationException("error");
		}
	}

}
