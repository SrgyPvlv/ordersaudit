package com.example.sergey;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class Users {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column
	private String login;
	
	@Column
	private String password;
	
	@Column
	private String role;
	
	@Column
	private String fullname;
	
	public Users() {}
	
	public Users(String login,String password,String role,String fullname) {
		this.login=login;
		this.password=password;
		this.role=role;
		this.fullname=fullname;
	}
	
	public int getId() {
		return id;
	}
	
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login=login;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password=password;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role=role;
	}
	
	public String getFullName() {
		return fullname;
	}
	
	public void setFullName(String fullname) {
		this.fullname=fullname;
	}
}
