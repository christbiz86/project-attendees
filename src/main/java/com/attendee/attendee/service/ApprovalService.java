package com.attendee.attendee.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendee.attendee.dao.ApprovalDao;
import com.attendee.attendee.exception.InvalidDataException;
import com.attendee.attendee.model.Approval;
import com.attendee.attendee.model.User;

@Service
public class ApprovalService {
	@Autowired
	private ApprovalDao aprDao;
	
	public List<Approval> findAll(){
		return aprDao.findAll();
	}
	
	public List<Approval> findByStatus(String status){
		return aprDao.findByStatus(status);
	} 
	
	@Transactional
	public void insert(Approval approval) throws Exception {
		approval.setCreatedBy(approval.getUser());
		approval.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		
		valNonBk(approval);
		valBkNotExist(approval.getKode());
		aprDao.save(approval);
	}
	
	@Transactional
	public void delete(Approval approval) throws Exception {
		valIdExist(approval.getId());
		aprDao.delete(approval);
	}
	
	public void proses(Approval approval, User user, String putusan) throws Exception{
		if(!putusan.equals("accepted") && !putusan.equals("refused")) {
			throw new Exception("Status surat hanya bisa accepted atau refused");
		}
		
		approval.setUpdatedBy(user);
		approval.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
		approval.getStatus().setStatus(putusan);
		
		valDataNotChange(approval);
		aprDao.save(approval);
	}
	
	private void valDataNotChange(Approval approve) throws Exception {
		valIdExist(approve.getId());
		Approval getApproval = aprDao.findById(approve.getId());
		if(!approve.getKode().equals(getApproval.getKode()) 
				|| !approve.getTglAkhir().equals(getApproval.getTglAkhir())
				|| !approve.getTglMulai().equals(getApproval.getTglMulai())) {
			throw new Exception("Sebagian data diubah");
		}
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
		if(approval.getTglMulai() == null) {
			listErr.add("Tanggal mulai harus diisi");
		}
		if(approval.getTglAkhir() == null) {
			listErr.add("Tanggal akhir harus diisi");
		}
		if(approval.getStatus().getId().equals(null)) {
			listErr.add("Harus memiliki status");
		}
		if(approval.getCreatedAt() == null) {
			listErr.add("Tanggal dibuat harus ada");
		}
		if(approval.getCreatedBy().getId().equals(null)) {
			listErr.add("Harus ada nama pembuat");
		}
		
		if(!listErr.isEmpty()) {
			throw new InvalidDataException(listErr);
		}
	}
}
