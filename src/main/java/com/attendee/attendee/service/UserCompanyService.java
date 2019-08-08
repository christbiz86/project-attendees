package com.attendee.attendee.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendee.attendee.dao.UserCompanyDao;
import com.attendee.attendee.exception.ValidationException;
import com.attendee.attendee.model.UserCompany;

@Service
public class UserCompanyService {
	@Autowired
	private UserCompanyDao userCompanyDao;

	private void valIdExist(UUID id) throws ValidationException {
		if (!userCompanyDao.isExist(id)) {
			throw new ValidationException("Data tidak ada!");
		}
	}

	private void valIdNotNull(UserCompany userCompany) throws ValidationException {
		if (userCompany.getId() == null) {
			throw new ValidationException("ID tidak boleh kosong!");
		}
	}

	private void valNonBk(UserCompany userCompany) throws ValidationException {
		StringBuilder sb = new StringBuilder();
		int error = 0;

//		if (userCompany.getIdCompanyUnitPosisi() == null || userCompany.getIdCompanyUnitPosisi().getId() == null) {
//			sb.append("Company unit posisi tidak boleh kosong!");
//			error++;
//		}
//		if (userCompany.getIdTipeUser() == null) {
//			sb.append("Tipe user tidak boleh kosong!");
//			error++;
//		}

		if (error > 0) {
			throw new ValidationException(sb.toString());
		}
	}

	private void valBkNotExist(UserCompany userCompany) throws ValidationException {
		if (userCompanyDao.isBkExist(userCompany.getIdUser().getId().toString())) {
			throw new ValidationException("Data sudah ada!");
		}
	}

	private void valBkNotChange(UserCompany userCompany) throws ValidationException {
		String s = findById(userCompany.getId()).getIdUser().getId().toString();

		if (!userCompany.getIdUser().getId().toString().equals(s.toString())) {
			throw new ValidationException("Kode tidak boleh berubah!");
		}
	}

	private void valBkNotNull(UserCompany userCompany) throws ValidationException {
		if (userCompany.getIdUser().getId() == null) {
			throw new ValidationException("Kode tidak boleh kosong!");
		}
	}

	public void save(UserCompany userCompany) throws ValidationException {
//		valBkNotNull(userCompany);
//		valBkNotExist(userCompany);
//		valNonBk(userCompany);
		userCompanyDao.save(userCompany);
	}

	public void update(UserCompany userCompany) throws ValidationException {
		valIdNotNull(userCompany);
		valIdExist(userCompany.getId());
		valBkNotNull(userCompany);
		valBkNotChange(userCompany);
		valNonBk(userCompany);
		userCompanyDao.save(userCompany);
	}

	public UserCompany findById(UUID id) throws ValidationException {
		return userCompanyDao.findById(id);
	}

	public UserCompany findByBk(UserCompany userCompany) {
		return userCompanyDao.findByBk(userCompany.getIdUser().getId().toString());
	}

	public List<UserCompany> findByFilter(UserCompany userCompany) {
		return userCompanyDao.findByFilter(userCompany);
	}

	public List<UserCompany> findAll() throws ValidationException {
		return userCompanyDao.findAll();
	}
	
}
