package com.example.sergey.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="red")
public class Red { //таблица содержит URL адресса БД, используемых в приложении
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column
	private String bdname;
	
	@Column
	private String ipaddress;
	
	public Red() {}
	
	public Red(String bdname,String ipaddress) {
		this.bdname=bdname;
		this.ipaddress=ipaddress;
	}
	
	public long getId() {
		return id;
	}
	
	public String getBdName() {
		return bdname;
	}
	public void setBdName(String bdname) {
		this.bdname=bdname;
	}
	
	public String getIpAddress() {
		return ipaddress;
	}
	public void setIpAddress(String ipaddress) {
		this.ipaddress=ipaddress;
	}
}
