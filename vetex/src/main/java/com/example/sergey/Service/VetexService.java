package com.example.sergey.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.sergey.Model.Vetex;
import com.example.sergey.Repository.VetexRepository;

@Service
public class VetexService {

	@Autowired VetexRepository vetexRepository;
	
	public List<Vetex> findAllPriceItems(){
		Sort sort=Sort.by(Sort.Direction.ASC,"ppnumber");
		return vetexRepository.findAll(sort);
	}
	
	public Vetex findPriceItemById(long id) {
		return vetexRepository.getById(id);
	}
	
	public void savePriceItem(Vetex priceItem) {
		vetexRepository.saveAndFlush(priceItem);
	}
	
	public List<Vetex> findPriceItemsByFilter(String filter,String filter1,String filter2) {
		return vetexRepository.findPriceItemsByFilter(filter,filter1,filter2);
	
	}
	
	public List<Vetex> findPriceItemByPpNumber(String pp) {
		return vetexRepository.findPriceItemByPpNumber(pp);
	}
	
	public List<Vetex> findPriceItemByWorkName(String workname,String workname1, String workname2) {
		return vetexRepository.findPriceItemsByFilter(workname,workname1,workname2);
	}
	
	public void deletePriceItemById(long id) {
		vetexRepository.deleteById(id);
	}
	
	public void deleteAllPrices() {
		vetexRepository.deleteAll();
	}
}
