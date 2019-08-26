package com.attendee.attendee.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.attendee.attendee.model.AnnualLeave;

@Repository
public class AnnualLeaveDao extends ParentDao {
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<AnnualLeave> findAll() {
		List<AnnualLeave> list = super.entityManager
				.createQuery("FROM AnnualLeave")
				.getResultList();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public List<AnnualLeave> findByCompany(UUID idCompany) {
		
		List<AnnualLeave> list = super.entityManager
				.createQuery("FROM AnnualLeave where idCompany =:idCompany")
				.setParameter("idCompany", idCompany)
				.getResultList();
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public List<AnnualLeave> findByFilter(UUID idCompany,Long tahun) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("FROM AnnualLeave WHERE 1=1");
		
		if(idCompany != null) {

			sb.append(" AND idCompany='"+idCompany+"'");
		}
		
		if (tahun != null) {
			sb.append(" AND tahun='"+tahun+"'");
		}

		List<AnnualLeave> list = super.entityManager
                .createQuery(sb.toString())
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
