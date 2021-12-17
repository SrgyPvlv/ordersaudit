package com.example.sergey.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sergey.Model.BsList;
import com.example.sergey.Repository.BsListRepository;

@Service
public class BsListService {

	@Autowired
	BsListRepository bsListRepository;
	
	public String findBsAddress(String bsnumber) {
		String message="БС с данным номером не найдена. Обратитесь к администратору приложения. (Пока-что Вы сможете "
				+ "внести адрес самостоятельно перед сохранением word-файла заявки.)";
		BsList bs=bsListRepository.findBsByNumber(bsnumber);
		
		if (bs!=null) {
		String bsaddress=bs.getBsAddress();
		return bsaddress;} else {return message;}	
	}
	public void deleteBs(String bsnumber) {
		BsList bs=bsListRepository.findBsByNumber(bsnumber);
		Long bsId=bs.getId();
		bsListRepository.deleteById(bsId);
	}
	public void createBs(String bsnumber,String bsaddress) {
		BsList newBs= new BsList();
		newBs.setBsNumber(bsnumber);
		newBs.setBsAddress(bsaddress);
		bsListRepository.saveAndFlush(newBs);
	}
	public void getBsById(Long id) {
		bsListRepository.getById(id);
	}
	public void saveBs(BsList bs) {
		bsListRepository.saveAndFlush(bs);
	}
}
