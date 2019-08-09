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
@Table(name="company")
public class Company {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Column(name="kode")
	private String kode;
	
	
	@Column(name="nama")
	private String nama;
	
	@Column(name="jatah_cuti")
	private Integer jatahCuti;
	
	@Column(name="toleransi_keterlambatan")
	private Integer toleransiKeterlambatan;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+7")
	@Column(name = "created_at")
	private Timestamp createdAt;

	@JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+7")
	@Column(name = "updated_at")
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
