package com.plugtree.spotplug.client.visualizationWidgets;

import java.util.List;

import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.visualizations.ScatterChart;

public class SmartChart extends ScatterChart {

	private Options createOptions() {
		Options options = Options.create();
		options.setWidth(400);
		options.setHeight(240);
		options.setLineSize(1);
		options.setOption("curveType","function");
		options.setOption("hAxis.maxValue", 120);
		options.setTitleX("Segundos (ultimos dos minutos)");
		options.setTitleY("Nro de Ocurrencias");
		options.setEnableTooltip(false);
		options.setOption("hAxis.title", "Nro de Ocurrencias");
		options.setOption("vAxis.title","Segundos");
		//{format:'#,###%'} hAxis.format
		options.setOption("vAxis.format","## Seg.");
		options.setTitle("Eventos Ocurridos en el Periodo (2 Min.)");

		return options;
	}

	public void draw(List<List<Long>> activationList) {

	    DataTable data = DataTable.create();

	    data.addColumn(ColumnType.NUMBER, "Hora");
	    data.addColumn(ColumnType.NUMBER, "Evento");
	    data.addRows(activationList.size());

	    int hour = 0;

        for(List<Long> activation : activationList) {
            data.setValue(hour, 0, hour);
            data.setValue(hour, 1, activation.size());
            hour++;
        }

        draw(data, createOptions());
	}
}
