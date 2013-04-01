package com.slatervictoroff.dto;

import java.util.ArrayList;
import java.util.List;

public class User {

	private String name;
	private List<EmailMessage> messages = new ArrayList<EmailMessage>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<EmailMessage> getMessages() {
		return messages;
	}

	public void setMessages(List<EmailMessage> messages) {
		this.messages = messages;
	}

}
