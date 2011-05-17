package com.plugtree.spotplug.client.visualizationWidgets;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.events.SelectHandler;
import com.google.gwt.visualization.client.visualizations.ScatterChart;

public abstract class ChartPanel extends RefreshablePanel {

	protected ListBox listBox = new ListBox();
	protected SmartChart chart = null;

	public ChartPanel() {
	    super(30000);

		Runnable onLoadCallback = new Runnable() {
			@Override
			public void run() {
				setUp();
			}
		};

		VisualizationUtils.loadVisualizationApi(onLoadCallback, ScatterChart.PACKAGE);
	}

	private void setUp() {

		chart = new SmartChart();
		updateComboBox();

		add(listBox);
		add(chart);

		addHandlers();
		startRefresh();
	}

	private void addHandlers() {

		chart.addSelectHandler(createSelectHandler());

		listBox.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				draw();
			}
		});
	}

	private SelectHandler createSelectHandler() {
		return new SelectHandler() {

			@Override
			public void onSelect(SelectEvent event) {

				String name = listBox.getItemText(listBox.getSelectedIndex());
				int hour = chart.getSelections().get(0).getRow();
				
				showSelection(name, hour);
			}
		};
	}
	
	@Override
	public void refresh() {
		draw();
	}
	
	abstract public void showSelection(String name, int hour);

	abstract public void updateComboBox();
	
	abstract public void draw();
}
