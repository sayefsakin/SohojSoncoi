package com.sakin.sohojshoncoi.daylihisab;

public class ReportElement {
	String categoryName;
	double totalAmount, totalPlan;
	
	public ReportElement(){}
	public ReportElement(String cat, double amount, double plan) {
		this.categoryName = cat;
		this.totalAmount = amount;
		this.totalPlan = plan;
	}

}
