package com.example.sergey.Controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.sergey.Model.ContractText;
import com.example.sergey.Model.Order;
import com.example.sergey.Service.ContractTextService;

@Controller
public class GeneralController {

	@Autowired
	private ContractTextService contractTextService;
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	@GetMapping("/") //переход на страницу index (главную) с передачей данных о подрядчиках
	public String index(Model model) {
		
		List<ContractText> contractors=contractTextService.getAllWithSomeColumn();
		
	    Date contractEndDate = null;
		
		for(ContractText contractor:contractors) {
			SimpleDateFormat formatterStringToDate=new SimpleDateFormat("yyyy-MM-dd");
			
			try {contractEndDate=formatterStringToDate.parse(contractor.getContractEnd());} catch (ParseException e) {e.printStackTrace();}
			
			SimpleDateFormat formatterDateToString=new SimpleDateFormat("dd.MM.yyyy");
			String contractEndString=formatterDateToString.format(contractEndDate);
			contractor.setContractEnd(contractEndString);
		}
		
		model.addAttribute("contractors", contractors);
		return "index";
	}
	
}
