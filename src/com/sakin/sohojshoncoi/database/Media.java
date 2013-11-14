package com.sakin.sohojshoncoi.database;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "media")
public class Media {

	public enum MediaType {AUDIO, VIDEO, PICTURE};
	@DatabaseField(generatedId = true)
    private int media_id;
	@DatabaseField(canBeNull = false, foreign = true)
    private MediaCategory media_category;
    @DatabaseField
    private String title;
    @DatabaseField
    private String description;
    @DatabaseField
    private double size;
    @DatabaseField(dataType = DataType.ENUM_INTEGER)
    private MediaType type;
    @DatabaseField
    private String media_url;
    
	//================================================================================
    // Constructors
    //================================================================================
    public Media() {
        // ORMLite needs a no-arg constructor 
    }
    public Media(MediaCategory category, String title, String desc,
    		double sz, MediaType type, String url) {
        this.media_category = category;
        this.title = title;
        this.description = desc;
        this.size = sz;
        this.type = type;
        this.media_url = url;
    }
    
	//================================================================================
    // Accessors
    //================================================================================
    public MediaCategory getMediaCategory() {
        return media_category;     
    }
    public void setMediaCategory(MediaCategory mc) {
        this.media_category = mc;
    }
    
    public String getTitle() {
        return title;     
    }
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    public void setDescription(String desc) {
        this.description = desc;
    }
    
    public double getSize() {
        return size;
    }
    public void setSize(double sz) {
        this.size = sz;
    }
    
    public String getMediaUrl() {
        return media_url;
    }
    public void setMediaUrl(String url) {
        this.media_url = url;
    }
    
    public MediaType getType() {
        return type;
    }
    public void setType(MediaType type) {
        this.type = type;
    }
}
