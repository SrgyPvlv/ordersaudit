package com.example.sergey.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.sergey.Model.AfuOrdersCount;
import com.example.sergey.Model.Contractor;
import com.example.sergey.Service.ContractorService;
import com.example.sergey.Service.DefaultOrderService;

@Controller
public class GeneralController {

	@Autowired private ContractorService contractorService;
	@Autowired private DefaultOrderService orderService;
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	@GetMapping("/") //переход на страницу index (главную) с передачей данных о подрядчиках
	public String index(Model model) {
				
		//List<Contractor> contractors=contractorService.getAllContractorsWithOutText();
		List<AfuOrdersCount> countSumContractorAfu=contractorService.countSumContractorAfu();
		
	    Date contractEndDate = null;
		
		/*for(Contractor contractor:contractors) {
			SimpleDateFormat formatterStringToDate=new SimpleDateFormat("yyyy-MM-dd");
			
			try {contractEndDate=formatterStringToDate.parse(contractor.getContractEnd());} catch (ParseException e) {e.printStackTrace();}
			
			SimpleDateFormat formatterDateToString=new SimpleDateFormat("dd.MM.yyyy");
			String contractEndString=formatterDateToString.format(contractEndDate);
			contractor.setContractEnd(contractEndString);
		}*/
	    
	    for(AfuOrdersCount countSumContractorAfuItem:countSumContractorAfu) {
		SimpleDateFormat formatterStringToDate=new SimpleDateFormat("yyyy-MM-dd");
		
		try {contractEndDate=formatterStringToDate.parse(countSumContractorAfuItem.getContractEnd());} catch (ParseException e) {e.printStackTrace();}
		
		SimpleDateFormat formatterDateToString=new SimpleDateFormat("dd.MM.yyyy");
		String contractEndString=formatterDateToString.format(contractEndDate);
		countSumContractorAfuItem.setContractEnd(contractEndString);
	}
		
		//model.addAttribute("contractors", contractors);
		model.addAttribute("countSumContractorAfu", countSumContractorAfu);
		return "index";
	}
	
}
