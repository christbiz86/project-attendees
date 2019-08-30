<<<<<<< HEAD
package com.attendee.attendee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendee.attendee.dao.AnnualLeaveDao;
import com.attendee.attendee.model.AnnualLeave;

@Service
public class AnnualLeaveService {
	@Autowired
	private AnnualLeaveDao anLevDao;

	public List<AnnualLeave> findByFilter(AnnualLeave annualLeave) {
		return anLevDao.findByFilter(annualLeave.getIdCompany(),annualLeave.getId().getKode() );
	}

=======
package com.attendee.attendee.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendee.attendee.dao.AnnualLeaveDao;
import com.attendee.attendee.model.AnnualLeave;

@Service
public class AnnualLeaveService {
	@Autowired
	private AnnualLeaveDao anLevDao;

	public List<AnnualLeave> findByFilter(AnnualLeave annualLeave) {
		return anLevDao.findByFilter(annualLeave.getIdCompany(),annualLeave.getId().getKode() );
	}

>>>>>>> 8e5a7197421146f62a06c7eafaa45d8e5be30006
}