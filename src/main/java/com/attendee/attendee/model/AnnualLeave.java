package com.attendee.attendee.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class AnnualLeave {
	@Id
	@Column(name = "id_user_company")
	private UUID idUserCompany;
	
	@Column(name = "sisa_cuti")
	private Integer sisaCuti;

	public UUID getUserCompany() {
		return idUserCompany;
	}

	public void setUserCompany(UUID idUserCompany) {
		this.idUserCompany = idUserCompany;
	}

	public Integer getSisaCuti() {
		return sisaCuti;
	}

	public void setSisaCuti(Integer sisaCuti) {
		this.sisaCuti = sisaCuti;
	}
}
