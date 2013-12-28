package com.sakin.sohojshoncoi.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "planning")
public class Planning {

	@DatabaseField(generatedId = true)
    private int planning_id;
	@DatabaseField(canBeNull = false, foreign = true)
    private Account account;
    @DatabaseField
    private double aeamount;
    @DatabaseField
    private double baeamount;
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
    public Planning(Account ac, double aeamount, double baeamount, int month, int year) {
    	this.account = ac;
    	this.aeamount = aeamount;
    	this.baeamount = baeamount;
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

    public double getAeAmount() {
        return aeamount;
    }
    public void setAeAmount(double amount) {
        this.aeamount = amount;
    }
    public double getBaeAmount() {
        return baeamount;
    }
    public void setBaeAmount(double amount) {
        this.baeamount = amount;
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
