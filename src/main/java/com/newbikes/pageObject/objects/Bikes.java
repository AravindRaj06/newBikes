package com.newbikes.pageObject.objects;

public class Bikes {
	private String name;
	private String price;
	private String launchDate;
	
	public Bikes(String name, String price, String launchDate) {
		this.name = name;
		this.price = price;
		this.launchDate = launchDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getLaunchDate() {
		return launchDate;
	}

	public void setLaunchDate(String launchDate) {
		this.launchDate = launchDate;
	}		
}
