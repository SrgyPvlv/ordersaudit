package com.example.sergey;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class VetexService {

	@Autowired VetexRepository vetexRepository;
	
	
	public VetexService(VetexRepository vetexRepository) {
		this.vetexRepository=vetexRepository;
	}
	
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
	
	public List<Vetex> findPriceItemsByFilter(String filter) {
		return vetexRepository.findPriceItemsByFilter(filter);
	
	}
	
	public List<Vetex> findPriceItemByPpNumber(String pp) {
		return vetexRepository.findPriceItemByPpNumber(pp);
	}
	
	public List<Vetex> findPriceItemByWorkName(String workname) {
		return vetexRepository.findPriceItemsByFilter(workname);
	}
	
	public void deletePriceItemById(long id) {
		vetexRepository.deleteById(id);
	}
}
