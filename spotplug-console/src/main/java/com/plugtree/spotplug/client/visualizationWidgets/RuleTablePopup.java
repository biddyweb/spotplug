package com.plugtree.spotplug.client.visualizationWidgets;

import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.plugtree.spotplug.shared.VisualRule;

public class RuleTablePopup extends DialogBox {

    private Button okButton = new Button("OK");
    private VerticalPanel vPanel = new VerticalPanel(); 
    
    public RuleTablePopup(String tableName, List<VisualRule> ruleList) {
        
        setText(tableName);
        RuleTable ruleTable = new RuleTable(ruleList);

        okButton.addClickHandler(new ClickHandler() {

            @Override
            public void onClick(ClickEvent event) {
                hide();
            }
        });
        
        vPanel.add(ruleTable);
        vPanel.add(okButton);
        add(vPanel);
        
        center();     
        
        setStylePrimaryName("gwt-DialogBox");
    }    
}
