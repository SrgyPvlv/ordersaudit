package com.example.sergey.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.sergey.Model.ContractText;

@Repository
public interface ContractTextRepository extends JpaRepository<ContractText,Long> {

	//получение названия подрядчика по номеру договора
	@Query(value="select (name) from contracttext where number like (:number)",nativeQuery=true)
	public String getNameByContractNumber(@Param("number") String number);
	
	//извлечение всех подрядчиков из БД без поля "текст договора"
	@Query(value="select new ContractText(c.id,c.contractor,c.number,c.date,c.name,c.email1,c.email2,"
			+ "c.email3,c.work,c.contractend,c.email11,c.email12,c.email13) from ContractText c order by c.id")
	public List<ContractText> getAllWithSomeColumn();
	
	//извлечение подрядчика по id из БД без поля "текст договора"
	@Query(value="select new ContractText(c.id,c.contractor,c.number,c.date,c.name,c.email1,c.email2,"
			+ "c.email3,c.work,c.contractend,c.email11,c.email12,c.email13) from ContractText c where c.id=(:id)")
	public ContractText getContractorWithOutText(@Param("id") Long id);
	
	//извлечение подрядчика по номеру договора из БД без поля "текст договора"
		@Query(value="select new ContractText(c.id,c.contractor,c.number,c.date,c.name,c.email1,c.email2,"
				+ "c.email3,c.work,c.contractend,c.email11,c.email12,c.email13) from ContractText c where c.number like (:number)")
		public ContractText getContractorByContractNumberWithOutText(@Param("number") String number);
	
}
