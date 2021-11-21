package com.example.sergey;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SpsController {
	
	double sumWithOutNds;
	double Nds;
	double sumWithNds;
	ArrayList<VetexOrder> cart;
	String html;

	@Autowired SpsService spsService;
	@Autowired OrderCart orderCart;
	
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
		
		List<Sps> listitems=spsService.findPriceItemByWorkName(workname);
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
		
		model.addAttribute("cart", cart);
		model.addAttribute("sumWithOutNds", this.sumWithOutNds);
		model.addAttribute("Nds", this.Nds);
		model.addAttribute("sumWithNds", this.sumWithNds);
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
	public String tableOrderSps(Model model) {
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
		
		model.addAttribute("cart", cart);
		model.addAttribute("sumWithOutNds", this.sumWithOutNds);
		model.addAttribute("Nds", this.Nds);
		model.addAttribute("sumWithNds", this.sumWithNds);
		return"orderPageSps";
	}
}