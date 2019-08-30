<<<<<<< HEAD
package com.attendee.attendee.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;

@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(
	    name = "getAnnualLeave", 
	    procedureName = "annual_leave", 
	    resultClasses = { AnnualLeave.class },
	    parameters = {
	    		@StoredProcedureParameter(
	    				mode = ParameterMode.IN, 
	                    name = "id_company", 
	                    type = UUID.class)
	    }
	)
})

@Entity
public class AnnualLeave {

	@EmbeddedId
	private AnnualLeaveBk id;
	
	@Column(name="nama_user")
	private String nama;
	
	@Column(name="id_company")
	private UUID idCompany;
	
	@Column(name="company")
	private String company;
	
	@Column(name="unit")
	private String unit;
	
	@Column(name="posisi")
	private String posisi;
	
	@Column(name="sisa_cuti")
	private Integer sisaCuti;
	
	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public UUID getIdCompany() {
		return idCompany;
	}

	public void setIdCompany(UUID idCompany) {
		this.idCompany = idCompany;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
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

	public AnnualLeaveBk getId() {
		return id;
	}

	public void setId(AnnualLeaveBk id) {
		this.id = id;
	}
	
}
=======
package com.attendee.attendee.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;

@NamedStoredProcedureQueries({
	@NamedStoredProcedureQuery(
	    name = "getAnnualLeave", 
	    procedureName = "annual_leave", 
	    resultClasses = { AnnualLeave.class },
	    parameters = {
	    		@StoredProcedureParameter(
	    				mode = ParameterMode.IN, 
	                    name = "id_company", 
	                    type = UUID.class)
	    }
	)
})

@Entity
public class AnnualLeave {

	@EmbeddedId
	private AnnualLeaveBk id;
	
	@Column(name="nama_user")
	private String nama;
	
	@Column(name="id_company")
	private UUID idCompany;
	
	@Column(name="company")
	private String company;
	
	@Column(name="unit")
	private String unit;
	
	@Column(name="posisi")
	private String posisi;
	
	@Column(name="sisa_cuti")
	private Integer sisaCuti;
	
	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public UUID getIdCompany() {
		return idCompany;
	}

	public void setIdCompany(UUID idCompany) {
		this.idCompany = idCompany;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
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

	public AnnualLeaveBk getId() {
		return id;
	}

	public void setId(AnnualLeaveBk id) {
		this.id = id;
	}
	
}
>>>>>>> 8e5a7197421146f62a06c7eafaa45d8e5be30006
