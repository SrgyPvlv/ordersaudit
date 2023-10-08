package com.example.sergey.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.sergey.Model.Red;
import com.example.sergey.Service.RedService;

@Controller
public class RedController {
		
	@Autowired private RedService redService;
		
	@GetMapping("/admin/redsShow") //список записей БД
	public String showReds(Model model) {
		List<Red> reds=redService.findAllReds();
		int redsSize=reds.size();
		model.addAttribute("reds", reds);
		model.addAttribute("redsSize", redsSize);
		return "reds";
	}
	
	@GetMapping("/admin/redCreate") // переход на форму внесения новой записи БД
	public String newRedForm() {	   
	   return "newRedForm";
	}
	
	@PostMapping("/admin/redCreate") // создание новой записи БД
	public String createNewRed(@RequestParam("bdname") String bdname, @RequestParam("ipaddress") String ipaddress) {	   
	   Red newRed=new Red(bdname,ipaddress);
	   redService.saveRed(newRed);
		
		return "redirect:/admin/redsShow";
	}
	
	@GetMapping("/admin/redEdit") // переход на форму редактирования записи БД
	public String editRedForm(@RequestParam("id") long id, Model model) {	   
		Red red=redService.findRedById(id);
		String bdname=red.getBdName();
		String ipaddress=red.getIpAddress();
		model.addAttribute("id", id);
		model.addAttribute("bdname", bdname);
		model.addAttribute("ipaddress", ipaddress);
		
		return "editRedForm";
	}
	
	@PostMapping("/admin/redEdit") // редактирование записи БД
	public String editRed(@RequestParam("id") long id,@RequestParam("bdname") String bdname, @RequestParam("ipaddress") String ipaddress) {	   
	   Red red=redService.findRedById(id);
	   red.setBdName(bdname);
	   red.setIpAddress(ipaddress);
	   redService.saveRed(red);
		
		return "redirect:/admin/redsShow";
	}
	
	@GetMapping("/admin/redDelete") // удаление записи БД
	public String deleteRed(@RequestParam("id") int id) {
		redService.deleteRedById(id);
		
		return "redirect:/admin/redsShow";
	}
}
