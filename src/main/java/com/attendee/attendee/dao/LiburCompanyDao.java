package com.attendee.attendee.dao;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.attendee.attendee.model.LiburCompany;

@Repository
public class LiburCompanyDao extends ParentDao {
	@Transactional
	public void save(LiburCompany liburCompany) {
		super.entityManager.merge(liburCompany);
	}
	
	@Transactional
	public void delete(UUID id) {
		LiburCompany liburCompany = findById(id);
		super.entityManager.remove(liburCompany);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public LiburCompany findById(UUID id) {	
		List<LiburCompany> list = super.entityManager
                .createQuery("FROM LiburCompany WHERE id=:id")
                .setParameter("id", id)
                .getResultList();

		if (list.size() == 0) {
			return new LiburCompany();
		}
		else {
			return (LiburCompany)list.get(0);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<LiburCompany> findByFilter(LiburCompany liburCompany) {		
		StringBuilder sb = new StringBuilder();
		sb.append("FROM LiburCompany WHERE 1=1");
		if (liburCompany.getLibur() != null) {
			if(liburCompany.getLibur().getNama() != null) {
				String libur = liburCompany.getLibur().getNama();
				sb.append(" AND libur.nama='"+libur+"'");
			}

			if (liburCompany.getLibur().getTglMulai() != null) {
				Date tglMulai = liburCompany.getLibur().getTglMulai();
				sb.append(" AND libur.tglMulai='"+tglMulai+"'");
			}
			
			if (liburCompany.getLibur().getTglAkhir() != null) {
				Date tglAkhir = liburCompany.getLibur().getTglAkhir();
				sb.append(" AND libur.tglAkhir='"+tglAkhir+"'");
			}
		}
		
		if (liburCompany.getCompany() != null) {
			UUID company = liburCompany.getCompany().getId();
			sb.append(" AND company.id='"+company+"'");
		}

		List<LiburCompany> list = super.entityManager
                .createQuery(sb.toString())
                .getResultList();

		if (list.size() == 0) {
			List<LiburCompany> nullList = new ArrayList<LiburCompany>();
			return nullList;
		}
		else {
			return list;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<LiburCompany> findAll() {	
		List<LiburCompany> list = super.entityManager
                .createQuery("FROM LiburCompany ")
                .getResultList();

		return list;
	}
	
	public boolean isExist(UUID id) {
		if(findById(id).getId()==null) {
			return false;
		}else {
			return true;
		}
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public LiburCompany findByBk(UUID libur, UUID company) {
		List<LiburCompany> list = super.entityManager.createNativeQuery("SELECT * FROM libur_company lc "
				+ "WHERE lc.id_libur=:libur AND lc.id_company=:company", LiburCompany.class)
                .setParameter("libur", libur)
                .setParameter("company", company)
                .getResultList();

		if (list.size() == 0) {
			return new LiburCompany();
		}
		else {
			return (LiburCompany)list.get(0);
		}
	}

	public boolean isBkExist(UUID libur, UUID company) {
		if(findByBk(libur, company).getId()==null) {
			return false;
		}else {
			return true;
		}	 
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<LiburCompany> findByTgl(LiburCompany liburCompany) {
		
		LocalDate localDate = liburCompany.getLibur().getTglMulai().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int year  = localDate.getYear();
		int month = localDate.getMonthValue();

		StringBuilder sb = new StringBuilder();
		sb.append(" SELECT lc.id, lc.id_libur, lc.id_company FROM LIBUR_COMPANY lc ");
		sb.append(" JOIN LIBUR l ON lc.id_libur=l.id ");
		sb.append(" WHERE lc.ID_COMPANY = '"+ liburCompany.getCompany().getId()+"' ");
		sb.append(" AND EXTRACT(YEAR FROM l.tgl_mulai)= "+year+"  ");
		sb.append(" AND EXTRACT(MONTH FROM l.tgl_mulai)= "+month+" ");

		List<LiburCompany> list = super.entityManager
                .createNativeQuery(sb.toString(),LiburCompany.class)
                .getResultList();

		if (list.size() == 0) {
			List<LiburCompany> nullList = new ArrayList<LiburCompany>();
			return nullList;
		}
		else {
			return list;
		}
	}
}
