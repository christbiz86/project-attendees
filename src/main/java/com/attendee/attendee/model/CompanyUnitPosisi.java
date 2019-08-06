package com.attendee.attendee.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="company_unit_posisi")
public class CompanyUnitPosisi {

	@Id
	@Column(name="id")
	private UUID id;
	
	@JoinColumn(name = "id_company", referencedColumnName = "id")
	@OneToOne(optional = false)
	private Company idCompany;
	
	@JoinColumn(name = "id_unit", referencedColumnName = "id")
	@OneToOne(optional = false)
	private Unit idUnit;

	@JoinColumn(name = "id_posisi", referencedColumnName = "id")
	@OneToOne(optional = false)
	private Posisi idPosisi;

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

	public Unit getIdUnit() {
		return idUnit;
	}

	public void setIdUnit(Unit idUnit) {
		this.idUnit = idUnit;
	}

	public Posisi getIdPosisi() {
		return idPosisi;
	}

	public void setIdPosisi(Posisi idPosisi) {
		this.idPosisi = idPosisi;
	}

}
