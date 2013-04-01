package com.slatervictoroff.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeriesDataItem;

import com.slatervictoroff.dto.EmailMessage;
import com.slatervictoroff.dto.User;

public class ShowChartPanel extends JPanel {
	private static final long serialVersionUID = -5612492544689482571L;
	private static final String TITLE = "Mood chart";
	private static final String TIME_AXIS_LABEL = "Date";
	private static final String VALUE_AXIS_LABEL = "Mood level";

	private ChartPanel chartPanel;
	private JFreeChart chart;

	private JPanel moodPanel;
	private JCheckBox innovativeCkb;
	private JCheckBox beautifulCkb;
	private JCheckBox informativeCkb;
	private JCheckBox unconvincingCkb;
	private JCheckBox boringCkb;
	private JPanel panel;
	private JCheckBox showLegendCkb;
	private JLabel lblNewLabel;

	private MessagePanel messagePanel;

	private Map<String, User> users;

	public static class MessageDataItem extends TimeSeriesDataItem {
		private static final long serialVersionUID = 2335691303345314182L;

		private EmailMessage message;

		public MessageDataItem(RegularTimePeriod period,
				java.lang.Number value, EmailMessage message) {
			super(period, value);
			this.message = message;
		}

		public EmailMessage getMessage() {
			return message;
		}
	}

	public ShowChartPanel() {
		panel = new JPanel();
		add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		chart = ChartFactory.createTimeSeriesChart(TITLE, TIME_AXIS_LABEL,
				VALUE_AXIS_LABEL, null, true, false, false);
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		chartPanel = new ChartPanel(chart);
		chartPanel.setVisible(false);
		ChartMouseListener listener = new ChartMouseListener() {

			@Override
			public void chartMouseMoved(ChartMouseEvent event) {
			}

			@Override
			public void chartMouseClicked(ChartMouseEvent event) {
				Object o = event.getEntity();
				if (o instanceof XYItemEntity) {
					XYItemEntity itemEntity = (XYItemEntity) o;
					TimeSeriesCollection seriesCollection = (TimeSeriesCollection) itemEntity
							.getDataset();
					TimeSeries series = seriesCollection.getSeries(itemEntity
							.getSeriesIndex());
					TimeSeriesDataItem item = series.getDataItem(itemEntity
							.getItem());
					if (item instanceof MessageDataItem) {
						if (messagePanel != null) {
							messagePanel.setMessage(((MessageDataItem) item)
									.getMessage());
						}
					}
				}
			}
		};
		chartPanel.addChartMouseListener(listener);
		panel.add(chartPanel, BorderLayout.CENTER);

		moodPanel = new JPanel();
		moodPanel.setVisible(false);
		panel.add(moodPanel, BorderLayout.SOUTH);
		moodPanel.setMaximumSize(new Dimension(0, 0));

		innovativeCkb = new JCheckBox("innovative");
		innovativeCkb.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				recreateChart();
			}
		});
		moodPanel.add(innovativeCkb);

		beautifulCkb = new JCheckBox("beautiful");
		beautifulCkb.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				recreateChart();
			}
		});
		moodPanel.add(beautifulCkb);

		informativeCkb = new JCheckBox("informative");
		informativeCkb.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				recreateChart();
			}
		});
		moodPanel.add(informativeCkb);

		unconvincingCkb = new JCheckBox("unconvincing");
		unconvincingCkb.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				recreateChart();
			}
		});
		moodPanel.add(unconvincingCkb);

		boringCkb = new JCheckBox("boring");
		boringCkb.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				recreateChart();
			}
		});
		moodPanel.add(boringCkb);

		lblNewLabel = new JLabel(" / ");
		moodPanel.add(lblNewLabel);

		showLegendCkb = new JCheckBox("Show legend");
		showLegendCkb.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				recreateChart();
			}
		});
		showLegendCkb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chart != null) {
					boolean legend = chart.getLegend().isVisible();
					chart.getLegend().setVisible(!legend);
				}
			}
		});
		moodPanel.add(showLegendCkb);
	}

	public ShowChartPanel(MessagePanel messagePanel) {
		this();
		this.messagePanel = messagePanel;
	}

	public void setUsers(Map<String, User> users) {
		this.users = users;
		innovativeCkb.setSelected(true);
		beautifulCkb.setSelected(true);
		informativeCkb.setSelected(true);
		unconvincingCkb.setSelected(true);
		boringCkb.setSelected(true);
		showLegendCkb.setSelected(false);
		recreateChart();
	}

	protected void recreateChart() {
		setVisible(false);

		Map<String, Boolean> moods = new HashMap<String, Boolean>();
		moods.put(innovativeCkb.getText(), innovativeCkb.isSelected());
		moods.put(beautifulCkb.getText(), beautifulCkb.isSelected());
		moods.put(informativeCkb.getText(), informativeCkb.isSelected());
		moods.put(unconvincingCkb.getText(), unconvincingCkb.isSelected());
		moods.put(boringCkb.getText(), boringCkb.isSelected());

		boolean showLegend = showLegendCkb.isSelected();

		TimeSeriesCollection seriesCollection = new TimeSeriesCollection();

		if (users != null) {
			for (Map.Entry<String, User> e : users.entrySet()) {
				User user = e.getValue();
				for (String mood : EmailMessage.MOODS) {
					if (!moods.get(mood)) {
						continue;
					}
					TimeSeries series = new TimeSeries(e.getKey() + " / "
							+ mood);
					for (EmailMessage message : user.getMessages()) {
						RegularTimePeriod period = new Day(message.getDate());
						TimeSeriesDataItem item = series.getDataItem(period);
						if (item == null) {
							series.add(new MessageDataItem(period, message
									.getMood().get(mood), message));
						}
					}
					seriesCollection.addSeries(series);
				}
			}
		}

		chart = ChartFactory.createTimeSeriesChart(TITLE, TIME_AXIS_LABEL,
				VALUE_AXIS_LABEL, seriesCollection, true, false, false);

		chart.getLegend().setVisible(showLegend);

		XYPlot xyPlot = (XYPlot) chart.getPlot();
		DateAxis dateAxis = (DateAxis) xyPlot.getDomainAxis();
		dateAxis.setTickUnit(new DateTickUnit(DateTickUnitType.DAY, 7,
				new SimpleDateFormat("dd/MM/yyyy")));
		dateAxis.setVerticalTickLabels(true);

		XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) xyPlot
				.getRenderer();
		renderer.setBaseShapesVisible(true);
		chartPanel.setChart(chart);
		chartPanel.setVisible(true);
		moodPanel.setVisible(true);
		setVisible(true);
	}
}
