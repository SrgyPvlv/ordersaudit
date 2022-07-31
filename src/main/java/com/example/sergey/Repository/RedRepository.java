package com.example.sergey.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.sergey.Model.Red;

public interface RedRepository extends JpaRepository<Red,Long> {

	//поиск записи используемых БД по названию БД
	public Red findByBdname(String bdname);
}
