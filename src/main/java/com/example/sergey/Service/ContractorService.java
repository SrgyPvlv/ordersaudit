package com.example.sergey.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.sergey.Model.AfuOrdersCount;
import com.example.sergey.Model.Contractor;
import com.example.sergey.Repository.ContractorRepository;
import com.example.sergey.Repository.IAfuOrdersCount;

@Service
public class ContractorService {

	@Autowired ContractorRepository contractorRepository;
	
	public void saveContractor(Contractor text) { //сохранение подрядчика в БД (аргумент-объект подрядчика)
		contractorRepository.saveAndFlush(text);
    }
	public void deleteContractor(Contractor text) { //удаление подрядчика в БД (аргумент-объект подрядчика)
		contractorRepository.delete(text);
	}
	public void deleteContractorById(Long id) { //удаление подрядчика в БД по id
		contractorRepository.deleteById(id);
	}
	public Contractor getContractor(Long id) { //извлечение подрядчика из БД по id
		return contractorRepository.getById(id);
	}
	public Contractor getContractorByContractor(String contractor) { //извлечение подрядчика из БД по логину подрядчика
		return contractorRepository.findByContractor(contractor);
	}
	public String getContractorName(String contractnumber) { //получение названия подрядчика по номеру договора
		return contractorRepository.getNameByContractNumber(contractnumber);
	}
	public List<Contractor> getAllContractors() { //извлечение списком всех подрядчиков из БД
		return contractorRepository.findAll();
	}
	public List<Contractor> getAllContractorsSortedById() { //извлечение списком всех подрядчиков из БД по увеличению id
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
	
	public List<AfuOrdersCount> countSumContractorAfuInfra() { //расчет процентов по работам АФУ и Инфраструктуры по подрядчикам, у которых договор по таким работам
		List<IAfuOrdersCount> ICountSumContractorAfuInfra=contractorRepository.countSumContractorAfuInfra();
		List<AfuOrdersCount> countSumContractorAfuInfra=new ArrayList<>();
		double result = 0;
		double resultAll= 0;
		double sumwithoutnds;
		double sumwithoutndsall;
		for (IAfuOrdersCount count: ICountSumContractorAfuInfra){
			Double getSumWithOutNds=count.getSumWithOutNds();
			Double getSumWithOutNdsAll=count.getSumWithOutNdsAll();
			if(getSumWithOutNds==null) result+=0; else result+=count.getSumWithOutNds();
			if(getSumWithOutNdsAll==null) resultAll+=0; else resultAll+=count.getSumWithOutNdsAll();
		}
		for (IAfuOrdersCount count2: ICountSumContractorAfuInfra){
			String contractor=count2.getContractor();
			
			Double getSumWithOutNds2=count2.getSumWithOutNds();
			if(getSumWithOutNds2==null) sumwithoutnds=0; else sumwithoutnds=count2.getSumWithOutNds();
			
			Double getSumWithOutNdsAll=count2.getSumWithOutNdsAll();
			if(getSumWithOutNdsAll==null) sumwithoutndsall=0; else sumwithoutndsall=count2.getSumWithOutNdsAll();
			
			String work=count2.getWork();
			String name=count2.getName();
			String number=count2.getNumber();
			String date=count2.getDate();
			String contractend=count2.getContractend();
			
			System.out.printf("sumwithoutnds = %.2f \n", sumwithoutnds);
			System.out.printf("sumwithoutndsall = %.2f \n", sumwithoutndsall);
			System.out.printf("resultAll = %.2f \n", resultAll);
			
			countSumContractorAfuInfra.add(new AfuOrdersCount(contractor,sumwithoutnds,sumwithoutndsall,work,result,resultAll,name,number,date,contractend));
		}
		return countSumContractorAfuInfra;
	}
}
