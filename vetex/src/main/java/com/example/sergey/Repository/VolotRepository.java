package com.example.sergey.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.sergey.Model.Volot;

public interface VolotRepository extends JpaRepository<Volot,Long> {

	@Query(value="select * from volot where lower(workname) like concat('%',:filter,'%') or lower(workname) like concat('%',:filter1,'%',:filter2,'%') or lower(workname) like concat('%',:filter2,'%',:filter1,'%')",nativeQuery=true)
	public List<Volot> findPriceItemsByFilter(@Param("filter") String filter,@Param("filter1") String filter1,@Param("filter2") String filter2);

	@Query(value="select * from volot where lower(ppnumber) like (:pp)",nativeQuery=true)
	public List<Volot> findPriceItemByPpNumber(@Param("pp") String pp);
	
}
