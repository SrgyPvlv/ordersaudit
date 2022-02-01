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

import com.example.sergey.Model.ContractText;
import com.example.sergey.Service.ContractTextService;

@Controller
public class ContractTextController {

	@Autowired
	private ContractTextService contractTextService;
	
	@PostMapping("/admin/loadContractText") // загрузка файла Договора в БД
	public String loadContractText(@RequestParam("file") MultipartFile file,@RequestParam("id") long id, Model model)throws IOException{
		byte[] text=file.getBytes();
		try {
			ContractText contractText=contractTextService.getContractText(id);
			contractText.setText(text);
			contractTextService.saveContractText(contractText);
			String note="Файл Договора сохранен в базе данных!";
		    model.addAttribute("note", note);
			return"noUpload";}
			catch(Exception e) {
				  String note="Ошибка  - Что-то пошло не так!";
			      model.addAttribute("note", note);
				  return"noUpload";}	
			}
	
	@GetMapping("/downloadContractText") // скачивание файла договора из БД
	public ResponseEntity<Resource> fileDown(@RequestParam("id") long id, HttpServletResponse response,HttpServletRequest request) throws IOException{
		try {
			ContractText contractText=contractTextService.getContractText(id);
		    String filename=contractText.getContractor()+".doc";
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
			  return"noUpload";
		}
		
		@GetMapping("/admin/contractorsShow") //список всех подрядчиков по увеличению id (текст договора не подгружается)
		public String showContractors(Model model) {
			List<ContractText> contractors=contractTextService.getAllWithSomeColumn();
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
				@RequestParam("work") String work,@RequestParam("contractend") String contractend) {	   
			ContractText newContractor=new ContractText(contractor,number,date,name,email1,email2,email3,work,contractend);
		    contractTextService.saveContractText(newContractor);
			return "redirect:/admin/contractorsShow";
		}
		
		@GetMapping("/admin/contractorEdit") // переход на форму редактирования подрядчика
		public String editContractorForm(@RequestParam("id") int id, Model model) {	   
			ContractText contractor=contractTextService.getContractorWithOutText(id);
			String contractorOld=contractor.getContractor();
			String numberOld=contractor.getNumber();
			String dateOld=contractor.getDate();
			String nameOld=contractor.getName();
			String email1Old=contractor.getEmail1();
			String email2Old=contractor.getEmail2();
			String email3Old=contractor.getEmail3();
			String workOld=contractor.getWork();
			String contractendOld=contractor.getContractEnd();
			
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
			
			return "editContractorForm";
		}
		
		@PostMapping("/admin/contractorEdit") // редактирование подрядчика
		public String editNewContractor(@RequestParam("id") int id,@RequestParam("contractor") String contractor, @RequestParam("number") String number,@RequestParam("date") String date,
				@RequestParam("name") String name,@RequestParam("email1") String email1,
				@RequestParam(name="email2",required=false) String email2,@RequestParam(name="email3",required=false) String email3,
				@RequestParam("work") String work,@RequestParam("contractend") String contractend) {	   
			ContractText contractorOld=contractTextService.getContractorWithOutText(id);
			contractorOld.setContractor(contractor);
			contractorOld.setNumber(number);
			contractorOld.setDate(date);
			contractorOld.setName(name);
			contractorOld.setEmail1(email1);
			contractorOld.setEmail2(email2);
			contractorOld.setEmail3(email3);
			contractorOld.setWork(work);
			contractorOld.setContractEnd(contractend);
		   contractTextService.saveContractText(contractorOld);
			
			return "redirect:/admin/contractorsShow";
		}
		
		@GetMapping("/superadmin/contractorDelete") // удаление подрядчика
		public String deleteContractor(@RequestParam("id") int id) {
			contractTextService.deleteContractTextById(id);
			
			return "redirect:/admin/contractorsShow";
		}
	}
	

