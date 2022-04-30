package com.example.sergey.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	
	public List<Prices> findPriceItemsByFilter(String contractor,String filter,String filter1,String filter2) { //поиск пуктов тцп по фильтрам в названии (иподрядчику)
		return pricesRepository.findPriceItemsByFilter(contractor,filter,filter1,filter2);
	
	}
	
	public List<Prices> findPriceItemByPpNumber(String contractor, String pp) { //поиск пункта тцп по номеру (и подрядчику)
		return pricesRepository.findPriceItemByPpNumber(contractor, pp);
	}
	
	public List<Prices> findPriceItemByWorkName(String contractor,String workname,String workname1, String workname2) { //поиск пуктов тцп по фильтрам в названии (и подрядчику)
		return pricesRepository.findPriceItemsByFilter(contractor,workname,workname1,workname2);
	}
	
	public void deletePriceItemById(long id) { //удаление пункта тцп по id
		pricesRepository.deleteById(id);
	}
	
	@Transactional
	public void deleteAllPricesByContractor(String contractor) { //удаление всех пунктов тцп данного подрядчика
		pricesRepository.deleteAllByContractor(contractor);
	}
}
