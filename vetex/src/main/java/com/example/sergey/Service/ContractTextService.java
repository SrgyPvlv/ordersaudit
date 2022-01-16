package com.example.sergey.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sergey.Model.ContractText;
import com.example.sergey.Repository.ContractTextRepository;

@Service
public class ContractTextService {

	@Autowired ContractTextRepository contractTextRepository;
	
	public void saveContractText(ContractText text) {
	    contractTextRepository.saveAndFlush(text);
    }
	public void deleteContractText(ContractText text) {
		contractTextRepository.delete(text);
	}
	public void deleteContractTextById(long id) {
		contractTextRepository.deleteById(id);
	}
	public ContractText getContractText(long id) {
		return contractTextRepository.getById(id);
	}
	public String getContractTextName(String contractnumber) {
		return contractTextRepository.getNameByContractNumber(contractnumber);
	}
	public List<ContractText> getAllContractText() {
		return contractTextRepository.findAll();
	}
	
}
