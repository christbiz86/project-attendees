<<<<<<< HEAD
package com.attendee.attendee.model;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Absen {
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="ASIA/JAKARTA")
 	private Timestamp jam;
	private String lokasi;

	public Timestamp getJam() {
		return jam;
	}

	public void setJam(Timestamp jam) {
		this.jam = jam;
	}

	public String getLokasi() {
		return lokasi;
	}

	public void setLokasi(String lokasi) {
		this.lokasi = lokasi;
	}
}
=======
package com.attendee.attendee.model;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Absen {
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="ASIA/JAKARTA")
 	private Timestamp jam;
	private String lokasi;

	public Timestamp getJam() {
		return jam;
	}

	public void setJam(Timestamp jam) {
		this.jam = jam;
	}

	public String getLokasi() {
		return lokasi;
	}

	public void setLokasi(String lokasi) {
		this.lokasi = lokasi;
	}
}
>>>>>>> 8e5a7197421146f62a06c7eafaa45d8e5be30006
