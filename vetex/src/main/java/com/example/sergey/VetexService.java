package com.example.sergey;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VetexService {

	@Autowired VetexRepository vetexRepository;
	
	
	public VetexService(VetexRepository vetexRepository) {
		this.vetexRepository=vetexRepository;
	}
	
	public List<Vetex> findAllPriceItems(){
		return vetexRepository.findAll();
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
	
	public List<Vetex> findPriceItemByPpNumber(int pp) {
		return vetexRepository.findPriceItemByPpNumber(pp);
	}
	
	public void deletePriceItemById(long id) {
		vetexRepository.deleteById(id);
	}
}
