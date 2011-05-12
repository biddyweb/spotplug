package com.plugtree.spotplug.client.visualizationWidgets;

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

			service.getRuleActivations(listBox.getItemText(listBox.getSelectedIndex()), new GenericAsyncCallback<List<Long>>() {

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

//TODO: Mostrar los eventos al ser activado. 
//				int hour = chart.getSelections().get(0).getRow();
//				List<Long> timestamps = eventsSelected.get(hour);
//				service.getEventsOccurred(listBox.getItemText(listBox.getSelectedIndex()), timestamps, new GenericAsyncCallback<List<VisualEvent>>() {
//
//					public void onSuccess(List<VisualEvent> list) {
//						TablePopup popup = new TablePopup("Tabla de Eventos", list);
//						popup.show();
//					}
//				});
//			}
			}
		};
	}

	@Override
	public void refresh() {
		draw();
	}

	private void setRuleNames() {

		service.getRulesName(new GenericAsyncCallback<List<String>>() {

			@Override
			public void onSuccess(List<String> ruleList) {
				for(String ruleName : ruleList){

					listBox.addItem(ruleName);
				}

				draw();
			}
		});
	}
}
