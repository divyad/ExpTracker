package com.example.practiceprj;

import java.util.Date;

public class Expense {
	
	private long id;
	private int amountSpent;
	private String place;
	private String desc;
	private Date expDt;
	private String category;
	
	public Expense(int amountSpent, String place, String desc,String category, Date expDt) {
		super();
		this.amountSpent = amountSpent;
		this.place = place;
		this.desc = desc;
		this.expDt = expDt;
		this.category = category;
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
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Expense{id:").append(id).append(", amount:").append(amountSpent)
		.append(", place:").append(place).append(", desc:").append(desc)
		.append(", expDt:").append(expDt).append(", category:").append(category)
		.append("}");
		return builder.toString();
	}
}
