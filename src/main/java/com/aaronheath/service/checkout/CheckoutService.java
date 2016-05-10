/**
 * 
 */
package com.aaronheath.service.checkout;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

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
	String printReceipt();
	void calculateTotal(List<Product> cartItems);
	
	//default method for getting current price data from a file
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
			logger.error("Error getting price data: ", e);
			throw e;
		}
		finally {
			br.close();
			br = null;
		}
		
		return priceData;
		
	}
}
