package com.plugtree.spotplug.client.visualizationWidgets;

import java.util.List;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.events.SelectHandler;
import com.google.gwt.visualization.client.visualizations.ScatterChart;

public class ChartPanel extends RefreshablePanel {

	private ListBox listBox = new ListBox();
	private SmartChart chart = null;
	//private EventServiceAsync service = null;
	private List<List<Long>> eventsSelected;

	public ChartPanel() {
	    super(30000);

//		service = GWT.create(EventService.class);
//		((ServiceDefTarget)service).setServiceEntryPoint(GWT.getModuleBaseURL() + "EventService");

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
		refreshEventNames();

		add(listBox);
		add(chart);

		addHandlers();
		startRefresh();
	}

	private void draw() {

		if (listBox.getItemCount() != 0) {

//			service.getEventCount(listBox.getItemText(listBox.getSelectedIndex()), 120, new GenericAsyncCallback<List<List<Long>>>() {
//
//				@Override
//				public void onSuccess(List<List<Long>> activations) {
//					eventsSelected = activations;
//					chart.draw(activations);
//				}
//			});
//		}else{
//			service.getEventCount("", 120, new GenericAsyncCallback<List<List<Long>>>() {
//
//				@Override
//				public void onSuccess(List<List<Long>> activations) {
//					eventsSelected = activations;
//					chart.draw(activations);
//				}
//			});
			refreshEventNames();
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

				int hour = chart.getSelections().get(0).getRow();
				List<Long> timestamps = eventsSelected.get(hour);
//				service.getEventsOccurred(listBox.getItemText(listBox.getSelectedIndex()), timestamps, new GenericAsyncCallback<List<VisualEvent>>() {
//
//					public void onSuccess(List<VisualEvent> list) {
//						TablePopup popup = new TablePopup("Tabla de Eventos", list);
//						popup.show();
//					}
//				});
			}
		};
	}

	@Override
	public void refresh() {

		refreshEventNames();
	}

	private void refreshEventNames() {

		if (listBox.getItemCount() == 0) {

//			service.getEvents(new GenericAsyncCallback<Set<String>>() {
//
//				@Override
//				public void onSuccess(Set<String> eventList) {
//					for(String eventCaption : eventList){
//
//						listBox.addItem(eventCaption);
//					}
//
//					draw();
//				}
//			});
		} else {
			final String eventName = listBox.getItemText(listBox.getSelectedIndex());

			listBox.clear();

//			service.getEvents(new GenericAsyncCallback<Set<String>>() {
//
//				@Override
//				public void onSuccess(Set<String> eventList) {
//
//					int i = 0;
//
//					for(String eventCaption : eventList) {
//
//						listBox.addItem(eventCaption);
//
//						if(listBox.getValue(i).equals(eventName)) {
//							listBox.setSelectedIndex(i);
//						}
//
//						i++;
//					}
//
//					draw();
//				}
//			});
		}
	}
}
