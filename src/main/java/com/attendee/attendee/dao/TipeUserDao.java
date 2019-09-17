package com.attendee.attendee.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.attendee.attendee.model.TipeUser;

@Repository
public class TipeUserDao extends ParentDao{
	@SuppressWarnings("unchecked")
	@Transactional
	public TipeUser findByTipe(String tipe) {
		List<TipeUser> tu = super.entityManager
				.createQuery("FROM TipeUser WHERE tipe = :tipe")
				.setParameter("tipe", tipe)
				.getResultList();
		
		if(tu.size() == 0) {
			return new TipeUser();
		}else {
			return (TipeUser)tu.get(0);
		}
	}
	
	@Transactional
	public void save(TipeUser tipeUser) {
		super.entityManager.merge(tipeUser);
	}
	
	@Transactional
	public void delete(UUID id) {
		TipeUser tipeUser = findById(id);
		super.entityManager.remove(tipeUser);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public TipeUser findById(UUID id) {	
		
		List<TipeUser> list = super.entityManager
                .createQuery("from TipeUser where id=:id")
                .setParameter("id", id)
                .getResultList();

		if (list.size() == 0) {
			return new TipeUser();
		}
		else {
			return (TipeUser)list.get(0);
		}
	}


	@SuppressWarnings("unchecked")
	@Transactional
	public List<TipeUser> findAll() {	
		
		List<TipeUser> list = super.entityManager
                .createQuery("from TipeUser ")
                .getResultList();

		return list;
	}

	
	@SuppressWarnings("unchecked")
	@Transactional
	public TipeUser findName(String tipe) {	
		
		List<TipeUser> list = super.entityManager
                .createQuery("from TipeUser where tipe=:tipe")
                .setParameter("tipe", tipe)
                .getResultList();

		if (list.size() == 0) {
			return new TipeUser();
		}
		else {
			return (TipeUser)list.get(0);
		}
	}
	
	public boolean isExist(UUID id) {
		
		if(findById(id).getId()==null) {
			return false;
		}else {
			return true;
		}
	}

	public boolean isBkExist(String tipe) {
		
		if(findName(tipe).getId()==null) {
	
			return false;
		}else {
			return true;
		}	 
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<TipeUser> findByFilter(TipeUser tipeUser) {
		
		StringBuilder sb=new StringBuilder("SELECT p.id,p.tipe ");
		sb.append("FROM tipe_user p ");
		sb.append(" WHERE 1=1 ");		

		if(tipeUser.getTipe()!=null) {
			sb.append(" AND p.tipe NOT LIKE '"+tipeUser.getTipe()+"' ");
		}
		
		List<TipeUser> list=super.entityManager.createNativeQuery(sb.toString(),TipeUser.class).getResultList();
		
		if(list.size()==0) {
			return new ArrayList<TipeUser>();
		}else {
			return list;
		}
	}
}
