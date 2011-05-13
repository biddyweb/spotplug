package com.plugtree.spotplug.client.visualizationWidgets;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.TabLayoutPanel;

public class MainView extends DockLayoutPanel {

	private TabLayoutPanel tabPanel = new TabLayoutPanel(1.5, Unit.EM);
	private String height;
	private String width;

	public MainView(String width,String height) {

		super(Unit.EM);
		this.width = width;
		this.height = height;
		setUp();
		addStyle();
	}

	private void setUp() {

		EventView eventView = new EventView();
		eventView.setSize(width, height);
		tabPanel.add(eventView, "Rule/Event Viewer");
		
		EventManagerView eventManager = new EventManagerView();
		eventManager.setSize(width, height);
		tabPanel.add(eventManager, "Event Manager");

		add(tabPanel);
		tabPanel.setSize(tabWidth(), tabHeight());
		setSize(tabWidth(),tabHeight());
	}

	public void addStyle(){

		setStylePrimaryName("gwt-DockLayoutPanel");   
		tabPanel.setStylePrimaryName("gwt-TabLayoutPanel");
	}

	public String tabHeight(){
		String number = height.split("px")[0];			
		return String.valueOf(Integer.valueOf(number) + 60) + "px" ;
	}

	public String tabWidth(){
		String number = width.split("px")[0];			
		return String.valueOf(Integer.valueOf(number) + 40) + "px" ;
	}
}
