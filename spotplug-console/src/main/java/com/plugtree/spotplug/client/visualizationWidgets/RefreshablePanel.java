package com.plugtree.spotplug.client.visualizationWidgets;


import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.VerticalPanel;

public abstract class RefreshablePanel extends VerticalPanel {

    private int refreshTime;
    
    public RefreshablePanel(int refreshTime) {
        this.refreshTime = refreshTime;
    }

	public void startRefresh(){
		Timer timer = new Timer() {

			@Override
			public void run() {
				refresh();
			}
		};

		timer.scheduleRepeating(refreshTime);
	}

	abstract public void refresh();
}
