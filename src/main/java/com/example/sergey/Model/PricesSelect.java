package com.example.sergey.Model;

import java.math.BigDecimal;
import java.math.RoundingMode;

//Класс, отражающий один выбранный пункт из ТЦП, со всеми полями из ТЦП, описывающими этот пункт,
//а также кол-вом и стоимостью, зависящей от кол-ва.

public class PricesSelect implements Comparable<PricesSelect>{
	
	private int tablenumber; //номер таблицы ТЦП (номера пунктов могут повторяться в разных таблицах)
	private String ppnumber; //номер пункта ТЦП
	private String workname; //название пункта
	private String unitmeasure; //единица измерения пункта
	private double price; //цена за одну единицу
	private String comment; //комментарий по пункту
	private String contractor; //подрядчик
	private double quantity; //количество, установленное пользователем
	private double endprice; //конечная цена, т.е. кол-во*цена пункта

	public PricesSelect(int tablenumber,String ppnumber,String workname,String unitmeasure,double price, String comment, String contractor, double quantity) {
		this.tablenumber=tablenumber;
		this.ppnumber=ppnumber;
		this.workname=workname;
		this.unitmeasure=unitmeasure;
		this.price=price;
		this.comment=comment;
		this.contractor=contractor;
		this.quantity=quantity;
		this.endprice=this.price*this.quantity;
		BigDecimal bd = new BigDecimal(this.endprice).setScale(2, RoundingMode.HALF_UP);
		this.endprice = bd.doubleValue();
	}
	
	public void setTableNumber(int tablenumber) {
		this.tablenumber=tablenumber;
	}
	public int getTableNumber() {
		return tablenumber;
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
	public void setContractor(String contractor) {
		this.contractor=contractor;
	}
	public String getContractor() {
		return contractor;
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
	public int compareTo(PricesSelect o) {
		
		return this.ppnumber.compareTo(o.ppnumber);
	}
	
}
