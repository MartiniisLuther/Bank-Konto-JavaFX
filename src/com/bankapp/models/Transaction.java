package com.bankapp.models;

public class Transaction {
	private final String title;
	private final String date;
	private final String amount;

//	constructor
	public Transaction(String title, String date, String amount) {
		this.title = title;
		this.date = date;
		this.amount = amount;
	}

//	getters
	public String getTitle() {
		return title; 
	}

	public String getDate() {
		return date;
	}

	public String getAmount() {
		return amount;
	}
}
