package com.example.sergey;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
	
	public List<Telros> findPriceItemsByFilter(String filter) {
		return telrosRepository.findPriceItemsByFilter(filter);
	
	}
	
	public List<Telros> findPriceItemByPpNumber(String pp) {
		return telrosRepository.findPriceItemByPpNumber(pp);
	}
	
	public List<Telros> findPriceItemByWorkName(String workname) {
		return telrosRepository.findPriceItemsByFilter(workname);
	}
	
	public void deletePriceItemById(long id) {
		telrosRepository.deleteById(id);
	}
	
	public void deleteAllPrices() {
		telrosRepository.deleteAll();
	}
	
}
