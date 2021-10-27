package com.example.sergey;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TelrosRepository extends JpaRepository<Telros,Long> {

	@Query(value="select * from telros where lower(workname) like concat('%',:filter,'%')",nativeQuery=true)
	public List<Telros> findPriceItemsByFilter(@Param("filter") String filter);

	@Query(value="select * from telros where lower(ppnumber) like concat('%',:pp,'%')",nativeQuery=true)
	public List<Telros> findPriceItemByPpNumber(@Param("pp") String pp);
	
}
