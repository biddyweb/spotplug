package com.plugtree.spotplug.client.mainviews;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.plugtree.spotplug.client.visualizationWidgets.AddEditEventTable;

public class EventManagerView extends HorizontalPanel {

	public EventManagerView() {
		setUp();
	}

	private void setUp() {
		//TODO: Add event type list.
		add(new AddEditEventTable());
	}	
}
