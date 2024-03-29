package com.example.sergey.Model;

import javax.persistence.*;

@Entity
@Table(name="prices") //сущность таблицы содержащей тцп всех подрядчиков, принадлежность пункта в таблице определяется по полю contractor
public class Prices {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column
	private String appendnumber; //номер приложения в ТЦП
	
	@Column
	private String tablenumber; //номер таблицы в ТЦП (т.к. номера пунктов могут повторяться в разных таблицах)
	
	@Column(name="ppnumber") //(name="ppnumber") указывать необязательно, если имя столбца совпадает с именем переменной
	private String ppnumber; //номер пункта(работы) тцп
	
	@Column
	private String workname; //название пункта
	
	@Column
	private String unitmeasure; //единица измерения пункта
	
	@Column
	private double price; //стоимость пункта
	
	@Column
	private String comment; //пояснение к пункту (перечень входящих работ)
	
	@Column
	private String contractor; //логин подрядчика
	
	@Column
	private String contractname; //логин подрядчика
	
	public Prices() {}
	
	public Prices(String appendnumber,String tablenumber,String ppnumber,String workname,String unitmeasure,double price, String comment,String contractor,String contractname) {
		this.appendnumber=appendnumber;
		this.tablenumber=tablenumber;
		this.ppnumber=ppnumber;
		this.workname=workname;
		this.unitmeasure=unitmeasure;
		this.price=price;
		this.comment=comment;
		this.contractor=contractor;
		this.contractname=contractname;
	}
	
	public long getId() {
		return id;
	}
	public void setAppendNumber(String appendnumber) {
		this.appendnumber=appendnumber;
	}
	public String getAppendNumber() {
		return appendnumber;
	}
	public void setTableNumber(String tablenumber) {
		this.tablenumber=tablenumber;
	}
	public String getTableNumber() {
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
	public void setContractName(String contractname) {
		this.contractname=contractname;
	}
	public String getContractName() {
		return contractname;
	}
}
