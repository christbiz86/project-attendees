package com.attendee.attendee.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendee.attendee.dao.CompanyDao;
import com.attendee.attendee.exception.ValidationException;
import com.attendee.attendee.model.Company;

@Service
public class CompanyService {
	@Autowired
	private CompanyDao companyDao;
	
	public void valIdExist(Company company) throws ValidationException {
		if (!companyDao.isIdExist(company.getId())) {
			throw new ValidationException("ID tidak ada!");
		}
	}
	
	public void valIdNotNull(Company company) throws ValidationException {
		if (company.getId() == null) {
			throw new ValidationException("ID tidak boleh kosong!");
		}
	}
	
	public List<Company> findAll() {
		return companyDao.findAll();
	}
	
	public Company findById(UUID id) {
		return companyDao.findById(id);
	}
	
	public Company findByBk(String kode) {
		return companyDao.findByBk(kode);
	}
	
	public Company findByFilter(Company company) {
		return companyDao.findByFilter(company);
	}
	
	public void valBkNotExist(Company company) throws ValidationException {
		if (companyDao.isBkExist(company.getKode())) {
			throw new ValidationException("Data sudah ada!");
		}
	}
	
	public void valBkNotNull(Company company) throws ValidationException {
		if (company.getKode() == null) {
			throw new ValidationException("Kode company tidak boleh kosong!");
		}
	}
	
	public void valBkNotChange(Company company) throws ValidationException {
		String s = findById(company.getId()).getKode();
		if (!company.getKode().toString().equals(s.toString())) {
			throw new ValidationException("Kode company tidak boleh berubah!");
		}
	}
	
	public void valNonBk(Company company) throws ValidationException {
		StringBuilder sb = new StringBuilder();
		int error = 0;
		
		if (company.getNama() == null) {
			sb.append("Nama perusaahan tidak boleh kosong! \n");
			error++;
		}
		if (company.getJatahCuti() == null) {
			sb.append("Jatah cuti tidak boleh kosong! \n");
			error++;
		}
		if (company.getToleransiKeterlambatan() == null) {
			sb.append("Toleransi keterlambatan tidak boleh kosong! \n");
			error++;
		}
		if (company.getCreatedAt() == null) {
			sb.append("Tanggal dibuat tidak boleh kosong \n");
			error++;
		}
//		if (company.getUpdatedAt() == null) {
//			sb.append("Tanggal diedit tidak boleh kosong \n");
//			error++;
//		}
//		if (company.getCreatedBy() == null) {
//			sb.append("Pembuat tidak boleh kosong \n");
//			error++;
//		}
//		if (company.getUpdatedBy() == null) {
//			sb.append("Pengedit tidak boleh kosong \n");
//			error++;
//		}

		if (error > 0) {
			throw new ValidationException(sb.toString());
		}	
	}
	
	public void insert(Company company) throws ValidationException {
		company.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		valBkNotNull(company);
		valBkNotExist(company);
		valNonBk(company);
		companyDao.save(company);
	}
	
	public void update(Company company) throws ValidationException {
		company.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
		valIdNotNull(company);
		valIdExist(company);
		valBkNotNull(company);
		valBkNotChange(company);
		valNonBk(company);
		companyDao.save(company);
	}

}
