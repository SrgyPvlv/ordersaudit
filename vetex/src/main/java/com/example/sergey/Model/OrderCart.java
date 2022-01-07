package com.example.sergey.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

//Класс,  отражающий корзину заказа, содержащий все выбранные пункты ТЦП, их кол-во и т.д.
//Позволяет изменять кол-во, пункты: добавлять, удалять, очищать корзину.
@Component
@SessionScope
public class OrderCart {
	
	private ArrayList<VetexOrder> itemsOrderCart=new ArrayList<VetexOrder>();
	
	public void addItem(VetexOrder vetexOrder) {
		
			   itemsOrderCart.addAll(Arrays.asList(vetexOrder));  
	}
	
	public void deleteItem(String ppnumber,double quantity) {
		
		Iterator<VetexOrder> vetexOrderIterator = itemsOrderCart.iterator();//создаем итератор
		while(vetexOrderIterator.hasNext()) {//до тех пор, пока в списке есть элементы

			VetexOrder nextVetexOrder = vetexOrderIterator.next();//получаем следующий элемент
		   if (nextVetexOrder.ppnumber.equals(ppnumber)&nextVetexOrder.quantity==quantity) {
			   vetexOrderIterator.remove();//удаляем с нужным именем
		   break;
		   }
		}
	}
	public void clearCart() {
		itemsOrderCart.clear();
	}

	public ArrayList<VetexOrder> getItemsOrderCart(){
		Collections.sort(itemsOrderCart);
		return itemsOrderCart;
	}
	
	public void setItemsOrderCart(ArrayList<VetexOrder> itemsOrderCart) {
		this.itemsOrderCart=itemsOrderCart;
	}
	
	public void saveQuantityItem(String ppnumber, double quantity, double newQuantity) {
		Iterator <VetexOrder> vetexOrderIterator=itemsOrderCart.iterator();
		while(vetexOrderIterator.hasNext()) {
			VetexOrder nextVetexOrder=vetexOrderIterator.next();
			if(nextVetexOrder.ppnumber.equals(ppnumber)&nextVetexOrder.quantity==quantity) {
				
				nextVetexOrder.setQuantity(newQuantity);
				nextVetexOrder.setEndPrice();
				break;
			}
		}
	}
}
