package com.example.sergey;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class SpsService {

@Autowired SpsRepository spsRepository;
	
	
	public SpsService(SpsRepository spsRepository) {
		this.spsRepository=spsRepository;
	}
	
	public List<Sps> findAllPriceItems(){
		Sort sort=Sort.by(Sort.Direction.ASC,"ppnumber");
		return spsRepository.findAll(sort);
	}
	
	public Sps findPriceItemById(long id) {
		return spsRepository.getById(id);
	}
	
	public void savePriceItem(Sps priceItem) {
		spsRepository.saveAndFlush(priceItem);
	}
	
	public List<Sps> findPriceItemsByFilter(String filter) {
		return spsRepository.findPriceItemsByFilter(filter);
	
	}
	
	public List<Sps> findPriceItemByPpNumber(String pp) {
		return spsRepository.findPriceItemByPpNumber(pp);
	}
	
	public List<Sps> findPriceItemByWorkName(String workname) {
		return spsRepository.findPriceItemsByFilter(workname);
	}
	
	public void deletePriceItemById(long id) {
		spsRepository.deleteById(id);
	}
	
	public void deleteAllPrices() {
		spsRepository.deleteAll();
	}
	
}
