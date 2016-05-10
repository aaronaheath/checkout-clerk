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
			//Get priceData as JSONArray
			register.setPricing(JSONUtils.toJSONArray(register.getPriceData()));
			List<Product> scannedItems = new ArrayList<Product>();	
			
			// order data
			String[] codes = "C,C,C,C,C,C,C".split(",");
			//String[] codes = "A,B,C,D".split(",");
			//String[] codes = "A,B,C,D,A,B,A,A".split(",");
			
			// scan each item in the order
			for (String codeStr : codes) {
				Product curProd = register.scan(codeStr.charAt(0));
				scannedItems.add(curProd);
			}
			if (scannedItems != null && ! scannedItems.isEmpty()) {
				//calculate total & printReceipt
				register.calculateTotal(scannedItems);
				String receiptStr = register.printReceipt();
				logger.debug("receiptStr " + receiptStr);
			}
			
		} catch (IOException e) {
			logger.error("Error running checkout service: ", e);
		}

	}

}

	
