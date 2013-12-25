package com.example.practiceprj;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {
	
	// TabSpec Names
    private static final String HOME_SPEC = "Home";
    private static final String NEWEXP_SPEC = "Add Expene";
    private static final String PASTEXP_SPEC = "Past Expense";
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        TabHost tabHost = getTabHost();

        // Home Tab
        TabSpec inboxSpec = tabHost.newTabSpec(HOME_SPEC);
        inboxSpec.setIndicator(HOME_SPEC, null);
        Intent inboxIntent = new Intent(this, HomeExpenseActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        inboxSpec.setContent(inboxIntent);

        // Add Expense Tab
        TabSpec outboxSpec = tabHost.newTabSpec(NEWEXP_SPEC);
        outboxSpec.setIndicator(NEWEXP_SPEC, null);
        Intent outboxIntent = new Intent(this, NewExpenseActivity.class);
        outboxSpec.setContent(outboxIntent);

        // Past Expense Tab
        TabSpec profileSpec = tabHost.newTabSpec(PASTEXP_SPEC);
        profileSpec.setIndicator(PASTEXP_SPEC, null);
        Intent profileIntent = new Intent(this, PastExpensesActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        profileSpec.setContent(profileIntent);

        // Adding all TabSpec to TabHost
        tabHost.addTab(inboxSpec); // Adding Home tab
        tabHost.addTab(outboxSpec); // Adding Add Expense tab
        tabHost.addTab(profileSpec); // Adding Past Expense tab
        
        System.out.println("Activity Created");
    }
    
}
