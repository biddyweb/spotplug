package com.plugtree.spotplug.client.visualizationWidgets;

import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.plugtree.spotplug.client.EventService;
import com.plugtree.spotplug.client.EventServiceAsync;
import com.plugtree.spotplug.client.util.GenericAsyncCallback;
import com.plugtree.spotplug.shared.VisualEvent;

public class EventChartPanel extends ChartPanel {

	private EventServiceAsync service = null;
	
	public EventChartPanel() {
		super();

		service = GWT.create(EventService.class);
		((ServiceDefTarget)service).setServiceEntryPoint(GWT.getModuleBaseURL() + "EventService");
	}
	
	@Override
	public void draw() {
		
		if (listBox.getItemCount() != 0) {

			service.getEventHistory(listBox.getItemText(listBox.getSelectedIndex()), new GenericAsyncCallback<List<Long>>() {

				@Override
				public void onSuccess(List<Long> activations) {
					chart.draw(activations);
				}
			});
		}
	}

	@Override
	public void showSelection(String name, int hour) {
		
		service.getEvents(name, new Date(), new GenericAsyncCallback<List<VisualEvent>>() {

			@Override
			public void onSuccess(List<VisualEvent> eventList) {
				EventTablePopup popup = new EventTablePopup("Event list", eventList);
				popup.show();
			}
		});
	}

	@Override
	public void updateComboBox() {
		
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
