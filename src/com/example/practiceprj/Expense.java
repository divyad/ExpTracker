package com.example.practiceprj;

import java.util.Date;

public class Expense {
	
	private long id;
	private int amountSpent;
	private String place;
	private String desc;
	private Date expDt;
	
	public Expense(int amountSpent, String place, String desc, Date expDt) {
		super();
		this.amountSpent = amountSpent;
		this.place = place;
		this.desc = desc;
		this.expDt = expDt;
	}
	public int getAmountSpent() {
		return amountSpent;
	}
	public void setAmountSpent(int amountSpent) {
		this.amountSpent = amountSpent;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Date getExpDt() {
		return expDt;
	}
	public void setExpDt(Date expDt) {
		this.expDt = expDt;
	}

	 public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	// Will be used by the ArrayAdapter in the ListView
	  /*@Override
	  public String toString() {
	    return desc;
	  }*/
}
