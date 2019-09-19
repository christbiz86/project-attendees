package com.attendee.attendee.dao;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.attendee.attendee.model.AttendeeRecapDetail;

@Repository
public class AttendeeRecapDetailDao extends ParentDao {
	@SuppressWarnings("unchecked")
	public List<AttendeeRecapDetail> getAll(String company, Date startDate, Date endDate) {
		List<AttendeeRecapDetail> list = super.entityManager
				.createNativeQuery("SELECT * FROM attendee_recap_detail(:company, :startDate, :endDate)", AttendeeRecapDetail.class)
				.setParameter("company", company)
				.setParameter("startDate", startDate)
				.setParameter("endDate", endDate)
				.getResultList();
		return list;
	}
}
