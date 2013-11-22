package com.sakin.sohojshoncoi.database;

import java.util.Date;

import com.j256.ormlite.field.DataType;
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
    @DatabaseField(dataType = DataType.DATE_LONG)
    private Date start_date;
    @DatabaseField(dataType = DataType.DATE_LONG)
    private Date end_date;
    
	//================================================================================
    // Constructors
    //================================================================================    
    public Planning() {
        // ORMLite needs a no-arg constructor 
    }
    public Planning(Account ac, Category cat, double amount, Date st, Date end) {
    	this.account = ac;
    	this.category = cat;
    	this.amount = amount;
    	this.start_date = st;
    	this.end_date = end;
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
    
    public Date getStartDate() {
        return start_date;
    }
    public void setStartDate(Date st) {
        this.start_date = st;
    }
    
    public Date getEndDate() {
        return end_date;
    }
    public void setEndDate(Date end) {
        this.end_date = end;
    }
}
