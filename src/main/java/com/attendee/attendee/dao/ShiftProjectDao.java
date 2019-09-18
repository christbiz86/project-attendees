package com.attendee.attendee.dao;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.attendee.attendee.model.Project;
import com.attendee.attendee.model.Shift;
import com.attendee.attendee.model.ShiftProject;

@Repository
public class ShiftProjectDao extends ParentDao {
	@Transactional
	public void save(ShiftProject shiftProject) {
		super.entityManager.merge(shiftProject);
	}
	
	@Transactional
	public void delete(UUID id) {
		ShiftProject shiftProject = findById(id);
		super.entityManager.remove(shiftProject);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public ShiftProject findById(UUID id) {	
		List<ShiftProject> list = super.entityManager
                .createQuery("FROM ShiftProject WHERE id=:id")
                .setParameter("id", id)
                .getResultList();

		if (list.size() == 0) {
			return new ShiftProject();
		}
		else {
			return (ShiftProject)list.get(0);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<ShiftProject> findAll() {	
		List<ShiftProject> list = super.entityManager
                .createQuery("FROM ShiftProject ")
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
	public ShiftProject findByBk(UUID shift, UUID project) {		
//		List<ShiftProject> list = super.entityManager
//                .createQuery("FROM ShiftProject WHERE project=:project AND shift=:shift")
//                .setParameter("project", project)
//                .setParameter("shift", shift)
//                .getResultList();
		List<ShiftProject> list = super.entityManager.createNativeQuery("SELECT * "
				+ "FROM shift_project sp INNER JOIN shift s ON sp.id_shift =s.id "
				+ "INNER JOIN project p ON sp.id_project = p.id "
				+ "WHERE s.id=:shift AND p.id=:project", ShiftProject.class)
                .setParameter("shift", shift)
                .setParameter("project", project)
                .getResultList();

		if (list.size() == 0) {
			return new ShiftProject();
		}
		else {
			return (ShiftProject)list.get(0);
		}
	}

	public boolean isBkExist(UUID shift, UUID project) {
		if(findByBk(shift, project).getId()==null) {
			return false;
		}else {
			return true;
		}	 
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public ShiftProject findByShiftProject(Shift shift, Project pro) {
		List<ShiftProject> list = super.entityManager.createNativeQuery("SELECT * "
				+ "FROM shift_project "
				+ "WHERE id_shift = :idShift "
				+ "AND id_project = :idProject ", ShiftProject.class)
				.setParameter("idShift", shift.getId())
				.setParameter("idProject", pro.getId())
				.getResultList();
		
		if (list.size() == 0) {
			return new ShiftProject();
		}
		else {
			return (ShiftProject)list.get(0);
		}
	}
}
