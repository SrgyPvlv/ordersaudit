package com.example.sergey.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.sergey.Model.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users,Integer> {
	
	public Users findByLogin(String login);//поиск пользователя по логину
	
	//поиск логина по имени пользователя(автора заявки)
	@Query(value="select (login) from users where fullname like concat('%',:avtor,'%')",nativeQuery=true)
	public String getLoginByAuthor(@Param("avtor") String avtor);
}
