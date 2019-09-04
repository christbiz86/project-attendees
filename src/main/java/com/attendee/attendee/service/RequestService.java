package com.attendee.attendee.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.attendee.attendee.dao.NotificationDao;
import com.attendee.attendee.dao.RequestDao;
import com.attendee.attendee.dao.StatusDao;
import com.attendee.attendee.exception.InvalidDataException;
import com.attendee.attendee.model.Notification;
import com.attendee.attendee.model.Request;
import com.attendee.attendee.model.User;
import com.attendee.attendee.model.UserPrinciple;

@Service
public class RequestService {
	@Autowired
	private RequestDao aprDao;
	
	@Autowired
	private StatusDao staDao;
	
	@Autowired
	private NotificationDao notifDao;
	
	public List<Request> findAll(){
		return aprDao.findAll();
	}
	
	public List<Request> findByStatus(String status){
		return aprDao.findByStatus(status);
	} 
	
	@Transactional
	public void insert(Request request) throws Exception {
		request.setCreatedBy(
				((UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal())
				.getUserCompany().getIdUser());
		request.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		request.setStatus(staDao.findByStatus("Request"));
		request.setKode("REQ"+aprDao.countRows());
		
		valNonBk(request);
		valBkNotExist(request.getKode());
		aprDao.save(request);
		
		Notification notif = new Notification();
		notif.setRequest(aprDao.findByBk(request.getKode()));
		notif.setStatus(staDao.findByStatus("Unread"));
		System.out.println(notif.getStatus().getId());
		notifDao.save(notif);
		System.out.println("saved");
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
		
		Notification notif = notifDao.findByBk(request.getId());
		notif.setStatus(staDao.findByStatus("Unread"));
		notifDao.save(notif);
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
		if(request.getUserCompany().getIdUser().getId().equals(null)) {
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
	
	public List<Request> findByCompanyAndStatus(UUID idCompany,String status){
		return aprDao.findByCompanyAndStatus(idCompany,status);
	} 

}
