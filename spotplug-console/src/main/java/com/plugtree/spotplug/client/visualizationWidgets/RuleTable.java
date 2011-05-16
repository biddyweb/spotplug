package com.plugtree.spotplug.client.visualizationWidgets;

import java.util.Date;
import java.util.List;


import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.dom.client.TableCellElement;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.FlexTable;
import com.plugtree.spotplug.client.EventService;
import com.plugtree.spotplug.client.EventServiceAsync;
import com.plugtree.spotplug.client.util.GenericAsyncCallback;
import com.plugtree.spotplug.shared.VisualEvent;
import com.plugtree.spotplug.shared.VisualRule;

public class RuleTable extends FlexTable {

    private EventServiceAsync service = null;

	public RuleTable(List<VisualRule> ruleList) {
        
        
        service = GWT.create(EventService.class);
		((ServiceDefTarget)service).setServiceEntryPoint(GWT.getModuleBaseURL() + "EventService");

        setText(0, 0, "Name");
        setText(0, 1, "Date");

        int row = 1;

        for(VisualRule visualrule : ruleList) {
            setText(row, 0, visualrule.getRuleName());
            setText(row, 1, new Date(visualrule.getTimestamp()).toString());

            row++;
        }

        addStyle();
        addEvents();
    }

    private void addStyle() {
        getRowFormatter().addStyleName(0, "listHeader");
        addStyleName("list");
    }

    private void addEvents() {
           sinkEvents(Event.ONCLICK);
        }

    @Override
       public void onBrowserEvent(Event event)
       {
           switch(DOM.eventGetType(event))
           {
           case Event.ONCLICK:
               TableCellElement cell =  event.getEventTarget().cast();
               //Window.alert(cell.getInnerText());
               service.getRuleEvents(new VisualRule(),new GenericAsyncCallback<List<VisualEvent>>(){

                   @Override
                   public void onSuccess(List<VisualEvent> visualEvents) {
                        EventTablePopup popup = new EventTablePopup("Event list", visualEvents);
						popup.show();
                   }
               });
               break;
           default:
               break;
           }
       }
   

}
