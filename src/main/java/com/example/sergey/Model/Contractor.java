package com.example.sergey.Model;

import javax.persistence.*;

import org.hibernate.annotations.Type;

//Подрядчик

@Entity
@Table(name="contractor")
public class Contractor {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id; //id подрядчика
	
	@Column
	private String contractor; //логин подрядчика
	
	@Column
	@Type(type="org.hibernate.type.BinaryType")
	private byte[] text; //текст договора
	
	@Column
	private String number; //номер договора
	
	@Column
	private String date; //дата договора
	
	@Column
	private String name; //полное название организации подрядчика
	
	@Column
	private String email1; //email подрядчика №1 для отправки заявки
	
	@Column
	private String email2; //email подрядчика №2 для отправки заявки
	
	@Column
	private String email3; //email подрядчика №3 для отправки заявки
	
	@Column
	private String work; //общее описание типа выполняемых работ (например ТО/АВР АФУ...или ТО/АВР ОПС и т.д.)
	
	@Column
	private String contractend; //дата окончания договора
	
	@Column
	private String email11; //email заказчика (МТС) №1 для отправки копии заявки
	
	@Column
	private String email12; //email заказчика (МТС) №2 для отправки копии заявки
	
	@Column
	private String email13; //email заказчика (МТС) №3 для отправки копии заявки
	
	public Contractor() {}
	
	//Конструктор без id, но с текстом договора
	public Contractor(String contractor,byte[] text, String number, String date, String name,String email1,
			String email2,String email3,String work,String contractend,String email11,String email12,String email13) {
		this.contractor=contractor;
		this.text=text;
		this.number=number;
		this.date=date;
		this.name=name;
		this.email1=email1;
		this.email2=email2;
		this.email3=email3;
		this.work=work;
		this.contractend=contractend;
		this.email11=email11;
		this.email12=email12;
		this.email13=email13;
	}
	
	//Конструктор без id и без текста договора
	public Contractor(String contractor, String number, String date, String name,String email1,
			String email2,String email3,String work,String contractend,String email11,String email12,String email13) {
		this.contractor=contractor;
		this.number=number;
		this.date=date;
		this.name=name;
		this.email1=email1;
		this.email2=email2;
		this.email3=email3;
		this.work=work;
		this.contractend=contractend;
		this.email11=email11;
		this.email12=email12;
		this.email13=email13;
	}
	
	//Конструктор с id, но без текста договора
	public Contractor(Long id,String contractor, String number, String date, String name,String email1,
			String email2,String email3,String work,String contractend,String email11,String email12,String email13) {
		this.id=id;
		this.contractor=contractor;
		this.number=number;
		this.date=date;
		this.name=name;
		this.email1=email1;
		this.email2=email2;
		this.email3=email3;
		this.work=work;
		this.contractend=contractend;
		this.email11=email11;
		this.email12=email12;
		this.email13=email13;
	}
	
	public Long getId() {
		return id;
	}
	public void setContractor(String contractor) {
		this.contractor=contractor;
	}
	public String getContractor() {
		return contractor;
	}
	public void setText(byte[] text) {
		this.text=text;
	}
	public byte[] getText() {
		return text;
	}
	public void setNumber(String number) {
		this.number=number;
	}
	public String getNumber() {
		return number;
	}
	public void setDate(String date) {
		this.date=date;
	}
	public String getDate() {
		return date;
	}
	public void setName(String name) {
		this.name=name;
	}
	public String getName() {
		return name;
	}
	public void setEmail1(String email1) {
		this.email1=email1;
	}
	public String getEmail1() {
		return email1;
	}
	public void setEmail2(String email2) {
		this.email2=email2;
	}
	public String getEmail2() {
		return email2;
	}
	public void setEmail3(String email3) {
		this.email3=email3;
	}
	public String getEmail3() {
		return email3;
	}
	public void setWork(String work) {
		this.work=work;
	}
	public String getWork() {
		return work;
	}
	public void setContractEnd(String contractend) {
		this.contractend=contractend;
	}
	public String getContractEnd() {
		return contractend;
	}
	public void setEmail11(String email11) {
		this.email11=email11;
	}
	public String getEmail11() {
		return email11;
	}
	public void setEmail12(String email12) {
		this.email12=email12;
	}
	public String getEmail12() {
		return email12;
	}
	public void setEmail13(String email13) {
		this.email13=email13;
	}
	public String getEmail13() {
		return email13;
	}
}
