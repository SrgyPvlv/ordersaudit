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

//Сущность Заявки(Заказа)

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
	private int ordernumber; //номер заявки
	
	@Column
	private String bsnumber; //номер БС
	
	@Column
	private String bsaddress; //адрес БС
	
	@Column
	private String send; //дата отправки заявки
	
	@Column
	private String start; //дата начала работ
	
	@Column
	private String endtime; //крайний срок окончания работ
	
	@Column
	private double sumwithoutnds; //сумма заказа без НДС
	
	@Column
	private double nds; //НДС
	
	@Column
	private double sumwithnds; //сумма Заказа с НДС
	
	@Column
	private String report; //наличие ИД (исполнительной документации)
	
	@Column
	private String cedr; //выложили ИД в Кедр (да или нет)
	
	@Column
	private String status; //статус заказа (№заявки в Oracle, оплачено и т.д.)
	
	@Column
	private String worktype; //тип работ
	
	@Column
	private String orderlistcomment; //комментарий по исполнению заявки
	
	@Column
	private String contractnumber; //номер договора
	
	@Column
	private String contractdate; //дата договора
	
	@Column
	private String remedy; //номер инцидента в Ремеди
	
	@Column
	private String arenda; //контакт арендодателя
	
	@Column
	private String comment; //комментарий по заявке(работе) для подрядчика
	
	@Column
	private String author; //инициатор заявки
	
	@Column
	private String cart; //корзина в формате json
	
	@Column
	private String contractor; //логин подрядчика
	
	@Column
	private String contractname; //название подрядчика
	
	public Order(int ordernumber,String bsnumber,String bsaddress,String send,String start,String endtime,
			double sumwithoutnds,double nds,double sumwithnds,String report,String cedr,String status,
			String worktype,String orderlistcomment,String contractnumber,String contractdate,String remedy,
			String arenda,String comment,String author,String cart,String contractor,String contractname) {
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
		this.contractor=contractor;
		this.contractname=contractname;
	}
}
