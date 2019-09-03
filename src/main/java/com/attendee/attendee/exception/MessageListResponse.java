package com.attendee.attendee.exception;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageListResponse {
	@JsonProperty(value="message")
	public List<String> message;
	
	public MessageListResponse(List<String> message) {
		this.message = message;
	}

	public List<String> getMessage() {
		return message;
	}

	public void setMessage(List<String> message) {
		this.message = message;
	}
}
