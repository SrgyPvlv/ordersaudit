package com.example.sergey.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

//Класс,  отражающий корзину заказа, содержащий все выбранные пункты ТЦП, их кол-во и т.д.
//Позволяет изменять кол-во, пункты: добавлять, удалять, очищать корзину. Корзина одна на всю сессию для всех подрядчиков!!!(надо чистить!)
@Component
@SessionScope
public class OrderCart {
	
	private ArrayList<PricesSelect> itemsOrderCart=new ArrayList<PricesSelect>(); //это Корзина (список всех ТЦП в Корзине)
	
	public void addItem(PricesSelect pricesSelect) { //добавление позиции ТЦП в Корзину
		
			   itemsOrderCart.addAll(Arrays.asList(pricesSelect));  
	}
	
	public void deleteItem(String ppnumber,double quantity) { //удаление позиции ТЦП из Корзины
		
		Iterator<PricesSelect> pricesSelectIterator = itemsOrderCart.iterator();//создаем итератор
		while(pricesSelectIterator.hasNext()) {//до тех пор, пока в списке есть элементы

			PricesSelect nextpricesSelect = pricesSelectIterator.next();//получаем следующий элемент
		   if (nextpricesSelect.getPpNumber().equals(ppnumber)&nextpricesSelect.getQuantity()==quantity) {
			   pricesSelectIterator.remove();//удаляем с нужным именем
		   break;
		   }
		}
	}
	public void clearCart() { //очистить Корзину
		itemsOrderCart.clear();
	}

	public ArrayList<PricesSelect> getItemsOrderCart(){ // getter для Корзины
		Collections.sort(itemsOrderCart);
		return itemsOrderCart;
	}
	
	public void setItemsOrderCart(ArrayList<PricesSelect> itemsOrderCart) { //setter для Корзины
		this.itemsOrderCart=itemsOrderCart;
	}
	
	public void saveQuantityItem(String ppnumber, double quantity, double newQuantity) { //установка кол-ва для данного пункта ТЦП
		Iterator <PricesSelect> pricesSelectIterator=itemsOrderCart.iterator();
		while(pricesSelectIterator.hasNext()) {
			PricesSelect nextpricesSelect=pricesSelectIterator.next();
			if(nextpricesSelect.getPpNumber().equals(ppnumber)&nextpricesSelect.getQuantity()==quantity) {
				
				nextpricesSelect.setQuantity(newQuantity);
				nextpricesSelect.setEndPrice();
				break;
			}
		}
	}
}
