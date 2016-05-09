/**
 * 
 */
package com.aaronheath.service.checkout;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.aaronheath.register.product.Product;
import com.aaronheath.register.utils.JSONUtils;



/**
 * @author aheath
 *
 */
public interface CheckoutService {
	
	static Logger logger = Logger.getLogger(CheckoutService.class);
	
	void setPricing(JSONArray priceData);
	//TODO was void
	Product scan(char code);
	void printReceipt();
	
	default double calculateTotal(List<Product> cartItems) {
		double totalPrice = 0;
		Collections.sort(cartItems);
		for (int i = 0; i < cartItems.size(); i++) {
			int totalDiscountItems = 0;
			double discountTotal = 0;
			double regularPrice = 0;
			Product curItem = cartItems.get(i);
			List<Product> prods = (List<Product>) cartItems.stream().filter(prod -> prod.getProductCode() == curItem.getProductCode()).collect(Collectors.toList());
			i += prods.size() -1;
			Product categoryProduct = prods.get(0);
			logger.debug("categoryProduct " + categoryProduct.getProductCode());
			int curVolumeLimit = categoryProduct.getVolumeLimit();
			int totalProducts = prods.size();
			double discountPrice = (categoryProduct.getVolumePrice() / curVolumeLimit);
			if (curVolumeLimit > 0) {
				totalDiscountItems = (totalProducts / curVolumeLimit) * curVolumeLimit  ;
			}
			
			logger.debug("	totalDiscountItems " + 	totalDiscountItems + "totalProducts " + totalProducts);
			if (totalDiscountItems > 0 && discountPrice > 0) {
				discountTotal = discountPrice * totalDiscountItems;
			}
			int fullPriceItems = 0;
			if (curVolumeLimit > 0) {
				fullPriceItems = totalProducts % curVolumeLimit;
			}
			else {
				fullPriceItems = totalProducts;
			}
			
			if (fullPriceItems > 0) {
				regularPrice =  fullPriceItems * categoryProduct.getUnitPrice();
				logger.debug("fullPriceItems " + fullPriceItems);
			}
			
		
			logger.debug(" discountTotal " +  discountTotal );
			logger.debug("regular price " +  regularPrice); 
			
			
			totalPrice += discountTotal + regularPrice;
			
		
		}
		logger.debug("TOTAL : " + totalPrice);
		return totalPrice;
	}
	
	default String getPriceData() throws IOException {
		String priceData = null;
		
		String csvFile = "./data/price_file.csv";
		BufferedReader br = null;
		String line = "";
		String delimiter = ",";
		List<Product> productList = null;
		try {
			br = new BufferedReader(new FileReader(csvFile));
			if (br != null) {
				productList = new ArrayList<Product>();
				while ((line = br.readLine()) != null) {
					String[] productLine = line.split(delimiter);
					if (productLine != null && productLine.length > 1) {
						Product curProd = new Product();
						curProd.setProductCode(productLine[0].charAt(0));
						curProd.setUnitPrice(Double.parseDouble(productLine[1]));
						curProd.setVolumeLimit(Integer.parseInt(productLine[2]));
						curProd.setVolumePrice(Double.parseDouble(productLine[3]));
						productList.add(curProd);
					}
				}
			}
			
			if (productList != null && ! productList.isEmpty()) {
				priceData = JSONUtils.toJson(productList);
			}
			
		}
		catch (IOException e) {
			logger.error("Error getting price data");
			throw e;
		}
		finally {
			br.close();
		}
		
		
		return priceData;
		
	}
	
}
