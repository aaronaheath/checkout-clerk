/**
 * 
 */
package com.aaronheath.register;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.aaronheath.register.product.Product;
import com.aaronheath.register.utils.JSONUtils;
import com.aaronheath.service.checkout.CheckoutService;
import com.aaronheath.service.checkout.CheckoutServiceImpl;

/**
 * @author aheath
 *
 */
public class RegisterTest {
	
	private static Logger logger = Logger.getLogger(RegisterTest.class);
	
	private static CheckoutService checkoutService;
	private String priceData;
	
	@BeforeClass
	public static void initRegister() {
		checkoutService = new CheckoutServiceImpl();
	}


	
	@Before
	public void setUp() {
		try {
			this.priceData = RegisterTest.checkoutService.getPriceData();
		}
		catch (IOException e) {
			logger.error("Error with checkout service setup during test: ", e);
		}
	}
	
	@Test
	public void testCalculate() {
		JSONArray priceJson ;
		try {
			priceJson = JSONUtils.toJSONArray(checkoutService.getPriceData());
		
		List<Product> scannedItems = new ArrayList<Product>();	
		checkoutService.setPricing(priceJson);
		String[] codes = "C,C,C,C,C,C,C".split(",");
		//String[] codes = "A,B,C,D".split(",");
		//String[] codes = "A,B,C,D,A,B,A,A".split(",");
		
		for (String codeStr : codes) {
			Product curProd = checkoutService.scan(codeStr.charAt(0));
			scannedItems.add(curProd);
		}
		if (scannedItems != null && ! scannedItems.isEmpty()) {
			Collections.sort(scannedItems);
		    checkoutService.calculateTotal(scannedItems);
		}
			String test1 = "Items AAAABBCD Total $32.40";
			String test2 = "Items CCCCCCC Total $7.25";
			
			assertEquals(test2, checkoutService.printReceipt());
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	}
}
	



