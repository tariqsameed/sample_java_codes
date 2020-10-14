package com.check24.data;

public class BooksDetail {
	
	private String id;

    private String name;
  
    private String details;
  
    private String price;
    
    private Object similar;
    

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Object getSimilar() {
		return similar;
	}

	public void setSimilar(Object similar) {
		this.similar = similar;
	}
}
