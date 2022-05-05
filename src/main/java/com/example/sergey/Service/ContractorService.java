package com.example.sergey.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.sergey.Model.Contractor;
import com.example.sergey.Repository.ContractorRepository;

@Service
public class ContractorService {

	@Autowired ContractorRepository contractorRepository;
	
	public void saveContractText(Contractor text) { //сохранение подрядчика в БД (аргумент-объект подрядчика)
		contractorRepository.saveAndFlush(text);
    }
	public void deleteContractText(Contractor text) { //удаление подрядчика в БД (аргумент-объект подрядчика)
		contractorRepository.delete(text);
	}
	public void deleteContractTextById(Long id) { //удаление подрядчика в БД по id
		contractorRepository.deleteById(id);
	}
	public Contractor getContractText(Long id) { //извлечение подрядчика из БД по id
		return contractorRepository.getById(id);
	}
	public Contractor getContractTextByContractor(String contractor) { //извлечение подрядчика из БД по логину подрядчика
		return contractorRepository.findByContractor(contractor);
	}
	public String getContractTextName(String contractnumber) { //получение названия подрядчика по номеру договора
		return contractorRepository.getNameByContractNumber(contractnumber);
	}
	public List<Contractor> getAllContractText() { //извлечение списком всех подрядчиков из БД
		return contractorRepository.findAll();
	}
	public List<Contractor> getAllContractTextSortedById() { //извлечение списком всех подрядчиков из БД по увеличению id
		return contractorRepository.findAll(Sort.by("id").ascending());
	}
	public List<Contractor> getAllContractorsWithOutText() { //извлечение всех подрядчиков из БД без поля "текст договора" по увеличению id
		return contractorRepository.getAllContractorsWithOutText();
	}
	public Contractor getContractorWithOutText(Long id) { //извлечение подрядчика из БД по id без поля "текст договора"
		return contractorRepository.getContractorWithOutText(id);
	}
	public Contractor getContractorByContractNumberWithOutText(String number) { //извлечение подрядчика по номеру договора из БД без поля "текст договора"
		return contractorRepository.getContractorByContractNumberWithOutText(number);
	}
}
