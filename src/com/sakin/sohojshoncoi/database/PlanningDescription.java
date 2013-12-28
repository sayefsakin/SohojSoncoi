package com.sakin.sohojshoncoi.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "planningdescrirption")
public class PlanningDescription {

	@DatabaseField(generatedId = true)
    private int planning_description_id;
	@DatabaseField(canBeNull = false, foreign = true)
    private Planning planning;
	@DatabaseField(canBeNull = false, foreign = true)
    private Category category;
	@DatabaseField
    private double amount;
	
	//================================================================================
    // Constructors
    //================================================================================    
    public PlanningDescription() {
        // ORMLite needs a no-arg constructor 
    }
    public PlanningDescription(Planning plan, Category cat, double amount) {
    	this.category = cat;
    	this.amount = amount;
    	this.planning = plan;
    }
    
   	//================================================================================
    // Accessors
    //================================================================================
    public int getPlanningDescriptionId() {
        return planning_description_id;
    }
    public void setPlanningDescriptionId(int id) {
        this.planning_description_id = id;
    }
    public Planning getPlanning() {
        return planning;
    }
    public void setPlanning(Planning plan) {
        this.planning = plan;
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
}
