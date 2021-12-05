package com.example.sergey;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BsListRepository extends JpaRepository<BsList,Long> {

	@Query(value="select * from bslist where bsnumber in(:filter)",nativeQuery=true)
	public BsList findBsByNumber(@Param("filter") String filter);
}
