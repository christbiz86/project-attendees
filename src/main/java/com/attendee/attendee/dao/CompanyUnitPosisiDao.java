package com.attendee.attendee.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.attendee.attendee.model.CompanyUnitPosisi;

@Repository
public class CompanyUnitPosisiDao extends ParentDao {
	@Transactional
	public void save(CompanyUnitPosisi cup) {
		super.entityManager.merge(cup);
	}

}
