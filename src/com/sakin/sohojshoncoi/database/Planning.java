package com.sakin.sohojshoncoi.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "planning")
public class Planning {

	@DatabaseField(generatedId = true)
    private int planning_id;
	@DatabaseField(canBeNull = false, foreign = true)
    private Category category;
	@DatabaseField(canBeNull = false, foreign = true)
    private Account account;
    @DatabaseField
    private double amount;
    @DatabaseField
    private int month;
    @DatabaseField
    private int year;
    
	//================================================================================
    // Constructors
    //================================================================================    
    public Planning() {
        // ORMLite needs a no-arg constructor 
    }
    public Planning(Account ac, Category cat, double amount, int month, int year) {
    	this.account = ac;
    	this.category = cat;
    	this.amount = amount;
    	this.month = month;
    	this.year = year;
    }
    
	//================================================================================
    // Accessors
    //================================================================================
    public int getPlanningId() {
        return planning_id;
    }
    public void setPlanningId(int id) {
        this.planning_id = id;
    }
    public Account getAccount() {
        return account;
    }
    public void setAccount(Account ac) {
        this.account = ac;
    }
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category cat) {
        this.category = cat;
    }

    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public int getMonth() {
        return month;
    }
    public void setMonth(int mn) {
        this.month = mn;
    }
    
    public int getYear() {
        return year;
    }
    public void setYear(int yr) {
        this.year = yr;
    }
}
