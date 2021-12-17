package com.example.sergey.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.sergey.Model.Sps;
import com.example.sergey.Repository.SpsRepository;

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
	
	public List<Sps> findPriceItemsByFilter(String filter,String filter1,String filter2) {
		return spsRepository.findPriceItemsByFilter(filter,filter1,filter2);
	
	}
	
	public List<Sps> findPriceItemByPpNumber(String pp) {
		return spsRepository.findPriceItemByPpNumber(pp);
	}
	
	public List<Sps> findPriceItemByWorkName(String workname,String workname1, String workname2) {
		return spsRepository.findPriceItemsByFilter(workname,workname1,workname2);
	}
	
	public void deletePriceItemById(long id) {
		spsRepository.deleteById(id);
	}
	
	public void deleteAllPrices() {
		spsRepository.deleteAll();
	}
	
}
