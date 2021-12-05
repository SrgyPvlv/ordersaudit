package com.example.sergey;

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

@Controller
public class VetexController {
	
	double sumWithOutNds;
	double Nds;
	double sumWithNds;
	ArrayList<VetexOrder> cart;
	String html;
	String second;
	Date dateSend;
	Date dateStart;
	Date dateEnd;

	@Autowired VetexService vetexService;
	@Autowired OrderCart orderCart;
	@Autowired
	private ContractTextService contractTextService;
	@Autowired UsersService userService;
	@Autowired BsListService bsListService;
	
	@GetMapping("/priceItems")
	public String getAllPriceItems(Model model) {
		List<Vetex> listitems=vetexService.findAllPriceItems();
		model.addAttribute("listitems", listitems);
		return "priceItems";
	}
	
	@GetMapping ("/superadmin/deleteAllPrices")
	public String deleteAllPrices() {
		vetexService.deleteAllPrices();
		return "redirect:/priceItems";
	}
	
	@GetMapping("/admin/newItem")
	public String newItemCreate() {
		return"newItemForm";
	}
	
	@PostMapping("/admin/newItemCreate")
	public String newItemCreate(@RequestParam("pp") String pp,@RequestParam("workname") String workname,//
			@RequestParam("unitmeasure") String unitmeasure,@RequestParam("price") double price,@RequestParam("comment") String comment) throws IOException{
		Vetex newPriceItem=new Vetex(pp,workname,unitmeasure,price,comment);
		try {
			vetexService.savePriceItem(newPriceItem);
		}catch (Exception e) {}
		
		return "redirect:/priceItems";
	}
	
	@GetMapping("/admin/itemDelete")
	public String itemDelete(@RequestParam("id") long id) {
		vetexService.deletePriceItemById(id);
		
		return "redirect:/priceItems";
	}
	
	@GetMapping("/admin/itemEditForm")
	public String itemEditForm(@RequestParam("id") long id,Model model) {
		Vetex item=vetexService.findPriceItemById(id);
		model.addAttribute("item", item);
		return "editItemForm";
	}
	
	@PostMapping ("/admin/itemEdite")
	public String itemEdit(@RequestParam("id") long id,@RequestParam("pp") String pp,@RequestParam("workname") String workname,//
	@RequestParam("unitmeasure") String unitmeasure,@RequestParam("price") double price,@RequestParam("comment") String comment) throws IOException{
		Vetex item=vetexService.findPriceItemById(id);
		item.setPpNumber(pp);
		item.setWorkName(workname);
		item.setUnitMeasure(unitmeasure);
		item.setPrice(price);
		item.setComment(comment);
		vetexService.savePriceItem(item);
		return "redirect:/priceItems";
	}
	
	@GetMapping("/findByNumber")
	public String findByNumber(@RequestParam("ppsearch") String ppsearch,Model model)throws IOException{
		
		List<Vetex> listitems=vetexService.findPriceItemByPpNumber(ppsearch);
		model.addAttribute("listitems", listitems);
		return "priceItems";
	}
	
	@GetMapping("/findByName")
	public String findByName(@RequestParam("workname") String workname,Model model)throws IOException{
		
		List<Vetex> listitems=vetexService.findPriceItemByWorkName(workname);
		model.addAttribute("listitems", listitems);
		return "priceItems";
	}
	
	@GetMapping("/addInOrder")
	public String addInOrder(@RequestParam("id") long id,@RequestParam("quantity") double quantity) {
		Vetex item=vetexService.findPriceItemById(id);
		String ppnumber=item.getPpNumber();
		String workname=item.getWorkName();
		String unitmeasure=item.getUnitMeasure();
		double price=item.getPrice();
		String comment=item.getComment();

		VetexOrder vetexOrder=new VetexOrder(ppnumber,workname,unitmeasure,price,comment,quantity);
		orderCart.addItem(vetexOrder);
		
		return "redirect:/priceItems";
	}
	
	@GetMapping ("/dispOrder")
	public String showCart(Model model) {
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
		return"dispOrder";
	}
	@GetMapping ("/clearCart")
	public String clearCart() {
		orderCart.clearCart();
		return "redirect:/dispOrder";
	}
	@GetMapping ("/clearCart2")
	public String clearCart2() {
		orderCart.clearCart();
		return "redirect:/priceItems";
	}
	
	@GetMapping ("/deleteFromOrder")
	public String deleteFromOrder(@RequestParam("ppnumber")String ppnumber,@RequestParam("quantity")double quantity) {
		orderCart.deleteItem(ppnumber,quantity);
		return "redirect:/dispOrder";
	}
	@GetMapping("/403")
    public String error403() {
        return "403";
    }
	@GetMapping ("/saveQuantityChanges")
	public String saveQuantityChanges(@RequestParam("ppnumber")String ppnumber,@RequestParam("quantity")double quantity,@RequestParam("newQuantity")double newQuantity) {
		orderCart.saveQuantityItem(ppnumber,quantity,newQuantity);
		return "redirect:/dispOrder";
	}
	@GetMapping ("/orderPage")
	public String tableOrder(@RequestParam("ordernumber")int ordernumber,@RequestParam("send")String send,
			@RequestParam("author")String author,@RequestParam("bsnumber")String bsnumber,
			//@RequestParam("bsadress")String bsadress
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
		
		ContractText vetexContract=contractTextService.getContractText(1);
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
		model.addAttribute("comment", comment);
		
		return"orderPage";
	}
}