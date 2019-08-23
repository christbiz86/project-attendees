package com.attendee.attendee.model;

import java.util.List;

public class UserDescriptor {
	private String nama;
	private List<List<Float>> descriptors;
	
	public String getNama() {
		return nama;
	}
	public void setNama(String nama) {
		this.nama = nama;
	}
	public List<List<Float>> getDescriptors() {
		return descriptors;
	}
	public void setDescriptors(List<List<Float>> descriptors) {
		this.descriptors = descriptors;
	}
}
