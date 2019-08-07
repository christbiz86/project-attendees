package com.attendee.attendee.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "libur")
public class Libur {
	@Id
	@Column(name = "id")
	private UUID id;
	
	@Column(name = "nama")
	private String nama;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "tgl_mulai")
	private Date tglMulai;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "tgl_akhir")
	private Date tglAkhir;
	
	@JoinColumn(name = "id_status", referencedColumnName = "id")
	@OneToOne
	private Status status;
	
	@Temporal(TemporalType.DATE) 
	@Column(name = "created_at")
	private Date createdAt;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "updated_at")
	private Date updatedAt;
	
	@JoinColumn(name = "created_by", referencedColumnName = "id")
	@OneToOne(optional = false)
	private User createdBy;	
	
	@JoinColumn(name = "updated_by", referencedColumnName = "id")
	@OneToOne(optional = false)
	private User updatedBy;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public Date getTglMulai() {
		return tglMulai;
	}

	public void setTglMulai(Date tglMulai) {
		this.tglMulai = tglMulai;
	}

	public Date getTglAkhir() {
		return tglAkhir;
	}

	public void setTglAkhir(Date tglAkhir) {
		this.tglAkhir = tglAkhir;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
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

	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}
}
