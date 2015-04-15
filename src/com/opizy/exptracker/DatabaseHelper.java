package com.example.practiceprj;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/* To create and upgrade a database in your Android application you create a subclass of the SQLiteOpenHelper class.
 * The SQLiteOpenHelper class provides the getReadableDatabase() and getWriteableDatabase() methods to get access to an SQLiteDatabase object; either in read or write mode */
public class DatabaseHelper extends SQLiteOpenHelper{

	public static final String TABLE_EXPENSES = "expenses";
	  public static final String COLUMN_ID = "_id";
	  public static final String COLUMN_AMOUNT = "amount";
	  public static final String COLUMN_PLACE = "place";
	  public static final String COLUMN_DESC = "desc";
	  public static final String COLUMN_CATEGORY = "category";
	  public static final String COLUMN_EXPDT = "expdt";

	  private static final String DATABASE_NAME = "ExptrackerDB";
	  private static final int DATABASE_VERSION = 1;

	  // Database creation sql statement
	  private static final String DATABASE_CREATE = "create table "
	      + TABLE_EXPENSES + "(" + COLUMN_ID
	      + " integer primary key autoincrement, " 
	      + COLUMN_AMOUNT + " integer not null, " 
	      + COLUMN_PLACE + " text not null," 
	      + COLUMN_DESC + " text not null,"
	      + COLUMN_EXPDT + " datetime ,"
	      + COLUMN_CATEGORY + " text not null);";

	  /* In the constructor of your subclass you call the super() method of 
  	 * SQLiteOpenHelper, specifying the database name and the current database version.*/
	  public DatabaseHelper(Context context) {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	  }

	  /* It is called by the framework, if the database is accessed but not yet created. 
       * It provides the execSQL() method, which allows to execute an SQL statement directly*/
	  @Override
	  public void onCreate(SQLiteDatabase database) {
		  System.out.println("my Oncreate called");
		  database.execSQL(DATABASE_CREATE);
	  }

	  /* It is called, if the database version is increased in your application code. 
       * This method allows you to update an existing database schema or to drop 
       * the existing database and recreate it via the onCreate() method */
	  @Override
	  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    Log.w(DatabaseHelper.class.getName(),
	        "Upgrading database from version " + oldVersion + " to "
	            + newVersion + ", which will destroy all old data");
	    db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);
	    onCreate(db);
	  }
}
