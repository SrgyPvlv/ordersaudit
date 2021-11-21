package com.example.sergey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
