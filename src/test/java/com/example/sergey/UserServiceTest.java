package com.example.sergey;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.sergey.Model.Users;
import com.example.sergey.Repository.UsersRepository;
import com.example.sergey.Service.UsersService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	@InjectMocks
	private UsersService userService;
	
	@Mock
	private UsersRepository userRepository;
	
	@Before
	public void init() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void testFindUsersByLogin() {
		Users u=new Users();
		u.setLogin("piska");
		when(userRepository.findByLogin("piska")).thenReturn(u);
		
		Users users=userService.findUsersByLogin("piska");
		
		verify(userRepository).findByLogin("piska");
		
		assertEquals("piska",users.getLogin());
	}
	
}
