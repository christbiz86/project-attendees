package com.attendee.attendee.model;

import java.sql.Time;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(
	    name = "getAttendeeRecapDetail", 
	    procedureName = "attendee_recap_detail", 
	    resultClasses = { AttendeeRecapDetail.class },
	    parameters = {
	            @StoredProcedureParameter(
	                    mode = ParameterMode.IN, 
	                    name = "startDate", 
	                    type = Date.class),
	            @StoredProcedureParameter(
	            		mode = ParameterMode.IN,
	            		name = "endDate",
	                    type = Date.class)
	    }
	)
})

@Entity
public class AttendeeRecapDetail {
	@Id
	@Column(name = "id")
	private UUID id;
	
	@Column(name = "kode")
	private String kode;
	
	@Column(name = "nama_user")
	private String name;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "tanggal")
	private Date tanggal;
	
	@Column(name = "clock_in")
	private Time clockIn;
	
	@Column(name = "clock_out")
	private Time clockOut;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
	
	public String getKode() {
		return kode;
	}

	public void setKode(String kode) {
		this.kode = kode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getTanggal() {
		return tanggal;
	}

	public void setTanggal(Date tanggal) {
		this.tanggal = tanggal;
	}

	public Time getClockIn() {
		return clockIn;
	}

	public void setClockIn(Time clockIn) {
		this.clockIn = clockIn;
	}

	public Time getClockOut() {
		return clockOut;
	}

	public void setClockOut(Time clockOut) {
		this.clockOut = clockOut;
	}	
}
