package com.example.practiceprj;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class NewExpenseActivity extends FragmentActivity {

	private ExpenseDAO datasource;
	EditText amountBox;
	EditText placeBox;
	EditText descBox;
	Spinner catSpinner;
	private String[] categoryList;

	private DatePicker dpResult;
	private Button btnChangeDate;

	private int year;
	private int month;
	private int day;

	static final int DATE_DIALOG_ID = 999;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newexpense);
		btnChangeDate = (Button) findViewById(R.id.btnChangeDate);
		amountBox = (EditText) findViewById(R.id.amountSpenttEdtTxt);
		placeBox = (EditText) findViewById(R.id.placeEdtTxt);
		descBox = (EditText) findViewById(R.id.descEdtTxt);
		catSpinner = (Spinner) findViewById(R.id.catSpinner);

		datasource = new ExpenseDAO(this);
		datasource.open();

		setCurrentDateOnView();
		addListenerOnButton();

		catSpinner = (Spinner) findViewById(R.id.catSpinner);
		categoryList = getResources().getStringArray(R.array.category_list);

		// Create the ArrayAdapter
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(NewExpenseActivity.this,
				android.R.layout.simple_spinner_item, categoryList);
		// Set the Adapter
		catSpinner.setAdapter(arrayAdapter);
		// Set the ClickListener for Spinner
		catSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

			}
			// If no option selected
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
	}

	/**
	 * onClick listener for add expense button
	 * @param view
	 */
	public void addExpense(View view) {
		int amount;
		String amountStr = amountBox.getText().toString();
		
		if (amountStr == null || "".equals(amountStr.trim())) {
			Toast.makeText(this, "Please select a valid amount", Toast.LENGTH_SHORT).show();
			return;
		}
		
		amount = Integer.parseInt(amountStr);
		
		Date expDate = stringToDate(btnChangeDate.getText().toString());
		if (expDate == null) {
			Toast.makeText(this, "Please select a valid date", Toast.LENGTH_SHORT).show();
			return;
		}

		Expense expObj = new Expense(amount, 
				placeBox.getText().toString(), 
				descBox.getText().toString(), 
				catSpinner.getSelectedItem().toString(), 
				expDate);

		if (datasource.createExpense(expObj) != null) {
			Toast.makeText(this, "Expense saved!", Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(this, "Unable to save expense", Toast.LENGTH_SHORT).show();
			return;
		}
		resetAddExpenseView();
	}
	
	private void resetAddExpenseView() {
		amountBox.setText("");
		placeBox.setText("");
		descBox.setText("");
		catSpinner.setSelection(0);
	}

	// display current date
	public void setCurrentDateOnView() {
		// dpResult = (DatePicker) findViewById(R.id.dpResult);

		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);

		// set current date into textview
		btnChangeDate.setText(new StringBuilder()
		// Month is 0 based, just add 1
				.append(month + 1).append("-").append(day).append("-").append(year).append(" "));
	}

	public void addListenerOnButton() {
		btnChangeDate = (Button) findViewById(R.id.btnChangeDate);
		btnChangeDate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(DATE_DIALOG_ID);
			}
		});
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			// set date picker as current date
			return new DatePickerDialog(this, datePickerListener, year, month, day);
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;

			// set selected date into textview
			btnChangeDate.setText(new StringBuilder().append(month + 1).append("-").append(day).append("-").append(year)
					.append(" "));

			// set selected date into datepicker also
			// dpResult.init(year, month, day, null);

		}
	};

	public void showDatePickerDialog(View v) {
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getSupportFragmentManager(), "datePicker");
		newFragment.setCancelable(false);

	}

	public Date stringToDate(String dateInString) {
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
		try {
			Date date = formatter.parse(dateInString);
			System.out.println(date);
			System.out.println(formatter.format(date));
			return date;
		} catch (ParseException e) {
			return null;
		}
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

}
