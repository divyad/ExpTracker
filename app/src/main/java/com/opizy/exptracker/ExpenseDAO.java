package com.opizy.exptracker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ExpenseDAO {
    // Database fields
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;
    private String[] allColumns = { DatabaseHelper.COLUMN_ID,
            DatabaseHelper.COLUMN_AMOUNT, DatabaseHelper.COLUMN_PLACE,
            DatabaseHelper.COLUMN_DESC, DatabaseHelper.COLUMN_CATEGORY,
            DatabaseHelper.COLUMN_EXPDT };

    public static class ExpenseCache {
        LinkedList<Expense> allExpenses ;
        Expense lastExpense;

        public List<Expense> getAllExpenses() {
            return allExpenses;
        }

        public void addNewExpense(Expense expense) {
            allExpenses = new LinkedList<Expense>();
            allExpenses.addFirst(expense);
            lastExpense = expense;
            Log.i("ExpenseCache", "Added new item:" + expense.getDesc());
        }

        public Expense getLastExpense() {
            return lastExpense;
        }

        public void setLastExpense(Expense exp) {
            Log.i("ExpenseCache", "Set last expense from db: " + exp.getDesc());
            this.lastExpense = exp;
        }



        public void setAllExpenses(LinkedList<Expense> expList) {
            Log.i("ExpenseCache", "Set all expense list in cache size: "+ expList.size());
            this.allExpenses = expList;
        }

        public void clear() {
            allExpenses = null;
            lastExpense = null;
            Log.i("ExpenseCache", "Cleared cache");
        }
    }

    public static final ExpenseCache CACHE = new ExpenseCache();

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
        Log.i("createExpense", "Creating new expense: " + expObj.toString());
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_AMOUNT, expObj.getAmountSpent());
        values.put(DatabaseHelper.COLUMN_PLACE, expObj.getPlace());
        values.put(DatabaseHelper.COLUMN_DESC, expObj.getDesc());
        values.put(DatabaseHelper.COLUMN_CATEGORY, expObj.getCategory());
        values.put(DatabaseHelper.COLUMN_EXPDT, expObj.getExpDt().toString());
        long insertId = database.insert(DatabaseHelper.TABLE_EXPENSES, null,
                values);
        if (insertId == -1) {
            Log.w("createExpense", "Unable to insert data");
            return null;
        }

        expObj.setId(insertId);
        // Update cache
        CACHE.addNewExpense(expObj);
        return expObj;
    }

    /*
     * Getting values from Cursor result set and setting to Expense Object
     */
    private Expense cursorToExpense(Cursor cursor) {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "E MMM dd HH:mm:ss z yyyy");
        Expense exp;
        try {
            exp = new Expense(Integer.parseInt(cursor.getString(1)),
                    cursor.getString(2), cursor.getString(3),
                    cursor.getString(5), formatter.parse(cursor.getString(4)));
            exp.setId(cursor.getLong(0));
            return exp;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void deleteExpense(Expense exp) {
        long id = exp.getId();
        System.out.println("Expense deleted with id: " + id);
        database.delete(DatabaseHelper.TABLE_EXPENSES, DatabaseHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Expense> getAllExpenses() {
        if (CACHE.getAllExpenses() == null) {
            LinkedList<Expense> expList = new LinkedList<Expense>();

            // Cursor cursor = database.query(DatabaseHelper.TABLE_EXPENSES,
            // allColumns, null, null, null, null, null);
            Cursor cursor = null;
            try {
                cursor = database.rawQuery("select * from expenses order by expdt desc", null);
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    Expense exp = cursorToExpense(cursor);
                    expList.add(exp);
                    cursor.moveToNext();
                }
                CACHE.setAllExpenses(expList);
            } finally {
                cursor.close();
            }
        } else {
            Log.i("ExpenseCache", "hit cache for all items");
        }
        return CACHE.getAllExpenses();
    }

    public Expense getLastExpenses() {
        if (CACHE.getLastExpense() == null) {
            Cursor cursor = null;

            try {
                cursor = database.rawQuery("select * from expenses where _id = (select max(_id) from expenses)", null);
                System.out.println("cursor.getCount():-" + cursor.getCount());
                if (cursor.getCount() == 1) {
                    cursor.moveToFirst();
                    CACHE.setLastExpense(cursorToExpense(cursor));
                }
            } finally {
                cursor.close();
            }
        } else {
            Log.i("ExpenseCache", "hit cache for last item");
        }
        return CACHE.getLastExpense();
    }

    public int getTotalExpenseAmount() {

        Cursor cursor = null;
        try {
            cursor = database.rawQuery("select sum(amount) from expenses",
                    null);//need to add past 30 days constraint

            System.out.println("cursor.getCount():-" + cursor.getCount());
            if (cursor.getCount() == 1) {
                cursor.moveToFirst();
                System.out.println("cursor.getInt(0):-" + cursor.getInt(0));
                return cursor.getInt(0);
            }
        } finally {
            cursor.close();
        }
        return 0;
    }

    public Date stringtoDate(String dateInString) {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "E MMM dd HH:mm:ss z yyyy");
        try {

            Date date = formatter.parse(dateInString);
            System.out.println(date);
            System.out.println(formatter.format(date));
            return date;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }
}
