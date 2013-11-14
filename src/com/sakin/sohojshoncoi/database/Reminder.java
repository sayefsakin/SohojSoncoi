package com.sakin.sohojshoncoi.database;

import java.util.Date;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

enum Status {paid, non_paid};

@DatabaseTable(tableName = "reminder")
public class Reminder {

	@DatabaseField(generatedId = true)
    private int reminder_id;
    @DatabaseField(canBeNull = false, foreign = true)
    private Category category;
    @DatabaseField
    private String description;
    @DatabaseField
    private double amount;
    @DatabaseField
    private Status status;
    @DatabaseField(dataType = DataType.DATE_LONG)
    private Date due_date;
    
	//================================================================================
    // Constructors
    //================================================================================
    public Reminder() {
        // ORMLite needs a no-arg constructor 
    }
    public Reminder(Category category, String description, double amount, Status status,
    		Date due_date) {
        this.category = category;
        this.description = description;
        this.amount = amount;
        this.status = status;
        this.due_date = due_date;
    }

	//================================================================================
    // Accessors
    //================================================================================
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    
    public String getDescription() {
        return description;
    }
    public void setDescription(String desc) {
        this.description = desc;
    }
    
    public double getAmount() {
        return amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public Status getStatus() {
        return status;     
    }
    public void setStatus(Status st) {
        this.status = st;
    }
    
    public Date getDueDate() {
        return due_date;     
    }
    public void setDueDate(Date date) {
        this.due_date = date;
    }
}
