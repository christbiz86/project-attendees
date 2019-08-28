package com.attendee.attendee.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.attendee.attendee.model.AnnualLeave;

@Repository
public class AnnualLeaveDao extends ParentDao {
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<AnnualLeave> findByFilter(UUID idCompany,String kode) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM annual_leave(:id_company) WHERE 1=1 ");

		if (kode != null) {
			sb.append(" AND kode='"+kode+"'");
		}
		
		List<AnnualLeave> list = super.entityManager
                .createNativeQuery(sb.toString(),AnnualLeave.class)
                .setParameter("id_company", idCompany)
                .getResultList();

		if (list.size() == 0) {
			List<AnnualLeave> nullList = new ArrayList<AnnualLeave>();
			return nullList;
		}
		else {
			return list;
		}
	}
}
