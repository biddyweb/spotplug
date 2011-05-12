package com.plugtree.spotplug.client;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;
import com.plugtree.spotplug.client.visualizationWidgets.EventView;

public class SpotplugConsole implements EntryPoint {

	public void onModuleLoad() {
		
		RootPanel.get().add(new EventView());
    }
}