package com.example.sergey;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class VetexOrder implements Comparable<VetexOrder>{
	
	String ppnumber;String workname;String unitmeasure;double price;String comment;double quantity; double endprice;

	public VetexOrder(String ppnumber,String workname,String unitmeasure,double price, String comment,double quantity) {
		this.ppnumber=ppnumber;
		this.workname=workname;
		this.unitmeasure=unitmeasure;
		this.price=price;
		this.comment=comment;
		this.quantity=quantity;
		this.endprice=this.price*this.quantity;
		BigDecimal bd = new BigDecimal(this.endprice).setScale(2, RoundingMode.HALF_UP);
		this.endprice = bd.doubleValue();
	}
	
	
	
	public void setPpNumber(String ppnumber) {
		this.ppnumber=ppnumber;
	}
	public String getPpNumber() {
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
	public void setQuantity(double quantity) {
		this.quantity=quantity;
	}
	public double getQuantity() {
		return quantity;
	}
	public void setEndPrice() {
		this.endprice=this.quantity*this.price;
		BigDecimal bd = new BigDecimal(this.endprice).setScale(2, RoundingMode.HALF_UP);
		this.endprice = bd.doubleValue();
	}
	public double getEndPrice() {
		return endprice;
	}

	@Override
	public int compareTo(VetexOrder o) {
		
		return this.ppnumber.compareTo(o.ppnumber);
	}
	
}
