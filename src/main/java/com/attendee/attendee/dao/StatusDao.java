package com.attendee.attendee.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.attendee.attendee.model.Status;

@Repository
public class StatusDao extends ParentDao {
	@SuppressWarnings("unchecked")
	@Transactional
	public Status findByStatus(String status) {
		List<Status> list = super.entityManager
                .createQuery("from Status where status=:status")
                .setParameter("status", status)
                .getResultList();

		if (list.size() == 0) {
			return new Status();
		}
		else {
			return (Status)list.get(0);
		}
	}
}
