package com.attendee.attendee.dao;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.attendee.attendee.model.AnnualLeaveRecap;

@Repository
public class AnnualLeaveRecapDao extends ParentDao {
	@SuppressWarnings("unchecked")
	@Transactional
	public List<AnnualLeaveRecap> getAllRecap(String company, Date startDate, Date endDate) {
		System.out.println(company);
		System.out.println(startDate);
		List<AnnualLeaveRecap> query = super.entityManager
				.createNativeQuery("SELECT * FROM annual_leave_recap(:company, :start_date, :end_date)", 
						AnnualLeaveRecap.class)
				.setParameter("company", company)
				.setParameter("start_date", startDate)
				.setParameter("end_date", endDate)
				.getResultList();
		return query;
	}
}
