package com.attendee.attendee.dao;

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

//	@SuppressWarnings("unchecked")
//	@Transactional
//	public List<User> findByFilter(String pasien) {	
//		
//		StringBuilder sb=new StringBuilder("SELECT p.id, p.kode , p.id_pemeriksaan ");
//		sb.append(" FROM resep p ");
//		sb.append(" JOIN pemeriksaan n ON p.id_pemeriksaan=n.id ");
//		sb.append(" JOIN registrasi r ON n.id_registrasi=r.id ");
//		sb.append(" JOIN pasien ps ON r.id_pasien=ps.id ");
//		sb.append(" WHERE 1=1 ");		
//			
//		if(!pasien.equals("null")) {
//			sb.append(" AND ps.nama LIKE '%"+pasien+"%' ");
//			System.out.println("not null nama");
//		}
//			
//		List<Resep> list=super.entityManager.createNativeQuery(sb.toString(),Resep.class).getResultList();
//		
//		if(list.size()==0) {
//			return new ArrayList<Resep>();
//		}else {
//			return list;
//		}
//	}
	
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
	
}
