package com.attendee.attendee.model;

import java.sql.Time;
import java.util.UUID;

public class PojoShift {
	private Project project;
	private Time masuk;
	private Time pulang;
//	private CompanyUnitPosisi cup;
	private UserCompany uc;
	private Status worktime;
	
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public Time getMasuk() {
		return masuk;
	}
	public void setMasuk(Time masuk) {
		this.masuk = masuk;
	}
	public Time getPulang() {
		return pulang;
	}
	public void setPulang(Time pulang) {
		this.pulang = pulang;
	}
//	public CompanyUnitPosisi getCup() {
//		return cup;
//	}
//	public void setCup(CompanyUnitPosisi cup) {
//		this.cup = cup;
//	}
	public UserCompany getUc() {
		return uc;
	}
	public void setUc(UserCompany uc) {
		this.uc = uc;
	}
	public Status getWorktime() {
		return worktime;
	}
	public void setWorktime(Status worktime) {
		this.worktime = worktime;
	}

}