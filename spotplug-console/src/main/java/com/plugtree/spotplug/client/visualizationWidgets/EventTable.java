package com.plugtree.spotplug.client.visualizationWidgets;


import com.google.gwt.user.client.ui.FlexTable;
import com.plugtree.spotplug.shared.VisualEvent;

import java.util.Date;
import java.util.List;


public class EventTable extends FlexTable {

    public EventTable(List<VisualEvent> eventList) {

        setHeader(eventList.iterator().next());

        int numberOfAttributes = eventList.iterator().next().getKeys().size(); //Excluding Name and TimeStamp

        int row = 1;
        int col = 0;

        for(VisualEvent visualevent : eventList) {
            setText(row, col, visualevent.getEventName());
            col++;
            setText(row, col, new Date(visualevent.getTimestamp()).toString());

            //Event Variable attributes
            for(col=2;col<numberOfAttributes;col++){
                for(String attributeName : visualevent.getKeys()){
                 setText(row, col,visualevent.getAttribute(attributeName));
                }       
            }

            row++;
        }

        addStyle();
    }

      private void addStyle() {
        getRowFormatter().addStyleName(0, "listHeader");
        addStyleName("list");
    }

     private void setHeader(VisualEvent event){
         int i = 0;
         setText(0, i, "Name");
         i++;
         setText(0, i, "Date");

         for(String attributeName : event.getKeys()){
             i++;
             setText(0, i, attributeName);

         }

     }

}
