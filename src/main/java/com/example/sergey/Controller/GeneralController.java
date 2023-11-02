package com.example.sergey.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.sergey.Model.ProcentOrdersOfContractor;
import com.example.sergey.Service.ContractorService;

@Controller
public class GeneralController {

	@Autowired private ContractorService contractorService;
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	@GetMapping("/") //переход на страницу index (главную) с передачей данных о подрядчиках
	public String index(Model model) {
				
		List<ProcentOrdersOfContractor> countSumContractorAfuInfra=contractorService.countSumContractorAfuInfra();
		
	    Date contractEndDate = null;
			    
	    for(ProcentOrdersOfContractor countSumContractorAfuInfraItem:countSumContractorAfuInfra) {
		SimpleDateFormat formatterStringToDate=new SimpleDateFormat("yyyy-MM-dd");
		
		try {contractEndDate=formatterStringToDate.parse(countSumContractorAfuInfraItem.getContractend());} catch (ParseException e) {e.printStackTrace();}
		
		SimpleDateFormat formatterDateToString=new SimpleDateFormat("dd.MM.yyyy");
		String contractEndString=formatterDateToString.format(contractEndDate);
		countSumContractorAfuInfraItem.setContractend(contractEndString);
	}
		
		model.addAttribute("countSumContractorAfuInfra", countSumContractorAfuInfra);
		return "index";
	}
	
}
