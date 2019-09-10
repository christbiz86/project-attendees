package com.attendee.attendee.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.attendee.attendee.model.Attendee;

@Repository
public class AttendeeDao extends ParentDao{
	
	@SuppressWarnings("unchecked")
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
	public List<Attendee> findByFilter(Attendee attendee) {
		StringBuilder sb = new StringBuilder("from Attendee where 1=1 ");
		
		if (attendee.getIdUserShiftProject() != null) {
			if (attendee.getIdUserShiftProject().getUserCompany() != null) {
				if (attendee.getIdUserShiftProject().getUserCompany().getIdUser() != null) {
					if (attendee.getIdUserShiftProject().getUserCompany().getIdUser().getId() != null) {
						sb.append("and idUserShiftProject.userCompany.idUser.id = '" 
					+ attendee.getIdUserShiftProject().getUserCompany().getIdUser().getId() + "' ");
					}
				}
				if (attendee.getIdUserShiftProject().getUserCompany().getIdCompanyUnitPosisi() != null) {
					if (attendee.getIdUserShiftProject().getUserCompany().getIdCompanyUnitPosisi().getIdCompany() != null) {
						if (attendee.getIdUserShiftProject().getUserCompany().getIdCompanyUnitPosisi().getIdCompany().getId() != null) {
							sb.append("and idUserShiftProject.userCompany.idCompanyUnitPosisi.idCompany.id = '" 
						+ attendee.getIdUserShiftProject().getUserCompany().getIdCompanyUnitPosisi().getIdCompany().getId() + "' ");
						}
					}
					if (attendee.getIdUserShiftProject().getUserCompany().getIdCompanyUnitPosisi().getIdUnit() != null) {
						if (attendee.getIdUserShiftProject().getUserCompany().getIdCompanyUnitPosisi().getIdUnit().getUnit() != null) {
							sb.append("and idUserShiftProject.userCompany.idCompanyUnitPosisi.idUnit.unit like '" 
						+ attendee.getIdUserShiftProject().getUserCompany().getIdCompanyUnitPosisi().getIdUnit().getUnit() + "' ");
						}
					}
					if (attendee.getIdUserShiftProject().getUserCompany().getIdCompanyUnitPosisi().getIdPosisi() != null) {
						if (attendee.getIdUserShiftProject().getUserCompany().getIdCompanyUnitPosisi().getIdPosisi().getPosisi() != null) {
							sb.append("and idUserShiftProject.userCompany.idCompanyUnitPosisi.idPosisi.posisi like '" 
						+ attendee.getIdUserShiftProject().getUserCompany().getIdCompanyUnitPosisi().getIdPosisi().getPosisi() + "' ");
						}
					}
				}
			}
			if (attendee.getIdUserShiftProject().getShiftProject() != null) {
				if (attendee.getIdUserShiftProject().getShiftProject().getProject() != null) {
					if (attendee.getIdUserShiftProject().getShiftProject().getProject().getNamaProject() != null) {
						sb.append("and idUserShiftProject.shiftProject.project.namaProject like '" 
					+ attendee.getIdUserShiftProject().getShiftProject().getProject().getNamaProject() + "' ");
					}
				}
				if (attendee.getIdUserShiftProject().getShiftProject().getShift() != null) {
					if (attendee.getIdUserShiftProject().getShiftProject().getShift().getKode() != null) {
						sb.append("and idUserShiftProject.shiftProject.shift.kode like '" 
					+ attendee.getIdUserShiftProject().getShiftProject().getShift().getKode() + "' ");
					}
				}
			}
			if (attendee.getIdUserShiftProject().getWorktime() != null) {
				if (attendee.getIdUserShiftProject().getWorktime().getStatus() != null) {
					sb.append("and idUserShiftProject.worktime.status like '" 
				+ attendee.getIdUserShiftProject().getWorktime().getStatus() + "' ");
				}
			}
		}
		
		System.out.println(sb.toString());
		List<Attendee> list = super.entityManager.createQuery(sb.toString()).getResultList();
		
		if (list.size() == 0) {
			return new ArrayList<Attendee>();
		}
		else {
			return list;
		}
	}
	
	public boolean isIdExist(UUID id) {
		if(findById(id).getId() == null) {
			return false;
		}else {
			return true;
		}
	}
	
	@SuppressWarnings("unchecked")
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
	
	public boolean isBkExist(Attendee attendee) {
		if(findByBk(attendee.getIdUserShiftProject().getId(),attendee.getMasuk()).getId() == null) {
			return false;
		}else {
			return true;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Attendee> findAll() {
		List<Attendee> list = super.entityManager
				.createQuery("FROM Attendee")
				.getResultList();
		return list;
	}
	
	public void save(Attendee attendee) {
		super.entityManager.merge(attendee);
	}
	
	
	@SuppressWarnings("unchecked")
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
	
	@SuppressWarnings("unchecked")
	public List<Attendee> findByUSP(UUID usp) {
		List<Attendee> list = super.entityManager
				.createQuery("FROM Attendee "
						+ "WHERE idUserShiftProject.id = :usp")
				.setParameter("usp", usp)
				.getResultList();
		
		if (list.size() == 0) {
			return new ArrayList<Attendee>();
		}
		else {
			return list;
		}
	}
}