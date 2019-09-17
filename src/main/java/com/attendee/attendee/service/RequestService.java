package com.attendee.attendee.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.attendee.attendee.dao.RequestDao;
import com.attendee.attendee.dao.StatusDao;
import com.attendee.attendee.exception.InvalidDataException;
import com.attendee.attendee.exception.ValidationException;
import com.attendee.attendee.model.AnnualLeave;
import com.attendee.attendee.model.AnnualLeaveBk;
import com.attendee.attendee.model.Libur;
import com.attendee.attendee.model.LiburCompany;
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
	private NotificationService notifService;
	
	@Autowired
	private AnnualLeaveService annleaveService;
	
	@Autowired
	private LiburCompanyService lcService;
	
	public List<Request> findAll(){
		return aprDao.findAll();
	}
	
	public List<Request> findUserRequestByStatus(String status){
		UUID userCompanyId = ((UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal())
				.getUserCompany().getId();
		return aprDao.findUserRequestByStatus(status, userCompanyId);
	} 
	
	@Transactional
	public void insert(Request request) throws Exception {

		LiburCompany lc=new LiburCompany();
		Libur l=new Libur();
		l.setTglMulai(request.getTglMulai());
		lc.setCompany(((UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserCompany().getIdCompanyUnitPosisi().getIdCompany());
		lc.setLibur(l);
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(request.getTglMulai());
		int total=0;
		long diffInMillies = Math.abs(request.getTglAkhir().getTime() - request.getTglMulai().getTime());
	    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

		List<LiburCompany> list=lcService.findByTgl(lc);
		l.setTglMulai(request.getTglAkhir());
		lc.setLibur(l);
		List<LiburCompany> listend=lcService.findByTgl(lc);
		
		List<Integer> cuti=new ArrayList<>();
		List<Integer> weekend=new ArrayList<>();
		
		for(int j=0;j<=diff;j++) {
			cuti.add(calendar1.get(Calendar.DAY_OF_YEAR));
			if ((calendar1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)  
					|| (calendar1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) { 
				weekend.add(calendar1.get(Calendar.DAY_OF_YEAR));
			}
			calendar1.add(Calendar.DATE, 1);

		}

		List<List<Integer>> listLibur=new ArrayList<>();
		LocalDate localRequeststart = request.getTglMulai().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate localRequestend = request.getTglAkhir().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int dayRequest=localRequeststart.getDayOfYear();
		int dayRequestend=localRequestend.getDayOfYear();
		
		for(int k=0;k<list.size();k++) {
			LocalDate localDateLiburStart=list.get(k).getLibur().getTglMulai().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate localDateLiburEnd = list.get(k).getLibur().getTglAkhir().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			int dayLiburStart  = localDateLiburStart.getDayOfYear();
			int dayLiburEnd=localDateLiburEnd.getDayOfYear();
			List<Integer>temp=new ArrayList<Integer>();
			Calendar libur = Calendar.getInstance();
			libur.setTime(list.get(k).getLibur().getTglMulai());
			if((dayRequest>=dayLiburStart && dayRequest<=dayLiburEnd)||(dayRequestend>=dayLiburStart&&dayRequestend<=dayLiburEnd)
					||(dayLiburStart>=dayRequest && dayLiburStart<=dayRequestend)||(dayLiburEnd>=dayRequest&&dayLiburEnd<=dayRequestend)) {
				for(int i=dayLiburStart;i<=dayLiburEnd;i++) {
					temp.add(libur.get(Calendar.DAY_OF_YEAR));
					libur.add(Calendar.DATE, 1);
				}
				listLibur.add(temp);
			}
		}
		
		for(int k=0;k<listend.size();k++) {
			LocalDate localDateLiburStart=listend.get(k).getLibur().getTglMulai().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			LocalDate localDateLiburEnd = listend.get(k).getLibur().getTglAkhir().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			int dayLiburStart  = localDateLiburStart.getDayOfYear();
			int dayLiburEnd=localDateLiburEnd.getDayOfYear();
			List<Integer>temp=new ArrayList<Integer>();
			Calendar libur = Calendar.getInstance();
			libur.setTime(listend.get(k).getLibur().getTglMulai());
			if((dayRequest>=dayLiburStart && dayRequest<=dayLiburEnd)||(dayRequestend>=dayLiburStart&&dayRequestend<=dayLiburEnd)
					||(dayLiburStart>=dayRequest && dayLiburStart<=dayRequestend)||(dayLiburEnd>=dayRequest&&dayLiburEnd<=dayRequestend)) {
				for(int i=dayLiburStart,m=0;i<=dayLiburEnd;i++,m++) {
					temp.add(libur.get(Calendar.DAY_OF_YEAR));
					libur.add(Calendar.DATE, 1);
				}
				System.out.println("");
				listLibur.add(temp);
			}
		}
		
		
		for(List<Integer> ll:listLibur) {
			cuti.removeAll(ll);
		}
		cuti.removeAll(weekend);
		total=cuti.size();
		
		AnnualLeave annualLeave=new AnnualLeave();
		AnnualLeaveBk annBk=new AnnualLeaveBk();
		annualLeave.setIdCompany(((UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserCompany().getIdCompanyUnitPosisi().getIdCompany().getId());
		annBk.setKode(((UserPrinciple)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserCompany().getIdUser().getKode());
		annualLeave.setId(annBk);
		
		if(annleaveService.findByFilter(annualLeave).get(0).getSisaCuti()<total) {
			throw new ValidationException("Sisa cuti kurang");
		}
		
		request.setTotalHari(total);
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
		notifService.insert(notif);

	}
	
	@Transactional
	public void delete(Request request) throws Exception {
		valIdExist(request.getId());
		aprDao.delete(request);
	}
	
	@Transactional
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
		
		Notification notif = notifService.findByBk(request.getId());
		notifService.update(notif);
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
		if(request.getTglMulai().getTime() > request.getTglAkhir().getTime()) {
			listErr.add("Tangal mulai harus lebih kecil dari tanggal akhir ");
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
