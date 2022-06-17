package com.example.sergey.Model;

public class AfuOrdersCount { //класс для запроса из бд затрат на АФУ и Инфраструктуры каждым подрядчиком
    private String contractor;
    private String work;
    private String procentAfu;
    private String procentInfra;
    private String name;
    private String number;
    private String date;
    private String contractend;
    
    public AfuOrdersCount (String contractor, String procentAfu,String procentInfra,String work,String name,String number,
    		String date,String contractend) {
        this.contractor = contractor; 
        this.work=work;
        this.procentAfu=procentAfu;
        this.procentInfra=procentInfra;
        this.name=name;
        this.number=number;
        this.date=date;
        this.contractend=contractend;
    }
        
    public void setContractor(String contractor) {
        this.contractor=contractor;
    }
    public String getContractor() {
        return contractor;
    }
    public void setProcentAfu(String procentAfu) {
        this.procentAfu=procentAfu;
    }   
    public String getProcentAfu(){
        return procentAfu; 
    }
    public void setProcentInfra(String procentInfra) {
        this.procentInfra=procentInfra;
    }
    public String getProcentInfra(){
        return procentInfra; 
    }
    public void setWork(String work){
        this.work=work; 
    }
    public String getWork(){
        return work; 
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
	public void setContractEnd(String contractend) {
		this.contractend=contractend;
	}
	public String getContractEnd() {
		return contractend;
	}
}
