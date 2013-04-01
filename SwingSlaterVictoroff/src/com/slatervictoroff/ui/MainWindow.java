package com.slatervictoroff.ui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.SwingWorker;

import com.slatervictoroff.EmailMessageLoader;

public class MainWindow {

	private JFrame frmEmailViewer;
	private JSplitPane splitPane;
	private ShowChartPanel showChartPanel;
	private MessagePanel messagePanel;
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem openItem;
	private JMenuItem exitItem;

	private JFileChooser fileChooser;
	private LoadingDialog loadingDialog;
	private EmailMessageLoader messageLoader;
	private SwingWorker<Void, Void> worker;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmEmailViewer.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainWindow() {
		initialize();
	}

	private void initialize() {
		frmEmailViewer = new JFrame();
		frmEmailViewer.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmEmailViewer.setPreferredSize(new Dimension(800, 600));
		frmEmailViewer.setTitle("Email viewer");
		frmEmailViewer.setVisible(true);
		frmEmailViewer.getContentPane()
				.setLayout(
						new BoxLayout(frmEmailViewer.getContentPane(),
								BoxLayout.X_AXIS));
		messagePanel = new MessagePanel();
		showChartPanel = new ShowChartPanel(messagePanel);
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, showChartPanel,
				messagePanel);
		splitPane.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		showChartPanel
				.setLayout(new BoxLayout(showChartPanel, BoxLayout.X_AXIS));
		frmEmailViewer.getContentPane().add(splitPane);
		frmEmailViewer.pack();
		splitPane.setDividerLocation(0.75);

		menuBar = new JMenuBar();
		frmEmailViewer.setJMenuBar(menuBar);

		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		openItem = new JMenuItem("Open messages");
		openItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (getFileChooser().showOpenDialog(frmEmailViewer) != JFileChooser.APPROVE_OPTION) {
					return;
				}
				getMessageLoader().setDirectory(
						getFileChooser().getSelectedFile());
				frmEmailViewer.setEnabled(false);
				loadingDialog = new LoadingDialog();
				loadingDialog.getCancelBtn().addActionListener(
						new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								getMessageLoader().setCanceled(true);
							}
						});
				loadingDialog.setVisible(true);

				worker = new SwingWorker<Void, Void>() {
					@Override
					protected Void doInBackground() throws Exception {
						messageLoader.run();
						return null;
					}

					@Override
					public void done() {
						frmEmailViewer.setEnabled(true);
						loadingDialog.dispose();

						if (getMessageLoader().isCanceled()) {
							JOptionPane.showMessageDialog(frmEmailViewer,
									"Canceled by user", "Canceled",
									JOptionPane.INFORMATION_MESSAGE);
							return;
						}
						if (getMessageLoader().isError()) {
							JOptionPane.showMessageDialog(frmEmailViewer,
									"Unexpected error:\n"
											+ getMessageLoader()
													.getErrorMessage(),
									"Error", JOptionPane.ERROR_MESSAGE);
							return;
						}
						if (getMessageLoader().getFailedCount() > 0) {
							JOptionPane.showMessageDialog(frmEmailViewer,
									"Messages loaded but "
											+ getMessageLoader()
													.getFailedCount()
											+ " failed.", "Warning",
									JOptionPane.WARNING_MESSAGE);
						}
						getShowChartPanel().setUsers(
								getMessageLoader().getUsers());
						getMessagePanel().setMessage(null);
					}

				};
				worker.execute();
			}
		});
		fileMenu.add(openItem);

		exitItem = new JMenuItem("Exit");
		exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmEmailViewer.dispose();
			}
		});
		fileMenu.add(exitItem);

		frmEmailViewer.setLocationRelativeTo(null);

	}

	protected JFileChooser getFileChooser() {
		if (fileChooser == null) {
			fileChooser = new JFileChooser();
			fileChooser
					.setDialogTitle("Select the directory where email messages are stored");
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fileChooser.setAcceptAllFileFilterUsed(false);
		}
		return fileChooser;
	}

	protected EmailMessageLoader getMessageLoader() {
		if (messageLoader == null) {
			messageLoader = new EmailMessageLoader();
		}
		return messageLoader;
	}

	public ShowChartPanel getShowChartPanel() {
		return showChartPanel;
	}

	public MessagePanel getMessagePanel() {
		return messagePanel;
	}
}
