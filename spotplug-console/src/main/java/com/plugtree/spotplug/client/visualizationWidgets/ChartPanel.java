package com.plugtree.spotplug.client.visualizationWidgets;

import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.events.SelectHandler;
import com.google.gwt.visualization.client.visualizations.ScatterChart;
import com.plugtree.spotplug.client.EventService;
import com.plugtree.spotplug.client.EventServiceAsync;
import com.plugtree.spotplug.client.util.GenericAsyncCallback;
import com.plugtree.spotplug.shared.VisualEvent;

public class ChartPanel extends RefreshablePanel {

	private ListBox listBox = new ListBox();
	private SmartChart chart = null;
	private EventServiceAsync service = null;

	public ChartPanel() {
	    super(30000);

		service = GWT.create(EventService.class);
		((ServiceDefTarget)service).setServiceEntryPoint(GWT.getModuleBaseURL() + "EventService");

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
		setRuleNames();

		add(listBox);
		add(chart);

		addHandlers();
		startRefresh();
	}

	private void draw() {

		if (listBox.getItemCount() != 0) {

			service.getEventHistory(listBox.getItemText(listBox.getSelectedIndex()), new GenericAsyncCallback<List<Long>>() {

				@Override
				public void onSuccess(List<Long> activations) {
					chart.draw(activations);
				}
			});
		}

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

				//TODO: Improve! Hour and rule name needed.
				int hour = chart.getSelections().get(0).getRow();
				
				service.getEvents(new Date(), new GenericAsyncCallback<List<VisualEvent>>() {

					@Override
					public void onSuccess(List<VisualEvent> ruleList) {
						//TablePopup popup = new TablePopup("Rule list", ruleList);
						//TODO: FIX
						///popup.show();
					}
				});
			}
		};
	}

	@Override
	public void refresh() {
		draw();
	}

	private void setRuleNames() {

		service.getEventNames(new GenericAsyncCallback<List<String>>() {

			@Override
			public void onSuccess(List<String> eventNameList) {
				
				for(String eventName : eventNameList){
					listBox.addItem(eventName);
				}

				draw();
			}
		});
	}
}
