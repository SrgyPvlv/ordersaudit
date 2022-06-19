package com.example.sergey.Repository;

public interface IAfuOrdersCount {
    int getId();
    String getContractor();
    String getWork();
    String getName();
    String getNumber();
    String getDate();
    String getContractend();
    Double getSumWithOutNdsAfu();
    Double getSumWithOutNdsAll();   
}
