package com.example.practiceprj;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class HomeExpenseActivity extends Activity{
	private ExpenseDAO datasource;
	TextView lastExp;
	TextView totalExpAmt;

	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_exp);
        
        datasource = new ExpenseDAO(this);
        datasource.open();
        
        //Total amount spent
        int totalAmountSpent = datasource.getTotalExpenseAmount();
        totalExpAmt = (TextView) findViewById( R.id.totalamtspentvalue );
        totalExpAmt.setText("Rs "+totalAmountSpent);
        
        //Last Expense
        Expense exp = datasource.getLastExpenses();
        if(exp != null)
        {	
        lastExp = (TextView) findViewById( R.id.lastexpvalue );
        lastExp.setText("On "+exp.getExpDt() + "\nRs "+exp.getAmountSpent() +"," + exp.getDesc() +" in "+  exp.getPlace());
        }
        
        
        
    }
}
