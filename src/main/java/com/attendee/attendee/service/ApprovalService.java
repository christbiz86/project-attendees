package com.attendee.attendee.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendee.attendee.dao.ApprovalDao;
import com.attendee.attendee.exception.InvalidDataException;
import com.attendee.attendee.model.Approval;

@Service
public class ApprovalService {
	@Autowired
	private ApprovalDao aprDao;
	
	public void save(Approval approval) throws Exception {
		valNonBk(approval);
		valBkNotExist(approval.getKode());
		aprDao.save(approval);
	}
	
	public void delete(Approval approval) throws Exception {
		valIdExist(approval.getId());
		aprDao.delete(approval);
	}
	
	private void valIdExist(UUID id) throws Exception {
		if(!aprDao.isIdExist(id)) {
			throw new Exception("Data tidak ditemukan");
		}
	}
	
	private void valBkNotExist(String kode) throws Exception {
		if(aprDao.isBkExist(kode)) {
			throw new Exception("Data Approval Sudah Ada");
		}
	}
	
	private void valNonBk(Approval approval) throws InvalidDataException{
		List<String> listErr = new ArrayList<String>();
		if(approval.getUser().getId().equals(null)) {
			listErr.add("Harus memiliki user");
		}
		if(approval.getTglMulai().equals(null)) {
			listErr.add("Tanggal mulai harus diisi");
		}
		if(approval.getTglAkhir().equals(null)) {
			listErr.add("Tanggal akhir harus diisi");
		}
		if(approval.getStatus().getId().equals(null)) {
			listErr.add("Harus memiliki status");
		}
		if(approval.getCreatedAt().equals(null)) {
			listErr.add("Tanggal dibuat harus ada");
		}
		if(approval.getCreatedBy().equals(null)) {
			listErr.add("Harus ada nama pembuat");
		}
		
		if(!listErr.isEmpty()) {
			throw new InvalidDataException(listErr);
		}
	}
}
