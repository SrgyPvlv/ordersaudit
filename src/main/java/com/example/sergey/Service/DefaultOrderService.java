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
	
	@Override
	public List<Order> findByAuthorAndContractnumberOrderByOrdernumberAsc(String author, String contractnumber) {
		List<Order> ordersList=orderRepository.findByAuthorAndContractnumberOrderByOrdernumberAsc(author, contractnumber);
		return ordersList;
	}
	
	@Override
	public int showNextOrderNumber(String contractnumber) {
		int lastNumber;
		try {lastNumber=orderRepository.showLastOrderNumber(contractnumber);} catch(Exception e) {lastNumber=0;}
		int nextNumber=lastNumber+1;
		return nextNumber;
	}
	
	@Override
	public void deleteAllOrdersByContractNumber(String contractnumber) {
		orderRepository.deleteAllByContractnumber(contractnumber);
	}
	
	@Override
	public List<Order> searchOrdersThroughAllContractors(String author, String contractname, String bsnumber, String worktype, String worktcp) {
		
		String worktype1;
		String worktype2;
		String worktcp1;
		String worktcp2;
		String[] words=worktype.split("\\s");
		if (words.length==1) {
			worktype1=words[0];
			worktype2="";}
		else {
			worktype1=words[0];
			worktype2=words[1];
		}
		
		String[] words1=worktcp.split("\\s");
		if (words1.length==1) {
			worktcp1=words1[0];
			worktcp2="";}
		else {
			worktcp1=words1[0];
			worktcp2=words1[1];
		}
		
		List<Order> ordersList=orderRepository.searchOrdersThroughAllContractors(author, contractname, bsnumber, worktype, worktype1, worktype2,
				worktcp, worktcp1,worktcp2);
		return ordersList;
	}
}
