package com.example.sergey.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.sergey.Model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
		
	//получить все заявки по подрядчику и номеру БС
	//получить все заявки по номеру БС
	//удалить все заявки по данному подрядчику
		
	//получить все заявки по подрядчику(т.е. номеру договора) по возрастанию номера заявки
	@Query(value="select * from orderlist where contractnumber like (:cn) order by ordernumber asc", nativeQuery=true)
	public List <Order> findAllByContractNumberOrderByOrdernumberAsc(@Param("cn") String contractnumber);

}
