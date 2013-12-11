package com.example.practiceprj;

import java.util.Date;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainActivity extends Activity {
	private ExpenseDAO datasource;
	EditText amountBox;
	EditText placeBox;
	EditText descBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        final TabHost tabHost = (TabHost) findViewById(R.id.tabhost);
        tabHost.setup();
        
        TabSpec spec1 = tabHost.newTabSpec("tab1");
        spec1.setContent(R.id.tab1);
        spec1.setIndicator("Home", null);
        tabHost.addTab(spec1);
        
        TabSpec spec2 = tabHost.newTabSpec("tab2");
        spec2.setContent(R.id.tab2);
        spec2.setIndicator("Add Expense", null);
        tabHost.addTab(spec2);
        
        TabSpec spec3 = tabHost.newTabSpec("tab3");
        spec3.setContent(R.id.tab3);
        spec3.setIndicator("Past Expenses", null);
        tabHost.addTab(spec3);
        
        datasource = new ExpenseDAO(this);
        datasource.open();
        
       // List<Expense> values = datasource.getAllExpenses();
        
        // use the SimpleCursorAdapter to show the
        // elements in a ListView
      //  ArrayAdapter<Expense> adapter = new ArrayAdapter<Expense>(this,
      //      android.R.layout.simple_list_item_1, values);
        //setListAdapter(adapter);
        
        //addExpenseButtonCLickEvent();
    }
    
 // Will be called via the onClick attribute
    // of the buttons in main.xml
   /* public void onClick(View view) {
      @SuppressWarnings("unchecked")
      ArrayAdapter<Expense> adapter = (ArrayAdapter<Expense>) getListAdapter();
      Expense exp = null;
      switch (view.getId()) {
      case R.id.add:
        String[] exps = new String[] { "Cool", "Very nice", "Hate it" };
        int nextInt = new Random().nextInt(3);
        // save the new comment to the database
        exp = datasource.createExpense(exps[nextInt]);
        adapter.add(exp);
        break;
      
      }
      adapter.notifyDataSetChanged();
    }*/
    
    public void addExpense (View view) 
    {
    	System.out.println("addExpense onclick function called ");
    	amountBox = (EditText) findViewById(R.id.amountSpenttEdtTxt);
        System.out.println("amount in OnCreate():-"+ amountBox);
        placeBox = (EditText) findViewById(R.id.placeEdtTxt);
        System.out.println("placeBox in OnCreate():-"+ placeBox.getText().toString());
        descBox = (EditText) findViewById(R.id.descEdtTxt);
        System.out.println("placeBox in OnCreate():-"+ descBox.getText().toString());
	   int amount = Integer.parseInt(amountBox.getText().toString());
	
	   Expense expObj = new Expense(amount, placeBox.getText().toString(), descBox.getText().toString() , new Date());
	
	   datasource.createExpense(expObj);
	   amountBox.setText("");
	   placeBox.setText("");
	   descBox.setText("");
 }

    @Override
    protected void onResume() {
    	System.out.println("OnResume called ");
      datasource.open();
      super.onResume();
    }

    @Override
    protected void onPause() {
    	System.out.println("onPause called ");
      datasource.close();
      super.onPause();
    }
    
    public void addExpenseButtonCLickEvent()
    {
    	Button addExpBtn = (Button)findViewById(R.id.add);
    	addExpBtn.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				System.out.println("onClick called");
				EditText amountEdtTxt = (EditText)findViewById(R.id.amountSpenttEdtTxt);
				System.out.println(amountEdtTxt.getText());
			}
		});
    }
    
}
