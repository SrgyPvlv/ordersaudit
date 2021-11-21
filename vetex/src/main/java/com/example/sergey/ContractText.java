package com.example.sergey;

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
	
	public ContractText() {}
	
	public ContractText(String contractor,byte[] text ) {
		this.contractor=contractor;
		this.text=text;
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
}
