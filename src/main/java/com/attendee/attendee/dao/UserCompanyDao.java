package com.attendee.attendee.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.attendee.attendee.model.Company;
import com.attendee.attendee.model.UserCompany;

@Repository
public class UserCompanyDao extends ParentDao {
	@Transactional
	public void save(UserCompany userCompany) {
		super.entityManager.merge(userCompany);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public UserCompany findById(UUID id) {
		List<UserCompany> list = super.entityManager
				.createQuery("from UserCompany where id = :id")
				.setParameter("id", id)
				.getResultList();

		if (list.size() == 0) {
			return new UserCompany();
		} else {
			return (UserCompany) list.get(0);
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<UserCompany> findByFilter(UserCompany userCompany) {
		StringBuilder sb = new StringBuilder("SELECT uc.id, u.id, cup.id, tu.id ");
		sb.append("FROM user_company uc ");
		sb.append("LEFT JOIN users u ");
		sb.append("ON uc.id_user = u.id ");
		sb.append("LEFT JOIN company_unit_posisi cup ");
		sb.append("ON uc.id_company_unit_posisi = cup.id ");
		sb.append("LEFT JOIN tipe_user tu ");
		sb.append("ON uc.id_tipe_user = tu.id ");
		sb.append("LEFT JOIN company c ");
		sb.append("ON cup.id_company = c.id ");
		sb.append("LEFT JOIN unit u ");
		sb.append("ON cup.id_unit = u.id ");
		sb.append("LEFT JOIN posisi p ");
		sb.append("ON cup.id_posisi = p.id ");
		sb.append("WHERE 1=1 ");

		if (!userCompany.getIdUser().getNama().equals("null")) {
			sb.append("AND u.nama LIKE '" + userCompany.getIdUser().getNama() + "' ");
		}

		if (!userCompany.getIdCompanyUnitPosisi().getIdCompany().getNama().equals("null")) {
			sb.append("AND c.nama LIKE '" + userCompany.getIdCompanyUnitPosisi().getIdCompany().getNama() + "' ");
		}

		if (!userCompany.getIdCompanyUnitPosisi().getIdPosisi().getPosisi().equals("null")) {
			sb.append("AND p.posisi LIKE '" + userCompany.getIdCompanyUnitPosisi().getIdPosisi().getPosisi() + "' ");
		}

		if (!userCompany.getIdCompanyUnitPosisi().getIdUnit().getUnit().equals("null")) {
			sb.append("AND u.unit LIKE '" + userCompany.getIdCompanyUnitPosisi().getIdUnit().getUnit() + "' ");
		}

		if (!userCompany.getIdTipeUser().getTipe().equals("null")) {
			sb.append("AND uc. LIKE '" + userCompany.getIdUser().getNama() + "' ");
		}

		List<UserCompany> list = super.entityManager.createNativeQuery(sb.toString(), Company.class).getResultList();

		if (list.size() == 0) {
			return new ArrayList<UserCompany>();
		} else {
			return list;
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
	public UserCompany findByBk(String idUser) {
		List<UserCompany> list = super.entityManager.createQuery("from UserCompany where idUser = :idUser")
				.setParameter("idUser", idUser).getResultList();

		if (list.size() == 0) {
			return new UserCompany();
		} else {
			return (UserCompany) list.get(0);
		}
	}

	public boolean isBkExist(String idUser) {
		if (findByBk(idUser).getId() == null) {

			return false;
		} else {
			return true;
		}
	}
}
