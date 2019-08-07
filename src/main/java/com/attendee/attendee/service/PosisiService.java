package com.attendee.attendee.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendee.attendee.dao.PosisiDao;
import com.attendee.attendee.exception.ValidationException;
import com.attendee.attendee.model.Posisi;

@Service
public class PosisiService {

	@Autowired
	private PosisiDao posisiDao;

	public void valIdExist(UUID id) throws ValidationException {
		if (!posisiDao.isExist(id)) {
			throw new ValidationException("Data tidak ada!");
		}
	}

	public void valIdNotNull(Posisi posisi) throws ValidationException {
		if (posisi.getId() == null) {
			throw new ValidationException("ID tidak boleh kosong!");
		}
	}

	public void valNonBk(Posisi posisi) throws ValidationException {
		StringBuilder sb = new StringBuilder();
		int error = 0;

		if (posisi.getIdStatus() == null || posisi.getIdStatus().getId() == null) {
			sb.append("Status tidak boleh kosong!");
			error++;
		}

		if (error > 0) {
			throw new ValidationException(sb.toString());
		}
	}

	public void valBkNotExist(Posisi posisi) throws ValidationException {
		if (posisiDao.isBkExist(posisi.getPosisi())) {
			throw new ValidationException("Data sudah ada");
		}
	}

	public void valBkNotChange(Posisi posisi) throws ValidationException {
		String s = findById(posisi.getId()).getPosisi();
		if (!posisi.getPosisi().equals(s)) {
			throw new ValidationException("Kode tidak boleh berubah!");
		}
	}

	public void valBkNotNull(Posisi posisi) throws ValidationException {
		if (posisi.getPosisi() == null) {
			throw new ValidationException("Kode tidak boleh kosong!");
		}
	}

	public void save(Posisi posisi) throws ValidationException {
		valBkNotNull(posisi);
		valBkNotExist(posisi);
		valNonBk(posisi);
		posisiDao.save(posisi);
	}

	public void update(Posisi posisi) throws ValidationException {
		valIdNotNull(posisi);
		valIdExist(posisi.getId());
		valBkNotNull(posisi);
		valBkNotChange(posisi);
		valNonBk(posisi);
		posisiDao.save(posisi);
	}

	public void delete(UUID id) throws ValidationException {
		valIdExist(id);
		posisiDao.delete(id);
	}

	public Posisi findById(UUID id) throws ValidationException {
		return posisiDao.findById(id);
	}

	public Posisi findByBk(Posisi jabatan) {
		return posisiDao.findByBk(jabatan.getPosisi());
	}

	public List<Posisi> findAll() throws ValidationException {
		return posisiDao.findAll();
	}

	public List<Posisi> findByFilter(Posisi posisi) throws ValidationException {
		return posisiDao.findByFilter(posisi);
	}

}
