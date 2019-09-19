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
	@Column(name = "id")
	private UUID id;
	
	@Column(name = "nama_user")
	private String name;
	
	@Column(name = "unit")
	private String unit;
	
	@Column(name = "posisi")
	private String posisi;
	
	@Column(name = "hari_kerja")
	private Integer hariKerja;
	
	@Column(name = "jml_masuk")
	private Integer masuk;
	
	@Column(name = "jml_terlambat")
	private Integer terlambat;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getPosisi() {
		return posisi;
	}

	public void setPosisi(String posisi) {
		this.posisi = posisi;
	}

	public Integer getHariKerja() {
		return hariKerja;
	}

	public void setHariKerja(Integer hariKerja) {
		this.hariKerja = hariKerja;
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
