package com.attendee.attendee.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="company")
public class Company {


	@Id
	@Column(name="id")
	private UUID id;
	
	@Column(name="kode")
	private String kode;
	
	
	@Column(name="nama")
	private String nama;
	
	@Column(name="jatah_cuti")
	private Integer jatahCuti;
	
	@Column(name="toleransi_keterlambatan")
	private Integer toleransiKeterlambatan;
	
	@JoinColumn(name = "created_at", referencedColumnName = "id")
	@OneToOne(optional = false)
	private Date createdAt;
	
	@JoinColumn(name = "updated_at", referencedColumnName = "id")
	@OneToOne(optional = false)
	private Date updatedAt;
	
	@JoinColumn(name = "created_by", referencedColumnName = "id")
	@OneToOne(optional = false)
	private UUID createdBy;	
	
	@JoinColumn(name = "updated_by", referencedColumnName = "id")
	@OneToOne(optional = false)
	private UUID updatedBy;

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

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public Integer getJatahCuti() {
		return jatahCuti;
	}

	public void setJatahCuti(Integer jatahCuti) {
		this.jatahCuti = jatahCuti;
	}

	public Integer getToleransiKeterlambatan() {
		return toleransiKeterlambatan;
	}

	public void setToleransiKeterlambatan(Integer toleransiKeterlambatan) {
		this.toleransiKeterlambatan = toleransiKeterlambatan;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public UUID getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(UUID createdBy) {
		this.createdBy = createdBy;
	}

	public UUID getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(UUID updatedBy) {
		this.updatedBy = updatedBy;
	}
	
}
