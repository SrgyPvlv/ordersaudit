package com.example.sergey;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class TelecomService {

@Autowired TelecomRepository telecomRepository;
	
	
	public TelecomService(TelecomRepository telecomRepository) {
		this.telecomRepository=telecomRepository;
	}
	
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
	
	public List<Telecom> findPriceItemsByFilter(String filter) {
		return telecomRepository.findPriceItemsByFilter(filter);
	
	}
	
	public List<Telecom> findPriceItemByPpNumber(String pp) {
		return telecomRepository.findPriceItemByPpNumber(pp);
	}
	
	public List<Telecom> findPriceItemByWorkName(String workname) {
		return telecomRepository.findPriceItemsByFilter(workname);
	}
	
	public void deletePriceItemById(long id) {
		telecomRepository.deleteById(id);
	}
	
	public void deleteAllPrices() {
		telecomRepository.deleteAll();
	}
	
}
