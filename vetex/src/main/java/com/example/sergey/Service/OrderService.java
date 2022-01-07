package com.example.sergey.Service;

import java.util.List;

import com.example.sergey.Model.Order;

public interface OrderService {
    
	//получить все заявки по подрядчику и номеру БС
	//получить все заявки по номеру БС
	
	//получить заявку по номеру id
	Order getOrderById(Long id);
	
	//получить все заявки по подрядчику(т.е. номеру договора) по возрастанию номера заявки	
	List<Order> findAllByContractNumberOrderByOrdernumberAsc(String contractnumber);
	
	//добавить, сохранить заявку
	void createOrder(Order order);
	
	//редактировать заявку
	void editOrder(Order order);
	
	//удалить заявку
	void deleteOrder(Long id);
	
	//удалить все заявки по данному подрядчику
	
	
}
