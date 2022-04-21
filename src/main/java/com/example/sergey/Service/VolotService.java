package com.example.sergey.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.sergey.Model.Volot;
import com.example.sergey.Repository.VolotRepository;

@Service
public class VolotService {

@Autowired VolotRepository volotRepository;
	
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
	
	public List<Volot> findPriceItemsByFilter(String filter,String filter1,String filter2) {
		return volotRepository.findPriceItemsByFilter(filter,filter1,filter2);
	
	}
	
	public List<Volot> findPriceItemByPpNumber(String pp) {
		return volotRepository.findPriceItemByPpNumber(pp);
	}
	
	public List<Volot> findPriceItemByWorkName(String workname,String workname1, String workname2) {
		return volotRepository.findPriceItemsByFilter(workname,workname1,workname2);
	}
	
	public void deletePriceItemById(long id) {
		volotRepository.deleteById(id);
	}
	
	public void deleteAllPrices() {
		volotRepository.deleteAll();
	}
	
}
