package com.attendee.attendee.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.attendee.attendee.model.TipeUser;

@Repository
public class TipeUserDao extends ParentDao {
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

}
