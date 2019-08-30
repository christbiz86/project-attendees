<<<<<<< HEAD
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
@Table(name="attendee")
public class Attendee {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@JoinColumn(name = "id_user_shift_project", referencedColumnName = "id")
	@OneToOne
	private UserShiftProject idUserShiftProject;	
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="ASIA/JAKARTA")
	@Column(name = "masuk")
	private Timestamp masuk;
	
	@Column(name="lokasi_masuk")
	private String lokasiMasuk;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="ASIA/JAKARTA")
	@Column(name = "pulang")
	private Timestamp pulang;
	
	@Column(name="lokasi_pulang")
	private String lokasiPulang;
	
	@Column(name="keterangan")
	private String keterangan;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UserShiftProject getIdUserShiftProject() {
		return idUserShiftProject;
	}

	public void setIdUserShiftProject(UserShiftProject idUserShiftProject) {
		this.idUserShiftProject = idUserShiftProject;
	}

	public Timestamp getMasuk() {
		return masuk;
	}

	public void setMasuk(Timestamp masuk) {
		this.masuk = masuk;
	}

	public String getLokasiMasuk() {
		return lokasiMasuk;
	}

	public void setLokasiMasuk(String lokasiMasuk) {
		this.lokasiMasuk = lokasiMasuk;
	}

	public Timestamp getPulang() {
		return pulang;
	}

	public void setPulang(Timestamp pulang) {
		this.pulang = pulang;
	}

	public String getLokasiPulang() {
		return lokasiPulang;
	}

	public void setLokasiPulang(String lokasiPulang) {
		this.lokasiPulang = lokasiPulang;
	}

	public String getKeterangan() {
		return keterangan;
	}

	public void setKeterangan(String keterangan) {
		this.keterangan = keterangan;
	}
=======
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
@Table(name="attendee")
public class Attendee {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;
	
	@JoinColumn(name = "id_user_shift_project", referencedColumnName = "id")
	@OneToOne
	private UserShiftProject idUserShiftProject;	
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="ASIA/JAKARTA")
	@Column(name = "masuk")
	private Timestamp masuk;
	
	@Column(name="lokasi_masuk")
	private String lokasiMasuk;
	
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="ASIA/JAKARTA")
	@Column(name = "pulang")
	private Timestamp pulang;
	
	@Column(name="lokasi_pulang")
	private String lokasiPulang;
	
	@Column(name="keterangan")
	private String keterangan;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UserShiftProject getIdUserShiftProject() {
		return idUserShiftProject;
	}

	public void setIdUserShiftProject(UserShiftProject idUserShiftProject) {
		this.idUserShiftProject = idUserShiftProject;
	}

	public Timestamp getMasuk() {
		return masuk;
	}

	public void setMasuk(Timestamp masuk) {
		this.masuk = masuk;
	}

	public String getLokasiMasuk() {
		return lokasiMasuk;
	}

	public void setLokasiMasuk(String lokasiMasuk) {
		this.lokasiMasuk = lokasiMasuk;
	}

	public Timestamp getPulang() {
		return pulang;
	}

	public void setPulang(Timestamp pulang) {
		this.pulang = pulang;
	}

	public String getLokasiPulang() {
		return lokasiPulang;
	}

	public void setLokasiPulang(String lokasiPulang) {
		this.lokasiPulang = lokasiPulang;
	}

	public String getKeterangan() {
		return keterangan;
	}

	public void setKeterangan(String keterangan) {
		this.keterangan = keterangan;
	}
>>>>>>> 8e5a7197421146f62a06c7eafaa45d8e5be30006
}