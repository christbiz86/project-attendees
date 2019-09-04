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
@Table(name = "company")
public class Company {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@Column(name = "kode")
	private String kode;

	@Column(name = "nama")
	private String nama;

	@Column(name = "jatah_cuti")
	private Integer jatahCuti;

	@Column(name = "toleransi_keterlambatan")
	private Integer toleransiKeterlambatan;

	@JoinColumn(name = "id_status", referencedColumnName = "id")
	@OneToOne
	private Status idStatus;

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

	public Status getIdStatus() {
		return idStatus;
	}

	public void setIdStatus(Status idStatus) {
		this.idStatus = idStatus;
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
