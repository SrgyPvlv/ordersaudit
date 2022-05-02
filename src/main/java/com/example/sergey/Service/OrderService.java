package com.example.sergey.Service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.example.sergey.Model.Order;

public interface OrderService {
    	
	//получить заявку по номеру id
	public Order getOrderById(Long id);
	
	//получить все заявки по подрядчику(т.е. номеру договора) по возрастанию номера заявки	
	public List<Order> findAllByContractNumberOrderByOrdernumberAsc(String contractnumber);
	
	//получить все заявки по номеру БС и подрядчику
	public List<Order> findByBsName(String bsnumber,String contractnumber);
	
	//получить все заявки по номеру Заявки и подрядчику
	public List<Order> findByOrderNumber(Integer ordernumber,String contractnumber);
	
	//создать номер для следующей заявки
	public int showNextOrderNumber(String contractnumber);
	
	//добавить, сохранить заявку
	public void createOrder(Order order);
	
	//редактировать заявку
	public void editOrder(Order order);
	
	//удалить заявку
	public void deleteOrder(Long id);
	
	//удалить Все заказы данного подрядчика
	@Transactional
	public void deleteAllByContractnumber(String contractnumber);
	
}
