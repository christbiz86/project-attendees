package com.attendee.attendee.model;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "project")
public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private UUID id;
	
	@Column(name = "kode")
	private String kode;
	
	@Column(name = "nama_project")
	private String namaProject;
	
	@Column(name = "lokasi")
	private String lokasi;
	
	@Column(name = "global_code")
	private String globalCode;
	
	@Column(name = "created_at")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+7")
	private Timestamp createdAt;
	
	@Column(name = "updated_at")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+7")
	private Timestamp updatedAt;
	
	@JoinColumn(name = "created_by", referencedColumnName = "id")
	@OneToOne
	private User createdBy;	
	
	@JoinColumn(name = "updated_by", referencedColumnName = "id")
	@OneToOne
	private User updatedBy;
	
	@JoinColumn(name = "id_status", referencedColumnName = "id")
	@OneToOne	
	private Status status;

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

	public String getNamaProject() {
		return namaProject;
	}

	public void setNamaProject(String namaProject) {
		this.namaProject = namaProject;
	}

	public String getLokasi() {
		return lokasi;
	}

	public void setLokasi(String lokasi) {
		this.lokasi = lokasi;
	}

	public String getGlobalCode() {
		return globalCode;
	}

	public void setGlobalCode(String globalCode) {
		this.globalCode = globalCode;
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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}
