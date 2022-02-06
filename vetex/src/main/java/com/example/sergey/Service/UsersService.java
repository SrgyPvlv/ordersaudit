package com.example.sergey.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sergey.Model.Users;
import com.example.sergey.Repository.UsersRepository;

@Service
public class UsersService {

	@Autowired
	UsersRepository usersrepository;
		
	public List<Users> findAllUsers(){ //получение всех пользователей
		return usersrepository.findAll();
	}
	
	public void saveUsers(Users users) { //сохранение пользователя
		usersrepository.saveAndFlush(users);
	}
	
	public Users findUsersById(int id) { //получение пользователя по id
		return usersrepository.getById(id);
	
	}
	
	public void deleteUserById(int id) { //удаление пользователя по id
		usersrepository.deleteById(id);
	}
	public Users findUsersByLogin(String login) { //получение пользователя по логину
		return usersrepository.findByLogin(login);
	}
	public String getLoginByAuthor(String avtor) { //получение логина по автору заявки
		return usersrepository.getLoginByAuthor(avtor);
	}
	public List<Users> findUserByFullName(String avtor) { //получение пользователя по фамилии автора заявки
		return usersrepository.findUserByFullName(avtor);
	}
}

