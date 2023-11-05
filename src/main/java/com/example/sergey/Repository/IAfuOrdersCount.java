package com.example.sergey.Repository;

public interface IAfuOrdersCount {//интерфейс объекта полученного из БД, содержащего нужные пункты для последующей передачи на стр. index 
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
