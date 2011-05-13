package com.plugtree.spotplug.client.visualizationWidgets;

import com.google.gwt.user.client.ui.HorizontalPanel;

public class EventManagerView extends HorizontalPanel {

	public EventManagerView() {
		setUp();
	}

	private void setUp() {
		//TODO: Add event type list.
		add(new AddEditEventTable());
	}	
}
