package com.example.sergey.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.sergey.Model.Users;
import com.example.sergey.Repository.UsersRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	UsersRepository usersrepository;
	
	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Users users=usersrepository.findByLogin(userName);
		
		if(users==null) {
		throw new UsernameNotFoundException ("Unknown user: "+ userName);}
		String encryptedPassword = this.passwordEncoder().encode(users.getPassword());
		UserDetails user=User.builder()
				.username(users.getLogin())
				.password(encryptedPassword)
				.roles(users.getRole())
				.build();
		return user;
	}
	
}
