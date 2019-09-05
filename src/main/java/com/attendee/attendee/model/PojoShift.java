package com.attendee.attendee.model;

import java.sql.Time;
import java.util.UUID;

public class PojoShift {
	private Project project;
	private Time masuk;
	private Time pulang;
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
	public Status getWorktime() {
		return worktime;
	}
	public void setWorktime(Status worktime) {
		this.worktime = worktime;
	}

}