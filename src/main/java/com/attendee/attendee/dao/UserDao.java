package com.attendee.attendee.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.attendee.attendee.model.User;

@Repository
public class UserDao extends ParentDao{
	@SuppressWarnings("unchecked")
	@Transactional
	public User findById(UUID id) {
		List<User> list = super.entityManager
                .createQuery("from User where id = :id")
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
	public User findByBk(String kode) {
		List<User> list = super.entityManager
                .createQuery("from User where kode = :kode")
                .setParameter("kode", kode)
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
	public List<User> findByFilter(User user) {
		StringBuilder sb = new StringBuilder("SELECT u.id, u.nama, u.alamat, u.email, u.telp");
		sb.append("FROM users ");
		sb.append("WHERE 1=1 ");
		
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
		
		List<User> list = super.entityManager
				.createNativeQuery(sb.toString(), User.class)
				.getResultList();
		
		if (list.size() == 0) {
			return new ArrayList<User>();
		}
		else {
			return list;
		}
	}
	
	public boolean isIdExist(UUID id) {
		if(findById(id).getId() == null) {
			return false;
		}else {
			return true;
		}
	}

	public boolean isBkExist(String kode) {
		if(findByBk(kode).getId() == null) {
			return false;
		}else {
			return true;
		}	 
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<User> findAll() {	
		List<User> list = super.entityManager
                .createQuery("from User")
                .getResultList();
		return list;
	}

	@Transactional
	public void save(User user) {
		super.entityManager.merge(user);
	}
	
}
