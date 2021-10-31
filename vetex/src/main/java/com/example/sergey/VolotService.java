package com.example.sergey;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class VolotService {

@Autowired VolotRepository volotRepository;
	
	
	public VolotService(VolotRepository volotRepository) {
		this.volotRepository=volotRepository;
	}
	
	public List<Volot> findAllPriceItems(){
		Sort sort=Sort.by(Sort.Direction.ASC,"ppnumber");
		return volotRepository.findAll(sort);
	}
	
	public Volot findPriceItemById(long id) {
		return volotRepository.getById(id);
	}
	
	public void savePriceItem(Volot priceItem) {
		volotRepository.saveAndFlush(priceItem);
	}
	
	public List<Volot> findPriceItemsByFilter(String filter) {
		return volotRepository.findPriceItemsByFilter(filter);
	
	}
	
	public List<Volot> findPriceItemByPpNumber(String pp) {
		return volotRepository.findPriceItemByPpNumber(pp);
	}
	
	public List<Volot> findPriceItemByWorkName(String workname) {
		return volotRepository.findPriceItemsByFilter(workname);
	}
	
	public void deletePriceItemById(long id) {
		volotRepository.deleteById(id);
	}
	
	public void deleteAllPrices() {
		volotRepository.deleteAll();
	}
	
}
