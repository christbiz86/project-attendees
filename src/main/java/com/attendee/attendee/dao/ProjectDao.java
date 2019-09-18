package com.attendee.attendee.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.attendee.attendee.model.Project;
import com.attendee.attendee.model.Status;

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
                .createQuery("FROM Project "
                		+ "ORDER BY kode ASC ")
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
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Project> filterProject(String status, String lokasi) {
		StringBuilder sb = new StringBuilder();
		sb.append("FROM Project WHERE 1=1 ");
		if(status != null) {
			sb.append("AND status.status = '" + status + "' ");
		}
		if(lokasi != null) {
			sb.append("AND lokasi = '" + lokasi + "' ");
		}
		List<Project> proList = super.entityManager
				.createQuery(sb.toString())
				.getResultList();
		if(proList.size() == 0) {
			 return new ArrayList<Project>();
		} else {
			return proList;
		}
	}

	public boolean isBkExist(String kode) {
		if(findByBk(kode).getId()==null) {
	
			return false;
		}else {
			return true;
		}	 
	}
	
	@Transactional
	public String countRows() {
		BigInteger count = (BigInteger) super.entityManager
				.createNativeQuery("SELECT count(*) FROM project").getSingleResult();
		
		int next = count.intValue() + 1;
		String num = Integer.toString(next);
		
		if(num.length() == 1) {
			return "00"+num;
		} else if(num.length() == 2) {
			return "0"+num;
		} else {
			return num;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Project findByProjectName(String namaProject) {
		List<Project> list = super.entityManager
                .createQuery("from Project where namaProject = :nama")
                .setParameter("nama", namaProject)
                .getResultList();

		if (list.size() == 0) {
			return new Project();
		}
		else {
			return (Project)list.get(0);
		}
	}
}
