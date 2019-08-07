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
@Table(name="users")
public class User {
	
	@Id
	@Column(name="id")
	private UUID id;
	
	@Column(name="kode")
	private String kode;
	
	@Column(name="nama")
	private String nama;
	
	@Column(name="alamat")
	private String alamat;
	
	@Temporal(TemporalType.DATE)
	@Column(name="tgl_lahir")
	private Date tglLahir;
	
	@Column(name="telp")
	private String telp;
	
	@Column(name="email")
	private String email;
	
	@Column(name="password")
	private String password;
	
	@Column(name="foto")
	private String foto;
	
	@JoinColumn(name = "id_status", referencedColumnName = "id")
	@OneToOne
	private Status idStatus;
	
	@Temporal(TemporalType.DATE)
	@Column(name="created_at")
	private Date createdAt;
	
	@Temporal(TemporalType.DATE)
	@Column(name="updated_at")
	private Date updatedAt;
	
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

	public String getAlamat() {
		return alamat;
	}

	public void setAlamat(String alamat) {
		this.alamat = alamat;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Date getCreateAt() {
		return createdAt;
	}

	public void setCreateAt(Date createAt) {
		this.createdAt = createAt;
	}

	public Date getUpdateAt() {
		return updatedAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updatedAt = updateAt;
	}

	public User getCreateBy() {
		return createdBy;
	}

	public void setCreateBy(User createBy) {
		this.createdBy = createBy;
	}

	public User getUpdateBy() {
		return updatedBy;
	}

	public void setUpdateBy(User updateBy) {
		this.updatedBy = updateBy;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getTglLahir() {
		return tglLahir;
	}

	public void setTglLahir(Date tglLahir) {
		this.tglLahir = tglLahir;
	}

	public String getTelp() {
		return telp;
	}

	public void setTelp(String telp) {
		this.telp = telp;
	}

	public Status getIdStatus() {
		return idStatus;
	}

	public void setIdStatus(Status idStatus) {
		this.idStatus = idStatus;
	}

}