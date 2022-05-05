package com.example.sergey.Controller;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.annotation.SessionScope;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.sergey.Model.Contractor;
import com.example.sergey.Model.Order;
import com.example.sergey.Model.OrderCart;
import com.example.sergey.Model.PricesSelect;
import com.example.sergey.Service.BsListService;
import com.example.sergey.Service.ContractorService;
import com.example.sergey.Service.OrderService;
import com.example.sergey.Service.UsersService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.Data;

//создание, создание копии, редактирование, удаление, получение номера заявок
@Data
@Controller
@SessionScope
public class OrderController {

	double sumWithOutNds;
	double Nds;
	double sumWithNds;
	@Autowired OrderCart orderCart;
	@Autowired UsersService userService;
	@Autowired BsListService bsListService;
	@Autowired ContractorService contractorService;
	@Autowired
	private OrderService orderService;
	@Autowired PricesController pricesController;
		
	private static final Logger logger=LoggerFactory.getLogger(OrderController.class);
	
	@GetMapping("/orders/createOrder") //записать заявку в базу данных
	public String createOrder(@RequestParam(name="id",required=false,defaultValue="0")Long id,@RequestParam("ordernumber")int ordernumber,@RequestParam("send")String send,
			@RequestParam("author")String author,@RequestParam("bsnumber")String bsnumber,
			@RequestParam("start")String start,@RequestParam("end")String end,@RequestParam("remedy")String remedy,
			@RequestParam("arenda")String arenda,@RequestParam("worktype")String worktype,@RequestParam("comment")String comment,
			@RequestParam(name="contractnumber") String contractnumber,
			@RequestParam(name="contractdate") String contractdate,
			@RequestParam(name="status",defaultValue="",required=false)String status,
			@RequestParam(name="orderlistcomment",defaultValue="",required=false)String orderlistcomment,
			@RequestParam(name="report",defaultValue="нет",required=false)String report,@RequestParam(name="cedr",defaultValue="нет",required=false)String cedr,
			RedirectAttributes redirectAttr,Model model) {
		sumWithOutNds=0;
		Nds=0;
		sumWithNds=0;
		
		ArrayList<PricesSelect> cart=orderCart.getItemsOrderCart();
        String cartJsonStr = new Gson().toJson(cart); //ArrayList в Json
		
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
		
		String bsaddress=bsListService.findBsAddress(bsnumber);
		
		try {
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
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String login=auth.getName();
		logger.info("Пользователь "+login+" сохранил заявку №"+ordernumber+" для БС "+bsnumber+". Договор: "+contractnumber+".");
		
		redirectAttr.addAttribute("contractnumber", contractnumber);
		redirectAttr.addAttribute("ordernumber", ordernumber);
		
		pricesController.clearCart3();
		
		return "redirect:/orders/showAllOrders";} catch(Exception e) {
	        model.addAttribute("note", "Не удалось записать Заявку в БД! Вероятно данный номер Заявки уже используется. "
	        		+ "Вернитесь назад, увеличьте номер на 1 единицу и попробуйте снова.");
	        return "noLoad";
	      }
	}
	
	@GetMapping("/orders/showAllOrders") //все заявки по подрядчику(т.е. номеру договора) по возрастанию номера заявки, 
	                              //а также фильтрованные по номеру заявки или номеру бс
	public String showAllOrders(@RequestParam(name="contractnumber") String contractnumber,
			@RequestParam(name="ordernumber",required=false,defaultValue="0") int ordernumber,
			@RequestParam(name="bsNumberSearch",defaultValue="",required=false) String bsNumberSearch, Model model) {

		List<Order> listOrders = null;
		if(ordernumber==0 & bsNumberSearch.isEmpty()) {
			listOrders=orderService.findAllByContractNumberOrderByOrdernumberAsc(contractnumber);} else {
			if(ordernumber!=0) listOrders=orderService.findByOrderNumber(ordernumber, contractnumber); else {
				if(!bsNumberSearch.isEmpty()) listOrders=orderService.findByBsName(bsNumberSearch, contractnumber);
			}
		}
		
		 Date sendDate = null;
	     Date startDate = null;
	     Date endDate = null;
		
		for(Order order: listOrders) {
			SimpleDateFormat formatterStringToDate=new SimpleDateFormat("yyyy-MM-dd");
			
			try {sendDate=formatterStringToDate.parse(order.getSend());
			     startDate=formatterStringToDate.parse(order.getStart());
			     endDate=formatterStringToDate.parse(order.getEndtime());}
			catch (ParseException e) {e.printStackTrace();}
			
			SimpleDateFormat formatterDateToString=new SimpleDateFormat("dd.MM.yyyy");
			String sendString=formatterDateToString.format(sendDate);
			String startString=formatterDateToString.format(startDate);
			String endString=formatterDateToString.format(endDate);
			order.setSend(sendString);
			order.setStart(startString);
			order.setEndtime(endString);
		}
		
		Contractor contractor1=contractorService.getContractorByContractNumberWithOutText(contractnumber);
		String email1=contractor1.getEmail1();
		String email2=contractor1.getEmail2();
		String email3=contractor1.getEmail3();
		String email11=contractor1.getEmail11();
		String email12=contractor1.getEmail12();
		String email13=contractor1.getEmail13();
		String contractname=contractor1.getName();
		String contractor=contractor1.getContractor();
		model.addAttribute("listOrders", listOrders);
		model.addAttribute("contractor", contractor);
		model.addAttribute("contractnumber", contractnumber);
		model.addAttribute("contractname", contractname);
		model.addAttribute("email1", email1);
		model.addAttribute("email2", email2);
		model.addAttribute("email3", email3);
		model.addAttribute("email11", email11);
		model.addAttribute("email12", email12);
		model.addAttribute("email13", email13);
	
		return "showOrders";
	}
	
	@GetMapping("/superadmin/orderDelete") //удалить заявку
	public String orderDelete(@RequestParam("id")Long id,@RequestParam(name="contractnumber") String contractnumber,
			RedirectAttributes redirectAttr) {
		
		orderService.deleteOrder(id);
		redirectAttr.addAttribute("contractnumber", contractnumber);
				
		return "redirect:/orders/showAllOrders";
	}
	
	@GetMapping("/orders/orderEdit") //переход на форму редактирования заявки
	public String orderEditForm(@RequestParam("id") Long id,@RequestParam(name="contractor") String contractor,
			@RequestParam(name="contractname")String contractname, RedirectAttributes redirectAttr,Model model) {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String login=auth.getName();
		Order orderDb=orderService.getOrderById(id);
		String avtor=orderDb.getAuthor();
		String loginAvtor=userService.getLoginByAuthor(avtor);
		if(login.equals(loginAvtor) || login.equals("admin") || login.equals("spavlov")){
		
		sumWithOutNds=0;
		Nds=0;
		sumWithNds=0;
		
		pricesController.clearCart3();
		
		String cartJsonStr=orderDb.getCart();
		Type listType = new TypeToken<ArrayList<PricesSelect>>() {}.getType();
        ArrayList<PricesSelect> cartArrayList = new Gson().fromJson(cartJsonStr,listType); //Json в ArrayList
        
        orderCart.setItemsOrderCart(cartArrayList);
        		
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
		redirectAttr.addAttribute("contractor", contractor);
		redirectAttr.addAttribute("contractname", contractname);
		
		return "redirect:/dispOrder";} else {
			model.addAttribute("note", "Вы не можете редактировать чужую заявку! Обратитесь к её автору или администратору.");
			return "noLoad";}
	}

	@GetMapping("/orders/orderCopy") //переход на форму создания копии заявки (редактирования без передачи id)
	public String orderCopyForm(@RequestParam("id") Long id,@RequestParam(name="contractor") String contractor,
			@RequestParam(name="contractname")String contractname,RedirectAttributes redirectAttr) {
		
		sumWithOutNds=0;
		Nds=0;
		sumWithNds=0;
		
		pricesController.clearCart3();
		
		Order orderDb=orderService.getOrderById(id);
		String cartJsonStr=orderDb.getCart();
		Type listType = new TypeToken<ArrayList<PricesSelect>>() {}.getType();
        ArrayList<PricesSelect> cartArrayList = new Gson().fromJson(cartJsonStr,listType); //Json в ArrayList
        
        orderCart.setItemsOrderCart(cartArrayList);
        	
		redirectAttr.addAttribute("worktype", orderDb.getWorktype());
		redirectAttr.addAttribute("contractnumber", orderDb.getContractnumber());
		redirectAttr.addAttribute("contractdate", orderDb.getContractdate());
		redirectAttr.addAttribute("contractor", contractor);
		redirectAttr.addAttribute("contractname", contractname);
				
		return "redirect:/dispOrder";}
	
	@GetMapping ("/orders/orderPage") //создание страницы заявки
	public String tableOrder(@RequestParam("id") Long id, Model model) {
			
		sumWithOutNds=0;
		Nds=0;
		sumWithNds=0;
		Date dateSend = null;
		Date dateStart = null;
		Date dateEnd = null;
		Date contractDateDate = null;
		
		Order orderDb=orderService.getOrderById(id);
		String cartJsonStr=orderDb.getCart();
		Type listType = new TypeToken<ArrayList<PricesSelect>>() {}.getType();
        ArrayList<PricesSelect> cartArrayList = new Gson().fromJson(cartJsonStr,listType); //Json в ArrayList
                
      String send=orderDb.getSend();
      String start=orderDb.getStart();
      String end=orderDb.getEndtime();
      String contractDateString=orderDb.getContractdate();
        
	  SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
	  SimpleDateFormat formatter1=new SimpleDateFormat("dd.MM.yyyy");
		
		try {
			dateSend = formatter.parse(send);
			dateStart = formatter.parse(start);
			dateEnd = formatter.parse(end);
			contractDateDate = formatter.parse(contractDateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		send=formatter1.format(dateSend);
		start=formatter1.format(dateStart);
		end=formatter1.format(dateEnd);
		contractDateString=formatter1.format(contractDateDate);
		
		model.addAttribute("cart", cartArrayList);
		model.addAttribute("sumWithOutNds", orderDb.getSumwithoutnds());
		model.addAttribute("Nds", orderDb.getNds());
		model.addAttribute("sumWithNds", orderDb.getSumwithnds());
		model.addAttribute("ordernumber", orderDb.getOrdernumber());
		model.addAttribute("contractnumber", orderDb.getContractnumber());
		model.addAttribute("contractdate", contractDateString);
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
		
	@GetMapping("/orders/showNextOrderNumber") //определение номера для следующей заявки для данного подрядчика 
	public String showNextOrderNumber(@RequestParam(name="contractnumber",required=false) String contractnumber,
			@RequestParam(name="contractor") String contractor,@RequestParam(name="contractname")String contractname,
			@RequestParam(name="contractdate",required=false) String contractdate,RedirectAttributes redirectAttr) {
		sumWithOutNds=0;
		Nds=0;
		sumWithNds=0;
		
		int ordernumber=orderService.showNextOrderNumber(contractnumber);
		redirectAttr.addAttribute("ordernumber", ordernumber);
		redirectAttr.addAttribute("contractor", contractor);
		redirectAttr.addAttribute("contractnumber", contractnumber);
		redirectAttr.addAttribute("contractdate", contractdate);
		redirectAttr.addAttribute("contractname", contractname);
		return "redirect:/dispOrder";
	}
	
	@GetMapping("/superadmin/deleteAllByContractor") //удалить Все заказы данного подрядчика
	public String deleteAllByContractor(@RequestParam(name="contractnumber") String contractnumber,RedirectAttributes redirectAttr) {
		
		orderService.deleteAllByContractnumber(contractnumber);
		
		redirectAttr.addAttribute("contractnumber", contractnumber);
		
		return "redirect:/orders/showAllOrders";
	}	
}
