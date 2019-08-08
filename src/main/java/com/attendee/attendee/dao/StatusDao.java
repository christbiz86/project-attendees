package com.attendee.attendee.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.attendee.attendee.model.Status;

@Repository
public class StatusDao extends ParentDao {
	@SuppressWarnings("unchecked")
	@Transactional
	public Status findByStatus(String status) {
		List<Status> st = super.entityManager
				.createQuery("FROM Status WHERE status = :status")
				.setParameter("status", status)
				.getResultList();
		
		if (st.size() == 0) {
			return new Status();
		}
		else {
			return (Status)st.get(0);
		}
	}

}
