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
@Table(name="posisi")
public class Posisi {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@Column(name="posisi",unique=true)
	private String posisi;
	
	@JoinColumn(name = "id_status", referencedColumnName = "id")
	@OneToOne
	private Status idStatus;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+7")
	@Column(name = "created_at")
	private Timestamp createdAt;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+7")
	@Column(name = "updated_at")
	private Timestamp updatedAt;
	
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

	public String getPosisi() {
		return posisi;
	}

	public void setPosisi(String posisi) {
		this.posisi = posisi;
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
