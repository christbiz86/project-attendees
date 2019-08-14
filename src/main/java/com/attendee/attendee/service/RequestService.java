package com.attendee.attendee.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.attendee.attendee.dao.RequestDao;
import com.attendee.attendee.dao.StatusDao;
import com.attendee.attendee.exception.InvalidDataException;
import com.attendee.attendee.model.Request;
import com.attendee.attendee.model.User;

@Service
public class RequestService {
	@Autowired
	private RequestDao aprDao;
	
	@Autowired
	private StatusDao staDao;
	
	public List<Request> findAll(){
		return aprDao.findAll();
	}
	
	public List<Request> findByStatus(String status){
		return aprDao.findByStatus(status);
	} 
	
	@Transactional
	public void insert(Request request) throws Exception {
		request.setCreatedBy(request.getUser());
		request.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		request.setStatus(staDao.findByStatus("Request"));
		request.setKode("REQ" + aprDao.count());
		
		valNonBk(request);
		valBkNotExist(request.getKode());
		aprDao.save(request);
	}
	
	@Transactional
	public void delete(Request request) throws Exception {
		valIdExist(request.getId());
		aprDao.delete(request);
	}
	
	public void proses(Request request, User user, String putusan) throws Exception{
		if(!putusan.equals("Approved") && !putusan.equals("Rejected")) {

			throw new Exception("Status surat hanya bisa Approved atau Rejected");
		}
		
		if(!request.getStatus().getStatus().equals("Request")) {

			throw new Exception("Surat sudah di proses");
		}
		valDataNotChange(request);
		
		request.setUpdatedBy(user);
		request.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
		request.setStatus(staDao.findByStatus(putusan));
		
		aprDao.save(request);
	}
	
	private void valDataNotChange(Request approve) throws Exception {
		valIdExist(approve.getId());
		Request getApproval = aprDao.findById(approve.getId());
		if(!approve.getKode().equals(getApproval.getKode()) 
				&& !approve.getTglAkhir().equals(getApproval.getTglAkhir())
				&& !approve.getTglMulai().equals(getApproval.getTglMulai())) {
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
			throw new Exception("Data Request Sudah Ada");
		}
	}
	
	private void valNonBk(Request request) throws InvalidDataException{
		List<String> listErr = new ArrayList<String>();
		if(request.getUser().getId().equals(null)) {
			listErr.add("Harus memiliki user");
		}
		if(request.getTglMulai() == null) {
			listErr.add("Tanggal mulai harus diisi");
		}
		if(request.getTglAkhir() == null) {
			listErr.add("Tanggal akhir harus diisi");
		}
		if(request.getStatus().getId().equals(null)) {
			listErr.add("Harus memiliki status");
		}
		if(request.getCreatedAt() == null) {
			listErr.add("Tanggal dibuat harus ada");
		}
		if(request.getCreatedBy().getId().equals(null)) {
			listErr.add("Harus ada nama pembuat");
		}
		
		if(!listErr.isEmpty()) {
			throw new InvalidDataException(listErr);
		}
	}
}
