package com.example.sergey.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.sergey.Model.ContractText;
import com.example.sergey.Repository.ContractTextRepository;

@Service
public class ContractTextService {

	@Autowired ContractTextRepository contractTextRepository;
	
	public void saveContractText(ContractText text) { //сохранение подрядчика в БД (аргумент-объект подрядчика)
	    contractTextRepository.saveAndFlush(text);
    }
	public void deleteContractText(ContractText text) { //удаление подрядчика в БД (аргумент-объект подрядчика)
		contractTextRepository.delete(text);
	}
	public void deleteContractTextById(Long id) { //удаление подрядчика в БД по id
		contractTextRepository.deleteById(id);
	}
	public ContractText getContractText(Long id) { //извлечение подрядчика из БД по id
		return contractTextRepository.getById(id);
	}
	public ContractText getContractTextByContractor(String contractor) { //извлечение подрядчика из БД по логину подрядчика
		return contractTextRepository.findByContractor(contractor);
	}
	public String getContractTextName(String contractnumber) { //получение названия подрядчика по номеру договора
		return contractTextRepository.getNameByContractNumber(contractnumber);
	}
	public List<ContractText> getAllContractText() { //извлечение списком всех подрядчиков из БД
		return contractTextRepository.findAll();
	}
	public List<ContractText> getAllContractTextSortedById() { //извлечение списком всех подрядчиков из БД по увеличению id
		return contractTextRepository.findAll(Sort.by("id").ascending());
	}
	public List<ContractText> getAllContractorsWithOutText() { //извлечение всех подрядчиков из БД без поля "текст договора" по увеличению id
		return contractTextRepository.getAllContractorsWithOutText();
	}
	public ContractText getContractorWithOutText(Long id) { //извлечение подрядчика из БД по id без поля "текст договора"
		return contractTextRepository.getContractorWithOutText(id);
	}
	public ContractText getContractorByContractNumberWithOutText(String number) { //извлечение подрядчика по номеру договора из БД без поля "текст договора"
		return contractTextRepository.getContractorByContractNumberWithOutText(number);
	}
}
