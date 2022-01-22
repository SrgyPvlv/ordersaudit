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
	
	//извлечение всех подрядчиков из БД с избранными полями
	@Query(value="select new ContractText(c.id,c.contractor,c.name,c.work,c.contractend) from ContractText c")
	public List<ContractText> getAllWithSomeColumn();
}
