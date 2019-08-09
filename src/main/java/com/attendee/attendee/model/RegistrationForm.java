package com.attendee.attendee.model;

import java.util.Date;

public class RegistrationForm {
	private String kodeCompany;
	private String namaCompany;
	private int jatahCuti;
	private int toleransiKeterlambatan;
	private String statusCompany;
	
	private String kodeUser;
	private String namaUser;
	private String alamat;
	private Date tglLahir;
	private String telp;
	private String email;
	private String foto;
	private String statusUser;
	
	public String getKodeCompany() {
		return kodeCompany;
	}
	public void setKodeCompany(String kodeCompany) {
		this.kodeCompany = kodeCompany;
	}
	public String getNamaCompany() {
		return namaCompany;
	}
	public void setNamaCompany(String namaCompany) {
		this.namaCompany = namaCompany;
	}
	public int getJatahCuti() {
		return jatahCuti;
	}
	public void setJatahCuti(int jatahCuti) {
		this.jatahCuti = jatahCuti;
	}
	public int getToleransiKeterlambatan() {
		return toleransiKeterlambatan;
	}
	public void setToleransiKeterlambatan(int toleransiKeterlambatan) {
		this.toleransiKeterlambatan = toleransiKeterlambatan;
	}
	public String getStatusCompany() {
		return statusCompany;
	}
	public void setStatusCompany(String statusCompany) {
		this.statusCompany = statusCompany;
	}
	public String getKodeUser() {
		return kodeUser;
	}
	public void setKodeUser(String kodeUser) {
		this.kodeUser = kodeUser;
	}
	public String getNamaUser() {
		return namaUser;
	}
	public void setNamaUser(String namaUser) {
		this.namaUser = namaUser;
	}
	public String getAlamat() {
		return alamat;
	}
	public void setAlamat(String alamat) {
		this.alamat = alamat;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public String getStatusUser() {
		return statusUser;
	}
	public void setStatusUser(String statusUser) {
		this.statusUser = statusUser;
	}

}
