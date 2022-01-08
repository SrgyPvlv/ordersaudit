package com.example.sergey.Controller;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.sergey.Model.ContractText;
import com.example.sergey.Model.Order;
import com.example.sergey.Model.OrderCart;
import com.example.sergey.Model.VetexOrder;
import com.example.sergey.Service.BsListService;
import com.example.sergey.Service.ContractTextService;
import com.example.sergey.Service.DefaultOrderService;
import com.example.sergey.Service.UsersService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.Data;

@Data
@Controller
@RequestMapping("/orders")
public class OrderController {

	double sumWithOutNds;
	double Nds;
	double sumWithNds;
	Date dateSend;
	Date dateStart;
	Date dateEnd;
	ArrayList<VetexOrder> cart;
	@Autowired OrderCart orderCart;
	@Autowired UsersService userService;
	@Autowired BsListService bsListService;
	@Autowired ContractTextService contractTextService;
	@Autowired
	private DefaultOrderService orderService;
	@Autowired VetexController vetexController;
	@Autowired SpsController spsController;
	@Autowired VolotController volotController;
	@Autowired TelecomController telecomController;
	@Autowired TelrosController telrosController;
	
	@GetMapping("/createOrder") //записать заявку в базу данных
	public String createOrder(@RequestParam(name="id",required=false,defaultValue="0")Long id,@RequestParam("ordernumber")int ordernumber,@RequestParam("send")String send,
			@RequestParam("author")String author,@RequestParam("bsnumber")String bsnumber,
			@RequestParam("start")String start,@RequestParam("end")String end,@RequestParam("remedy")String remedy,
			@RequestParam("arenda")String arenda,@RequestParam("worktype")String worktype,@RequestParam("comment")String comment,
			@RequestParam(name="contractnumber") String contractnumber,
			@RequestParam(name="contractdate") String contractdate,
			@RequestParam(name="status",defaultValue="",required=false)String status,
			@RequestParam(name="orderlistcomment",defaultValue="",required=false)String orderlistcomment,
			@RequestParam(name="report",defaultValue="нет",required=false)String report,@RequestParam(name="cedr",defaultValue="нет",required=false)String cedr,
			RedirectAttributes redirectAttr) {
		sumWithOutNds=0;
		Nds=0;
		sumWithNds=0;
		
        cart=orderCart.getItemsOrderCart();
        String cartJsonStr = new Gson().toJson(cart); //ArrayList в Json
		
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
		
		String bsaddress=bsListService.findBsAddress(bsnumber);
		
		if(id!=0) {
			Order orderOld=orderService.getOrderById(id);
			orderOld.setOrdernumber(ordernumber);orderOld.setBsnumber(bsnumber);orderOld.setBsaddress(bsaddress);
			orderOld.setSend(send);orderOld.setStart(start);orderOld.setEndtime(end);
			orderOld.setSumwithoutnds(sumWithOutNds);orderOld.setNds(Nds);orderOld.setSumwithnds(sumWithNds);
			orderOld.setReport(report);orderOld.setCedr(cedr);orderOld.setStatus(status);
			orderOld.setWorktype(worktype);orderOld.setOrderlistcomment(orderlistcomment);orderOld.setContractnumber(contractnumber);
			orderOld.setContractdate(contractdate);orderOld.setRemedy(remedy);orderOld.setArenda(arenda);
			orderOld.setComment(comment);orderOld.setAuthor(author);orderOld.setCart(cartJsonStr);
			orderService.editOrder(orderOld);} else {
				
				Order order=new Order(ordernumber, bsnumber, bsaddress, send, start, end, 
						sumWithOutNds, Nds, sumWithNds, report, cedr, status, worktype, orderlistcomment, 
						contractnumber, contractdate, remedy, arenda, comment, author, cartJsonStr);
		orderService.createOrder(order);}
		
		redirectAttr.addAttribute("contractnumber", contractnumber);
		redirectAttr.addAttribute("contractdate", contractdate);
		
		return "redirect:/orders/showAllOrders";
	}
	
	@GetMapping("/showAllOrders") //все заявки по подрядчику(т.е. номеру договора) по возрастанию номера заявки 
	public String showAllOrders(@RequestParam(name="contractnumber",required=false) String contractnumber,
			@RequestParam(name="contractdate",required=false) String contractdate,Model model) {

		List<Order> listOrders=orderService.findAllByContractNumberOrderByOrdernumberAsc(contractnumber);
		model.addAttribute("listOrders", listOrders);
		model.addAttribute("contractnumber", contractnumber);
		model.addAttribute("contractdate", contractdate);
	
		return "showOrders";
	}
	
	@GetMapping("/orderDelete") //удалить заявку
	public String orderDelete(@RequestParam("id")Long id,@RequestParam(name="contractnumber") String contractnumber,
			@RequestParam(name="contractdate") String contractdate, RedirectAttributes redirectAttr) {
		
		orderService.deleteOrder(id);
		redirectAttr.addAttribute("contractnumber", contractnumber);
		redirectAttr.addAttribute("contractdate", contractdate);
		
		return "redirect:/orders/showAllOrders";
	}
	
	@GetMapping("/orderEdit") //переход на форму редактирования заявки
	public String orderEditForm(@RequestParam("id") Long id,@RequestParam(name="contractnumber") String contractnumber,
			@RequestParam(name="contractdate") String contractdate, Model model,RedirectAttributes redirectAttr) {
		sumWithOutNds=0;
		Nds=0;
		sumWithNds=0;
		
		Order orderDb=orderService.getOrderById(id);
		String cartJsonStr=orderDb.getCart();
		Type listType = new TypeToken<ArrayList<VetexOrder>>() {}.getType();
        ArrayList<VetexOrder> cartArrayList = new Gson().fromJson(cartJsonStr,listType); //Json в ArrayList
        
        orderCart.setItemsOrderCart(cartArrayList);
        cart=orderCart.getItemsOrderCart();
		
		String vetex=contractTextService.getContractText(1).getNumber();
		String sps=contractTextService.getContractText(2).getNumber();
		String volot=contractTextService.getContractText(3).getNumber();
		String telecom=contractTextService.getContractText(4).getNumber();
		String telros=contractTextService.getContractText(5).getNumber();
		
		String asd = null;
		
		if (contractnumber.equals(vetex)) asd="redirect:/dispOrder"; else {
		if (contractnumber.equals(sps)) asd= "redirect:/dispOrder/sps";else {
				if(contractnumber.equals(volot)) asd= "redirect:/dispOrder/volot"; else {
					if(contractnumber.equals(telecom)) asd= "redirect:/dispOrder/telecom"; else {
						if(contractnumber.equals(telros)) asd= "redirect:/dispOrder/telros";
	}
  }
 }
}		
		redirectAttr.addAttribute("id", id);
		redirectAttr.addAttribute("ordernumber", orderDb.getOrdernumber());
		redirectAttr.addAttribute("bsnumber", orderDb.getBsnumber());
		redirectAttr.addAttribute("send", orderDb.getSend());
		redirectAttr.addAttribute("start", orderDb.getStart());
		redirectAttr.addAttribute("endtime", orderDb.getEndtime());
		redirectAttr.addAttribute("remedy", orderDb.getRemedy());
		redirectAttr.addAttribute("author", orderDb.getAuthor());
		redirectAttr.addAttribute("arenda", orderDb.getArenda());
		redirectAttr.addAttribute("worktype", orderDb.getWorktype());
		redirectAttr.addAttribute("comment", orderDb.getComment());
		redirectAttr.addAttribute("contractnumber", orderDb.getContractnumber());
		redirectAttr.addAttribute("contractdate", orderDb.getContractdate());
		redirectAttr.addAttribute("status", orderDb.getStatus());
		redirectAttr.addAttribute("orderlistcomment", orderDb.getOrderlistcomment());
		redirectAttr.addAttribute("report", orderDb.getReport());
		redirectAttr.addAttribute("cedr", orderDb.getCedr());
		
		return asd;}
	
	@GetMapping("/orderCopy") //переход на форму создания копии заявки (редактирования без передачи id)
	public String orderCopyForm(@RequestParam("id") Long id,@RequestParam(name="contractnumber") String contractnumber,
			@RequestParam(name="contractdate") String contractdate, Model model,RedirectAttributes redirectAttr) {
		sumWithOutNds=0;
		Nds=0;
		sumWithNds=0;
		
		String vetex=contractTextService.getContractText(1).getNumber();
		String sps=contractTextService.getContractText(2).getNumber();
		String volot=contractTextService.getContractText(3).getNumber();
		String telecom=contractTextService.getContractText(4).getNumber();
		String telros=contractTextService.getContractText(5).getNumber();
		
		String asd = null;
		
		if (contractnumber.equals(vetex)) {vetexController.clearCart3(); asd="redirect:/dispOrder";} else {
		if (contractnumber.equals(sps)) {spsController.clearCart3();asd= "redirect:/dispOrder/sps";}else {
				if(contractnumber.equals(volot)) {volotController.clearCart3();asd= "redirect:/dispOrder/volot";} else {
					if(contractnumber.equals(telecom)) {telecomController.clearCart3();asd= "redirect:/dispOrder/telecom";} else {
						if(contractnumber.equals(telros)) {telrosController.clearCart3();asd= "redirect:/dispOrder/telros";}
	}
  }
 }
}	
		Order orderDb=orderService.getOrderById(id);
		String cartJsonStr=orderDb.getCart();
		Type listType = new TypeToken<ArrayList<VetexOrder>>() {}.getType();
        ArrayList<VetexOrder> cartArrayList = new Gson().fromJson(cartJsonStr,listType); //Json в ArrayList
        
        orderCart.setItemsOrderCart(cartArrayList);
        cart=orderCart.getItemsOrderCart();
	
		//redirectAttr.addAttribute("ordernumber", orderDb.getOrdernumber());
		//redirectAttr.addAttribute("bsnumber", orderDb.getBsnumber());
		//redirectAttr.addAttribute("send", orderDb.getSend());
		//redirectAttr.addAttribute("start", orderDb.getStart());
		//redirectAttr.addAttribute("endtime", orderDb.getEndtime());
		//redirectAttr.addAttribute("remedy", orderDb.getRemedy());
		//redirectAttr.addAttribute("author", orderDb.getAuthor());
		//redirectAttr.addAttribute("arenda", orderDb.getArenda());
		redirectAttr.addAttribute("worktype", orderDb.getWorktype());
		redirectAttr.addAttribute("comment", orderDb.getComment());
		redirectAttr.addAttribute("contractnumber", orderDb.getContractnumber());
		redirectAttr.addAttribute("contractdate", orderDb.getContractdate());
		//redirectAttr.addAttribute("status", orderDb.getStatus());
		//redirectAttr.addAttribute("orderlistcomment", orderDb.getOrderlistcomment());
		//redirectAttr.addAttribute("report", orderDb.getReport());
		//redirectAttr.addAttribute("cedr", orderDb.getCedr());
		
		return asd;}
	
	@GetMapping ("/orderPage") //создание страницы заявки
	public String tableOrder(@RequestParam("id") Long id, Model model) {
			
		sumWithOutNds=0;
		Nds=0;
		sumWithNds=0;
		
		Order orderDb=orderService.getOrderById(id);
		String cartJsonStr=orderDb.getCart();
		Type listType = new TypeToken<ArrayList<VetexOrder>>() {}.getType();
        ArrayList<VetexOrder> cartArrayList = new Gson().fromJson(cartJsonStr,listType); //Json в ArrayList
        
        orderCart.setItemsOrderCart(cartArrayList);
        cart=orderCart.getItemsOrderCart();
        
      String send=orderDb.getSend();
      String start=orderDb.getStart();
      String end=orderDb.getEndtime();
        
	  SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
	  SimpleDateFormat formatter1=new SimpleDateFormat("dd-MM-yyyy");
		
		try {
			this.dateSend = formatter.parse(send);
			this.dateStart = formatter.parse(start);
			this.dateEnd = formatter.parse(end);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		send=formatter1.format(this.dateSend);
		start=formatter1.format(this.dateStart);
		end=formatter1.format(this.dateEnd);
		
		model.addAttribute("cart", cart);
		model.addAttribute("sumWithOutNds", orderDb.getSumwithoutnds());
		model.addAttribute("Nds", orderDb.getNds());
		model.addAttribute("sumWithNds", orderDb.getSumwithnds());
		model.addAttribute("ordernumber", orderDb.getOrdernumber());
		model.addAttribute("contractnumber", orderDb.getContractnumber());
		model.addAttribute("contractdate", orderDb.getContractdate());
		model.addAttribute("send", send);
		model.addAttribute("author", orderDb.getAuthor());
		model.addAttribute("bsnumber", orderDb.getBsnumber());
		model.addAttribute("bsadress", orderDb.getBsaddress());
		model.addAttribute("start", start);
		model.addAttribute("end", end);
		model.addAttribute("remedy", orderDb.getRemedy());
		model.addAttribute("arenda", orderDb.getArenda());
		model.addAttribute("comment", orderDb.getComment());
		return"orderPage";
	}
}
