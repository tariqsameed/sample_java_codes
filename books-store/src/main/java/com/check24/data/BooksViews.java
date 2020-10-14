package com.check24.data;

public class BooksViews {

	private String book;
	
	private String user;
	
	public BooksViews() {

	}

	public BooksViews(String book, String user) {
		this.book = book;
		this.user = user;
	}

	public String getBook() {
		return book;
	}

	public void setBook(String book) {
		this.book = book;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	
}
