package com.slatervictoroff.dto;

import java.util.Date;
import java.util.Map;

public class EmailMessage {
	public static String[] MOODS = { "innovative", "beautiful", "informative",
			"unconvincing", "boring" };

	private String sender;
	private String[] recipients;
	private Date date;
	private String subject;
	private String messageBody;
	private Map<String, Double> mood;

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String[] getRecipients() {
		return recipients;
	}

	public void setRecipients(String[] recipients) {
		this.recipients = recipients;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}

	public Map<String, Double> getMood() {
		return mood;
	}

	public void setMood(Map<String, Double> mood) {
		this.mood = mood;
	}

}
