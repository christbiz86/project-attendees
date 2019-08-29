package com.attendee.attendee.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "libur_company")
public class LiburCompany {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@OneToOne
	@JoinColumn(name = "id_libur", referencedColumnName = "id")
	private Libur libur;
	
	@OneToOne
	@JoinColumn(name = "id_company", referencedColumnName = "id")
	private Company company;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Libur getLibur() {
		return libur;
	}

	public void setLibur(Libur libur) {
		this.libur = libur;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
}
