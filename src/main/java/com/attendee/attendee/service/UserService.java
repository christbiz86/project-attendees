package com.attendee.attendee.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendee.attendee.dao.UserDao;
import com.attendee.attendee.exception.ValidationException;
import com.attendee.attendee.model.User;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	public void valIdNotExist(UUID id) throws ValidationException {

		if (userDao.isExist(id)) {
			throw new ValidationException("Data sudah ada");
		}
	}

	public void valIdExist(UUID id) throws ValidationException {

		if (!userDao.isExist(id)) {
			throw new ValidationException("Data tidak ada");
		}
	}

	public void valIdNotNull(User user) throws ValidationException {

		if (user.getId() == null) {
			throw new ValidationException("Id tidak boleh kosong");
		}
	}

	public void valNonBk(User user) throws ValidationException {

		StringBuilder sb = new StringBuilder();
		int error = 0;

		if (user.getAlamat() == null) {
			sb.append("alamat tidak boleh kosong \n");
			error++;
		}
		if (user.getTglLahir() == null) {
			sb.append("tanggal lahir tidak boleh kosong \n");
			error++;
		}
		if (user.getUserName() == null) {
			sb.append("username tidak boleh kosong \n");
			error++;
		}
		if (user.getTelp() == null) {
			sb.append("telepon tidak boleh kosong \n");
			error++;
		}
		if (user.getFoto() == null) {
			sb.append("foto tidak boleh kosong \n");
			error++;
		}
		if (user.getNama() == null) {
			sb.append("nama tidak boleh kosong \n");
			error++;
		}
		if (user.getPassword() == null) {
			sb.append("password tidak boleh kosong \n");
			error++;
		}
		if (user.getCreateAt() == null) {
			sb.append("tanggal dibuat tidak boleh kosong \n");
			error++;
		}
		if (user.getCreateBy() == null) {
			sb.append("pembuat tidak boleh kosong \n");
			error++;
		}
		if (user.getUpdateAt() == null) {
			sb.append("tanggal diedit tidak boleh kosong \n");
			error++;
		}
		if (user.getUpdateBy() == null) {
			sb.append("pengedit tidak boleh kosong \n");
			error++;
		}

		if (error > 0) {
			throw new ValidationException(sb.toString());
		}
	}

	public void valBkNotExist(User user) throws ValidationException {
		if (userDao.isBkExist(user.getKode())) {
			throw new ValidationException("Data sudah ada");
		}
	}

	public void valBkNotChange(User user) throws ValidationException {
		String s = findById(user.getId()).getKode();
		if (!user.getKode().toString().equals(s.toString())) {

			throw new ValidationException("Kode tidak boleh berubah");
		}
	}

	public void valBkNotNull(User user) throws ValidationException {

		if (user.getKode() == null) {

			throw new ValidationException("Kode tidak boleh kosong");
		}
	}

	public void save(User user) throws ValidationException {
		valBkNotNull(user);
		valBkNotExist(user);
		valNonBk(user);
		userDao.save(user);
	}

	public void update(User user) throws ValidationException {
		valIdNotNull(user);
		valIdExist(user.getId());
		valBkNotNull(user);
		valBkNotChange(user);
		valNonBk(user);
		userDao.save(user);
	}

	public void delete(UUID id) throws ValidationException {

		valIdExist(id);
		userDao.delete(id);
	}

	public User findById(UUID id) throws ValidationException {

		return userDao.findById(id);
	}

	public User findByBk(User user) {

		return userDao.findByBk(user.getKode());
	}

	public List<User> findAll() throws ValidationException {
		return userDao.findAll();
	}

	public User findKode(String kode) {

		return userDao.findByBk(kode);
	}

}
