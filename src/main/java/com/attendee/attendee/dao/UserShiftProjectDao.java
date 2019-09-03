package com.attendee.attendee.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.attendee.attendee.model.UserShiftProject;

@Repository
public class UserShiftProjectDao extends ParentDao {
	@Transactional
	public void save(UserShiftProject userShiftProject) {
		super.entityManager.merge(userShiftProject);
	}

	@Transactional
	public void delete(UUID id) {
		UserShiftProject userShiftProject = findById(id);
		super.entityManager.remove(userShiftProject);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public UserShiftProject findById(UUID id) {
		List<UserShiftProject> list = super.entityManager
				.createQuery("FROM UserShiftProject WHERE id=:id")
				.setParameter("id", id)
				.getResultList();

		if (list.size() == 0) {
			return new UserShiftProject();
		} else {
			return (UserShiftProject) list.get(0);
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<UserShiftProject> findAll() {
		List<UserShiftProject> list = super.entityManager
				.createQuery("FROM UserShiftProject ")
				.getResultList();

		return list;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<UserShiftProject> filterUserShift(String worktime) {
		List<UserShiftProject> userShiftList = super.entityManager
				.createQuery("FROM UserShiftProject worktime=:worktime")
				.setParameter("worktime", worktime)
				.getResultList();
		if (userShiftList.size() == 0) {
			return new ArrayList<UserShiftProject>();
		} else {
			return userShiftList;
		}
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
	public UserShiftProject findByBk(UUID userCompany, UUID shiftProject) {
		List<UserShiftProject> list = super.entityManager
				.createNativeQuery("SELECT * "
						+ "FROM user_shift_project "
						+ "WHERE id_user_company=:userCompany AND id_shift_project=:shiftProject",
						UserShiftProject.class)
				.setParameter("userCompany", userCompany)
				.setParameter("shiftProject", shiftProject)
				.getResultList();

		if (list.size() == 0) {
			return new UserShiftProject();
		} else {
			return (UserShiftProject) list.get(0);
		}
	}

	public boolean isBkExist(UUID userCompany, UUID shiftProject) {
		if (findByBk(userCompany, shiftProject).getId() == null) {
			return false;
		} else {
			return true;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public UserShiftProject findByUser(UUID userCompany) {
		List<UserShiftProject> list = super.entityManager
				.createNativeQuery("SELECT * " 
						+ "FROM user_shift_project " 
						+ "WHERE id_user_company=:userCompany ",
						UserShiftProject.class)
				.setParameter("userCompany", userCompany)
				.getResultList();

		if (list.size() == 0) {
			return new UserShiftProject();
		} else {
			return (UserShiftProject) list.get(0);
		}
	}
}
