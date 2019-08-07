package com.attendee.attendee.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;

@Entity
public class Shift {
	@Id
	@Column(name = "id")
	private UUID id;
	
	@Column(name = "kode")
	private String kode;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "masuk")
	private Date masuk;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "pulang")
	private Date pulang;
	
	@JoinColumn(name = "id_status", referencedColumnName = "id")
	@OneToOne
	private Status status;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	@CreatedDate
	private Date createdAt;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "updated_at")
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

	public Date getMasuk() {
		return masuk;
	}

	public void setMasuk(Date masuk) {
		this.masuk = masuk;
	}

	public Date getPulang() {
		return pulang;
	}

	public void setPulang(Date pulang) {
		this.pulang = pulang;
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
