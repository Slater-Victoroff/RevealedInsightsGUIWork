package com.slatervictoroff.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;

public class LoadingDialog extends JFrame {
	private static final long serialVersionUID = 8380366819383494310L;

	private JButton cancelBtn;

	public LoadingDialog() {
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		getContentPane().setMaximumSize(new Dimension(0, 0));
		setResizable(false);
		setTitle("Loading messages...");
		setBounds(100, 100, 270, 70);
		getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		cancelBtn = new JButton("Cancel");
		cancelBtn.setVisible(true);
		getContentPane().add(cancelBtn);
		setLocationRelativeTo(null);
	}

	public JButton getCancelBtn() {
		return cancelBtn;
	}
}
