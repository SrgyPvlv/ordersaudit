package com.example.sergey.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sergey.Model.Red;
import com.example.sergey.Repository.RedRepository;

@Service
public class RedService {

	@Autowired
	RedRepository redRepository;
	
	public List<Red> findAllReds(){ //получение записей БД Red и других БД, используемых в приложении
		return redRepository.findAll();
	}
	
	public void saveRed(Red red) { //сохранение записи БД
		redRepository.saveAndFlush(red);
	}
	
	public Red findRedById(long id) { //получение записи БД по id
		return redRepository.getById(id);
	
	}
	
	public void deleteRedById(long id) { //удаление записи БД по id
		redRepository.deleteById(id);
	}
	
	public Red findByBdname(String bdname) { //получение пользователя по логину
		return redRepository.findByBdname(bdname);
	}
}
