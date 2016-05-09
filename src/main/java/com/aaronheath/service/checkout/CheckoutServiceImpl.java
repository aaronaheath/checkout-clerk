/**
 * 
 */
package com.aaronheath.service.checkout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.aaronheath.register.product.Product;

/**
 * @author aheath
 *
 */
public class CheckoutServiceImpl implements CheckoutService {
	private static Logger logger = Logger.getLogger(CheckoutServiceImpl.class);
	
	private Map<Character, Product> productList;
	private List<Product> cartItems;

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
	public void printReceipt() {
		// TODO Auto-generated method stub
		
	}

}
