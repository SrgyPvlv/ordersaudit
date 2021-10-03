package com.example.sergey;

public class VetexOrder {
	
	int ppnumber;String workname;String unitmeasure;double price;String comment;int quantity;double endprice;

	public VetexOrder(int ppnumber,String workname,String unitmeasure,double price, String comment,int quantity) {
		this.ppnumber=ppnumber;
		this.workname=workname;
		this.unitmeasure=unitmeasure;
		this.price=price;
		this.comment=comment;
		this.quantity=quantity;
		this.endprice=this.price*this.quantity;
	}
	
	public void setPpNumber(int ppnumber) {
		this.ppnumber=ppnumber;
	}
	public int getPpNumber() {
		return ppnumber;
	}
	public void setWorkName(String workname) {
		this.workname=workname;
	}
	public String getWorkName() {
		return workname;
	}
	public void setUnitMeasure(String unitmeasure) {
		this.unitmeasure=unitmeasure;
	}
	public String getUnitMeasure() {
		return unitmeasure;
	}
	public void setPrice(double price) {
		this.price=price;
	}
	public double getPrice() {
		return price;
	}
	public void setComment(String comment) {
		this.comment=comment;
	}
	public String getComment() {
		return comment;
	}
	public void setQuantity(int quantity) {
		this.quantity=quantity;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setEndPrice(double endprice) {
		this.endprice=endprice;
	}
	public double getEndPrice() {
		return endprice;
	}
	
}
