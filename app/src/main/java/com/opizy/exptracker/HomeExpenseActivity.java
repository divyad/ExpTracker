package com.opizy.exptracker;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class HomeExpenseActivity extends Activity {
    private ExpenseDAO datasource;
    TextView lastExp;
    TextView totalExpAmt;
    final String DT_FORMAT = "dd-MMM-yyyy";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_expense);

        datasource = null;

        try {
            datasource = new ExpenseDAO(this);
            datasource.open();

            // Total amount spent
            int totalAmountSpent = datasource.getTotalExpenseAmount();
            totalExpAmt = (TextView) findViewById(R.id.totalamtspentvalue);
            NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
            totalExpAmt.setText(nf.format(totalAmountSpent));

            // Last Expense
            Expense exp = datasource.getLastExpenses();
            SimpleDateFormat sdf = new SimpleDateFormat(DT_FORMAT);
            if (exp != null) {
                lastExp = (TextView) findViewById(R.id.lastexpvalue);
                lastExp.setText(nf.format(exp.getAmountSpent())  + "\nOn " + sdf.format(exp.getExpDt())
                        +  "\n" + exp.getDesc() + " at "+ exp.getPlace());
            }
        } finally {
            if (datasource != null)
                datasource.close();
        }
        Log.i("HomeExpenseActivity", "HomeExpenseActivity created");
    }
}