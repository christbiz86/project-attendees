package com.attendee.attendee.model;

import java.util.List;

public class UserDescriptor {
	private String name;
	private List<List<Double>> descriptors;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<List<Double>> getDescriptors() {
		return descriptors;
	}
	public void setDescriptors(List<List<Double>> descriptors) {
		this.descriptors = descriptors;
	}
}
