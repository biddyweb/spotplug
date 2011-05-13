package com.plugtree.spotplug.client.visualizationWidgets;

import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.ui.FlexTable;
import com.plugtree.spotplug.shared.VisualRule;

public class RuleTable extends FlexTable {

	public RuleTable(List<VisualRule> ruleList) {

        setText(0, 0, "Name");
        setText(0, 1, "Date");

        int row = 1;

        for(VisualRule visualrule : ruleList) {
            setText(row, 0, visualrule.getRuleName());
            setText(row, 1, new Date(visualrule.getTimestamp()).toString());

            row++;
        }

        addStyle();
    }

    private void addStyle() {
        getRowFormatter().addStyleName(0, "listHeader");
        addStyleName("list");
    }
}
