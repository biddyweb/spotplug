package com.plugtree.spotplug.client.visualizationWidgets;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.plugtree.spotplug.shared.VisualEvent;
import com.plugtree.spotplug.shared.VisualRule;

import java.util.List;


public class EventTablePopup extends DialogBox {

    private Button okButton = new Button("OK");
    private VerticalPanel vPanel = new VerticalPanel();

    public EventTablePopup(String tableName, List<VisualEvent> eventList) {

        setText(tableName);
        EventTable eventTable = new EventTable(eventList);

        okButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                hide();
            }
        });

        vPanel.add(eventTable);
        vPanel.add(okButton);
        add(vPanel);

        center();

        setStylePrimaryName("gwt-DialogBox");
    }
}
