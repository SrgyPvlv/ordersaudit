package com.example.sergey.Model;

import java.text.DecimalFormat;

public class AfuOrdersCount { //класс для запроса из бд затрат на афу каждым подрядчиком
    private String contractor;
    private double sumWithOutNds;
    private String work;
    private String procentAfu;
    private String name;
    private String number;
    private String date;
    private String contractend;
    
    
    DecimalFormat dF = new DecimalFormat("##");
    
    public AfuOrdersCount (String contractor, double sumWithOutNds, String work, double result, String name, String number,
    		String date, String contractend) {
        this.contractor = contractor; 
        this.sumWithOutNds = sumWithOutNds;
        this.work=work;
        this.procentAfu=dF.format(Math.round((sumWithOutNds/result)*100))+"%";
        this.name=name;
        this.number=number;
        this.date=date;
        this.contractend=contractend;
    }
    
    public AfuOrdersCount (String contractor, double sumWithOutNds, String work) {
        this.contractor = contractor; 
        this.sumWithOutNds = sumWithOutNds;
        this.work=work;
    }
    
    public void setContractor(String contractor) {
        this.contractor=contractor;
    }
    
    public String getContractor() {
        return contractor;
    }
    
    public void setSumWithOutNds(double sumWithOutNds) {
        this.sumWithOutNds=sumWithOutNds;
    }

    public double getSumWithOutNds() {
        return sumWithOutNds;
    }
        
    public String getProcentAfu(){
        return procentAfu; 
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
