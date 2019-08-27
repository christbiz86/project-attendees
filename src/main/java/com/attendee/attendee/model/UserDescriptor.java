package com.attendee.attendee.model;

import java.util.List;

public class UserDescriptor {
	private String name;
	private List<List<String>> Descriptors;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<List<String>> getDescriptors() {
		return Descriptors;
	}
	public void setDescriptors(List<List<String>> descriptors) {
		Descriptors = descriptors;
	}
}
