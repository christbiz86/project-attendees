package com.attendee.attendee.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.attendee.attendee.model.Attendee;

@Repository
public class AttendeeDao extends ParentDao{
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Attendee findById(UUID id) {
		List<Attendee> list = super.entityManager
				.createQuery("FROM Attendee "
						+ "WHERE id = :id")
				.setParameter("id", id)
				.getResultList();
		
		if (list.size() == 0) {
			return new Attendee();
		}
		else {
			return (Attendee)list.get(0);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Attendee findByFilter(Attendee attendee) {
		StringBuilder sb = new StringBuilder("SELECT c.id, c.kode, c.nama, c.jatah_cuti, c.toleransi_keterlambatan, c.id_status, ");
		sb.append("c.created_at, c.updated_at, c.created_by, c.updated_by, s.id, s.status ");
		sb.append("FROM company c ");
		sb.append("JOIN status s ");
		sb.append("ON c.id_status = s.id ");
		sb.append("WHERE 1=1 ");
		
	
		
		List<Attendee> list = super.entityManager
				.createNativeQuery(sb.toString(), Attendee.class)
				.getResultList();
		
		if (list.size() == 0) {
			return new Attendee();
		}
		else {
			return (Attendee)list.get(0);
		}
	}
	
	@Transactional
	public boolean isIdExist(UUID id) {
		if(findById(id).getId() == null) {
			return false;
		}else {
			return true;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Attendee findByBk(UUID idUserShiftProject,Timestamp masuk) {
		List<Attendee> list = super.entityManager
				.createQuery("FROM Attendee "
						+ "WHERE idUserShiftProject.id = :idUserShiftProject "
						+ "and masuk =:masuk")
				.setParameter("idUserShiftProject", idUserShiftProject)
				.setParameter("masuk", masuk)
				.getResultList();
		
		if (list.size() == 0) {
			return new Attendee();
		}
		else {
			return (Attendee)list.get(0);
		}
	}
	
	@Transactional
	public boolean isBkExist(Attendee attendee) {
		if(findByBk(attendee.getIdUserShiftProject().getId(),attendee.getMasuk()).getId() == null) {
			return false;
		}else {
			return true;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<Attendee> findAll() {
		List<Attendee> list = super.entityManager
				.createQuery("FROM Attendee")
				.getResultList();
		return list;
	}
	
	@Transactional
	public void save(Attendee attendee) {
		super.entityManager.merge(attendee);
	}
	
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Attendee findByUser(UUID idUser) {
		List<Attendee> list = super.entityManager
				.createQuery("FROM Attendee "
						+ "WHERE idUserShiftProject.userCompany.idUser.id = :idUser")
				.setParameter("idUser", idUser)
				.getResultList();
		
		if (list.size() == 0) {
			return new Attendee();
		}
		else {
			return (Attendee)list.get(0);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Attendee findByUserAndTime(UUID idUser) {
		List<Attendee> list = super.entityManager
				.createQuery("FROM Attendee "
						+ "WHERE idUserShiftProject.userCompany.idUser.id = :idUser "
						+ "AND keterangan=:keterangan ")
				.setParameter("idUser", idUser)
				.setParameter("keterangan", "masuk")
				.getResultList();
		
		if (list.size() == 0) {
			return new Attendee();
		}
		else {
			return (Attendee)list.get(0);
		}
	}
}