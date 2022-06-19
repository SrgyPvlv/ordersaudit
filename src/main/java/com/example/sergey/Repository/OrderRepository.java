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
	
	//получить все заявки по подрядчику(т.е. номеру договора) по убыванию номера заявки
	
	//поиск по номеру заказа и подрядчику
	@Query(value="select * from orderlist where ordernumber in(:ordernum) and contractnumber like (:contractnum) order by ordernumber",nativeQuery=true)
	public List<Order> findByOrderNumber(@Param("ordernum") Integer ordernumber,@Param("contractnum") String contractnumber);

	//поиск по номеру БС и подрядчику
	@Query(value="select * from orderlist where lower(bsnumber) like concat('%',:bsnum,'%') and (contractnumber) like (:contractnum) order by ordernumber",nativeQuery=true)
	public List<Order> findByBsName(@Param("bsnum") String bsnumber,@Param("contractnum") String contractnumber);
	
	//поиск последнего номера заявки для данного подрядчика
	@Query(value="select max(ordernumber) from orderlist where contractnumber like (:cn)",nativeQuery=true)
	public int showLastOrderNumber(@Param("cn") String contractnumber);

	//удалить Все заказы данного подрядчика
	public void deleteAllByContractnumber(String contractnumber);
	
}
