package com.tavisca.inmemorycache.model;

public class Car {

	private long id;
	
	private String make;
	
	private Double engineCC;
	
	private String type;
	
	private Double exShowroomPrice;
	
	private Double onRoadPrice;
	
	private Integer seatingCapacity;
	
	private String color;
	

	public Car(String make, Double engineCC, String type, Double exShowroomPrice, Double onRoadPrice,
			Integer seatingCapacity, String color) {
		this.make = make;
		this.engineCC = engineCC;
		this.type = type;
		this.exShowroomPrice = exShowroomPrice;
		this.onRoadPrice = onRoadPrice;
		this.seatingCapacity = seatingCapacity;
		this.color = color;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public Double getEngineCC() {
		return engineCC;
	}

	public void setEngineCC(Double engineCC) {
		this.engineCC = engineCC;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getExShowroomPrice() {
		return exShowroomPrice;
	}

	public void setExShowroomPrice(Double exShowroomPrice) {
		this.exShowroomPrice = exShowroomPrice;
	}

	public Double getOnRoadPrice() {
		return onRoadPrice;
	}

	public void setOnRoadPrice(Double onRoadPrice) {
		this.onRoadPrice = onRoadPrice;
	}

	public Integer getSeatingCapacity() {
		return seatingCapacity;
	}

	public void setSeatingCapacity(Integer seatingCapacity) {
		this.seatingCapacity = seatingCapacity;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "Car [id=" + id + ", make=" + make + ", engineCC=" + engineCC + ", type=" + type + ", exShowroomPrice="
				+ exShowroomPrice + ", onRoadPrice=" + onRoadPrice + ", seatingCapacity=" + seatingCapacity + ", color="
				+ color + "]";
	}
	
	
}
