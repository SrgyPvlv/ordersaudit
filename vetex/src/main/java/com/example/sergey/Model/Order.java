package com.example.sergey.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="orderlist")
public class Order {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	Long id;
	
	@Column
	private int ordernumber;
	
	@Column
	private String bsnumber;
	
	@Column
	private String bsaddress;
	
	@Column
	private String send;
	
	@Column
	private String start;
	
	@Column
	private String endtime;
	
	@Column
	private double sumwithoutnds;
	
	@Column
	private double nds;
	
	@Column
	private double sumwithnds;
	
	@Column
	private String report;
	
	@Column
	private String cedr;
	
	@Column
	private String status;
	
	@Column
	private String worktype;
	
	@Column
	private String orderlistcomment;
	
	@Column
	private String contractnumber;
	
	@Column
	private String contractdate;
	
	@Column
	private String remedy;
	
	@Column
	private String arenda;
	
	@Column
	private String comment;
	
	@Column
	private String author;
	
	@Column
	private String cart;
	
	public Order(int ordernumber,String bsnumber,String bsaddress,String send,String start,String endtime,
			double sumwithoutnds,double nds,double sumwithnds,String report,String cedr,String status,
			String worktype,String orderlistcomment,String contractnumber,String contractdate,String remedy,
			String arenda,String comment,String author,String cart) {
		this.ordernumber=ordernumber;
		this.bsnumber=bsnumber;
		this.bsaddress=bsaddress;
		this.send=send;
		this.start=start;
		this.endtime=endtime;
		this.sumwithoutnds=sumwithoutnds;
		this.nds=nds;
		this.sumwithnds=sumwithnds;
		this.report=report;
		this.cedr=cedr;
		this.status=status;
		this.worktype=worktype;
		this.orderlistcomment=orderlistcomment;
		this.contractnumber=contractnumber;
		this.contractdate=contractdate;
		this.remedy=remedy;
		this.arenda=arenda;
		this.comment=comment;
		this.author=author;
		this.cart=cart;
	}
}
