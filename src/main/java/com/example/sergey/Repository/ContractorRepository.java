package com.example.sergey.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.sergey.Model.Contractor;

@Repository
public interface ContractorRepository extends JpaRepository<Contractor,Long> {

	//получение названия подрядчика по номеру договора
	@Query(value="select (name) from contractor where number like (:number)",nativeQuery=true)
	public String getNameByContractNumber(@Param("number") String number);
	
	//извлечение всех подрядчиков из БД без поля "текст договора"
	@Query(value="select new Contractor(c.id,c.contractor,c.number,c.date,c.name,c.email1,c.email2,"
			+ "c.email3,c.work,c.contractend,c.email11,c.email12,c.email13) from Contractor c order by c.id")
	public List<Contractor> getAllContractorsWithOutText();
	
	//подсчет Всех расходов каждого подрядчика и расходов по заказам АФУ,если в его договоре есть работы по АФУ и Инфраструктуре 
	@Query(value="with mytable1 as (select c.id,c.contractor,c.work,c.name,c.number,c.date,c.contractend,sum(o.sumwithoutnds) as sumWithOutNdsAfu from contractor as c "
			+ "left join orderlist as o on c.contractor=o.contractor and lower(o.worktype) similar to '%(афу|азим|автовыш|юстиро|ррл|ррс|радио|трубосто|антен|трос|"
			+ "фидер|джамп|опти|пита|кабел|кожух|комбайн|репитер|ксв|телекоммуник|кросс|rru|sfp|ret|odu|idu|gps|utp)%' and lower(c.work) similar to '%(афу)%' "
			+ "group by (c.id,c.contractor,c.work,c.name,c.number,c.date,c.contractend)),mytable2 as (select c.contractor,c.work,c.name,c.number,"
			+ "c.date,c.contractend,sum(o.sumwithoutnds) as sumWithOutNdsAll from contractor as c left join orderlist as o on "
			+ "c.contractor=o.contractor and lower(c.work) similar to '%(афу)%' and lower(c.work) similar to '%(инфраструктур)%'  "
			+ "group by (c.contractor,c.work,c.name,c.number,c.date,c.contractend)) select mytable1.id,mytable1.contractor,"
			+ "mytable1.work,mytable1.name,mytable1.number,mytable1.date,mytable1.contractend,mytable1.sumWithOutNdsAfu,"
			+ "mytable2.sumWithOutNdsAll from mytable1 left join mytable2 on mytable1.contractor=mytable2.contractor "
			+ "order by mytable1.id;",nativeQuery=true)
		public List<IAfuOrdersCount> countSumContractorAfuInfra();
	
	//извлечение подрядчика по id из БД без поля "текст договора"
	@Query(value="select new Contractor(c.id,c.contractor,c.number,c.date,c.name,c.email1,c.email2,"
			+ "c.email3,c.work,c.contractend,c.email11,c.email12,c.email13) from Contractor c where c.id=(:id)")
	public Contractor getContractorWithOutText(@Param("id") Long id);
	
	//извлечение подрядчика по номеру договора из БД без поля "текст договора"
		@Query(value="select new Contractor(c.id,c.contractor,c.number,c.date,c.name,c.email1,c.email2,"
				+ "c.email3,c.work,c.contractend,c.email11,c.email12,c.email13) from Contractor c where c.number like (:number)")
		public Contractor getContractorByContractNumberWithOutText(@Param("number") String number);

	//извлечение подрядчика по логину подрядчика
		public Contractor findByContractor(String contractor);
	
}
