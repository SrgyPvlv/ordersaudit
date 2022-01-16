package com.example.sergey.Model;

import javax.persistence.*;

import org.hibernate.annotations.Type;

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
	
	public ContractText() {}
	
	public ContractText(String contractor,byte[] text, String number, String date, String name) {
		this.contractor=contractor;
		this.text=text;
		this.number=number;
		this.date=date;
		this.name=name;
	}
	public ContractText(String contractor, String number, String date, String name) {
		this.contractor=contractor;
		this.number=number;
		this.date=date;
		this.name=name;
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
	
}
