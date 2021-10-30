package com.example.sergey;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TelecomRepository extends JpaRepository<Telecom,Long> {

	@Query(value="select * from telecom where lower(workname) like concat('%',:filter,'%')",nativeQuery=true)
	public List<Telecom> findPriceItemsByFilter(@Param("filter") String filter);

	@Query(value="select * from telecom where lower(ppnumber) like concat('%',:pp,'%')",nativeQuery=true)
	public List<Telecom> findPriceItemByPpNumber(@Param("pp") String pp);
	
}
