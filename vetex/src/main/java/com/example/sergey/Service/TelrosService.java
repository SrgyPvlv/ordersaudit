package com.example.sergey.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.sergey.Model.Telros;
import com.example.sergey.Repository.TelrosRepository;

@Service
public class TelrosService {

@Autowired TelrosRepository telrosRepository;
	
	
	public TelrosService(TelrosRepository telrosRepository) {
		this.telrosRepository=telrosRepository;
	}
	
	public List<Telros> findAllPriceItems(){
		Sort sort=Sort.by(Sort.Direction.ASC,"ppnumber");
		return telrosRepository.findAll(sort);
	}
	
	public Telros findPriceItemById(long id) {
		return telrosRepository.getById(id);
	}
	
	public void savePriceItem(Telros priceItem) {
		telrosRepository.saveAndFlush(priceItem);
	}
	
	public List<Telros> findPriceItemsByFilter(String filter,String filter1,String filter2) {
		return telrosRepository.findPriceItemsByFilter(filter,filter1,filter2);
	
	}
	
	public List<Telros> findPriceItemByPpNumber(String pp) {
		return telrosRepository.findPriceItemByPpNumber(pp);
	}
	
	public List<Telros> findPriceItemByWorkName(String workname,String workname1, String workname2) {
		return telrosRepository.findPriceItemsByFilter(workname,workname1,workname2);
	}
	
	public void deletePriceItemById(long id) {
		telrosRepository.deleteById(id);
	}
	
	public void deleteAllPrices() {
		telrosRepository.deleteAll();
	}
	
}
