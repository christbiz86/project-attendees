package com.attendee.attendee.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.attendee.attendee.model.AttendeeRecap;

@Repository
public class AttendeeRecapDao extends ParentDao {
	@SuppressWarnings("unchecked")
	@Transactional
	public List<AttendeeRecap> getAllRecap(String company, Date startDate, Date endDate) {
		List<AttendeeRecap> list = super.entityManager
				.createNativeQuery("SELECT * FROM attendee_recap(:company, :startDate, :endDate)", AttendeeRecap.class)
				.setParameter("company", company)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.getResultList();
		
		return list;
	}
}
