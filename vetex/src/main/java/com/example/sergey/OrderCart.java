package com.example.sergey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class OrderCart {
	
	private ArrayList<VetexOrder> itemsOrderCart=new ArrayList<VetexOrder>();
	
	public void addItem(VetexOrder vetexOrder) {
		itemsOrderCart.addAll(Arrays.asList(vetexOrder));
		
	}
	public void deleteItem(int ppnumber) {
		
		Iterator<VetexOrder> vetexOrderIterator = itemsOrderCart.iterator();//создаем итератор
		while(vetexOrderIterator.hasNext()) {//до тех пор, пока в списке есть элементы

			VetexOrder nextVetexOrder = vetexOrderIterator.next();//получаем следующий элемент
		   if (nextVetexOrder.ppnumber==ppnumber) {
			   vetexOrderIterator.remove();//удаляем с нужным именем
		   }
		}
	}
	public void clearCart() {
		itemsOrderCart.clear();
	}

	public ArrayList<VetexOrder> getItemsOrderCart(){
		return itemsOrderCart;
	}
}
