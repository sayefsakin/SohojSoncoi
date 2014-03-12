package com.sakin.sohojshoncoi.daylihisab.charts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer.FillOutsideLine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;

public class CompareChart extends AbstractDemoChart {
	  
	double[] plan, amount;
	String month;
	String[] categories;
	public CompareChart(){}
	
	public CompareChart(String[] cat, double[] plan, double[] amount, String month) {
		this.plan = plan;
		this.amount = amount;
		this.month = month;
		this.categories = cat;
	}
	  public String getName() {
	    return "Comperative Pie Chart for a Month";
	  }
	  
	  public String getDesc() {
	    return "Plan & Expense for this month";
	  }

	  public Intent execute(Context context) {
	    String[] titles = new String[] { "Plan", "Expense",
	        "Difference between plan and expense sales" };
	    List<double[]> values = new ArrayList<double[]>();
	    values.add(plan);
	    values.add(amount);
	    int length = values.get(0).length;
	    double[] diff = new double[length];
	    double minY = 0.0, maxY = 0.0, a, b;
	    for (int i = 0; i < length; i++) {
	    	a = values.get(0)[i];
	    	b = values.get(1)[i];
	      diff[i] = a - b;
	      if(i==0){
	    	  minY = diff[i];
	    	  maxY = Math.max(a, b);
	      }
	      else {
	    	  minY = Math.min(diff[i], minY);
	    	  maxY = Math.max(maxY, Math.max(a, b));
	      }
	    }
	    values.add(diff);
	    int[] colors = new int[] { Color.YELLOW, Color.CYAN, Color.BLUE };
	    PointStyle[] styles = new PointStyle[] { PointStyle.POINT, PointStyle.POINT, PointStyle.POINT };
	    XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
	    setChartSettings(renderer, month + " review", "", "Amount in taka", 0.75,
	        length + 0.25, minY - 100.0, maxY + 100.0, Color.GRAY, Color.LTGRAY);
	    for(int i = 1; i <= length; i++) {
	    	renderer.addXTextLabel(i,categories[i-1]);
	    }
	    renderer.setXLabels(0);
	    renderer.setXLabelsAngle(-90.0f);
	    renderer.setYLabels(10);
	    renderer.setChartTitleTextSize(30);
	    renderer.setShowGrid(true);
	    renderer.setLegendHeight(100);
	    renderer.setApplyBackgroundColor(true);
	    renderer.setBackgroundColor(Color.BLACK);
//	    renderer.setMarginsColor(Color.RED);
	    renderer.setMargins(new int[] {0, 50, 60, 10});
	    renderer.setZoomButtonsVisible(false);
//	    renderer.setTextTypeface("sans_serif", Typeface.BOLD);
	    renderer.setLabelsTextSize(20f);
	    renderer.setAxisTitleTextSize(30);
	    renderer.setLegendTextSize(30);
	    length = renderer.getSeriesRendererCount();

	    for (int i = 0; i < length; i++) {
	      XYSeriesRenderer seriesRenderer = (XYSeriesRenderer) renderer.getSeriesRendererAt(i);
	      if (i == length - 1) {
	        FillOutsideLine fill = new FillOutsideLine(FillOutsideLine.Type.BOUNDS_ALL);
	        fill.setColor(Color.BLUE);
	        seriesRenderer.addFillOutsideLine(fill);
	      }
	      seriesRenderer.setLineWidth(2.5f);
	      seriesRenderer.setDisplayChartValues(true);
	      seriesRenderer.setChartValuesTextSize(20f);
	    }
	    return ChartFactory.getCubicLineChartIntent(context, buildBarDataset(titles, values), renderer,
	        0.5f);
	  }
	}