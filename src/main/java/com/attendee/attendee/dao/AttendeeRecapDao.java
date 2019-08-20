package com.attendee.attendee.dao;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.attendee.attendee.model.AttendeeRecap;

@Repository
public class AttendeeRecapDao extends ParentDao {
	@SuppressWarnings("unchecked")
	@Transactional
	public List<AttendeeRecap> getAllRecap(Date startDate, Date endDate) {
		List<AttendeeRecap> query = super.entityManager
				.createNativeQuery("SELECT * FROM attendee_recap(:start_date,:end_date)", AttendeeRecap.class)
				.setParameter("start_date", startDate)
				.setParameter("end_date", endDate)
				.getResultList();
		return query;
	}
}