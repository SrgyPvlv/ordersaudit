package com.example.sergey.Controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.sergey.Model.Contractor;
import com.example.sergey.Service.ContractorService;
import com.example.sergey.Service.OrderService;
import com.example.sergey.Service.PricesService;

//создание, редактирование, удаление, скачивание/загрузка подрядчиков, а также их ТЦП и договоров 
@Controller
public class ContractorController {

	@Autowired private ContractorService contractorService;
	@Autowired private PricesService pricesService;
	@Autowired private OrderService orderService;
	
	
	@PostMapping("/admin/loadContractText") // загрузка файла Договора в БД
	public String loadContractText(@RequestParam("file") MultipartFile file,
			@RequestParam("contractor") String contractor, Model model)throws IOException{
		byte[] text=file.getBytes();
						
		try {
			Contractor contractText=contractorService.getContractorByContractor(contractor);
			contractText.setText(text);
			contractorService.saveContractor(contractText);
			String note="Файл Договора сохранен в базе данных!";
		    model.addAttribute("note", note);
			return"noLoad";}
			catch(Exception e) {
				  String note="Ошибка  - Что-то пошло не так!";
			      model.addAttribute("note", note);
				  return"noLoad";}	
			}
	
	@GetMapping("/downloadContractText") // скачивание файла договора из БД
	public ResponseEntity<Resource> fileDown(@RequestParam("contractor") String contractor, 
			HttpServletResponse response,HttpServletRequest request) throws IOException{
		
		try {
			Contractor contractText=contractorService.getContractorByContractor(contractor);
		    String filename="contracttext_"+contractor+".doc";
		    byte[] text=contractText.getText();
		    InputStreamResource file=new InputStreamResource(new ByteArrayInputStream(text));
			
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
			        .contentType(MediaType.parseMediaType("application/msword"))
			        .body(file);}
	catch(Exception e) {response.sendRedirect("/errorDownload");}
		return null;
	}
	
		@GetMapping("/errorDownload") //редирект на страницу ошибки
		public String errorDownload(Model model) {
			
			  String note="Ошибка  - В базе нет файла для этого Договора!";
		      model.addAttribute("note", note);
			  return"noLoad";
		}
		
		@GetMapping("/admin/contractorsShow") //список всех подрядчиков по увеличению id (текст договора не подгружается)
		public String showContractors(Model model) {
			List<Contractor> contractors=contractorService.getAllContractorsWithOutText();
			model.addAttribute("contractors", contractors);
			return "contractors";
		}
		
		@GetMapping("/admin/contractorCreate") // переход на форму создания нового подрядчика
		public String newContractorForm() {	   
		   return "newContractorForm";
		}
		
		@PostMapping("/admin/contractorCreate") // создание нового подрядчика
		public String createNewContractor(@RequestParam("contractor") String contractor, @RequestParam("number") String number,
				@RequestParam("date") String date,@RequestParam("name") String name,@RequestParam("email1") String email1,
				@RequestParam(name="email2",required=false) String email2,@RequestParam(name="email3",required=false) String email3,
				@RequestParam("work") String work,@RequestParam("contractend") String contractend,
				@RequestParam("email11") String email11,@RequestParam(name="email12",required=false) String email12,
				@RequestParam(name="email13",required=false) String email13) {	   
			Contractor newContractor=new Contractor(contractor,number,date,name,email1,email2,email3,work,contractend,
					email11,email12,email13);
			contractorService.saveContractor(newContractor);
			return "redirect:/admin/contractorsShow";
		}
		
		@GetMapping("/admin/contractorEdit") // переход на форму редактирования подрядчика
		public String editContractorForm(@RequestParam("id") Long id, Model model) {	   
			Contractor contractor=contractorService.getContractorWithOutText(id);
			String contractorOld=contractor.getContractor();
			String numberOld=contractor.getNumber();
			String dateOld=contractor.getDate();
			String nameOld=contractor.getName();
			String email1Old=contractor.getEmail1();
			String email2Old=contractor.getEmail2();
			String email3Old=contractor.getEmail3();
			String workOld=contractor.getWork();
			String contractendOld=contractor.getContractEnd();
			String email11Old=contractor.getEmail11();
			String email12Old=contractor.getEmail12();
			String email13Old=contractor.getEmail13();
			
			model.addAttribute("id", id);
			model.addAttribute("contractor", contractorOld);
			model.addAttribute("number", numberOld);
			model.addAttribute("date", dateOld);
			model.addAttribute("name", nameOld);
			model.addAttribute("email1", email1Old);
			model.addAttribute("email2", email2Old);
			model.addAttribute("email3", email3Old);
			model.addAttribute("work", workOld);
			model.addAttribute("contractend", contractendOld);
			model.addAttribute("email11", email11Old);
			model.addAttribute("email12", email12Old);
			model.addAttribute("email13", email13Old);
			
			return "editContractorForm";
		}
		
		@PostMapping("/admin/contractorEdit") // редактирование подрядчика
		public String editNewContractor(@RequestParam("id") Long id,@RequestParam("contractor") String contractor, @RequestParam("number") String number,@RequestParam("date") String date,
				@RequestParam("name") String name,@RequestParam("email1") String email1,
				@RequestParam(name="email2",required=false) String email2,@RequestParam(name="email3",required=false) String email3,
				@RequestParam("work") String work,@RequestParam("contractend") String contractend,
				@RequestParam("email11") String email11,@RequestParam(name="email12",required=false) String email12,
				@RequestParam(name="email13",required=false) String email13) {	   
			Contractor contractorOld=contractorService.getContractor(id);
			contractorOld.setContractor(contractor);
			contractorOld.setNumber(number);
			contractorOld.setDate(date);
			contractorOld.setName(name);
			contractorOld.setEmail1(email1);
			contractorOld.setEmail2(email2);
			contractorOld.setEmail3(email3);
			contractorOld.setWork(work);
			contractorOld.setContractEnd(contractend);
			contractorOld.setEmail11(email11);
			contractorOld.setEmail12(email12);
			contractorOld.setEmail13(email13);
			contractorService.saveContractor(contractorOld);
			
			return "redirect:/admin/contractorsShow";
		}
		
		@GetMapping("/superadmin/contractorDelete") // удаление подрядчика
		public String deleteContractor(@RequestParam("id") Long id,@RequestParam("contractor") String contractor,
				@RequestParam("contractnumber") String contractnumber) {
			
			pricesService.deleteAllPricesByContractor(contractor);
			orderService.deleteAllOrdersByContractNumber(contractnumber);
			contractorService.deleteContractorById(id);
			
			return "redirect:/admin/contractorsShow";
		}
	}
	
