package com.plugtree.spotplug.client.visualizationWidgets;

import com.google.gwt.user.client.ui.FlexTable;

public class AddEditEventTable extends FlexTable {

	public AddEditEventTable() {
		//TODO: Improve
		setText(0, 0, "Name");
		setText(1, 0, "Date");
		
		setText(1, 1, "");
		setText(0, 1, "");
		
		setBorderWidth(1);
	}
}
