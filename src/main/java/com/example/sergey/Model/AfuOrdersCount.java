package com.example.sergey.Model;

import java.text.DecimalFormat;

public class AfuOrdersCount { //класс для запроса из бд затрат на афу каждым подрядчиком
    private String contractor;
    private double sumWithOutNds;
    private String work;
    private String procent;
    
    DecimalFormat dF = new DecimalFormat("##");
    
    public AfuOrdersCount (String contractor, double sumWithOutNds, String work, double result) {
        this.contractor = contractor; 
        this.sumWithOutNds = sumWithOutNds;
        this.work=work;
        this.procent=dF.format(Math.round((sumWithOutNds/result)*100))+"%"; 
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
        
    public String getProcent(){
        return procent; 
    }
    
    public void setWork(String work){
        this.work=work; 
    }
    
    public String getWork(){
        return work; 
    }
}
