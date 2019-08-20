package com.attendee.attendee.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;

@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(
	    name = "getAnnualLeaveRecap", 
	    procedureName = "annual_leave_recap", 
	    resultClasses = { AnnualLeaveRecap.class },
	    parameters = {
	    		@StoredProcedureParameter(
	    				mode = ParameterMode.IN, 
	                    name = "company", 
	                    type = String.class),
	            @StoredProcedureParameter(
	                    mode = ParameterMode.IN, 
	                    name = "start_date", 
	                    type = Date.class),
	            @StoredProcedureParameter(
	            		mode = ParameterMode.IN,
	            		name = "end_date",
	                    type = Date.class)
	    }
	)
})

@Entity
public class AnnualLeaveRecap {
	@Id
	@Column(name = "nama_user")
	private String namaUser;
	
	@Column(name = "unit")
	private String unit;
	
	@Column(name = "posisi")
	private String posisi;
	
	@Column(name = "sisa_cuti")
	private Integer sisaCuti;
	
	@Column(name = "tahun")
	private Integer tahun;

	public String getNamaUser() {
		return namaUser;
	}

	public void setNamaUser(String namaUser) {
		this.namaUser = namaUser;
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

	public Integer getSisaCuti() {
		return sisaCuti;
	}

	public void setSisaCuti(Integer sisaCuti) {
		this.sisaCuti = sisaCuti;
	}

	public Integer getTahun() {
		return tahun;
	}

	public void setTahun(Integer tahun) {
		this.tahun = tahun;
	}
}
