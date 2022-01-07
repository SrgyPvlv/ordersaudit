package com.example.sergey.Model;

import javax.persistence.*;

import org.hibernate.annotations.Type;

@Entity
@Table(name="contracttext")
public class ContractText {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public long id;
	
	@Column
	public String contractor;
	
	@Column
	@Type(type="org.hibernate.type.BinaryType")
	private byte[] text;
	
	@Column
	public String number;
	
	@Column
	public String date;
	
	public ContractText() {}
	
	public ContractText(String contractor,byte[] text, String number, String date ) {
		this.contractor=contractor;
		this.text=text;
		this.number=number;
		this.date=date;
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
	
}
