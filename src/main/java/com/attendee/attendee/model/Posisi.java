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
@Table(name="posisi")
public class Posisi {

	@Id
	@Column(name="id")
	private UUID id;
	
	@Column(name="posisi")
	private String posisi;
	
	@JoinColumn(name = "id_status", referencedColumnName = "id")
	@OneToOne(optional = false)
	private Status idStatus;
	
	@Temporal(TemporalType.DATE)
	@Column(name="create_at")
	private Date createAt;
	
	@Temporal(TemporalType.DATE)
	@Column(name="update_at")
	private Date updateAt;
	
	@JoinColumn(name = "create_by", referencedColumnName = "id")
	@OneToOne(optional = false)
	private User createBy;
	
	@JoinColumn(name = "update_by", referencedColumnName = "id")
	@OneToOne(optional = false)
	private User updateBy;

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

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public User getCreateBy() {
		return createBy;
	}

	public void setCreateBy(User createBy) {
		this.createBy = createBy;
	}

	public User getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(User updateBy) {
		this.updateBy = updateBy;
	}

}
