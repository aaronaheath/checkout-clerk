/**
 * 
 */
package com.aaronheath.service.checkout;


/**
 * @author aheath
 *
 */
public class CheckoutServiceFactory {
	
	public static CheckoutService getCheckoutService() {
		CheckoutService checkout = new CheckoutServiceImpl();
		return checkout;
	}

}
