package com.sakin.sohojshoncoi.database;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "category")
public class Category {

	public enum CategoryType {INCOME ("INCOME"), EXPENSE ("EXPENSE");
		private final String name;       
	
	    private CategoryType(String s) {
	        name = s;
	    }
	
	    public boolean equalsName(String otherName){
	        return (otherName == null)? false:name.equals(otherName);
	    }
	
	    public String toString(){
	       return name;
	    }
	};
	
	@DatabaseField(generatedId = true)
    private int category_id;
    @DatabaseField
    private String name;
    @DatabaseField(dataType = DataType.ENUM_INTEGER)
    private CategoryType type;
    @DatabaseField
    private String icon_url;
    @DatabaseField
    private int parent_id;
    
	//================================================================================
    // Constructors
    //================================================================================
    public Category() {
        // ORMLite needs a no-arg constructor 
    }
    public Category(String name, CategoryType type, String icon_url, int pid) {
        this.name = name;
        this.type = type;
        this.icon_url = icon_url;
        this.parent_id = pid;
    }
    public Category(int id, String name, CategoryType type, String icon_url, int pid) {
    	this.category_id = id;
        this.name = name;
        this.type = type;
        this.icon_url = icon_url;
        this.parent_id = pid;
    }


	//================================================================================
    // Accessors
    //================================================================================
    public int getCategoryID() {
        return category_id;     
    }
    public String getName() {
        return name;     
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public CategoryType getType() {
        return type;     
    }
    public void setType(CategoryType type) {
        this.type = type;
    }
    
    public String getIconUrl() {
        return icon_url;     
    }
    public void setIconUrl(String url) {
        this.icon_url = url;
    }
    
    public int getParentId() {
        return parent_id;     
    }
    public void setParentId(int id) {
        this.parent_id = id;
    }
}
