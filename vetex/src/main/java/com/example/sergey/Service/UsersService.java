package com.example.sergey.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.example.sergey.Model.Users;
import com.example.sergey.Repository.UsersRepository;

@Service
public class UsersService {

	@Autowired
	UsersRepository usersrepository;
	
    public UsersService(UsersRepository usersrepository) {
		this.usersrepository=usersrepository;
	}
	
	public List<Users> findAllUsers(){
		return usersrepository.findAll();
	}
	
	public void saveUsers(Users users) {
		usersrepository.saveAndFlush(users);
	}
	
	public Users findUsersById(int id) {
		return usersrepository.getById(id);
	
	}
	
	public void deleteUserById(int id) {
		usersrepository.deleteById(id);
	}
	public Users findUsersByLogin(String login) {
		return usersrepository.findByLogin(login);
	}
	public String getLoginByAuthor(String avtor) { //получение логина по автору заявки
		return usersrepository.getLoginByAuthor(avtor);
	}
	
}

