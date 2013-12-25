package com.example.practiceprj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

public class PastExpensesActivity extends ListActivity{
	
	private ExpenseDAO datasource;
	ArrayList<HashMap<String, String>> expObjList;
	private static final String TAG_AMT = "amount";
    private static final String TAG_PLACE = "place";
    private static final String TAG_DESC = "desc";
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pastexpense_list);
        
     // Hashmap for ListView
        expObjList = new ArrayList<HashMap<String, String>>();
        
        datasource = new ExpenseDAO(this);
        datasource.open();
        
        List<Expense> values = datasource.getAllExpenses();
        
        if(values != null)
        {	
        	System.out.println("values.size():-"+values.size());
        	Iterator<Expense> iterator = values.iterator();
        	while (iterator.hasNext()) {
        		
        		Expense expObj = iterator.next();
        		HashMap<String, String> map = new HashMap<String, String>();
        		 
                // adding each child node to HashMap key => value
                map.put(TAG_AMT, String.valueOf(expObj.getAmountSpent()));
                map.put(TAG_PLACE, expObj.getPlace());
                map.put(TAG_DESC, expObj.getDesc());

                // adding HashList to ArrayList
                expObjList.add(map);
        	}
        }
        
        ListAdapter adapterlt = new SimpleAdapter(this, expObjList, R.layout.pastexpense_list_item, new String[] { TAG_AMT, TAG_DESC, TAG_PLACE },  new int[] { R.id.amount, R.id.desc, R.id.place });
        
        setListAdapter(adapterlt);
    }
}

