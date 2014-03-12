package com.sakin.sohojshoncoi.daylihisab.charts;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.renderer.DefaultRenderer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

public class PieChart extends AbstractDemoChart {

	double[] plan, amount;
	String month;
	String[] planCategories, amountCategories;
	public PieChart() {}
	
	public PieChart(String[] pc, String[] ac, double[] plan, double[] amount, String month) {
		this.plan = plan;
		this.amount = amount;
		this.month = month;
		this.planCategories = pc;
		this.amountCategories = ac;
	}
	
	@Override
	public String getName() {
		return "Comperative Pie Chart for a Month";
	}

	@Override
	public String getDesc() {
		return "Plan & Expense for this month";
	}

	@Override
	public Intent execute(Context context) {
		List<double[]> values = new ArrayList<double[]>();
	    List<String[]> titles = new ArrayList<String[]>();
	    values.add(amount);
	    values.add(plan);
	    titles.add(amountCategories);
	    titles.add(planCategories);
	    int sz = amount.length;
	    int[] colors = new int[sz];
	    for(int i=0;i<sz;i++){ 
	    	colors[i] = Color.rgb(
	    			(int)(Math.random() * 255.0),
	    			(int)(Math.random() * 255.0),
	    			(int)(Math.random() * 255.0));
	    }
	    DefaultRenderer renderer = buildCategoryRenderer(colors);
	    renderer.setApplyBackgroundColor(true);
	    renderer.setBackgroundColor(Color.BLACK);
	    renderer.setLabelsColor(Color.WHITE);
	    renderer.setLabelsTextSize(30);
	    renderer.setLegendTextSize(50);
	    renderer.setZoomButtonsVisible(false);
	    return ChartFactory.getDoughnutChartIntent(context,
	        buildMultipleCategoryDataset("Project budget", titles, values), renderer,
	        "Pie Chart (" + month + ")");
	}

}
