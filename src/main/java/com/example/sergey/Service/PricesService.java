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
	
	public List<Prices> findAllPriceItemsByContractor(String contractor){ //поиск всех пунктов тцп по подрядчику (сортированных по номеру таблицы и пункта)
		return pricesRepository.findByContractorOrderByTablenumberAscPpnumberAsc(contractor);
	}
	
	public Prices findPriceItemById(long id) { //поиск пункта тцп по id
		return pricesRepository.getById(id);
	}
	
	public void savePriceItem(Prices priceItem) { //сохранение пукта тцп в бд
		pricesRepository.saveAndFlush(priceItem);
	}
		
	public List<Prices> findPriceItemByPpNumber(String contractor, String pp) { //поиск пункта тцп по номеру (и подрядчику)
		return pricesRepository.findPriceItemByPpNumber(contractor, pp);
	}
	
	public List<Prices> findPriceItemByWorkName(String contractor,String workname) { //поиск пуктов тцп по фильтрам в названии (и подрядчику), сортированные по номеру таблицы и номеру пункта
		
		String worknameLower=workname.toLowerCase();
		String workname1;
		String workname2;
		String[] words=worknameLower.split("\\s");
		if (words.length==1) {
		workname1=words[0];
		workname2="";}
		else {
			workname1=words[0];
			workname2=words[1];
		}
		
		return pricesRepository.findPriceItemsByFilter(contractor,worknameLower,workname1,workname2);
	}
	
	public void deletePriceItemById(long id) { //удаление пункта тцп по id
		pricesRepository.deleteById(id);
	}
	
	@Transactional
	public void deleteAllPricesByContractor(String contractor) { //удаление всех пунктов тцп данного подрядчика
		pricesRepository.deleteAllByContractor(contractor);
	}
	
	public List<Prices> findPriceItemsByWorkNameThroughAllContractors(String workname) { //поиск пунктов тцп по фильтрам в названии (по всем подрядчикам)
		
		String worknameLower=workname.toLowerCase();
		String workname1;
		String workname2;
		String[] words=worknameLower.split("\\s");
		if (words.length==1) {
		workname1=words[0];
		workname2="";}
		else {
			workname1=words[0];
			workname2=words[1];
		}
		
		return pricesRepository.findPriceItemsByFilterThroughAllContractors(worknameLower, workname1, workname2);
	}
}
