package com.example.sergey.Repository;

public interface IProcentOrdersOfContractor { //интерфейс объекта полученного из БД, содержащего нужные пункты для последующей передачи на стр. index

	//это новый интерфейс с 2023 г.
	
	int getId();
    String getContractor();
    String getWork();
    String getName();
    String getNumber();
    String getDate();
    String getContractend();
    Double getSumWithOutNds();
}
