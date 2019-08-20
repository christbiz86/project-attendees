package com.attendee.attendee.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.attendee.attendee.model.AnnualLeave;

@Repository
public class AnnualLeaveDao extends ParentDao {
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<AnnualLeave> findAll() {
		List<AnnualLeave> list = super.entityManager
				.createQuery("FROM AnnualLeave")
				.getResultList();
		return list;
	}
}
