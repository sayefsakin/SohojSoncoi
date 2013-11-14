package com.sakin.sohojshoncoi.database;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "mediacatagory")
public class MediaCategory {

	@DatabaseField(generatedId = true)
    private int category_id;
    @DatabaseField
    private String name;
    @DatabaseField
    private String icon_url;
    @DatabaseField
    private int parent_id;
    
	//================================================================================
    // Constructors
    //================================================================================
    public MediaCategory() {
        // ORMLite needs a no-arg constructor 
    }
    public MediaCategory(String name, String url, int pid) {
        this.name = name;
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
