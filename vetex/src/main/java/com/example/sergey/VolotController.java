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
public class VolotController {
	
	double sumWithOutNds;
	double Nds;
	double sumWithNds;
	ArrayList<VetexOrder> cart;
	String html;

	@Autowired VolotService volotService;
	@Autowired OrderCart orderCart;
	
	@GetMapping("/priceItems/volot")
	public String getAllPriceItemsVolot(Model model) {
		List<Volot> listitems=volotService.findAllPriceItems();
		model.addAttribute("listitems", listitems);
		return "priceItemsVolot";
	}
	
	@GetMapping ("/admin/deleteAllPrices/volot")
	public String deleteAllPricesVolot() {
		volotService.deleteAllPrices();
		return "redirect:/priceItems/volot";
	}
	
	@GetMapping("/admin/newItem/volot")
	public String newItemCreateVolot() {
		return"newItemFormVolot";
	}
	
	@PostMapping("/admin/newItemCreate/volot")
	public String newItemCreateVolot(@RequestParam("pp") String pp,@RequestParam("workname") String workname,//
			@RequestParam("unitmeasure") String unitmeasure,@RequestParam("price") double price,@RequestParam("comment") String comment) throws IOException{
		Volot newPriceItem=new Volot(pp,workname,unitmeasure,price,comment);
		try {
			volotService.savePriceItem(newPriceItem);
		}catch (Exception e) {}
		
		return "redirect:/priceItems/volot";
	}
	
	@GetMapping("/admin/itemDelete/volot")
	public String itemDeleteVolot(@RequestParam("id") long id) {
		volotService.deletePriceItemById(id);
		
		return "redirect:/priceItems/volot";
	}
	
	@GetMapping("/admin/itemEditForm/volot")
	public String itemEditFormVolot(@RequestParam("id") long id,Model model) {
		Volot item=volotService.findPriceItemById(id);
		model.addAttribute("item", item);
		return "editItemFormVolot";
	}
	
	@PostMapping ("/admin/itemEdite/volot")
	public String itemEditVolot(@RequestParam("id") long id,@RequestParam("pp") String pp,@RequestParam("workname") String workname,//
	@RequestParam("unitmeasure") String unitmeasure,@RequestParam("price") double price,@RequestParam("comment") String comment) throws IOException{
		Volot item=volotService.findPriceItemById(id);
		item.setPpNumber(pp);
		item.setWorkName(workname);
		item.setUnitMeasure(unitmeasure);
		item.setPrice(price);
		item.setComment(comment);
		volotService.savePriceItem(item);
		return "redirect:/priceItems/volot";
	}
	
	@GetMapping("/findByNumber/volot")
	public String findByNumberVolot(@RequestParam("ppsearch") String ppsearch,Model model)throws IOException{
		
		List<Volot> listitems=volotService.findPriceItemByPpNumber(ppsearch);
		model.addAttribute("listitems", listitems);
		return "priceItemsVolot";
	}
	
	@GetMapping("/findByName/volot")
	public String findByNameVolot(@RequestParam("workname") String workname,Model model)throws IOException{
		
		List<Volot> listitems=volotService.findPriceItemByWorkName(workname);
		model.addAttribute("listitems", listitems);
		return "priceItemsVolot";
	}
	
	@GetMapping("/addInOrder/volot")
	public String addInOrderVolot(@RequestParam("id") long id,@RequestParam("quantity") double quantity) {
		Volot item=volotService.findPriceItemById(id);
		String ppnumber=item.getPpNumber();
		String workname=item.getWorkName();
		String unitmeasure=item.getUnitMeasure();
		double price=item.getPrice();
		String comment=item.getComment();

		VetexOrder vetexOrder=new VetexOrder(ppnumber,workname,unitmeasure,price,comment,quantity);
		orderCart.addItem(vetexOrder);
		
		return "redirect:/priceItems/volot";
	}
	
	@GetMapping ("/dispOrder/volot")
	public String showCartVolot(Model model) {
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
		return"dispOrderVolot";
	}
	@GetMapping ("/clearCart/volot")
	public String clearCartVolot() {
		orderCart.clearCart();
		return "redirect:/dispOrder/volot";
	}
	@GetMapping ("/clearCart2/volot")
	public String clearCart2Volot() {
		orderCart.clearCart();
		return "redirect:/priceItems/volot";
	}
	
	@GetMapping ("/deleteFromOrder/volot")
	public String deleteFromOrderVolot(@RequestParam("ppnumber")String ppnumber,@RequestParam("quantity")double quantity) {
		orderCart.deleteItem(ppnumber,quantity);
		return "redirect:/dispOrder/volot";
	}
	
	@GetMapping ("/saveQuantityChanges/volot")
	public String saveQuantityChangesVolot(@RequestParam("ppnumber")String ppnumber,@RequestParam("quantity")double quantity,@RequestParam("newQuantity")double newQuantity) {
		orderCart.saveQuantityItem(ppnumber,quantity,newQuantity);
		return "redirect:/dispOrder/volot";
	}
	@GetMapping ("/orderPage/volot")
	public String tableOrderVolot(Model model) {
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
		return"orderPageVolot";
	}
}