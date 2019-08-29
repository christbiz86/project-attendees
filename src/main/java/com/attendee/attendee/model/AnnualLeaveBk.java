package com.attendee.attendee.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AnnualLeaveBk implements Serializable{
	
	@Column(name = "kode")
	private String kode;
	
	@Column(name="tahun")
	private Long tahun;
	
	public String getKode() {
		return kode;
	}

	public void setKode(String kode) {
		this.kode = kode;
	}
	public Long getTahun() {
		return tahun;
	}

	public void setTahun(Long tahun) {
		this.tahun = tahun;
	}

}