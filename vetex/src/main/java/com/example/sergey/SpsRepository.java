package com.example.sergey;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpsRepository extends JpaRepository<Sps,Long> {

	@Query(value="select * from sps where lower(workname) like concat('%',:filter,'%')",nativeQuery=true)
	public List<Sps> findPriceItemsByFilter(@Param("filter") String filter);

	@Query(value="select * from sps where lower(ppnumber) like concat('%',:pp,'%')",nativeQuery=true)
	public List<Sps> findPriceItemByPpNumber(@Param("pp") String pp);
	
}
