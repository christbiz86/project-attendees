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
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="users",uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")})
public class User {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
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
	
	@Column(name="email",unique=true)
	private String email;

	@Column(name="telp")
	private String telp;

	@Column(name="username",unique=true)
	private String username;
	

	@Column(name="password")
	private String password;
	
	@Column(name="foto",unique=true)
	private String foto;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+7")
	@Column(name = "created_at")
 	private Date createdAt;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING,pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+7")
	@Column(name = "updated_at")
 	private Timestamp updatedAt;
	
	@JoinColumn(name = "created_by", referencedColumnName = "id")
//	@OneToOne(optional = false)
	@OneToOne
	private User createdBy;
	
	@JoinColumn(name = "updated_by", referencedColumnName = "id")
//	@OneToOne(optional = false)
	@OneToOne
	private User updatedBy;
	
	@JoinColumn(name = "id_status", referencedColumnName = "id")
//	@OneToOne(optional = false)	
	@OneToOne
	private Status idStatus;
	
//    @ManyToMany(cascade=CascadeType.REFRESH, fetch = FetchType.EAGER)
//    @JoinTable(name = "user_company", 
//      joinColumns = @JoinColumn(name = "id_user"), 
//      inverseJoinColumns = @JoinColumn(name = "id_tipe_user"))
//    private Set<TipeUser> roles = new HashSet<>();
	
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


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt =createdAt;
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

//	public Set<TipeUser> getRoles() {
//		return roles;
//	}
//
//	public void setRoles(Set<TipeUser> roles) {
//		this.roles = roles;
//	}


}