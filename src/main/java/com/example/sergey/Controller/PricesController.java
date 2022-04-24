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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.sergey.Model.ContractText;
import com.example.sergey.Model.OrderCart;
import com.example.sergey.Model.Users;
import com.example.sergey.Model.Prices;
import com.example.sergey.Model.PricesSelect;
import com.example.sergey.Service.BsListService;
import com.example.sergey.Service.ContractTextService;
import com.example.sergey.Service.UsersService;
import com.example.sergey.Service.PricesService;

@Controller
@SessionScope
public class PricesController {
	
	double sumWithOutNds;
	double Nds;
	double sumWithNds;
	ArrayList<PricesSelect> cart;
	int cartSize;
	Date dateSend;
	Date dateStart;
	Date dateEnd;
	
	Long id;Integer ordernumber;String bsnumber;String bsaddress;String send;String start;String endtime;String report;
	String cedr;String status;String worktype;String orderlistcomment;String contractnumber;String contractdate;String remedy;
	String arenda;String comment;String author;

	@Autowired PricesService pricesService;
	@Autowired OrderCart orderCart;
	@Autowired ContractTextService contractTextService;
	@Autowired UsersService userService;
	@Autowired BsListService bsListService;
	
	@GetMapping("/priceItems") //показать все пункты тцп по конкретному подрядчику для добавления в заявку, без возможности редактирования (для users)
	public String getAllPriceItems(@RequestParam(name="contractor") String contractor,
			@RequestParam(name="contractnumber") String contractnumber,
			@RequestParam(name="contractdate") String contractdate, @RequestParam(name="contractname") String contractname,Model model) {
		
		List<Prices> listitems=pricesService.findAllPriceItemsByContractor(contractor);
		
		if (orderCart.getItemsOrderCart()!=null) {cartSize=orderCart.getItemsOrderCart().size();} else {cartSize=0;};
		
		model.addAttribute("listitems", listitems);
		model.addAttribute("contractor", contractor);
		model.addAttribute("contractnumber", contractnumber);
		model.addAttribute("contractdate", contractdate);
		model.addAttribute("contractname", contractname);
		model.addAttribute("cartSize", cartSize);
				
		return "priceItems";
	}
	
	@GetMapping("/admin/priceItems") //показать все пункты тцп по конкретному подрядчику с возможностью редактирования (для admin)
	public String getEditAllPriceItems(@RequestParam(name="contractor") String contractor,
			@RequestParam(name="contractname") String contractname,Model model) {
		
		List<Prices> listitems=pricesService.findAllPriceItemsByContractor(contractor);
				
		model.addAttribute("listitems", listitems);
		model.addAttribute("contractor", contractor);
		model.addAttribute("contractname", contractname);
						
		return "prices";
	}
	
	@GetMapping ("/superadmin/deleteAllPrices") //удаление всех пунктов тцп данного подрядчика (без удаления самого подрядчика)
	public String deleteAllPricesByContractor(@RequestParam(name="contractor") String contractor,
			@RequestParam(name="contractname") String contractname,RedirectAttributes redirectAttr) {
		
		pricesService.deleteAllPricesByContractor(contractor);
		
		redirectAttr.addAttribute("contractor", contractor);
		redirectAttr.addAttribute("contractname", contractname);
		
		return "redirect:/admin/priceItems";
	}
	
	@GetMapping("/admin/newPriceItemForm") //переход на форму добавления нового пункта тцп для данного подрядчика
	public String newPriceItemForm(@RequestParam(name="contractor") String contractor,
			@RequestParam("contractname") String contractname, Model model) {
		
		model.addAttribute("contractor", contractor);
		model.addAttribute("contractname", contractname);
		
		return"newPriceItemForm";
	}
	
	@PostMapping("/admin/newPriceItemCreate") //сохранение нового пункта тцп и возврат на страницу тцп данного подрядчика
	public String newPriceItemCreate(@RequestParam("pp") String pp,@RequestParam("workname") String workname,//
			@RequestParam("unitmeasure") String unitmeasure,@RequestParam("price") double price,@RequestParam("comment") String comment,
			@RequestParam("contractor") String contractor,@RequestParam("contractname") String contractname,RedirectAttributes redirectAttr) throws IOException{
		
		Prices newPriceItem=new Prices(pp,workname,unitmeasure,price,comment,contractor);
		try {
			pricesService.savePriceItem(newPriceItem);
		}catch (Exception e) {}
		
		redirectAttr.addAttribute("contractor", contractor);
		redirectAttr.addAttribute("contractname", contractname);
		
		return "redirect:/admin/priceItems";
	}
	
	@GetMapping("/admin/deletePriceItem") //удаление одного конкретного пункта тцп данного подрядчика
	public String deletePriceItem(@RequestParam("id") long id,@RequestParam("contractor") String contractor,
			@RequestParam("contractname") String contractname,RedirectAttributes redirectAttr) {
		
		pricesService.deletePriceItemById(id);
		
		redirectAttr.addAttribute("contractor", contractor);
		redirectAttr.addAttribute("contractname", contractname);
		
		return "redirect:/admin/priceItems";
	}
	
	@GetMapping("/admin/editPriceItemForm") //переход на форму редактирования конкретного пункта тцп для данного подрядчика
	public String editPriceItemForm(@RequestParam("id") long id,@RequestParam("contractor") String contractor,
			@RequestParam("contractname") String contractname,Model model,RedirectAttributes redirectAttr) {
		
		Prices item=pricesService.findPriceItemById(id);
		
		model.addAttribute("item", item);
		redirectAttr.addAttribute("contractor", contractor);
		redirectAttr.addAttribute("contractname", contractname);
		
		return "editPriceItemForm";
	}
	
	@PostMapping ("/admin/editPriceItem") //сохранение редактирования конкретного пункта тцп для данного подрядчика
	public String editPriceItem(@RequestParam("id") long id,@RequestParam("pp") String pp,@RequestParam("workname") String workname,
	@RequestParam("unitmeasure") String unitmeasure,@RequestParam("price") double price,@RequestParam("comment") String comment,
	@RequestParam("contractor") String contractor,@RequestParam("contractname") String contractname,RedirectAttributes redirectAttr) throws IOException{
		
		Prices item=pricesService.findPriceItemById(id);
		item.setPpNumber(pp);
		item.setWorkName(workname);
		item.setUnitMeasure(unitmeasure);
		item.setPrice(price);
		item.setComment(comment);
		pricesService.savePriceItem(item);
		
		redirectAttr.addAttribute("contractor", contractor);
		redirectAttr.addAttribute("contractname", contractname);
		
		return "redirect:/admin/priceItems";
	}
	
	@GetMapping("/findByNumber") //поиск для данного подрядчика пункта тцп по его номеру
	public String findByNumber(@RequestParam(name="contractor") String contractor, @RequestParam("ppsearch") String ppsearch,
			@RequestParam(name="contractnumber",required=false) String contractnumber,
			@RequestParam(name="contractdate",required=false) String contractdate, @RequestParam(name="contractname") String contractname, Model model)throws IOException{
		
		List<Prices> listitems=pricesService.findPriceItemByPpNumber(contractor, ppsearch);
		
		if(contractnumber.isEmpty()) {
		model.addAttribute("listitems", listitems);
		model.addAttribute("contractnumber", contractnumber);
		model.addAttribute("contractdate", contractdate);
		model.addAttribute("contractname", contractname);
		model.addAttribute("cartSize", cartSize);
		
		return "priceItems";} else {
			
			model.addAttribute("listitems", listitems);
			model.addAttribute("contractor", contractor);
			model.addAttribute("contractname", contractname);
			
			return "prices";
		}
	}
	
	@GetMapping("/findByName") //поиск для данного подрядчика пункта тцп по фильтрам в названии пункта (фильтр до 2-х слов, в любом их порядке и сокращении)
	public String findByName(@RequestParam(name="contractor") String contractor, @RequestParam("workname") String workname,
			@RequestParam(name="contractnumber") String contractnumber,
			@RequestParam(name="contractdate") String contractdate, @RequestParam(name="contractname") String contractname, Model model)throws IOException{
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
		List<Prices> listitems=pricesService.findPriceItemByWorkName(contractor,workname,workname1,workname2);
		
		if(contractnumber.isEmpty()) {
			model.addAttribute("listitems", listitems);
			model.addAttribute("contractnumber", contractnumber);
			model.addAttribute("contractdate", contractdate);
			model.addAttribute("contractname", contractname);
			model.addAttribute("cartSize", cartSize);
			
			return "priceItems";} else {
				
				model.addAttribute("listitems", listitems);
				model.addAttribute("contractor", contractor);
				model.addAttribute("contractname", contractname);
				
				return "prices";
			}
	}
	
	@GetMapping("/addInOrder")
	public String addInOrder(@RequestParam("id") long id,@RequestParam("quantity") double quantity,
			@RequestParam(name="contractor") String contractor,@RequestParam(name="contractnumber") String contractnumber,
			@RequestParam(name="contractdate") String contractdate, @RequestParam(name="contractname") String contractname,RedirectAttributes redirectAttr) {
		
		Prices item=pricesService.findPriceItemById(id);
		String ppnumber=item.getPpNumber();
		String workname=item.getWorkName();
		String unitmeasure=item.getUnitMeasure();
		double price=item.getPrice();
		String comment=item.getComment();
		String contractorGet=item.getContractor();

		PricesSelect pricesSelect=new PricesSelect(ppnumber,workname,unitmeasure,price,comment,contractorGet,quantity);
		orderCart.addItem(pricesSelect);
		
		redirectAttr.addAttribute("contractor", contractor);
		redirectAttr.addAttribute("contractnumber", contractnumber);
		redirectAttr.addAttribute("contractdate", contractdate);
		redirectAttr.addAttribute("contractname", contractname);
				
		return "redirect:/priceItems";
	}
	
	@GetMapping ("/dispOrder")
	public String showCart(@RequestParam(name="id",defaultValue="0",required=false) Long id,@RequestParam(name="ordernumber",defaultValue="0",required=false) Integer ordernumber,
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
			ContractText vetexContract=contractTextService.getContractorWithOutText(1);
			this.contractnumber=vetexContract.getNumber();
			this.contractdate=vetexContract.getDate();}
		
		cart=orderCart.getItemsOrderCart();
		
		for(PricesSelect cartitem : cart) {
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
		return"dispOrder";
	}
	@GetMapping ("/clearCart")
	public String clearCart() {
		orderCart.clearCart();
		this.id=null; this.ordernumber=null; this.bsnumber=null; this.send=null; this.start=null; this.endtime=null;
		this.remedy=null; this.author=null; this.arenda=null; this.worktype=null; this.comment=null; this.status=null;
		this.report=null; this.cedr=null; this.orderlistcomment=null;
		return "redirect:/dispOrder";
	}
	@GetMapping ("/clearCart2")
	public String clearCart2() {
		orderCart.clearCart();
		this.id=null; this.ordernumber=null; this.bsnumber=null; this.send=null; this.start=null; this.endtime=null;
		this.remedy=null; this.author=null; this.arenda=null; this.worktype=null; this.comment=null; this.status=null;
		this.report=null; this.cedr=null; this.orderlistcomment=null;
		return "redirect:/priceItems";
	}
	
	public void clearCart3() {
		orderCart.clearCart();
		this.id=null; this.ordernumber=null; this.bsnumber=null; this.send=null; this.start=null; this.endtime=null;
		this.remedy=null; this.author=null; this.arenda=null; this.worktype=null; this.comment=null; this.status=null;
		this.report=null; this.cedr=null; this.orderlistcomment=null;
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
}