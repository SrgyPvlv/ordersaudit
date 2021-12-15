package com.example.sergey;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VetexRepository extends JpaRepository<Vetex,Long> {
	
	@Query(value="select * from vetex where lower(workname) like concat('%',:filter,'%') or lower(workname) like concat('%',:filter1,'%',:filter2,'%') or lower(workname) like concat('%',:filter2,'%',:filter1,'%')",nativeQuery=true)
	public List<Vetex> findPriceItemsByFilter(@Param("filter") String filter,@Param("filter1") String filter1,@Param("filter2") String filter2);

	@Query(value="select * from vetex where lower(ppnumber) like concat('%',:pp,'%')",nativeQuery=true)
	public List<Vetex> findPriceItemByPpNumber(@Param("pp") String pp);
}