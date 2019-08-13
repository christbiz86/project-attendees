package com.attendee.attendee.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.attendee.attendee.model.UserCompany;

@Repository
public class UserCompanyDao extends ParentDao {
	@Transactional
	public void save(UserCompany userCompany) {
		super.entityManager.merge(userCompany);
	}

	@Transactional
	public void delete(UUID id) {
		UserCompany userCompany = findById(id);
		super.entityManager.remove(userCompany);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public UserCompany findById(UUID id) {

		List<UserCompany> list = super.entityManager.createQuery("from UserCompany where id=:id").setParameter("id", id)
				.getResultList();

		if (list.size() == 0) {
			return new UserCompany();
		} else {
			return (UserCompany) list.get(0);
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<UserCompany> findAll() {
		List<UserCompany> list = super.entityManager.createQuery("from UserCompany ").getResultList();
		return list;
	}

	public boolean isExist(UUID id) {
		if (findById(id).getId() == null) {
			return false;
		} else {
			return true;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public UserCompany findByBk(UUID idUser) {
		List<UserCompany> list = super.entityManager.createQuery("from UserCompany where idUser.id=:idUser")
				.setParameter("idUser", idUser).getResultList();

		if (list.size() == 0) {
			return new UserCompany();
		} else {
			return (UserCompany) list.get(0);
		}
	}

	public boolean isBkExist(UUID idUser) {
		if (findByBk(idUser).getId() == null) {
			return false;
		} else {
			return true;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<UserCompany> findByFilter(UserCompany userCompany) {
		StringBuilder sb = new StringBuilder();
		sb.append("from UserCompany uc where 1=1");

		if (userCompany.getIdUser().getNama() != null) {
			sb.append(" and uc.idUser.nama like '%" + userCompany.getIdUser().getNama() + "%' ");
		}
		if (userCompany.getIdTipeUser().getTipe() != null) {
			sb.append(" and uc.idTipeUser.tipe like '%" + userCompany.getIdTipeUser().getTipe() + "%' ");
		}
//		if (userCompany.getIdCompanyUnitPosisi() != null) {
//			sb.append(" and uc.idCompanyUnitPosisi like '%" + userCompany.getIdCompanyUnitPosisi().getIdCompany().get + "%' ");
//		}

		List<UserCompany> list = super.entityManager.createQuery(sb.toString())
				.getResultList();

		if (list.size() == 0) {
			List<UserCompany> nullList = new ArrayList<UserCompany>();
			return nullList;
		} else {
			return list;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public UserCompany findByUsername(String username) {
		List<UserCompany> list = super.entityManager.createQuery("from UserCompany where idUser.username=:username")
				.setParameter("username", username).getResultList();

		if (list.size() == 0) {
			return new UserCompany();
		} else {
			return (UserCompany) list.get(0);
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public UserCompany findByEmail(String email) {
		List<UserCompany> list = super.entityManager.createQuery("from UserCompany where idUser.email=:email")
				.setParameter("email", email).getResultList();

		if (list.size() == 0) {
			return new UserCompany();
		} else {
			return (UserCompany) list.get(0);
		}
	}
}
