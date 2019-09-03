package com.attendee.attendee.exception;

import java.util.List;

@SuppressWarnings("serial")
public class InvalidDataException extends Exception
{	
	private List<String> messages;
	
	public InvalidDataException(List<String> messages){
		this.messages = messages;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
}
