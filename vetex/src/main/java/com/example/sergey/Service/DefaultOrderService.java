package com.example.sergey.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.sergey.Model.Order;
import com.example.sergey.Repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DefaultOrderService implements OrderService {
	
	@Autowired
	private final OrderRepository orderRepository;
	
	public List<Order> findAllByContractNumberOrderByOrdernumberAsc(String contractnumber) {
		List<Order> ordersList=orderRepository.findAllByContractNumberOrderByOrdernumberAsc(contractnumber);
		return ordersList;
	}

	@Override
	public Order getOrderById(Long id) {
		return orderRepository.getById(id);
	}

	@Override
	public void createOrder(Order order) {
		orderRepository.saveAndFlush(order);
	}

	@Override
	public void deleteOrder(Long id) {
		orderRepository.deleteById(id);
	}

	@Override
	public void editOrder(Order order) {
		orderRepository.saveAndFlush(order);
	}

	@Override
	public List<Order> findByOrderNumber(Integer ordernumber, String contractnumber) {
		List<Order> ordersList=orderRepository.findByOrderNumber(ordernumber, contractnumber);
		return ordersList;
	}
	
	@Override
	public List<Order> findByBsName(String bsnumber, String contractnumber) {
		List<Order> ordersList=orderRepository.findByBsName(bsnumber, contractnumber);
		return ordersList;
	}

}
