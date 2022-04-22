package com.example.sergey.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sergey.Model.Prices;
import com.example.sergey.Repository.PricesRepository;

@Service
public class PricesService {

	@Autowired PricesRepository pricesRepository;
	
	public List<Prices> findAllPriceItemsByContractor(String contractor){ //поиск всех пунктов тцп по подрядчику
		return pricesRepository.findByContractor(contractor);
	}
	
	public Prices findPriceItemById(long id) { //поиск пункта тцп по id
		return pricesRepository.getById(id);
	}
	
	public void savePriceItem(Prices priceItem) { //сохранение пукта тцп в бд
		pricesRepository.saveAndFlush(priceItem);
	}
	
	public List<Prices> findPriceItemsByFilter(String filter,String filter1,String filter2) { //поиск пуктов тцп по фильтрам в названии
		return pricesRepository.findPriceItemsByFilter(filter,filter1,filter2);
	
	}
	
	public List<Prices> findPriceItemByPpNumber(String pp) { //поиск пункта тцп по номеру
		return pricesRepository.findPriceItemByPpNumber(pp);
	}
	
	public List<Prices> findPriceItemByWorkName(String workname,String workname1, String workname2) { //поиск пуктов тцп по фильтрам в названии
		return pricesRepository.findPriceItemsByFilter(workname,workname1,workname2);
	}
	
	public void deletePriceItemById(long id) { //удаление пункта тцп по id
		pricesRepository.deleteById(id);
	}
	
	public void deleteAllPrices() { //удаление всех пунктов тцп
		pricesRepository.deleteAll();
	}
}
