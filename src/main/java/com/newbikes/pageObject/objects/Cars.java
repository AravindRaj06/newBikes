package com.newbikes.pageObject.objects;

public class Cars {
	private String name;
	private String price;
	private String fuelType;
	private String kms;
	private String model;
	
	public Cars(String name, String price, String fuelType, String kms, String model) {
		this.name = name;
		this.price = price;
		this.fuelType = fuelType;
		this.kms = kms;
		this.model = model;
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

	public String getFuelType() {
		return fuelType;
	}

	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}

	public String getKms() {
		return kms;
	}

	public void setKms(String kms) {
		this.kms = kms;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
}
