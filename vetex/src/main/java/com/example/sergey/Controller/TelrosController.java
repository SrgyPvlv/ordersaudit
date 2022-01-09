package com.example.sergey.Controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
	
	Long id;Integer ordernumber;String bsnumber;String bsaddress;String send;String start;String endtime;String report;
	String cedr;String status;String worktype;String orderlistcomment;String contractnumber;String contractdate;String remedy;
	String arenda;String comment;String author;

	@Autowired TelrosService telrosService;
	@Autowired OrderCart orderCart;
	@Autowired ContractTextService contractTextService;
	@Autowired UsersService userService;
	@Autowired BsListService bsListService;
	
	@GetMapping("/priceItems/telros")
	public String getAllPriceItemsTelros(Model model) {
		List<Telros> listitems=telrosService.findAllPriceItems();
		ContractText vetexContract=contractTextService.getContractText(5);
		contractnumber=vetexContract.getNumber();
		contractdate=vetexContract.getDate();
		
		model.addAttribute("listitems", listitems);
		model.addAttribute("contractnumber", contractnumber);
		model.addAttribute("contractdate", contractdate);
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
	public String showCartTelros(@RequestParam(name="id",defaultValue="0",required=false) Long id,@RequestParam(name="ordernumber",defaultValue="0",required=false) Integer ordernumber,
			@RequestParam(name="bsnumber",defaultValue="",required=false)String bsnumber,
			@RequestParam(name="send",defaultValue="",required=false)String send,@RequestParam(name="start",defaultValue="",required=false)String start,
			@RequestParam(name="endtime",defaultValue="",required=false)String endtime,@RequestParam(name="remedy",defaultValue="",required=false)String remedy,
			@RequestParam(name="author",defaultValue="",required=false)String author,@RequestParam(name="arenda",defaultValue="",required=false)String arenda,
			@RequestParam(name="worktype",defaultValue="",required=false)String worktype,@RequestParam(name="status",defaultValue="",required=false)String status,
			@RequestParam(name="orderlistcomment",defaultValue="",required=false)String orderlistcomment,
			@RequestParam(name="report",defaultValue="",required=false)String report,@RequestParam(name="cedr",defaultValue="",required=false)String cedr,
			@RequestParam(name="contractnumber",defaultValue="",required=false)String contractnumber,@RequestParam(name="contractdate",defaultValue="",required=false)String contractdate,
			@RequestParam(name="comment",defaultValue="",required=false)String comment,Model model) {
		sumWithOutNds=0;
		Nds=0;
		sumWithNds=0;
		if(id!=0) this.id=id;
		if(ordernumber!=0) this.ordernumber=ordernumber;
		if(!bsnumber.isEmpty()) this.bsnumber=bsnumber;
		if(!send.isEmpty()) this.send=send;
		if(!start.isEmpty()) this.start=start;
		if(!endtime.isEmpty()) this.endtime=endtime;
		if(!remedy.isEmpty()) this.remedy=remedy;
		if(!author.isEmpty()) this.author=author;
		if(!arenda.isEmpty()) this.arenda=arenda;
		if(!worktype.isEmpty()) this.worktype=worktype;
		if(!comment.isEmpty()) this.comment=comment;
		if(!status.isEmpty()) this.status=status;
		if(!orderlistcomment.isEmpty()) this.orderlistcomment=orderlistcomment;
		if(!report.isEmpty()) this.report=report;
		if(!cedr.isEmpty()) this.cedr=cedr;
		if(!contractnumber.isEmpty() && !contractdate.isEmpty()) {this.contractnumber=contractnumber;this.contractdate=contractdate;} else {
			ContractText vetexContract=contractTextService.getContractText(5);
			this.contractnumber=vetexContract.getNumber();
			this.contractdate=vetexContract.getDate();}
		
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
		model.addAttribute("id", this.id);
		model.addAttribute("sumWithOutNds", this.sumWithOutNds);
		model.addAttribute("Nds", this.Nds);
		model.addAttribute("sumWithNds", this.sumWithNds);
		model.addAttribute("username", username);
		model.addAttribute("contractnumber", this.contractnumber);
		model.addAttribute("contractdate", this.contractdate);
		model.addAttribute("ordernumber", this.ordernumber);
		model.addAttribute("bsnumber", this.bsnumber);
		model.addAttribute("send", this.send);
		model.addAttribute("start", this.start);
		model.addAttribute("endtime", this.endtime);
		model.addAttribute("remedy", this.remedy);
		model.addAttribute("author", this.author);
		model.addAttribute("arenda", this.arenda);
		model.addAttribute("worktype", this.worktype);
		model.addAttribute("comment", this.comment);
		model.addAttribute("status", this.status);
		model.addAttribute("report", this.report);
		model.addAttribute("cedr", this.cedr);
		model.addAttribute("orderlistcomment", this.orderlistcomment);
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
	
	public void clearCart3() {
		orderCart.clearCart();
		this.id=null; this.ordernumber=null; this.bsnumber=null; this.send=null; this.start=null; this.endtime=null;
		this.remedy=null; this.author=null; this.arenda=null; this.worktype=null; this.comment=null; this.status=null;
		this.report=null; this.cedr=null; this.orderlistcomment=null;
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
}