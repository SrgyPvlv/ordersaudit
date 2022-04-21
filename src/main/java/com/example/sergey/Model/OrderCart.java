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
	
	private ArrayList<VetexOrder> itemsOrderCart=new ArrayList<VetexOrder>(); //это Корзина (список всех ТЦП в Корзине)
	
	public void addItem(VetexOrder vetexOrder) { //добавление позиции ТЦП в Корзину
		
			   itemsOrderCart.addAll(Arrays.asList(vetexOrder));  
	}
	
	public void deleteItem(String ppnumber,double quantity) { //удаление позиции ТЦП из Корзины
		
		Iterator<VetexOrder> vetexOrderIterator = itemsOrderCart.iterator();//создаем итератор
		while(vetexOrderIterator.hasNext()) {//до тех пор, пока в списке есть элементы

			VetexOrder nextVetexOrder = vetexOrderIterator.next();//получаем следующий элемент
		   if (nextVetexOrder.getPpNumber().equals(ppnumber)&nextVetexOrder.getQuantity()==quantity) {
			   vetexOrderIterator.remove();//удаляем с нужным именем
		   break;
		   }
		}
	}
	public void clearCart() { //очистить Корзину
		itemsOrderCart.clear();
	}

	public ArrayList<VetexOrder> getItemsOrderCart(){ // getter для Корзины
		Collections.sort(itemsOrderCart);
		return itemsOrderCart;
	}
	
	public void setItemsOrderCart(ArrayList<VetexOrder> itemsOrderCart) { //setter для Корзины
		this.itemsOrderCart=itemsOrderCart;
	}
	
	public void saveQuantityItem(String ppnumber, double quantity, double newQuantity) { //установка кол-ва для данного пункта ТЦП
		Iterator <VetexOrder> vetexOrderIterator=itemsOrderCart.iterator();
		while(vetexOrderIterator.hasNext()) {
			VetexOrder nextVetexOrder=vetexOrderIterator.next();
			if(nextVetexOrder.getPpNumber().equals(ppnumber)&nextVetexOrder.getQuantity()==quantity) {
				
				nextVetexOrder.setQuantity(newQuantity);
				nextVetexOrder.setEndPrice();
				break;
			}
		}
	}
}
