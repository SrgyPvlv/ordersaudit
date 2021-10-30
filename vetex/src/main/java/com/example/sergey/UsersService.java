package com.example.sergey;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
}
