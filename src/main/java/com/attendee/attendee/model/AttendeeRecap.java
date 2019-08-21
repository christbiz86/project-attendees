package com.attendee.attendee.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(
	    name = "getAttendeeRecap", 
	    procedureName = "attendee_recap", 
	    resultClasses = { AttendeeRecap.class },
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
public class AttendeeRecap{
	@Id
	@Column(name = "nama_user")
	private String name;
	
	@Column(name = "jml_terlambat")
	private Integer terlambat;
	
	@Column(name = "jml_masuk")
	private Integer masuk;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTerlambat() {
		return terlambat;
	}

	public void setTerlambat(Integer terlambat) {
		this.terlambat = terlambat;
	}

	public Integer getMasuk() {
		return masuk;
	}

	public void setMasuk(Integer masuk) {
		this.masuk = masuk;
	}
}
