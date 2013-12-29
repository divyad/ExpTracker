package com.example.practiceprj;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

public class PastExpensesActivity extends ListActivity {

	private ExpenseDAO datasource;
	ArrayList<HashMap<String, String>> expObjList;
	private static final String TAG_AMT = "amount";
	private static final String TAG_PLACE = "place";
	private static final String TAG_DESC = "desc";
	private static final String TAG_CATEGORY = "category";
	private static final String TAG_EXPDT = "expdt";
	SimpleDateFormat formatter = new SimpleDateFormat(
			"E MMM dd HH:mm:ss z yyyy");
	final String NEW_FORMAT = "dd-MMM-yyyy";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pastexpense_list);

		formatter.applyPattern(NEW_FORMAT);

		// Hashmap for ListView
		expObjList = new ArrayList<HashMap<String, String>>();

		datasource = null;
		
		try {
			datasource = new ExpenseDAO(this);
			datasource.open();
			List<Expense> values = datasource.getAllExpenses();

			if (values != null) {
				System.out.println("values.size():-" + values.size());
				Iterator<Expense> iterator = values.iterator();
				NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("en", "IN"));
				while (iterator.hasNext()) {

					Expense expObj = iterator.next();
					HashMap<String, String> map = new HashMap<String, String>();

					// adding each child node to HashMap key => value
					map.put(TAG_AMT,
							nf.format(expObj.getAmountSpent()));
					map.put(TAG_PLACE, expObj.getPlace());
					map.put(TAG_DESC, expObj.getDesc());
					map.put(TAG_CATEGORY, expObj.getCategory());
					map.put(TAG_EXPDT, formatter.format(expObj.getExpDt()));
					// adding HashList to ArrayList
					expObjList.add(map);
				}
			}

			ListAdapter adapterlt = new SimpleAdapter(this, expObjList,
					R.layout.pastexpense_list_item, new String[] { TAG_AMT,
							TAG_DESC, TAG_EXPDT, TAG_PLACE }, new int[] {
							R.id.amount, R.id.desc, R.id.category, R.id.place });

			setListAdapter(adapterlt);
		} finally {
			if (datasource !=  null)
			datasource.close();
		}
		Log.i("PastExpenseActivity", "Created past expense activity");
	}
}
