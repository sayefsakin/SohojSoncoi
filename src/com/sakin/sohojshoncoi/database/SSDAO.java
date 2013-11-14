package com.sakin.sohojshoncoi.database;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import android.app.Activity;
import android.app.Application;

public class SSDAO {
	//properties
	private DatabaseHelper databaseHelper = null;
	private RuntimeExceptionDao<Account, Integer> accountDAO = null;
	private RuntimeExceptionDao<Category, Integer> categoryDAO = null;
	private RuntimeExceptionDao<Media, Integer> mediaDAO = null;
	private RuntimeExceptionDao<MediaCategory, Integer> mediaCategoryDAO = null;
	private RuntimeExceptionDao<Reminder, Integer> reminderDAO = null;
	private RuntimeExceptionDao<Transaction, Integer> transactionDAO = null;
	//getter and setter
	public DatabaseHelper getDatabaseHelper(){
		return databaseHelper;
	}
	public void setDatabaseHelper(DatabaseHelper dbh){
		this.databaseHelper = dbh;
	}
	public DatabaseHelper getHelper(Activity ac) {
	    if (databaseHelper == null) {
	        databaseHelper = OpenHelperManager.getHelper(ac, DatabaseHelper.class);
	    }
	    return databaseHelper;
	}
	public RuntimeExceptionDao<Account, Integer> getAccountDAO() {
		return accountDAO;
	}
	public void setAccountDAO(RuntimeExceptionDao<Account, Integer> accountDAO) {
		this.accountDAO = accountDAO;
	}
	public RuntimeExceptionDao<Category, Integer> getCategoryDAO() {
		return categoryDAO;
	}
	public void setCategoryDAO(RuntimeExceptionDao<Category, Integer> categoryDAO) {
		this.categoryDAO = categoryDAO;
	}
	public RuntimeExceptionDao<Media, Integer> getMediaDAO() {
		return mediaDAO;
	}
	public void setMediaDAO(RuntimeExceptionDao<Media, Integer> mediaDAO) {
		this.mediaDAO = mediaDAO;
	}
	public RuntimeExceptionDao<MediaCategory, Integer> getMediaCategoryDAO() {
		return mediaCategoryDAO;
	}
	public void setMediaCategoryDAO(RuntimeExceptionDao<MediaCategory, Integer> mediaCategoryDAO) {
		this.mediaCategoryDAO = mediaCategoryDAO;
	}
	public RuntimeExceptionDao<Transaction, Integer> getTransactionDAO() {
		return transactionDAO;
	}
	public void setTransactionDAO(RuntimeExceptionDao<Transaction, Integer> transactionDAO) {
		this.transactionDAO = transactionDAO;
	}
	public RuntimeExceptionDao<Reminder, Integer> getReminderDAO() {
		return reminderDAO;
	}
	public void setReminderDAO(RuntimeExceptionDao<Reminder, Integer> reminderDAO) {
		this.reminderDAO = reminderDAO;
	}
	//singleton pattern
	public SSDAO(){}
	private static SSDAO ssdao = null;
	public static SSDAO getSSdao(){
		if(ssdao == null){
			ssdao = new SSDAO();
		}
		return ssdao;
	}
	
	public void init(Activity ac){
		databaseHelper = getHelper(ac);
		setAccountDAO(databaseHelper.getAccountDAO());
		setCategoryDAO(databaseHelper.getCategoryDAO());
		setMediaDAO(databaseHelper.getMediaDAO());
		setMediaCategoryDAO(databaseHelper.getMediaCategoryDAO());
		setReminderDAO(databaseHelper.getReminderDAO());
		setTransactionDAO(databaseHelper.getTransactionDAO());
	}
	public void close(){
		if (databaseHelper != null) {
	        OpenHelperManager.releaseHelper();
	        databaseHelper = null;
	    }
	}
}
