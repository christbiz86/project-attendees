package com.attendee.attendee.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendee.attendee.dao.UnitDao;
import com.attendee.attendee.exception.ValidationException;
import com.attendee.attendee.model.Unit;

@Service
public class UnitService {
	@Autowired
	private UnitDao unitDao;

	public void valIdExist(UUID id) throws ValidationException {
		if (!unitDao.isExist(id)) {
			throw new ValidationException("Data tidak ada!");
		}
	}

	public void valIdNotNull(Unit divisi) throws ValidationException {
		if (divisi.getId() == null) {
			throw new ValidationException("ID tidak boleh kosong!");
		}
	}

	public void valNonBk(Unit divisi) throws ValidationException {
		StringBuilder sb = new StringBuilder();
		int error = 0;

		if (divisi.getIdStatus() == null || divisi.getIdStatus().getId() == null) {
			sb.append("Status tidak boleh kosong!");
			error++;
		}
		if (error > 0) {
			throw new ValidationException(sb.toString());
		}
	}

	public void valBkNotExist(Unit unit) throws ValidationException {
		if (unitDao.isBkExist(unit.getUnit())) {
			throw new ValidationException("Data sudah ada");
		}
	}

	public void valBkNotChange(Unit unit) throws ValidationException {
		String s = findById(unit.getId()).getUnit();
		if (!unit.getUnit().toString().equals(s.toString())) {
			throw new ValidationException("divisi tidak boleh berubah");
		}
	}

	public void valBkNotNull(Unit divisi) throws ValidationException {
		if (divisi.getUnit() == null) {
			throw new ValidationException("divisi tidak boleh kosong");
		}
	}

	public void save(Unit unit) throws ValidationException {
		valBkNotNull(unit);
		valBkNotExist(unit);
		valNonBk(unit);
		unitDao.save(unit);
	}

	public void update(Unit unit) throws ValidationException {
		valIdNotNull(unit);
		valIdExist(unit.getId());
		valBkNotNull(unit);
		valBkNotChange(unit);
		valNonBk(unit);
		unitDao.save(unit);
	}

	public Unit findById(UUID id) throws ValidationException {
		return unitDao.findById(id);
	}

	public Unit findByBk(Unit unit) {
		return unitDao.findByBk(unit.getUnit());
	}

	public List<Unit> findAll() throws ValidationException {
		return unitDao.findAll();
	}

	public List<Unit> findByFilter(Unit unit) throws ValidationException {
		return unitDao.findByFilter(unit);
	}
}
