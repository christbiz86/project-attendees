package com.attendee.attendee.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.attendee.attendee.model.Notification;

@Repository
public class NotificationDao extends ParentDao {

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Notification> findByFilter(Notification notification) {
		StringBuilder sb = new StringBuilder("from Notification where 1=1 ");
		
		if (notification.getRequest().getKode() != null) {
			sb.append(" and request.kode like '%"+ notification.getRequest().getKode() +"%'");
		}
		if (notification.getRequest().getStatus() != null) {
			sb.append(" and request.status like '%"+ notification.getRequest().getStatus().getStatus() +"%'");
		}
		if(notification.getRequest().getUserCompany() != null) {
			if (notification.getRequest().getUserCompany().getIdUser() != null) {
				if (notification.getRequest().getUserCompany().getIdUser().getNama() != null) {
					sb.append(" and request.userCompany.idUser.nama like '%"+ notification.getRequest().getUserCompany().getIdUser().getNama() +"%'");
				}
			}
			if (notification.getRequest().getUserCompany().getIdCompanyUnitPosisi() != null) {
				if (notification.getRequest().getUserCompany().getIdCompanyUnitPosisi().getIdCompany() != null) {
					sb.append(" and request.userCompany.idCompanyUnitPosisi.idCompany.nama like '"+ notification.getRequest().getUserCompany().getIdCompanyUnitPosisi().getIdCompany().getNama() +"'");
				}
				if (notification.getRequest().getUserCompany().getIdCompanyUnitPosisi().getIdUnit() != null) {
					sb.append(" and request.userCompany.idCompanyUnitPosisi.idUnit.unit like '%"+ notification.getRequest().getUserCompany().getIdCompanyUnitPosisi().getIdUnit().getUnit() +"%'");
				}
				if (notification.getRequest().getUserCompany().getIdCompanyUnitPosisi().getIdPosisi() != null) {
					sb.append(" and request.userCompany.idCompanyUnitPosisi.idPosisi.posisi like '%"+ notification.getRequest().getUserCompany().getIdCompanyUnitPosisi().getIdPosisi().getPosisi() +"%'");
				}
			}
		}
		
		List<Notification> list = super.entityManager.createQuery(sb.toString(), Notification.class)
				.getResultList();

		if (list.size() == 0) {
			return new ArrayList<Notification>();
		} else {
			return list;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Notification> findAll() {
		List<Notification> list = super.entityManager.createQuery("from Notification ").getResultList();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Notification findById(UUID id) {
		List<Notification> list = super.entityManager
				.createQuery("from Notification where id=:id")
				.setParameter("id", id)
				.getResultList();
		if(list.size() == 0) {
			return new Notification();
		} else {
			return list.get(0);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public Notification findByBk(UUID id) {	
		
		List<Notification> list = super.entityManager
                .createQuery("from Notification where request.id=:id")
                .setParameter("id", id)
                .getResultList();

		if (list.size() == 0) {
			return new Notification();
		}
		else {
			return (Notification)list.get(0);
		}
	}
	
	public void save(Notification notification) {
		super.entityManager.merge(notification);
	}
	
	public boolean isIdExist(UUID id) {
		if(findById(id).getId().equals(null)) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean isBkExist(UUID id) {
		if(findByBk(id).getId() == null) {
			return false;
		} else {
			return true;
		}
	}
}
