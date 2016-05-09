/**
 * 
 */
package com.aaronheath.register;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import com.aaronheath.register.product.Product;
import com.aaronheath.register.utils.JSONUtils;
import com.aaronheath.service.checkout.CheckoutService;
import com.aaronheath.service.checkout.CheckoutServiceFactory;


/**
 * @author aheath
 *
 */
public class Register {
	
	private static Logger logger = Logger.getLogger(Register.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		CheckoutService register = CheckoutServiceFactory.getCheckoutService();
		
		try {
			String priceStr = register.getPriceData();
			JSONArray priceJson = JSONUtils.toJSONArray(priceStr);
			List<Product> scannedItems = new ArrayList<Product>();	
			register.setPricing(priceJson);
			String[] codes = "C,C,C,C,C,C,C".split(",");
			//String[] codes = "A,B,C,D".split(",");
			//String[] codes = "A,B,C,D,A,B,A,A".split(",");
			
			
			for (String codeStr : codes) {
				Product curProd = register.scan(codeStr.charAt(0));
				scannedItems.add(curProd);
			}
			if (scannedItems != null && ! scannedItems.isEmpty()) {
				Collections.sort(scannedItems);
				scannedItems.forEach(product->logger.debug("product " + product.getUnitPrice()));
				register.calculateTotal(scannedItems);
			}
			
		
		} catch (IOException e) {
			logger.error("Error ", e);
		}
		
		//PriceService priceService = () -> pro1 = new Product();
		//System.out.println( "wow" + priceService.getProductList() ); 
		
		
		

	}
	
	


}

	
