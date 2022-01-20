package com.example.sergey.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.sergey.Model.ContractText;

public interface ContractTextRepository extends JpaRepository<ContractText,Long> {

	//получение названия подрядчика по номеру договора
	@Query(value="select (name) from contracttext where number like (:number)",nativeQuery=true)
	public String getNameByContractNumber(@Param("number") String number);
	
}
