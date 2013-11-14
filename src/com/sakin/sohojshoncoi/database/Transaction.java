package com.sakin.sohojshoncoi.database;

import java.util.Date;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "transaction")
public class Transaction {

	@DatabaseField(generatedId = true)
    private int transaction_id;
	@DatabaseField(canBeNull = false, foreign = true)
    private Category category;
	@DatabaseField(canBeNull = false, foreign = true)
    private Account account;
	@DatabaseField
    private String description;
	@DatabaseField
    private double amount;
	@DatabaseField
    private String picture_url;
	@DatabaseField
    private double picture_size;
	@DatabaseField(dataType = DataType.DATE_LONG)
    private Date date;
	
	//================================================================================
    // Constructors
    //================================================================================
    public Transaction() {
        // ORMLite needs a no-arg constructor 
    }
    public Transaction(Category category, Account account, String desc, double amount,
    		String url, double sz, Date date) {
        this.category = category;
        this.account = account;
        this.description = desc;
        this.amount = amount;
        this.picture_url = url;
        this.picture_size = sz;
        this.date = date;
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
    
    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
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
    
    public String getPictureUrl() {
        return picture_url;
    }
    public void setPictureUrl(String url) {
        this.picture_url = url;
    }
    
    public double getPictureSize() {
        return picture_size;
    }
    public void setPictureSize(double sz) {
        this.picture_size = sz;
    }
    
    public Date getDate() {
        return date;
    }
    public void setDate(Date dt) {
        this.date = dt;
    }
}
