package com.example.sergey.Model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcentOrdersOfContractor { //класс для запроса из бд затрат на АФУ + Инфраструктуры каждым подрядчиком

	private String contractor;
    private String work;
    private String procentOrders;
    private String name;
    private String number;
    private String date;
    private String contractend;
    
    public int getDateValid() {
    	LocalDate today = LocalDate.now();
		LocalDate contractEndDate = null;
		String [] contractendMassive = this.contractend.split("[.]");
		String contractendFormatted = contractendMassive[2] + "-" + contractendMassive[1] + "-" + contractendMassive[0];
		try {contractEndDate=LocalDate.parse(contractendFormatted);} catch (Exception e) {e.printStackTrace();}
		return contractEndDate.compareTo(today);
    }
}
