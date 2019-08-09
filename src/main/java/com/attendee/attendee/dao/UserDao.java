package com.attendee.attendee.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.attendee.attendee.model.User;

@Repository
public class UserDao extends ParentDao{

	@Transactional
	public void save(User user) {
		super.entityManager.merge(user);
	}
	
	@Transactional
	public void delete(UUID id) {
		User user = findById(id);
		super.entityManager.remove(user);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public User findById(UUID id) {	
		
		List<User> list = super.entityManager
                .createQuery("from User where id=:id")
                .setParameter("id", id)
                .getResultList();

		if (list.size() == 0) {
			return new User();
		}
		else {
			return (User)list.get(0);
		}
	}


	@SuppressWarnings("unchecked")
	@Transactional
	public List<User> findAll() {	
		
		List<User> list = super.entityManager
                .createQuery("from User ")
                .getResultList();

		return list;
	}
	
	public boolean isExist(UUID id) {
		
		if(findById(id).getId()==null) {
			return false;
		}else {
			return true;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public User findByBk(String kode) {	
		
		List<User> list = super.entityManager
                .createQuery("from User where kode=:kode")
                .setParameter("kode", kode)
                .getResultList();

		if (list.size() == 0) {
			return new User();
		}
		else {
			return (User)list.get(0);
		}
	}

	public boolean isBkExist(String kode) {
		
		if(findByBk(kode).getId()==null) {
			
			return false;
		}else {
			return true;
		}	 
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<User> findByFilter(User user) {

		StringBuilder sb=new StringBuilder("SELECT u.id , u.kode , u.nama , u.alamat , u.tgl_lahir , u.telp , u.email , u.password , u.foto , u.id_status , u.created_at , u.updated_at , u.created_by , u.updated_by ");
		sb.append("FROM users u ");
		sb.append(" WHERE 1=1 ");		

		if(user.getNama()!=null) {
			sb.append(" AND u.nama LIKE '%"+user.getNama()+"%' ");
		}
		if(user.getAlamat()!=null) {
			sb.append(" AND u.alamat LIKE '%"+user.getAlamat()+"%' ");
		}
		if(user.getEmail()!=null) {
			sb.append(" AND u.email LIKE '%"+user.getEmail()+"%' ");
		}
		if(user.getTelp()!=null) {
			sb.append(" AND u.telp LIKE '%"+user.getTelp()+"%' ");
		}
		
		List<User> list=super.entityManager.createNativeQuery(sb.toString(),User.class).getResultList();
		
		if(list.size()==0) {
			return new ArrayList<User>();
		}else {
			return list;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public User userLogin(String email) {	
		
		List<User> list = super.entityManager
                .createQuery("from User where email=:email")
                .setParameter("email", email)
                .getResultList();

		if (list.size() == 0) {
			return new User();
		}
		else {
			return (User)list.get(0);
		}
	}

}
