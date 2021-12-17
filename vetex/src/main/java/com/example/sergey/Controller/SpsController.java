package com.example.sergey.Controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.sergey.Model.ContractText;
import com.example.sergey.Model.OrderCart;
import com.example.sergey.Model.Sps;
import com.example.sergey.Model.Users;
import com.example.sergey.Model.VetexOrder;
import com.example.sergey.Service.BsListService;
import com.example.sergey.Service.ContractTextService;
import com.example.sergey.Service.SpsService;
import com.example.sergey.Service.UsersService;

@Controller
public class SpsController {
	
	double sumWithOutNds;
	double Nds;
	double sumWithNds;
	ArrayList<VetexOrder> cart;
	String html;
	Date dateSend;
	Date dateStart;
	Date dateEnd;

	@Autowired SpsService spsService;
	@Autowired OrderCart orderCart;
	@Autowired
	private ContractTextService contractTextService;
	@Autowired UsersService userService;
	@Autowired BsListService bsListService;
	
	@GetMapping("/priceItems/sps")
	public String getAllPriceItemsSps(Model model) {
		List<Sps> listitems=spsService.findAllPriceItems();
		model.addAttribute("listitems", listitems);
		return "priceItemsSps";
	}
	
	@GetMapping ("/superadmin/deleteAllPrices/sps")
	public String deleteAllPricesSps() {
		spsService.deleteAllPrices();
		return "redirect:/priceItems/sps";
	}
	
	@GetMapping("/admin/newItem/sps")
	public String newItemCreateSps() {
		return"newItemFormSps";
	}
	
	@PostMapping("/admin/newItemCreate/sps")
	public String newItemCreateSps(@RequestParam("pp") String pp,@RequestParam("workname") String workname,//
			@RequestParam("unitmeasure") String unitmeasure,@RequestParam("price") double price,@RequestParam("comment") String comment) throws IOException{
		Sps newPriceItem=new Sps(pp,workname,unitmeasure,price,comment);
		try {
			spsService.savePriceItem(newPriceItem);
		}catch (Exception e) {}
		
		return "redirect:/priceItems/sps";
	}
	
	@GetMapping("/admin/itemDelete/sps")
	public String itemDeleteSps(@RequestParam("id") long id) {
		spsService.deletePriceItemById(id);
		
		return "redirect:/priceItems/sps";
	}
	
	@GetMapping("/admin/itemEditForm/sps")
	public String itemEditFormSps(@RequestParam("id") long id,Model model) {
		Sps item=spsService.findPriceItemById(id);
		model.addAttribute("item", item);
		return "editItemFormSps";
	}
	
	@PostMapping ("/admin/itemEdite/sps")
	public String itemEditSps(@RequestParam("id") long id,@RequestParam("pp") String pp,@RequestParam("workname") String workname,//
	@RequestParam("unitmeasure") String unitmeasure,@RequestParam("price") double price,@RequestParam("comment") String comment) throws IOException{
		Sps item=spsService.findPriceItemById(id);
		item.setPpNumber(pp);
		item.setWorkName(workname);
		item.setUnitMeasure(unitmeasure);
		item.setPrice(price);
		item.setComment(comment);
		spsService.savePriceItem(item);
		return "redirect:/priceItems/sps";
	}
	
	@GetMapping("/findByNumber/sps")
	public String findByNumberSps(@RequestParam("ppsearch") String ppsearch,Model model)throws IOException{
		
		List<Sps> listitems=spsService.findPriceItemByPpNumber(ppsearch);
		model.addAttribute("listitems", listitems);
		return "priceItemsSps";
	}
	
	@GetMapping("/findByName/sps")
	public String findByNameSps(@RequestParam("workname") String workname,Model model)throws IOException{
		String workname1;
		String workname2;
		String[] words=workname.split("\\s");
		if (words.length==1) {
		workname1=words[0];
		workname2="";}
		else {
			workname1=words[0];
			workname2=words[1];
		}
		List<Sps> listitems=spsService.findPriceItemByWorkName(workname,workname1,workname2);
		model.addAttribute("listitems", listitems);
		return "priceItemsSps";
	}
	
	@GetMapping("/addInOrder/sps")
	public String addInOrderSps(@RequestParam("id") long id,@RequestParam("quantity") double quantity) {
		Sps item=spsService.findPriceItemById(id);
		String ppnumber=item.getPpNumber();
		String workname=item.getWorkName();
		String unitmeasure=item.getUnitMeasure();
		double price=item.getPrice();
		String comment=item.getComment();

		VetexOrder vetexOrder=new VetexOrder(ppnumber,workname,unitmeasure,price,comment,quantity);
		orderCart.addItem(vetexOrder);
		
		return "redirect:/priceItems/sps";
	}
	
	@GetMapping ("/dispOrder/sps")
	public String showCartSps(Model model) {
		sumWithOutNds=0;
		Nds=0;
		sumWithNds=0;
		
		cart=orderCart.getItemsOrderCart();
		
		for(VetexOrder cartitem : cart) {
			this.sumWithOutNds=this.sumWithOutNds+cartitem.getEndPrice();
			BigDecimal bd = new BigDecimal(this.sumWithOutNds).setScale(2, RoundingMode.HALF_UP);
			this.sumWithOutNds = bd.doubleValue();
		}
		this.Nds=this.sumWithOutNds*0.2;
		BigDecimal bd1 = new BigDecimal(this.Nds).setScale(2, RoundingMode.HALF_UP);
		this.Nds = bd1.doubleValue();
		
		this.sumWithNds=this.sumWithOutNds+this.Nds;
		BigDecimal bd2 = new BigDecimal(this.sumWithNds).setScale(2, RoundingMode.HALF_UP);
		this.sumWithNds = bd2.doubleValue();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String login=auth.getName();
		Users user=userService.findUsersByLogin(login);
		String username=user.getFullName();
		
		model.addAttribute("cart", cart);
		model.addAttribute("sumWithOutNds", this.sumWithOutNds);
		model.addAttribute("Nds", this.Nds);
		model.addAttribute("sumWithNds", this.sumWithNds);
		model.addAttribute("username", username);
		return"dispOrderSps";
	}
	@GetMapping ("/clearCart/sps")
	public String clearCartSps() {
		orderCart.clearCart();
		return "redirect:/dispOrder/sps";
	}
	@GetMapping ("/clearCart2/sps")
	public String clearCart2Sps() {
		orderCart.clearCart();
		return "redirect:/priceItems/sps";
	}
	
	@GetMapping ("/deleteFromOrder/sps")
	public String deleteFromOrderSps(@RequestParam("ppnumber")String ppnumber,@RequestParam("quantity")double quantity) {
		orderCart.deleteItem(ppnumber,quantity);
		return "redirect:/dispOrder/sps";
	}
	
	@GetMapping ("/saveQuantityChanges/sps")
	public String saveQuantityChangesSps(@RequestParam("ppnumber")String ppnumber,@RequestParam("quantity")double quantity,@RequestParam("newQuantity")double newQuantity) {
		orderCart.saveQuantityItem(ppnumber,quantity,newQuantity);
		return "redirect:/dispOrder/sps";
	}
	@GetMapping ("/orderPage/sps")
	public String tableOrderSps(@RequestParam("ordernumber")int ordernumber,@RequestParam("send")String send,
			@RequestParam("author")String author,@RequestParam("bsnumber")String bsnumber,
			@RequestParam("start")String start,@RequestParam("end")String end,@RequestParam("remedy")String remedy,
			@RequestParam("arenda")String arenda,@RequestParam("comment")String comment,Model model) {
		sumWithOutNds=0;
		Nds=0;
		sumWithNds=0;
		
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatter1=new SimpleDateFormat("dd-MM-yyyy");
		
		try {
			this.dateSend = formatter.parse(send);
			this.dateStart = formatter.parse(start);
			this.dateEnd = formatter.parse(end);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		send=formatter1.format(this.dateSend);
		start=formatter1.format(this.dateStart);
		end=formatter1.format(this.dateEnd);
    	
		
		cart=orderCart.getItemsOrderCart();
		
		for(VetexOrder cartitem : cart) {
			this.sumWithOutNds=this.sumWithOutNds+cartitem.getEndPrice();
			BigDecimal bd = new BigDecimal(this.sumWithOutNds).setScale(2, RoundingMode.HALF_UP);
			this.sumWithOutNds = bd.doubleValue();
		}
		this.Nds=this.sumWithOutNds*0.2;
		BigDecimal bd1 = new BigDecimal(this.Nds).setScale(2, RoundingMode.HALF_UP);
		this.Nds = bd1.doubleValue();
		
		this.sumWithNds=this.sumWithOutNds+this.Nds;
		BigDecimal bd2 = new BigDecimal(this.sumWithNds).setScale(2, RoundingMode.HALF_UP);
		this.sumWithNds = bd2.doubleValue();
		
		ContractText vetexContract=contractTextService.getContractText(2);
		String contractnumber=vetexContract.getNumber();
		String contractdate=vetexContract.getDate();
		
		String bsadress=bsListService.findBsAddress(bsnumber);
		
		model.addAttribute("cart", cart);
		model.addAttribute("sumWithOutNds", this.sumWithOutNds);
		model.addAttribute("Nds", this.Nds);
		model.addAttribute("sumWithNds", this.sumWithNds);
		model.addAttribute("ordernumber", ordernumber);
		model.addAttribute("contractnumber", contractnumber);
		model.addAttribute("contractdate", contractdate);
		model.addAttribute("send", send);
		model.addAttribute("author", author);
		model.addAttribute("bsnumber", bsnumber);
		model.addAttribute("bsadress", bsadress);
		model.addAttribute("start", start);
		model.addAttribute("end", end);
		model.addAttribute("remedy", remedy);
		model.addAttribute("arenda", arenda);
		model.addAttribute("comment", comment);
		
		return"orderPage";
	}
}