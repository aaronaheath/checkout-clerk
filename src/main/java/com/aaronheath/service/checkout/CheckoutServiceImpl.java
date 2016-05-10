/**
 * 
 */
package com.aaronheath.service.checkout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.aaronheath.register.product.Order;
import com.aaronheath.register.product.Product;

/**
 * @author aheath
 *
 */
public class CheckoutServiceImpl implements CheckoutService {
	private static Logger logger = Logger.getLogger(CheckoutServiceImpl.class);
	
	private Map<Character, Product> productList;
	private Order currentOrder;;

	@Override
	public void setPricing(JSONArray priceData) {
		logger.debug("setPricing");
		if (priceData != null && ! priceData.isEmpty()) {
			List<Product> curProducts = new ArrayList<Product>();
			//loop over JSON Data & create List of Products
			priceData.forEach(item->curProducts.add((Product) JSONObject.toBean((JSONObject) item, Product.class)));
			
			if (! curProducts.isEmpty()) {
				this.productList = new HashMap<Character, Product>();
				//Add products to a HashMap indexed by productCode
				curProducts.forEach(product->this.productList.put(product.getProductCode(), product));
			}
			
		}
	}

	@Override
	public Product scan(char code) {
		Product cartItem = null;
		if (this.productList != null) {
			cartItem = this.productList.get(code);
		}
		
		return cartItem;
	}

	@Override
	public String printReceipt() {
		String receiptStr = null;
		if (this.currentOrder != null) {
			List<Product> curProducts = this.currentOrder.getItems();
			if (curProducts != null && ! curProducts.isEmpty()) {
				receiptStr = "Items ";
				for (Product curProd : curProducts) {
					receiptStr += curProd.getProductCode();
				}
				
				receiptStr += " Total $" + this.currentOrder.getTotalPrice();
			}
		}
		
		return receiptStr;
	}

	@Override
	public void calculateTotal(List<Product> cartItems) {
		if (cartItems != null && ! cartItems.isEmpty()) {
			double totalPrice = 0;
			for (int i = 0; i < cartItems.size(); i++) {
				double discountTotal = 0;
				Product curItem = cartItems.get(i);
				
				//Filter out all products by product code
				List<Product> prods = (List<Product>) cartItems.stream().filter(prod -> prod.getProductCode() == curItem.getProductCode()).collect(Collectors.toList());
				if (prods != null && ! prods.isEmpty()) {
					//Remove filtered items from cartItems list.
					i += prods.size() -1;
					
					Product categoryProduct = prods.get(0);
					int curVolumeLimit = categoryProduct.getVolumeLimit();
					int totalProducts = prods.size();
					// current volume item price
					double discountPrice = (categoryProduct.getVolumePrice() / curVolumeLimit);
					
					if (curVolumeLimit > 0) {
						// total items eligible for volume price discount
						int totalDiscountItems = (totalProducts / curVolumeLimit) * curVolumeLimit  ;
						if (totalDiscountItems > 0 && discountPrice > 0) {
							discountTotal = discountPrice * totalDiscountItems;
						}
					}
						
					// determine # of full price items 
					int fullPriceItems = totalProducts;
					if (curVolumeLimit > 0) {
						fullPriceItems = totalProducts % curVolumeLimit;
					}
					double regularPrice = fullPriceItems * categoryProduct.getUnitPrice();
					totalPrice += discountTotal + regularPrice;
				
				}
			}	
			//Setting current order for receipt
			this.currentOrder = new Order();
			this.currentOrder.setItems(cartItems);
			this.currentOrder.setTotalPrice(totalPrice);
		}
	}

}
