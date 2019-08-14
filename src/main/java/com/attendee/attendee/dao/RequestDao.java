package com.attendee.attendee.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.attendee.attendee.model.Request;

@Repository
public class RequestDao extends ParentDao {
	
	public void save(Request request) {
		super.entityManager.merge(request);
	}
	
	public void delete(Request request) {
		super.entityManager.remove(request);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Request findById(UUID id) {
		List<Request> list = super.entityManager
				.createQuery("FROM Request WHERE id=:id")
				.setParameter("id", id)
				.getResultList();
		if(list.size() == 0) {
			return new Request();
		} else {
			return list.get(0);
		}
	}
	
	@Transactional
	@SuppressWarnings("unchecked")
	public List<Request> findAll() {
		List<Request> list = super.entityManager
                .createQuery(" from Request ")
                .getResultList();
		if (list.size() == 0)
			return new ArrayList<Request>();
		else {
			return list;
		}
	}
	
	@Transactional
	public String countRows() {
		BigInteger count = (BigInteger) super.entityManager
				.createNativeQuery("SELECT count(*) FROM users").getSingleResult();
		
		int next = count.intValue() + 1;
		String num = Integer.toString(next);
		
		if(num.length() == 1) {
			return "00"+num;
		} else if(num.length() == 2) {
			return "0"+num;
		} else {
			return num;
		}
	}
	
	@Transactional
	@SuppressWarnings("unchecked")
	public List<Request> findByStatus(String status) {
		List<Request> list = super.entityManager
                .createQuery("from Request where status.status=:status")
                .setParameter("status", status)
                .getResultList();
		if (list.size() == 0)
			return new ArrayList<Request>();
		else {
			return list;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Request findByBk(String kode) {	
		
		List<Request> list = super.entityManager
                .createQuery("from Request where kode=:kode")
                .setParameter("kode", kode)
                .getResultList();

		if (list.size() == 0) {
			return new Request();
		}
		else {
			return (Request)list.get(0);
		}
	}
	
	public boolean isIdExist(UUID id) {
		if(findById(id).getId().equals(null)) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean isBkExist(String kode) {
		if(findByBk(kode).getId() == null) {
			return false;
		} else {
			return true;
		}
	}
}
