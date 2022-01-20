package com.example.sergey.Model;

import javax.persistence.*;

import org.hibernate.annotations.Type;

//Подрядчик и его договор

@Entity
@Table(name="contracttext")
public class ContractText {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column
	private String contractor;
	
	@Column
	@Type(type="org.hibernate.type.BinaryType")
	private byte[] text;
	
	@Column
	private String number;
	
	@Column
	private String date;
	
	@Column
	private String name;
	
	@Column
	private String email1;
	
	@Column
	private String email2;
	
	@Column
	private String email3;
	
	@Column
	private String work;
	
	@Column
	private String contractend;
	
	public ContractText() {}
	
	public ContractText(String contractor,byte[] text, String number, String date, String name,String email1,
			String email2,String email3,String work,String contractend) {
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
	}
	public ContractText(String contractor, String number, String date, String name,String email1,
			String email2,String email3,String work,String contractend) {
		this.contractor=contractor;
		this.number=number;
		this.date=date;
		this.name=name;
		this.email1=email1;
		this.email2=email2;
		this.email3=email3;
		this.work=work;
		this.contractend=contractend;
	}
	public long getId() {
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
}
