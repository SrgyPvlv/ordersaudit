package com.example.sergey.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sergey.Model.AfuOrdersCount;
import com.example.sergey.Model.Order;
import com.example.sergey.Repository.IAfuOrdersCount;
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
	public List<AfuOrdersCount> countSumContractorAfu() {
		List<IAfuOrdersCount> ICountSumContractorAfu=orderRepository.countSumContractorAfu();
		List<AfuOrdersCount> countSumContractorAfu=new ArrayList<>();
		double result=0;
		for (IAfuOrdersCount count: ICountSumContractorAfu){
		result+=count.getSumWithOutNds();
		}
		for (IAfuOrdersCount count2: ICountSumContractorAfu){
			String contractor=count2.getContractor();
			double sumwithoutnds=count2.getSumWithOutNds();
			String work=count2.getWork();
			
			countSumContractorAfu.add(new AfuOrdersCount(contractor,sumwithoutnds,work,result));
		}
		return countSumContractorAfu;
	}

}
