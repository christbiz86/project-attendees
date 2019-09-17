package com.attendee.attendee.model;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PojoAbsen {
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="ASIA/JAKARTA")
 	private Timestamp jam;
	private List<String> kode;

	public Timestamp getJam() {
		return jam;
	}

	public void setJam(Timestamp jam) {
		this.jam = jam;
	}

	public List<String> getKode() {
		return kode;
	}

	public void setKode(List<String> code) {
		this.kode = code;
	}
}
