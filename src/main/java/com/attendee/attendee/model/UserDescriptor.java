package com.attendee.attendee.model;

import java.util.List;

public class UserDescriptor {
	private String nama;
	private List<List<String>> Descriptors;
	public String getNama() {
		return nama;
	}
	public void setNama(String nama) {
		this.nama = nama;
	}
	public List<List<String>> getDescriptors() {
		return Descriptors;
	}
	public void setDescriptors(List<List<String>> descriptors) {
		Descriptors = descriptors;
	}
}
