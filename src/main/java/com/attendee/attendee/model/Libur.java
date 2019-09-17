package com.attendee.attendee.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "libur")
public class Libur {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Column(name = "nama")
	private String nama;
	
	@JsonFormat(pattern= "yyyy-MM-dd HH:mm:ss", timezone="GMT+7")
	@Column(name = "tgl_mulai")
	private Date tglMulai;
	
	@JsonFormat(pattern= "yyyy-MM-dd HH:mm:ss", timezone="GMT+7")
	@Column(name = "tgl_akhir")
	private Date tglAkhir;
	
	@OneToOne
	@JoinColumn(name = "id_status", referencedColumnName = "id")
	private Status status;
	
	@Column(name = "created_at")
	@JsonFormat(pattern= "yyyy-MM-dd HH:mm:ss", timezone="Asia/Jakarta")
	private Timestamp createdAt;
	
	@Column(name = "updated_at")
	@JsonFormat(pattern= "yyyy-MM-dd HH:mm:ss", timezone="GMT+7")
	private Timestamp updatedAt;
	
	@OneToOne
	@JoinColumn(name = "created_by", referencedColumnName = "id")
	private User createdBy;	
	
	@OneToOne
	@JoinColumn(name = "updated_by", referencedColumnName = "id")
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

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
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
