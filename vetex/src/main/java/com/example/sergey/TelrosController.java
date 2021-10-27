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
public class TelrosController {
	
	double sumWithOutNds;
	double Nds;
	double sumWithNds;
	ArrayList<VetexOrder> cart;
	String html;

	@Autowired TelrosService telrosService;
	@Autowired OrderCart orderCart;
	
	@GetMapping("/priceItems/telros")
	public String getAllPriceItemsTelros(Model model) {
		List<Telros> listitems=telrosService.findAllPriceItems();
		model.addAttribute("listitems", listitems);
		return "priceItemsTelros";
	}
	
	@GetMapping ("/admin/deleteAllPrices/telros")
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
		
		List<Telros> listitems=telrosService.findPriceItemByWorkName(workname);
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
		
		model.addAttribute("cart", cart);
		model.addAttribute("sumWithOutNds", this.sumWithOutNds);
		model.addAttribute("Nds", this.Nds);
		model.addAttribute("sumWithNds", this.sumWithNds);
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
	public String tableOrderTelros(Model model) {
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
		return"orderPageTelros";
	}
}