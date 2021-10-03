package com.example.sergey;

import java.util.ArrayList;
import java.util.Arrays;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class OrderCart {
	
	private ArrayList<VetexOrder> itemsOrderCart=new ArrayList<VetexOrder>();
	
	public void addItem(VetexOrder vetexOrder) {
		itemsOrderCart.addAll(Arrays.asList(vetexOrder));
	}
	
	public void clearCart() {
		itemsOrderCart.clear();
	}

	public ArrayList<VetexOrder> getItemsOrderCart(){
		return itemsOrderCart;
	}
}
