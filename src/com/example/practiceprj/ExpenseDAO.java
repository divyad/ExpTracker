package com.example.practiceprj;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.w3c.dom.Comment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class ExpenseDAO {
	 // Database fields
	  private SQLiteDatabase database;
	  private DatabaseHelper dbHelper;
	  private String[] allColumns = { DatabaseHelper.COLUMN_ID,DatabaseHelper.COLUMN_AMOUNT,
			  DatabaseHelper.COLUMN_PLACE, DatabaseHelper.COLUMN_DESC };

	  public ExpenseDAO(Context context) {
	    dbHelper = new DatabaseHelper(context);
	  }

	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }

	  public void close() {
	    dbHelper.close();
	  }

	  public Expense createExpense(Expense expObj) {
	    ContentValues values = new ContentValues();
	    values.put(DatabaseHelper.COLUMN_AMOUNT, expObj.getAmountSpent());
	    values.put(DatabaseHelper.COLUMN_PLACE, expObj.getPlace());
	    values.put(DatabaseHelper.COLUMN_DESC, expObj.getDesc());
	    long insertId = database.insert(DatabaseHelper.TABLE_EXPENSES, null,
	        values);
	    Cursor cursor = database.query(DatabaseHelper.TABLE_EXPENSES,
	        allColumns, DatabaseHelper.COLUMN_ID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    Expense newExp = cursorToExpense(cursor);
	    cursor.close();
	    return newExp;
	  }
	  
	  /*
	   * Getting values from Cursor result set and setting to Expense Object
	   */
	  private Expense cursorToExpense(Cursor cursor) {
		  	System.out.println(" cursor.getString(1):-"+ cursor.getString(1));
		  	System.out.println(" cursor.getString(2):-"+ cursor.getString(2));
		  	System.out.println(" cursor.getString(3):-"+ cursor.getString(3));
		  	System.out.println("cursor.getLong(0):-"+cursor.getLong(0));
		    Expense exp = new Expense(100, cursor.getString(2) ,cursor.getString(3) , new Date());
		    exp.setId(cursor.getLong(0));
		    return exp;
		  }

	  public void deleteExpense(Expense exp) {
	    long id = exp.getId();
	    System.out.println("Expense deleted with id: " + id);
	    database.delete(DatabaseHelper.TABLE_EXPENSES, DatabaseHelper.COLUMN_ID
	        + " = " + id, null);
	  }

	  public List<Expense> getAllExpenses() {
	    List<Expense> expList = new ArrayList<Expense>();

	    Cursor cursor = database.query(DatabaseHelper.TABLE_EXPENSES,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      Expense exp = cursorToExpense(cursor);
	      expList.add(exp);
	      cursor.moveToNext();
	    }
	    // make sure to close the cursor
	    cursor.close();
	    return expList;
	  }

	  
}
