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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Request {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private UUID id;
	
	@Column(name = "kode")
	private String kode;
	
	@JoinColumn(name = "id_user_company", referencedColumnName = "id")
	@OneToOne
	private UserCompany userCompany;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "tgl_mulai")
	private Date tglMulai;
	
	@Temporal(TemporalType.DATE)
	@Column (name = "tgl_akhir")
	private Date tglAkhir;
	
	@Column(name="keterangan")
	private String keterangan;
	
	@JoinColumn(name = "id_status", referencedColumnName = "id")
	@OneToOne
	private Status status;
	
	@Column(name = "created_at")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+7")
	private Timestamp createdAt;
	
	@Column(name = "updated_at")
	@JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+7")
	private Timestamp updatedAt;
	
	@JoinColumn(name = "created_by", referencedColumnName = "id")
	@OneToOne
	private User createdBy;	
	
	@JoinColumn(name = "updated_by", referencedColumnName = "id")
	@OneToOne
	private User updatedBy;

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

	public UserCompany getUserCompany() {
		return userCompany;
	}

	public void setUserCompany(UserCompany userCompany) {
		this.userCompany = userCompany;
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

	public String getKeterangan() {
		return keterangan;
	}

	public void setKeterangan(String keterangan) {
		this.keterangan = keterangan;
	}

	public Date getCreatedAt() {
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
