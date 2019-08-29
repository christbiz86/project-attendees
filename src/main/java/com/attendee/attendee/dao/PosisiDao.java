package com.attendee.attendee.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.attendee.attendee.model.Posisi;

@Repository
public class PosisiDao extends ParentDao {

	@Transactional
	public void save(Posisi posisi) {
		super.entityManager.merge(posisi);
	}

	@Transactional
	public void delete(UUID id) {
		Posisi posisi = findById(id);
		super.entityManager.remove(posisi);
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public Posisi findById(UUID id) {

		List<Posisi> list = super.entityManager.createQuery("from Posisi where id=:id").setParameter("id", id)
				.getResultList();

		if (list.size() == 0) {
			return new Posisi();
		} else {
			return (Posisi) list.get(0);
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Posisi> findAll() {

		List<Posisi> list = super.entityManager.createQuery("from Posisi ").getResultList();

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
	public Posisi findByBk(String posisi) {

		List<Posisi> list = super.entityManager.createQuery("from Posisi where posisi=:posisi")
				.setParameter("posisi", posisi).getResultList();

		if (list.size() == 0) {
			return new Posisi();
		} else {
			return (Posisi) list.get(0);
		}
	}

	public boolean isBkExist(String posisi) {

		if (findByBk(posisi).getId() == null) {

			return false;
		} else {
			return true;
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<Posisi> findByFilter(Posisi posisi) {

		StringBuilder sb = new StringBuilder("SELECT * ");
		sb.append("FROM posisi p ");
		sb.append(" WHERE 1=1 ");

		if (posisi.getPosisi() != null) {
			sb.append(" AND p.posisi LIKE '%" + posisi.getPosisi() + "%' ");
		}
		if (posisi.getIdStatus().getId() != null) {
			sb.append(" AND p.id_status LIKE '%" + posisi.getIdStatus().getId() + "%' ");
		}

		List<Posisi> list = super.entityManager.createNativeQuery(sb.toString(), Posisi.class).getResultList();

		if (list.size() == 0) {
			return new ArrayList<Posisi>();
		} else {
			return list;
		}
	}
}
