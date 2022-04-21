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
import org.springframework.web.context.annotation.SessionScope;

import com.example.sergey.Model.ContractText;
import com.example.sergey.Model.OrderCart;
import com.example.sergey.Model.Sps;
import com.example.sergey.Model.Users;
import com.example.sergey.Model.VetexOrder;
import com.example.sergey.Service.BsListService;
import com.example.sergey.Service.ContractTextService;
import com.example.sergey.Service.SpsService;
import com.example.sergey.Service.UsersService;

//test git

@Controller
@SessionScope
public class SpsController {
	
	double sumWithOutNds;
	double Nds;
	double sumWithNds;
	ArrayList<VetexOrder> cart;
	int cartSize;
	Date dateSend;
	Date dateStart;
	Date dateEnd;
	
	Long id;Integer ordernumber;String bsnumber;String bsaddress;String send;String start;String endtime;String report;
	String cedr;String status;String worktype;String orderlistcomment;String contractnumber;String contractdate;String remedy;
	String arenda;String comment;String author;

	@Autowired SpsService spsService;
	@Autowired OrderCart orderCart;
	@Autowired ContractTextService contractTextService;
	@Autowired UsersService userService;
	@Autowired BsListService bsListService;
	
	@GetMapping("/priceItems/sps")
	public String getAllPriceItemsSps(Model model) {
		List<Sps> listitems=spsService.findAllPriceItems();
		ContractText vetexContract=contractTextService.getContractorWithOutText(2);
		contractnumber=vetexContract.getNumber();
		contractdate=vetexContract.getDate();
		
		if (orderCart.getItemsOrderCart()!=null) {cartSize=orderCart.getItemsOrderCart().size();} else {cartSize=0;};
		
		model.addAttribute("listitems", listitems);
		model.addAttribute("contractnumber", contractnumber);
		model.addAttribute("contractdate", contractdate);
		model.addAttribute("cartSize", cartSize);
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
	public String findByNumberSps(@RequestParam("ppsearch") String ppsearch,
			@RequestParam(name="contractnumber") String contractnumber,
			@RequestParam(name="contractdate") String contractdate, Model model)throws IOException{
		
		List<Sps> listitems=spsService.findPriceItemByPpNumber(ppsearch);
		model.addAttribute("listitems", listitems);
		model.addAttribute("contractnumber", contractnumber);
		model.addAttribute("contractdate", contractdate);
		model.addAttribute("cartSize", cartSize);
		return "priceItemsSps";
	}
	
	@GetMapping("/findByName/sps")
	public String findByNameSps(@RequestParam("workname") String workname,
			@RequestParam(name="contractnumber") String contractnumber,
			@RequestParam(name="contractdate") String contractdate,Model model)throws IOException{
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
		model.addAttribute("contractnumber", contractnumber);
		model.addAttribute("contractdate", contractdate);
		model.addAttribute("cartSize", cartSize);
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
	public String showCartSps(@RequestParam(name="id",defaultValue="0",required=false) Long id,@RequestParam(name="ordernumber",defaultValue="0",required=false) Integer ordernumber,
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
			ContractText vetexContract=contractTextService.getContractorWithOutText(2);
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
		
		String contractname=contractTextService.getContractTextName(this.contractnumber);
		
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
		model.addAttribute("contractname", contractname);
		return"dispOrderSps";
	}
	@GetMapping ("/clearCart/sps")
	public String clearCartSps() {
		orderCart.clearCart();
		this.id=null; this.ordernumber=null; this.bsnumber=null; this.send=null; this.start=null; this.endtime=null;
		this.remedy=null; this.author=null; this.arenda=null; this.worktype=null; this.comment=null; this.status=null;
		this.report=null; this.cedr=null; this.orderlistcomment=null;
		return "redirect:/dispOrder/sps";
	}
	@GetMapping ("/clearCart2/sps")
	public String clearCart2Sps() {
		orderCart.clearCart();
		this.id=null; this.ordernumber=null; this.bsnumber=null; this.send=null; this.start=null; this.endtime=null;
		this.remedy=null; this.author=null; this.arenda=null; this.worktype=null; this.comment=null; this.status=null;
		this.report=null; this.cedr=null; this.orderlistcomment=null;
		return "redirect:/priceItems/sps";
	}
	
	public void clearCart3() {
		orderCart.clearCart();
		this.id=null; this.ordernumber=null; this.bsnumber=null; this.send=null; this.start=null; this.endtime=null;
		this.remedy=null; this.author=null; this.arenda=null; this.worktype=null; this.comment=null; this.status=null;
		this.report=null; this.cedr=null; this.orderlistcomment=null;
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
}