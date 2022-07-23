package com.example.sergey.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.sergey.Model.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users,Integer> {
	
	//поиск пользователя по логину
	public Users findByLogin(String login);
	
	//поиск логина по имени пользователя(автора заявки)
	@Query(value="select (login) from users where lower(fullname) like lower(concat('%',:avtor,'%'))",nativeQuery=true)
	public String getLoginByAuthor(@Param("avtor") String avtor);
	
	//поиск пользователя по фамилии
	@Query(value="select * from users where lower(fullname) like lower(concat('%',:avtor,'%'))",nativeQuery=true)
	public List<Users> findUserByFullName(@Param("avtor") String avtor);
}
