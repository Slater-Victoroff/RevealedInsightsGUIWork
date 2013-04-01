package com.slatervictoroff.ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import com.slatervictoroff.dto.EmailMessage;

public class MessagePanel extends JPanel {
	private static final long serialVersionUID = 1508280293659702142L;
	private JTextField subjectText;
	private JTextField dateText;
	private JList recipientsList;
	private JTextPane messageBody;
	private JTextField senderText;

	public MessagePanel() {
		setBorder(new EmptyBorder(5, 5, 5, 5));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JLabel senderLbl = new JLabel("Sender:");
		GridBagConstraints gbc_senderLbl = new GridBagConstraints();
		gbc_senderLbl.insets = new Insets(0, 0, 5, 0);
		gbc_senderLbl.gridx = 0;
		gbc_senderLbl.gridy = 0;
		add(senderLbl, gbc_senderLbl);

		senderText = new JTextField();
		senderText.setEditable(false);
		GridBagConstraints gbc_senderText = new GridBagConstraints();
		gbc_senderText.insets = new Insets(0, 0, 5, 0);
		gbc_senderText.fill = GridBagConstraints.HORIZONTAL;
		gbc_senderText.gridx = 0;
		gbc_senderText.gridy = 1;
		add(senderText, gbc_senderText);
		senderText.setColumns(10);

		JLabel lblSubject = new JLabel("Subject:");
		GridBagConstraints gbc_lblSubject = new GridBagConstraints();
		gbc_lblSubject.insets = new Insets(0, 0, 5, 0);
		gbc_lblSubject.gridx = 0;
		gbc_lblSubject.gridy = 2;
		add(lblSubject, gbc_lblSubject);

		subjectText = new JTextField();
		subjectText.setEditable(false);
		GridBagConstraints gbc_subjectText = new GridBagConstraints();
		gbc_subjectText.insets = new Insets(0, 0, 5, 0);
		gbc_subjectText.fill = GridBagConstraints.HORIZONTAL;
		gbc_subjectText.gridx = 0;
		gbc_subjectText.gridy = 3;
		add(subjectText, gbc_subjectText);
		subjectText.setColumns(10);

		JLabel lblDate = new JLabel("Date:");
		GridBagConstraints gbc_lblDate = new GridBagConstraints();
		gbc_lblDate.insets = new Insets(0, 0, 5, 0);
		gbc_lblDate.gridx = 0;
		gbc_lblDate.gridy = 4;
		add(lblDate, gbc_lblDate);

		dateText = new JTextField();
		dateText.setEditable(false);
		GridBagConstraints gbc_dateText = new GridBagConstraints();
		gbc_dateText.insets = new Insets(0, 0, 5, 0);
		gbc_dateText.fill = GridBagConstraints.HORIZONTAL;
		gbc_dateText.gridx = 0;
		gbc_dateText.gridy = 5;
		add(dateText, gbc_dateText);
		dateText.setColumns(10);

		JLabel lblRecipients = new JLabel("Recipients:");
		GridBagConstraints gbc_lblRecipients = new GridBagConstraints();
		gbc_lblRecipients.insets = new Insets(0, 0, 5, 0);
		gbc_lblRecipients.gridx = 0;
		gbc_lblRecipients.gridy = 6;
		add(lblRecipients, gbc_lblRecipients);

		recipientsList = new JList();
		GridBagConstraints gbc_recipientsList = new GridBagConstraints();
		gbc_recipientsList.insets = new Insets(0, 0, 5, 0);
		gbc_recipientsList.fill = GridBagConstraints.BOTH;
		gbc_recipientsList.gridx = 0;
		gbc_recipientsList.gridy = 7;
		add(recipientsList, gbc_recipientsList);

		JLabel lblText = new JLabel("Text:");
		GridBagConstraints gbc_lblText = new GridBagConstraints();
		gbc_lblText.insets = new Insets(0, 0, 5, 0);
		gbc_lblText.gridx = 0;
		gbc_lblText.gridy = 8;
		add(lblText, gbc_lblText);

		messageBody = new JTextPane();
		messageBody.setEditable(false);
		GridBagConstraints gbc_messageBody = new GridBagConstraints();
		gbc_messageBody.fill = GridBagConstraints.BOTH;
		gbc_messageBody.gridx = 0;
		gbc_messageBody.gridy = 9;
		add(messageBody, gbc_messageBody);
	}

	public void setMessage(EmailMessage message) {
		if (message == null) {
			senderText.setText("");
			subjectText.setText("");
			dateText.setText("");
			recipientsList.setListData(new String[0]);
			messageBody.setText("");
			return;
		}
		Date date = message.getDate();
		senderText.setText(message.getSender());
		subjectText.setText(message.getSubject());
		dateText.setText(date == null ? "" : message.getDate().toString());
		recipientsList.setListData(message.getRecipients());
		messageBody.setText(message.getMessageBody());
	}

}
