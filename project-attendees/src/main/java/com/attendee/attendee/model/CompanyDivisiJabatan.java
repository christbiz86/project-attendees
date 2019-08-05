package com.attendee.attendee.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="company_divisi_jabatan")
public class CompanyDivisiJabatan {

	@Id
	@Column(name="id")
	private UUID id;
	
	@JoinColumn(name = "id_company", referencedColumnName = "id")
	@OneToOne(optional = false)
	private Company idCompany;
	
	@JoinColumn(name = "id_divisi", referencedColumnName = "id")
	@OneToOne(optional = false)
	private Divisi idDivisi;

	@JoinColumn(name = "id_jabatan", referencedColumnName = "id")
	@OneToOne(optional = false)
	private Jabatan idJabatan;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Company getIdCompany() {
		return idCompany;
	}

	public void setIdCompany(Company idCompany) {
		this.idCompany = idCompany;
	}

	public Divisi getIdDivisi() {
		return idDivisi;
	}

	public void setIdDivisi(Divisi idDivisi) {
		this.idDivisi = idDivisi;
	}

	public Jabatan getIdJabatan() {
		return idJabatan;
	}

	public void setIdJabatan(Jabatan idJabatan) {
		this.idJabatan = idJabatan;
	}
	
}
