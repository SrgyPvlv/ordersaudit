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
import com.example.sergey.Model.Telecom;
import com.example.sergey.Model.Users;
import com.example.sergey.Model.VetexOrder;
import com.example.sergey.Service.BsListService;
import com.example.sergey.Service.ContractTextService;
import com.example.sergey.Service.TelecomService;
import com.example.sergey.Service.UsersService;

@Controller
public class TelecomController {
	
	double sumWithOutNds;
	double Nds;
	double sumWithNds;
	ArrayList<VetexOrder> cart;
	String html;
	Date dateSend;
	Date dateStart;
	Date dateEnd;
	
	Long id;Integer ordernumber;String bsnumber;String bsaddress;String send;String start;String endtime;String report;
	String cedr;String status;String worktype;String orderlistcomment;String contractnumber;String contractdate;String remedy;
	String arenda;String comment;String author;

	@Autowired TelecomService telecomService;
	@Autowired OrderCart orderCart;
	@Autowired
	private ContractTextService contractTextService;
	@Autowired UsersService userService;
	@Autowired BsListService bsListService;
	
	@GetMapping("/priceItems/telecom")
	public String getAllPriceItemsTelecom(Model model) {
		List<Telecom> listitems=telecomService.findAllPriceItems();
		model.addAttribute("listitems", listitems);
		return "priceItemsTelecom";
	}
	
	@GetMapping ("/superadmin/deleteAllPrices/telecom")
	public String deleteAllPricesTelecom() {
		telecomService.deleteAllPrices();
		return "redirect:/priceItems/telecom";
	}
	
	@GetMapping("/admin/newItem/telecom")
	public String newItemCreateTeleccom() {
		return"newItemFormTelecom";
	}
	
	@PostMapping("/admin/newItemCreate/telecom")
	public String newItemCreateTelecom(@RequestParam("pp") String pp,@RequestParam("workname") String workname,//
			@RequestParam("unitmeasure") String unitmeasure,@RequestParam("price") double price,@RequestParam("comment") String comment) throws IOException{
		Telecom newPriceItem=new Telecom(pp,workname,unitmeasure,price,comment);
		try {
			telecomService.savePriceItem(newPriceItem);
		}catch (Exception e) {}
		
		return "redirect:/priceItems/telecom";
	}
	
	@GetMapping("/admin/itemDelete/telecom")
	public String itemDeleteTelecom(@RequestParam("id") long id) {
		telecomService.deletePriceItemById(id);
		
		return "redirect:/priceItems/telecom";
	}
	
	@GetMapping("/admin/itemEditForm/telecom")
	public String itemEditFormTelecom(@RequestParam("id") long id,Model model) {
		Telecom item=telecomService.findPriceItemById(id);
		model.addAttribute("item", item);
		return "editItemFormTelecom";
	}
	
	@PostMapping ("/admin/itemEdite/telecom")
	public String itemEditTelecom(@RequestParam("id") long id,@RequestParam("pp") String pp,@RequestParam("workname") String workname,//
	@RequestParam("unitmeasure") String unitmeasure,@RequestParam("price") double price,@RequestParam("comment") String comment) throws IOException{
		Telecom item=telecomService.findPriceItemById(id);
		item.setPpNumber(pp);
		item.setWorkName(workname);
		item.setUnitMeasure(unitmeasure);
		item.setPrice(price);
		item.setComment(comment);
		telecomService.savePriceItem(item);
		return "redirect:/priceItems/telecom";
	}
	
	@GetMapping("/findByNumber/telecom")
	public String findByNumberTelecom(@RequestParam("ppsearch") String ppsearch,Model model)throws IOException{
		
		List<Telecom> listitems=telecomService.findPriceItemByPpNumber(ppsearch);
		model.addAttribute("listitems", listitems);
		return "priceItemsTelecom";
	}
	
	@GetMapping("/findByName/telecom")
	public String findByNameTelecom(@RequestParam("workname") String workname,Model model)throws IOException{
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
		List<Telecom> listitems=telecomService.findPriceItemByWorkName(workname,workname1,workname2);
		model.addAttribute("listitems", listitems);
		return "priceItemsTelecom";
	}
	
	@GetMapping("/addInOrder/telecom")
	public String addInOrderTelecom(@RequestParam("id") long id,@RequestParam("quantity") double quantity) {
		Telecom item=telecomService.findPriceItemById(id);
		String ppnumber=item.getPpNumber();
		String workname=item.getWorkName();
		String unitmeasure=item.getUnitMeasure();
		double price=item.getPrice();
		String comment=item.getComment();

		VetexOrder vetexOrder=new VetexOrder(ppnumber,workname,unitmeasure,price,comment,quantity);
		orderCart.addItem(vetexOrder);
		
		return "redirect:/priceItems/telecom";
	}
	
	@GetMapping ("/dispOrder/telecom")
	public String showCartTelecom(Model model) {
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
		return"dispOrderTelecom";
	}
	@GetMapping ("/clearCart/telecom")
	public String clearCartTelecom() {
		orderCart.clearCart();
		return "redirect:/dispOrder/telecom";
	}
	@GetMapping ("/clearCart2/telecom")
	public String clearCart2Telecom() {
		orderCart.clearCart();
		return "redirect:/priceItems/telecom";
	}
	
	public void clearCart3() {
		orderCart.clearCart();
		this.id=null; this.ordernumber=null; this.bsnumber=null; this.send=null; this.start=null; this.endtime=null;
		this.remedy=null; this.author=null; this.arenda=null; this.worktype=null; this.comment=null; this.status=null;
		this.report=null; this.cedr=null; this.orderlistcomment=null;
	}
	
	@GetMapping ("/deleteFromOrder/telecom")
	public String deleteFromOrderTelecom(@RequestParam("ppnumber")String ppnumber,@RequestParam("quantity")double quantity) {
		orderCart.deleteItem(ppnumber,quantity);
		return "redirect:/dispOrder/telecom";
	}
	
	@GetMapping ("/saveQuantityChanges/telecom")
	public String saveQuantityChangesTelecom(@RequestParam("ppnumber")String ppnumber,@RequestParam("quantity")double quantity,@RequestParam("newQuantity")double newQuantity) {
		orderCart.saveQuantityItem(ppnumber,quantity,newQuantity);
		return "redirect:/dispOrder/telecom";
	}
}