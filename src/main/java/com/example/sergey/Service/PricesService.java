package com.example.sergey.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.sergey.Model.Prices;
import com.example.sergey.Repository.PricesRepository;

@Service
public class PricesService {

	@Autowired PricesRepository pricesRepository;
	
	public List<Prices> findAllPriceItemsByContractor(){
		return pricesRepository.findPricesByContractor();
	}
	
	public Prices findPriceItemById(long id) {
		return pricesRepository.getById(id);
	}
	
	public void savePriceItem(Prices priceItem) {
		pricesRepository.saveAndFlush(priceItem);
	}
	
	public List<Prices> findPriceItemsByFilter(String filter,String filter1,String filter2) {
		return pricesRepository.findPriceItemsByFilter(filter,filter1,filter2);
	
	}
	
	public List<Prices> findPriceItemByPpNumber(String pp) {
		return pricesRepository.findPriceItemByPpNumber(pp);
	}
	
	public List<Prices> findPriceItemByWorkName(String workname,String workname1, String workname2) {
		return pricesRepository.findPriceItemsByFilter(workname,workname1,workname2);
	}
	
	public void deletePriceItemById(long id) {
		pricesRepository.deleteById(id);
	}
	
	public void deleteAllPrices() {
		pricesRepository.deleteAll();
	}
}
