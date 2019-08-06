package com.attendee.attendee.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.attendee.attendee.model.Project;


@Repository
public class ProjectDao extends ParentDao {
	@Transactional
	public void save(Project project) {
		super.entityManager.merge(project);
	}
	
	@Transactional
	public void delete(UUID id) {
		Project project= findById(id);
		super.entityManager.remove(project);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Project findById(UUID id) {	
		
		List<Project> list = super.entityManager
                .createQuery("FROM Project WHERE id=:id")
                .setParameter("id", id)
                .getResultList();

		if (list.size() == 0) {
			return new Project();
		}
		else {
			return (Project)list.get(0);
		}
	}


	@SuppressWarnings("unchecked")
	@Transactional
	public List<Project> findAll() {	
		
		List<Project> list = super.entityManager
                .createQuery("FROM Project ")
                .getResultList();

		return list;
	}
	
	public boolean isExist(UUID id) {
		
		if(findById(id).getId()==null) {
			return false;
		}else {
			return true;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Project findByBk(String kode) {	
		
		List<Project> list = super.entityManager
                .createQuery("FROM Project WHERE kode=:kode")
                .setParameter("kode", kode)
                .getResultList();

		if (list.size() == 0) {
			return new Project();
		}
		else {
			return (Project)list.get(0);
		}
	}

	public boolean isBkExist(String kode) {
		
		if(findByBk(kode).getId()==null) {
	
			return false;
		}else {
			return true;
		}	 
	}
}
