package com.example.sergey.Model;

import javax.persistence.*;

@Entity
@Table(name="telecom")
public class Telecom {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name="ppnumber") //(name="ppnumber") указывать необязательно, если имя столбца совпадает с именем переменной
	private String ppnumber;
	
	@Column
	private String workname;
	
	@Column
	private String unitmeasure;
	
	@Column
	private double price;
	
	@Column
	private String comment;
	
	public Telecom() {}
	
	public Telecom(String ppnumber,String workname,String unitmeasure,double price, String comment) {
		this.ppnumber=ppnumber;
		this.workname=workname;
		this.unitmeasure=unitmeasure;
		this.price=price;
		this.comment=comment;
	}
	
	public long getId() {
		return id;
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
	
}
