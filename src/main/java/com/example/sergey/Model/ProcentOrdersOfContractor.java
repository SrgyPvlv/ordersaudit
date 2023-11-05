package com.example.sergey.Model;

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
}
