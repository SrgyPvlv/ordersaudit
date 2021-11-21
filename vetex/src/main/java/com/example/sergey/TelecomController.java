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
public class TelecomController {
	
	double sumWithOutNds;
	double Nds;
	double sumWithNds;
	ArrayList<VetexOrder> cart;
	String html;

	@Autowired TelecomService telecomService;
	@Autowired OrderCart orderCart;
	
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
		
		List<Telecom> listitems=telecomService.findPriceItemByWorkName(workname);
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
		
		model.addAttribute("cart", cart);
		model.addAttribute("sumWithOutNds", this.sumWithOutNds);
		model.addAttribute("Nds", this.Nds);
		model.addAttribute("sumWithNds", this.sumWithNds);
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
	@GetMapping ("/orderPage/telecom")
	public String tableOrderTelecom(Model model) {
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
		return"orderPageTelecom";
	}
}