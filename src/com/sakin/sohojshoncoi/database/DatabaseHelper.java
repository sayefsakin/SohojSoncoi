package com.sakin.sohojshoncoi.database;

import android.content.Context;
import java.sql.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	private static final String DATABASE_NAME = "sohojShoncoi.db";
	private static final int DATABASE_VERSION = 1;

	private RuntimeExceptionDao<Account, Integer> accountDAO = null;
	private RuntimeExceptionDao<Transaction, Integer> transactionDAO = null;
	private RuntimeExceptionDao<Category, Integer> categoryDAO = null;
	private RuntimeExceptionDao<MediaCategory, Integer> mediaCategoryDAO = null;
	private RuntimeExceptionDao<Reminder, Integer> reminderDAO = null;
	private RuntimeExceptionDao<Media, Integer> mediaDAO = null;
	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
	}
	
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onCreate");
			TableUtils.createTable(connectionSource, Account.class);
			TableUtils.createTable(connectionSource, Transaction.class);
			TableUtils.createTable(connectionSource, Category.class);
			TableUtils.createTable(connectionSource, MediaCategory.class);
			TableUtils.createTable(connectionSource, Reminder.class);
			TableUtils.createTable(connectionSource, Media.class);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
		try {
			Log.i(DatabaseHelper.class.getName(), "onUpgrade");
			TableUtils.dropTable(connectionSource, Account.class, true);
			TableUtils.dropTable(connectionSource, Transaction.class, true);
			TableUtils.dropTable(connectionSource, Category.class, true);
			TableUtils.dropTable(connectionSource, MediaCategory.class, true);
			TableUtils.dropTable(connectionSource, Reminder.class, true);
			TableUtils.dropTable(connectionSource, Media.class, true);
			// after we drop the old databases, we create the new ones
			onCreate(db, connectionSource);
		} catch (SQLException e) {
			Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}

	public RuntimeExceptionDao<Account, Integer> getAccountDAO() {
		if (accountDAO == null) {
			accountDAO = getRuntimeExceptionDao(Account.class);
		}
		return accountDAO;
	}
	public RuntimeExceptionDao<Transaction, Integer> getTransactionDAO() {
		if (transactionDAO == null) {
			transactionDAO = getRuntimeExceptionDao(Transaction.class);
		}
		return transactionDAO;
	}
	public RuntimeExceptionDao<Category, Integer> getCategoryDAO() {
		if (categoryDAO == null) {
			categoryDAO = getRuntimeExceptionDao(Category.class);
		}
		return categoryDAO;
	}
	public RuntimeExceptionDao<MediaCategory, Integer> getMediaCategoryDAO() {
		if (mediaCategoryDAO == null) {
			mediaCategoryDAO = getRuntimeExceptionDao(MediaCategory.class);
		}
		return mediaCategoryDAO;
	}
	public RuntimeExceptionDao<Reminder, Integer> getReminderDAO() {
		if (reminderDAO == null) {
			reminderDAO = getRuntimeExceptionDao(Reminder.class);
		}
		return reminderDAO;
	}
	public RuntimeExceptionDao<Media, Integer> getMediaDAO() {
		if (mediaDAO == null) {
			mediaDAO = getRuntimeExceptionDao(Media.class);
		}
		return mediaDAO;
	}
	
	@Override
	public void close() {
		super.close();
		
		accountDAO = null;
		transactionDAO = null;
		categoryDAO = null;
		mediaCategoryDAO = null;
		reminderDAO = null;
		mediaDAO = null;
	}
}
