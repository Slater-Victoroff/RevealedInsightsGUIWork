package com.slatervictoroff;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.slatervictoroff.dto.EmailMessage;
import com.slatervictoroff.dto.User;

/**
 * <p>
 * Load email messages from specified directory.
 * </p>
 * 
 * @author ssamayoa
 * 
 */
public class EmailMessageLoader implements Runnable {

	private File directory;
	private Map<String, User> users = new HashMap<String, User>();
	private List<EmailMessage> messages = new ArrayList<EmailMessage>();
	private int failedCount;
	private boolean canceled;
	private boolean error;
	private String errorMessage;

	public EmailMessageLoader() {
	}

	public EmailMessageLoader(File directory) {
		this.directory = directory;
	}

	public void setDirectory(File directory) {
		this.directory = directory;
	}

	public void setCanceled(boolean canceled) {
		this.canceled = canceled;
	}

	public Map<String, User> getUsers() {
		return users;
	}

	public List<EmailMessage> getMessages() {
		return messages;
	}

	public int getLoadedCount() {
		return messages.size();
	}

	public int getFailedCount() {
		return failedCount;
	}

	public boolean isCanceled() {
		return canceled;
	}

	public boolean isError() {
		return error;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	private Gson gson = new GsonBuilder().setDateFormat("yyyyMMddHHmmss Z")
			.create();

	/**
	 * <p>
	 * Loads the message from the file.
	 * </p>
	 * 
	 * @param file
	 * @return EmailMessage or null if something goes wrong.
	 */
	protected EmailMessage loadMessage(File file) {
		try {
			InputStream in = new FileInputStream(file);
			try {
				String json = IOUtils.toString(in);
				return gson.fromJson(json, EmailMessage.class);
			} finally {
				in.close();
			}
		} catch (Exception ex) {
			// Nothing...
		}
		return null;
	}

	@Override
	public void run() {
		users = new HashMap<String, User>();
		messages = new ArrayList<EmailMessage>();
		failedCount = 0;
		canceled = false;
		error = false;
		errorMessage = null;

		try {
			File[] files = directory.listFiles(new FilenameFilter() {

				@Override
				public boolean accept(File dir, String name) {
					return name != null && name.endsWith(".txt");
				}
			});

			for (File file : files) {
				if (canceled) {
					break;
				}
				EmailMessage message = loadMessage(file);
				if (message == null) {
					failedCount++;
					continue;
				}
				String sender = message.getSender();
				if (sender != null && !"".equals(sender.trim())) {
					User user = users.get(sender);
					if (user == null) {
						user = new User();
						user.setName(sender);
						users.put(sender, user);
					}
					user.getMessages().add(message);
				}
				messages.add(message);
			}
		} catch (Exception ex) {
			String className = ex.getClass().getSimpleName();
			error = true;
			errorMessage = ex.getMessage();
			if (errorMessage == null || "".equals(errorMessage.trim())) {
				errorMessage = className;
			} else {
				errorMessage = className + ":" + errorMessage;
			}
		}
	}
}
