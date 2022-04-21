package com.example.sergey.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.sergey.Model.Telecom;
import com.example.sergey.Repository.TelecomRepository;

@Service
public class TelecomService {

@Autowired TelecomRepository telecomRepository;
	
	public List<Telecom> findAllPriceItems(){
		Sort sort=Sort.by(Sort.Direction.ASC,"ppnumber");
		return telecomRepository.findAll(sort);
	}
	
	public Telecom findPriceItemById(long id) {
		return telecomRepository.getById(id);
	}
	
	public void savePriceItem(Telecom priceItem) {
		telecomRepository.saveAndFlush(priceItem);
	}
	
	public List<Telecom> findPriceItemsByFilter(String filter,String filter1,String filter2) {
		return telecomRepository.findPriceItemsByFilter(filter,filter1,filter2);
	
	}
	
	public List<Telecom> findPriceItemByPpNumber(String pp) {
		return telecomRepository.findPriceItemByPpNumber(pp);
	}
	
	public List<Telecom> findPriceItemByWorkName(String workname,String workname1, String workname2) {
		return telecomRepository.findPriceItemsByFilter(workname,workname1,workname2);
	}
	
	public void deletePriceItemById(long id) {
		telecomRepository.deleteById(id);
	}
	
	public void deleteAllPrices() {
		telecomRepository.deleteAll();
	}
	
}
