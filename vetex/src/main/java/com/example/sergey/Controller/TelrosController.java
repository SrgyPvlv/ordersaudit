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
import com.example.sergey.Model.Telros;
import com.example.sergey.Model.Users;
import com.example.sergey.Model.VetexOrder;
import com.example.sergey.Service.BsListService;
import com.example.sergey.Service.ContractTextService;
import com.example.sergey.Service.TelrosService;
import com.example.sergey.Service.UsersService;

@Controller
public class TelrosController {
	
	double sumWithOutNds;
	double Nds;
	double sumWithNds;
	ArrayList<VetexOrder> cart;
	String html;
	Date dateSend;
	Date dateStart;
	Date dateEnd;

	@Autowired TelrosService telrosService;
	@Autowired OrderCart orderCart;
	@Autowired
	private ContractTextService contractTextService;
	@Autowired UsersService userService;
	@Autowired BsListService bsListService;
	
	@GetMapping("/priceItems/telros")
	public String getAllPriceItemsTelros(Model model) {
		List<Telros> listitems=telrosService.findAllPriceItems();
		model.addAttribute("listitems", listitems);
		return "priceItemsTelros";
	}
	
	@GetMapping ("/superadmin/deleteAllPrices/telros")
	public String deleteAllPricesTelros() {
		telrosService.deleteAllPrices();
		return "redirect:/priceItems/telros";
	}
	
	@GetMapping("/admin/newItem/telros")
	public String newItemCreateTelros() {
		return"newItemFormTelros";
	}
	
	@PostMapping("/admin/newItemCreate/telros")
	public String newItemCreateTelros(@RequestParam("pp") String pp,@RequestParam("workname") String workname,//
			@RequestParam("unitmeasure") String unitmeasure,@RequestParam("price") double price,@RequestParam("comment") String comment) throws IOException{
		Telros newPriceItem=new Telros(pp,workname,unitmeasure,price,comment);
		try {
			telrosService.savePriceItem(newPriceItem);
		}catch (Exception e) {}
		
		return "redirect:/priceItems/telros";
	}
	
	@GetMapping("/admin/itemDelete/telros")
	public String itemDeleteTelros(@RequestParam("id") long id) {
		telrosService.deletePriceItemById(id);
		
		return "redirect:/priceItems/telros";
	}
	
	@GetMapping("/admin/itemEditForm/telros")
	public String itemEditFormTelros(@RequestParam("id") long id,Model model) {
		Telros item=telrosService.findPriceItemById(id);
		model.addAttribute("item", item);
		return "editItemFormTelros";
	}
	
	@PostMapping ("/admin/itemEdite/telros")
	public String itemEditTelros(@RequestParam("id") long id,@RequestParam("pp") String pp,@RequestParam("workname") String workname,//
	@RequestParam("unitmeasure") String unitmeasure,@RequestParam("price") double price,@RequestParam("comment") String comment) throws IOException{
		Telros item=telrosService.findPriceItemById(id);
		item.setPpNumber(pp);
		item.setWorkName(workname);
		item.setUnitMeasure(unitmeasure);
		item.setPrice(price);
		item.setComment(comment);
		telrosService.savePriceItem(item);
		return "redirect:/priceItems/telros";
	}
	
	@GetMapping("/findByNumber/telros")
	public String findByNumberTelros(@RequestParam("ppsearch") String ppsearch,Model model)throws IOException{
		
		List<Telros> listitems=telrosService.findPriceItemByPpNumber(ppsearch);
		model.addAttribute("listitems", listitems);
		return "priceItemsTelros";
	}
	
	@GetMapping("/findByName/telros")
	public String findByNameTelros(@RequestParam("workname") String workname,Model model)throws IOException{
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
		List<Telros> listitems=telrosService.findPriceItemByWorkName(workname,workname1,workname2);
		model.addAttribute("listitems", listitems);
		return "priceItemsTelros";
	}
	
	@GetMapping("/addInOrder/telros")
	public String addInOrderTelros(@RequestParam("id") long id,@RequestParam("quantity") double quantity) {
		Telros item=telrosService.findPriceItemById(id);
		String ppnumber=item.getPpNumber();
		String workname=item.getWorkName();
		String unitmeasure=item.getUnitMeasure();
		double price=item.getPrice();
		String comment=item.getComment();

		VetexOrder vetexOrder=new VetexOrder(ppnumber,workname,unitmeasure,price,comment,quantity);
		orderCart.addItem(vetexOrder);
		
		return "redirect:/priceItems/telros";
	}
	
	@GetMapping ("/dispOrder/telros")
	public String showCartTelros(Model model) {
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
		return"dispOrderTelros";
	}
	@GetMapping ("/clearCart/telros")
	public String clearCartTelros() {
		orderCart.clearCart();
		return "redirect:/dispOrder/telros";
	}
	@GetMapping ("/clearCart2/telros")
	public String clearCart2Telros() {
		orderCart.clearCart();
		return "redirect:/priceItems/telros";
	}
	
	@GetMapping ("/deleteFromOrder/telros")
	public String deleteFromOrderTelros(@RequestParam("ppnumber")String ppnumber,@RequestParam("quantity")double quantity) {
		orderCart.deleteItem(ppnumber,quantity);
		return "redirect:/dispOrder/telros";
	}
	
	@GetMapping ("/saveQuantityChanges/telros")
	public String saveQuantityChangesTelros(@RequestParam("ppnumber")String ppnumber,@RequestParam("quantity")double quantity,@RequestParam("newQuantity")double newQuantity) {
		orderCart.saveQuantityItem(ppnumber,quantity,newQuantity);
		return "redirect:/dispOrder/telros";
	}
	@GetMapping ("/orderPage/telros")
	public String tableOrderTelros(@RequestParam("ordernumber")int ordernumber,@RequestParam("send")String send,
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
		
		ContractText vetexContract=contractTextService.getContractText(5);
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