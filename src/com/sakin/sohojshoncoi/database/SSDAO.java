package com.sakin.sohojshoncoi.database;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.sakin.sohojshoncoi.Utils;

import android.app.Activity;

public class SSDAO {
	//properties
	private DatabaseHelper databaseHelper = null;
	private RuntimeExceptionDao<Account, Integer> accountDAO = null;
	private RuntimeExceptionDao<Category, Integer> categoryDAO = null;
	private RuntimeExceptionDao<Media, Integer> mediaDAO = null;
	private RuntimeExceptionDao<MediaCategory, Integer> mediaCategoryDAO = null;
	private RuntimeExceptionDao<Reminder, Integer> reminderDAO = null;
	private RuntimeExceptionDao<Transaction, Integer> transactionDAO = null;
	private RuntimeExceptionDao<Planning, Integer> planningDAO = null;
	private RuntimeExceptionDao<PlanningDescription, Integer> planningDescriptionDAO = null;
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
	public RuntimeExceptionDao<Planning, Integer> getPlanningDAO() {
		return planningDAO;
	}
	public void setPlanningDAO(RuntimeExceptionDao<Planning, Integer> planningDAO) {
		this.planningDAO = planningDAO;
	}
	public RuntimeExceptionDao<PlanningDescription, Integer> getPlanningDescriptionDAO() {
		return planningDescriptionDAO;
	}
	public void setPlanningDescriptionDAO(RuntimeExceptionDao<PlanningDescription, Integer> planningDescriptionDAO) {
		this.planningDescriptionDAO = planningDescriptionDAO;
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
		setPlanningDAO(databaseHelper.getPlanningDAO());
		setPlanningDescriptionDAO(databaseHelper.getPlanningDescriptionDAO());
	}
	public void close(){
		if (databaseHelper != null) {
	        OpenHelperManager.releaseHelper();
	        databaseHelper = null;
	    }
	}
	
	//Account table related methods
	public void deleteAccountByName(String name) throws SQLException{
		DeleteBuilder<Account, Integer> deleteBuilder = getAccountDAO().deleteBuilder();
		deleteBuilder.where().eq("name", name);
		deleteBuilder.delete();
	}
	
	//Category table related methods
	public List<Category> getCategories() {
//		List<Category> categories = new ArrayList<Category>();
//		CloseableWrappedIterable<Category> wrappedIterable = getCategoryDAO().getWrappedIterable();
//		try {
//		    for (Category category : wrappedIterable) {
//		    	categories.add(category);
//		    }
//		} finally {
//		    wrappedIterable.close();
//		}
//		return categories;
		return getCategoryDAO().queryForAll();
	}
	public Category getCategoryFromID(int id) throws SQLException{
		Category cat = null;
		QueryBuilder<Category, Integer> qb = getCategoryDAO().queryBuilder();
		qb.where().eq("category_id", id);
		cat = qb.query().get(0);
		return cat;
	}
	public Category getCategoryFromName(String name) throws SQLException{
		Category cat = null;
		QueryBuilder<Category, Integer> qb = getCategoryDAO().queryBuilder();
		qb.where().eq("name", name);
		cat = qb.query().get(0);
		return cat;
	}
	
	//MediaCategory table related methods
	public List<MediaCategory> getMediaCategories() {
		return getMediaCategoryDAO().queryForAll();
	}
	
	//Transaction table related methods
	public List<Transaction> getTransaction() throws SQLException{
		QueryBuilder<Transaction, Integer> qb = getTransactionDAO().queryBuilder();
		qb.orderBy("date", false);
		return qb.query();
	}
	public List<Transaction> getTransactionBetweenDate(/*Account account, */Date start, Date end) throws SQLException {
		if(end.before(start))return null;
		QueryBuilder<Transaction, Integer> qb = getTransactionDAO().queryBuilder();
		qb.orderBy("date", false);
	    qb.where()/*.eq("account_id", account.getAccountId()).and()*/.between("date", start, end);
	    return qb.query();
	}
	public String getTransactionSumBetweenDate(/*Account account, */Date start, Date end, boolean aeOrBae) throws SQLException {
		//true bae, false ae
		if(end.before(start)){
			Utils.print("date format error!");
			return null;
		}
		QueryBuilder<Transaction, Integer> qb = getTransactionDAO().queryBuilder();
		qb.orderBy("date", true);
//	    qb.where()/*.eq("account_id", account.getAccountId()).and()*/.between("date", start, end);
		int low = 1, high = Utils.MAX_BAE_INDEX - 1;
		if(aeOrBae == false){
			low = Utils.MAX_BAE_INDEX;
			high = Utils.MAX_AE_INDEX - 1;
		}
		qb.where().between("date", start, end).and().between("category_id", low, high);
	    qb.selectRaw("SUM(amount)");
	    GenericRawResults<String[]> results = qb.queryRaw();
	    return results.getFirstResult()[0];
	}
	public String getTransactionSumToDate(Date end) throws SQLException {
		QueryBuilder<Transaction, Integer> qb = getTransactionDAO().queryBuilder();
//		qb.orderBy("date", true);
		qb.where().le("date", end);
	    qb.selectRaw("SUM(amount)");
	    GenericRawResults<String[]> results = qb.queryRaw();
	    return results.getFirstResult()[0];
	}
	public List<Transaction> getTransactionOfCategory(Account account, Category category) throws SQLException {
		QueryBuilder<Transaction, Integer> qb = getTransactionDAO().queryBuilder();
		qb.orderBy("date", false);
		qb.where().eq("account_id", account.getAccountId()).and().eq("category_id", category);
		return qb.query();
	}
	//isAeBae true hole look in category. category = 1 bae, category = 0 ae
	public List<Transaction> getTransactionOfCategoryBetweenDate(/*Account account, */int category, 
							Date start, Date end, boolean isAeBae) throws SQLException {
		if(end.before(start))return null;
		QueryBuilder<Transaction, Integer> qb = getTransactionDAO().queryBuilder();
		qb.orderBy("date", false);
		if(isAeBae) {
			if(category == 1) {
				qb.where().between("category_id", 1, Utils.MAX_BAE_INDEX - 1)
				.and().between("date", start, end);
			} else {
				qb.where().between("category_id", Utils.MAX_BAE_INDEX, Utils.MAX_AE_INDEX - 1)
				.and().between("date", start, end);
			}
		} else {
			qb.where()./*eq("account_id", account.getAccountId()).and().*/
						eq("category_id", category).and().between("date", start, end);
		}
	    return qb.query();
	}
	public String getTransactionSumOfCategoryBetweenDate(/*Account account, */int category,
					Date start, Date end, boolean isAeBae) throws SQLException {
		if(end.before(start))return null;
		QueryBuilder<Transaction, Integer> qb = getTransactionDAO().queryBuilder();
		qb.orderBy("date", true);
		if(isAeBae) {
			if(category == 1) {
				qb.where().between("category_id", 1, Utils.MAX_BAE_INDEX - 1)
				.and().between("date", start, end);
			} else {
				qb.where().between("category_id", Utils.MAX_BAE_INDEX, Utils.MAX_AE_INDEX - 1)
				.and().between("date", start, end);
			}
		} else {
			qb.where()/*.eq("account_id", account.getAccountId()).and()*/
			.eq("category_id", category).and().between("date", start, end);
		}
	    qb.selectRaw("SUM(amount)");
	    GenericRawResults<String[]> results = qb.queryRaw();
	    return results.getFirstResult()[0];
	}
	
	//Reminder table related methods
	public List<Reminder> getReminder() throws SQLException{
		return getReminderDAO().queryForAll();
	}
	public List<Reminder> getReminderBetweenDate(/*Account account, */Date start, Date end) throws SQLException{
		if(end.before(start))return null;
		QueryBuilder<Reminder, Integer> qb = getReminderDAO().queryBuilder();
		qb.orderBy("due_date", false);
		qb.where()/*.eq("account_id", account).and()*/.between("date", start, end);
		return qb.query();
	}
	public String getReminderSumBetweenDate(/*Account account, */Date start, Date end) throws SQLException {
		if(end.before(start))return null;
		QueryBuilder<Reminder, Integer> qb = getReminderDAO().queryBuilder();
		qb.orderBy("due_date", true);
	    qb.where()/*.eq("account_id", account).and()*/.between("date", start, end);
	    qb.selectRaw("SUM(amount)");
	    GenericRawResults<String[]> results = qb.queryRaw();
	    return results.getFirstResult()[0];
	}
	public List<Reminder> getReminderFromDate(Date start) throws SQLException{
		QueryBuilder<Reminder, Integer> qb = getReminderDAO().queryBuilder();
		qb.orderBy("due_date", true);
		qb.where().ge("due_date", start);
		return qb.query();
	}
//	public List<Reminder> getReminderOfCategory(Account account, Category category) throws SQLException {
//		QueryBuilder<Reminder, Integer> qb = getReminderDAO().queryBuilder();
//		qb.orderBy("date", true);
//		qb.where().eq("account_id", account).and().eq("category_id", category);
//		return qb.query();
//	}
//	public List<Reminder> getReminderOfCategoryBetweenDate(Account account, Category category, Date start, Date end) throws SQLException {
//		if(end.before(start))return null;
//		QueryBuilder<Reminder, Integer> qb = getReminderDAO().queryBuilder();
//		qb.orderBy("date", true);
//		qb.where().eq("account_id", account).and().eq("category_id", category).and().between("date", start, end);
//	    return qb.query();
//	}
//	public String getReminderSumOfCategoryBetweenDate(Account account, Category category, Date start, Date end) throws SQLException {
//		if(end.before(start))return null;
//		QueryBuilder<Reminder, Integer> qb = getReminderDAO().queryBuilder();
//		qb.orderBy("date", true);
//	    qb.where().eq("account_id", account).and().eq("category_id", category).and().between("date", start, end);
//	    qb.selectRaw("SUM(amount)");
//	    GenericRawResults<String[]> results = qb.queryRaw();
//	    return results.getFirstResult()[0];
//	}
	
	//Planning table related methods
	public List<Planning> getPlanning() throws SQLException{
		return getPlanningDAO().queryForAll();
	}
	public List<Planning> getPlanningOfMonthAndYear(int month, int year) throws SQLException{
		QueryBuilder<Planning, Integer> qb = getPlanningDAO().queryBuilder();
		qb.where().eq("month", month).and().eq("year", year);
		return qb.query();
	}
	public List<Planning> getPlanningOfYear(int year) throws SQLException{
		QueryBuilder<Planning, Integer> qb = getPlanningDAO().queryBuilder();
		qb.where().eq("year", year);
		return qb.query();
	}
	public String getPlanningSumOfYear(int year) throws SQLException{
		QueryBuilder<Planning, Integer> qb = getPlanningDAO().queryBuilder();
		qb.where().eq("year", year);
		qb.selectRaw("SUM(amount)");
	    GenericRawResults<String[]> results = qb.queryRaw();
	    return results.getFirstResult()[0];
	}
	public double getPlanningSumUptoDate(int month, int year) throws SQLException{
		QueryBuilder<Planning, Integer> qb = getPlanningDAO().queryBuilder();
		qb.where().le("month", month).and().le("year", year);
		qb.selectRaw("SUM(baeamount)").selectRaw("SUM(aeamount)");
	    GenericRawResults<String[]> results = qb.queryRaw();
	    List<String[]> rr = results.getResults();
	    if(rr == null || rr.size() == 0) return 0.0;
//	    Utils.print("planning ae bae value: " + rr.get(0)[0] + " " + rr.get(0)[1]);
	    double bae = 0;
	    if(rr.get(0)[0] != null) {
	    	bae = Double.parseDouble(rr.get(0)[0]);
	    }
	    double ae = 0;
	    if(rr.get(0)[1] != null) {
	    	ae = Double.parseDouble(rr.get(0)[1]);
	    }
	    return (ae - bae);
	}
	
	//PlanningDescription related metods
	public List<PlanningDescription> getPlanningDescription() throws SQLException{
		return getPlanningDescriptionDAO().queryForAll();
	}
	public List<PlanningDescription> getPlanningDescriptionOfPlanning(int planning_id) throws SQLException {
		QueryBuilder<PlanningDescription, Integer> qb = getPlanningDescriptionDAO().queryBuilder();
		qb.orderBy("category_id", false);
		qb.where().eq("planning_id", planning_id);
		return qb.query();
	}
	public void removePlanningDescriptionOfPlanning(int planning) throws SQLException {
		DeleteBuilder<PlanningDescription, Integer> qb = getPlanningDescriptionDAO().deleteBuilder();
		qb.where().eq("planning_id", planning);
		qb.delete();
	}
	public void removePlanningDescriptionOfPlanningAndCategory(int planning, int category) throws SQLException {
		DeleteBuilder<PlanningDescription, Integer> qb = getPlanningDescriptionDAO().deleteBuilder();
		qb.where().eq("category_id", category).and().eq("planning_id", planning);
		qb.delete();
	}
}
