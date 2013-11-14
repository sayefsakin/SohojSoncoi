package com.sakin.sohojshoncoi.database;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

enum CategoryType {income, expense, media};

@DatabaseTable(tableName = "category")
public class Category {

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
    public Category(String name, CategoryType type, String url, int pid) {
        this.name = name;
        this.type = type;
        this.icon_url = url;
        this.parent_id = pid;
    }

	//================================================================================
    // Accessors
    //================================================================================
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
