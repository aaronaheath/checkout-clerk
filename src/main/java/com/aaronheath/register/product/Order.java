/**
 * 
 */
package com.aaronheath.register.product;

import java.util.List;


/**
 * @author aheath
 *
 */
public class Order {
	
	private List<Product> items;
	private double totalPrice;
	
	/**
	 * @return the items
	 */
	public List<Product> getItems() {
		return items;
	}
	
	/**
	 * @param items the items to set
	 */
	public void setItems(List<Product> items) {
		this.items = items;
	}
	
	/**
	 * @return the totalPrice
	 */
	public double getTotalPrice() {
		return totalPrice;
	}
	
	/**
	 * @param totalPrice the totalPrice to set
	 */
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}


}
