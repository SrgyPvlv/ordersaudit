package com.example.sergey.Model;

import java.text.DecimalFormat;

public class AfuOrdersCount { //класс для запроса из бд затрат на АФУ и Инфраструктуры каждым подрядчиком
    private String contractor;
    private double sumWithOutNdsAfu;
    private double sumWithOutNdsAll;
    private String work;
    private String procentAfu;
    private String procentInfra;
    private String name;
    private String number;
    private String date;
    private String contractend;
    
    
    DecimalFormat dF = new DecimalFormat("##");
    
    public AfuOrdersCount (String contractor, double sumWithOutNdsAfu,double sumWithOutNdsAll,String work,double result,double resultAll,String name,String number,
    		String date,String contractend) {
        this.contractor = contractor; 
        this.sumWithOutNdsAfu = sumWithOutNdsAfu;
        this.sumWithOutNdsAll = sumWithOutNdsAll;
        this.work=work;
        this.procentAfu=dF.format(Math.round((sumWithOutNdsAfu/result)*100))+"%";
        if(sumWithOutNdsAll==0) this.procentInfra=dF.format(0); else this.procentInfra=dF.format(Math.round(((sumWithOutNdsAll-sumWithOutNdsAfu)/resultAll)*100))+"%";
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
    
    public void setSumWithOutNdsAfu(double sumWithOutNdsAfu) {
        this.sumWithOutNdsAfu=sumWithOutNdsAfu;
    }

    public double getSumWithOutNdsAfu() {
        return sumWithOutNdsAfu;
    }
    
    public void setSumWithOutNdsAll(double sumWithOutNdsAll) {
        this.sumWithOutNdsAll=sumWithOutNdsAll;
    }

    public double getSumWithOutNdsAll() {
        return sumWithOutNdsAll;
    }
        
    public String getProcentAfu(){
        return procentAfu; 
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
