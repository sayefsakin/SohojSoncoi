package com.sakin.sohojshoncoi;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Application;

public class MyApplication extends Application {
	
	int ITEM;
    @Override
    public void onCreate() {
        super.onCreate();

        // Create global configuration and initialize ImageLoader with this configuration
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
								        		.Builder(getApplicationContext())
								            	.build();
        ImageLoader.getInstance().init(config);
        setValue(-1);
    }
    
    public synchronized int getValue(){
        return ITEM;
    }
    public synchronized void setValue(int Value){
        this.ITEM = Value;
    }
}
