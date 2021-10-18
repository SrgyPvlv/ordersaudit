package com.example.sergey;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import com.itextpdf.html2pdf.HtmlConverter;

@Controller
public class VetexController {
	
	double sumWithOutNds;
	double Nds;
	double sumWithNds;
	ArrayList<VetexOrder> cart;
	String html;

	@Autowired VetexService vetexService;
	@Autowired OrderCart orderCart;
	
	@GetMapping("/priceItems")
	public String getAllPriceItems(Model model) {
		List<Vetex> listitems=vetexService.findAllPriceItems();
		model.addAttribute("listitems", listitems);
		return "priceItems";
	}
	
	@GetMapping ("/admin/deleteAllPrices")
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
	
	@GetMapping ("/showCart")
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
	@GetMapping ("/createOrder")
	public ResponseEntity<byte[]> getPDF() throws IOException {

        /* Create HTML using Thymeleaf template Engine */
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCacheable(false);
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        
        Context context = new Context();
        context.setVariable("cart", cart);
        context.setVariable("sumWithOutNds", sumWithOutNds);
        context.setVariable("Nds", Nds);
        context.setVariable("sumWithNds", sumWithNds);
        String orderHtml = templateEngine.process("showCart", context);

        /* Setup Source and target I/O streams */
        String filename="order.pdf";
        ByteArrayOutputStream target = new ByteArrayOutputStream();
        
        /* Call convert method */
        HtmlConverter.convertToPdf(orderHtml, target);

        /* extract output as bytes */
        byte[] bytes = target.toByteArray();

        /* Send the response as downloadable PDF */
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+ filename)
                .contentType(MediaType.APPLICATION_PDF)
                .body(bytes);

    }
	@GetMapping ("/deleteFromOrder")
	public String deleteFromOrder(@RequestParam("ppnumber")String ppnumber,@RequestParam("quantity")double quantity) {
		orderCart.deleteItem(ppnumber,quantity);
		return "redirect:/showCart";
	}
	@GetMapping("/403")
    public String error403() {
        return "/error/403";
    }
	@GetMapping ("/saveQuantityChanges")
	public String saveQuantityChanges(@RequestParam("ppnumber")String ppnumber,@RequestParam("quantity")double quantity,@RequestParam("newQuantity")double newQuantity) {
		orderCart.saveQuantityItem(ppnumber,quantity,newQuantity);
		return "redirect:/showCart";
	}
	@GetMapping ("/orderPage")
	public String tableOrder(Model model) {
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
		return"orderPage";
	}
}