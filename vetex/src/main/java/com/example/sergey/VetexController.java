package com.example.sergey;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired VetexService vetexService;
	
	@Autowired OrderCart orderCart;
	
	@GetMapping("/priceItems")
	public String getAllPriceItems(Model model) {
		List<Vetex> listitems=vetexService.findAllPriceItems();
		model.addAttribute("listitems", listitems);
		return "priceItems";
	}
	
	@GetMapping("/admin/newItem")
	public String newItemCreate() {
		return"newItemForm";
	}
	
	@PostMapping("/admin/newItemCreate")
	public String newItemCreate(@RequestParam("pp") int pp,@RequestParam("workname") String workname,//
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
	public String itemEdit(@RequestParam("id") long id,@RequestParam("pp") int pp,@RequestParam("workname") String workname,//
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
	public String findByNumber(@RequestParam("ppsearch") int ppsearch,Model model)throws IOException{
		if(ppsearch==0) {
			List<Vetex> listitems=vetexService.findAllPriceItems();
			model.addAttribute("listitems", listitems);
		} else {
			List<Vetex> listitems=vetexService.findPriceItemByPpNumber(ppsearch);
		model.addAttribute("listitems", listitems);}
		return "priceItems";
	}
	
	@GetMapping("/findByName")
	public String findByNumber(@RequestParam("workname") String workname,Model model)throws IOException{
		if(workname.equals(null)) {
			List<Vetex> listitems=vetexService.findAllPriceItems();
			model.addAttribute("listitems", listitems);
		} else {
			List<Vetex> listitems=vetexService.findPriceItemByWorkName(workname);
		model.addAttribute("listitems", listitems);}
		return "priceItems";
	}
	
	@GetMapping("/addInOrder")
	public String addInOrder(@RequestParam("id") long id,@RequestParam("quantity") int quantity) {
		Vetex item=vetexService.findPriceItemById(id);
		int ppnumber=item.getPpNumber();
		String workname=item.getWorkName();
		String unitmeasure=item.getUnitMeasure();
		double price=item.getPrice();
		String comment=item.getComment();

		VetexOrder vetexOrder=new VetexOrder(ppnumber,workname,unitmeasure,price,comment,quantity);
		orderCart.addItem(vetexOrder);
		
		return "redirect:/priceItems";
	}
	
	@GetMapping ("/showCart")
	public String showCart(Model model) {
		sumWithOutNds=0;
		Nds=0;
		sumWithNds=0;
		
		ArrayList<VetexOrder> cart=orderCart.getItemsOrderCart();
		
		for(VetexOrder cartitem : cart) {
			this.sumWithOutNds=this.sumWithOutNds+cartitem.getEndPrice();
		}
		this.Nds=this.sumWithOutNds*0.2;
		this.sumWithNds=this.sumWithOutNds+this.Nds;
		model.addAttribute("cart", cart);
		model.addAttribute("sumWithOutNds", this.sumWithOutNds);
		model.addAttribute("Nds", this.Nds);
		model.addAttribute("sumWithNds", this.sumWithNds);
		return"showCart";
	}
	@GetMapping ("/clearCart")
	public String clearCart() {
		orderCart.clearCart();
		return "redirect:/showCart";
	}
	@GetMapping ("/clearCart2")
	public String clearCart2() {
		orderCart.clearCart();
		return "redirect:/priceItems";
	}
	@GetMapping ("/deleteFromOrder")
	public String deleteFromOrder(@RequestParam("ppnumber")int ppnumber) {
		orderCart.deleteItem(ppnumber);
		return "redirect:/showCart";
	}
	
}
