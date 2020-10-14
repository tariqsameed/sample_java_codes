package com.check24.data;

public class Books {
	/* id;name;details;price;image from csv file*/
	
    private String id;

    private String name;
  
    private String details;
  
    private String price;
    
    private String image;
    
    public Books() {
    	
    }

    public Books(String id, String name, String details, String price, String image) {
        this.id = id;
        this.name = name;
        this.details = details;
        this.price = price;
        this.image = image;
    }

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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
