package com.plugtree.spotplug.client.visualizationWidgets;

import java.util.Date;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.plugtree.spotplug.client.RuleService;
import com.plugtree.spotplug.client.RuleServiceAsync;
import com.plugtree.spotplug.client.util.GenericAsyncCallback;
import com.plugtree.spotplug.shared.VisualRule;

public class RuleChartPanel extends ChartPanel {

	private RuleServiceAsync service = null;
	
	public RuleChartPanel() {
		super();

		service = GWT.create(RuleService.class);
		((ServiceDefTarget)service).setServiceEntryPoint(GWT.getModuleBaseURL() + "RuleService");
	}
	
	@Override
	public void draw() {

		if (listBox.getItemCount() != 0) {
			
			service.getRuleHistory(listBox.getItemText(listBox.getSelectedIndex()), new GenericAsyncCallback<List<Long>>() {
				
				@Override
				public void onSuccess(List<Long> activations) {
					chart.draw(activations);
				}
			});
		}
	}
	
	@Override
	public void showSelection(String name, int hour) {
		
		service.getRules(name, new Date(), new GenericAsyncCallback<List<VisualRule>>() {

			@Override
			public void onSuccess(List<VisualRule> ruleList) {
				TablePopup popup = new TablePopup("Rule list", ruleList);
				popup.show();
			}
		});
	}

	@Override
	public void updateComboBox() {
		
		service.getRuleNames(new GenericAsyncCallback<List<String>>() {

			@Override
			public void onSuccess(List<String> eventNameList) {

				for(String eventName : eventNameList){
					listBox.addItem(eventName);
				}

				draw();
			}
		});
	}
}
