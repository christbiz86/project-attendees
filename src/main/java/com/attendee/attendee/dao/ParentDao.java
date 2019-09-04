package com.attendee.attendee.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public abstract class ParentDao {
	
	@PersistenceContext
	protected EntityManager entityManager;
}
