package com.attendee.attendee.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="user_company")
public class UserCompany {

	@Id
	@Column(name="id")
	private UUID id;
	
	@JoinColumn(name = "id_user", referencedColumnName = "id")
	@OneToOne(optional = false)
	private User idUser;

	@JoinColumn(name = "id_company_divisi_jabatan", referencedColumnName = "id")
	@OneToOne(optional = false)
	private CompanyDivisiJabatan idCompanyDivisiJabatan;	
	
	@JoinColumn(name = "id_tipe_user", referencedColumnName = "id")
	@OneToOne(optional = false)
	private TipeUser idTipeUser;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public User getIdUser() {
		return idUser;
	}

	public void setIdUser(User idUser) {
		this.idUser = idUser;
	}

	public TipeUser getIdTipeUser() {
		return idTipeUser;
	}

	public void setIdTipeUser(TipeUser idTipeUser) {
		this.idTipeUser = idTipeUser;
	}

	public CompanyDivisiJabatan getIdCompanyDivisiJabatan() {
		return idCompanyDivisiJabatan;
	}

	public void setIdCompanyDivisiJabatan(CompanyDivisiJabatan idCompanyDivisiJabatan) {
		this.idCompanyDivisiJabatan = idCompanyDivisiJabatan;
	}
	
}
