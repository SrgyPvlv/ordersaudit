package com.example.sergey;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VetexRepository extends JpaRepository<Vetex,Long> {
	
	@Query(value="select * from vetex where lower(workname) like concat('%',:filter,'%')",nativeQuery=true)
	public List<Vetex> findPriceItemsByFilter(@Param("filter") String filter);

	@Query(value="select * from vetex where lower(ppnumber) like concat('%',:pp,'%')",nativeQuery=true)
	public List<Vetex> findPriceItemByPpNumber(@Param("pp") String pp);
}