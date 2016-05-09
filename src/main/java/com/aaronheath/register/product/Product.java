/**
 * 
 */
package com.aaronheath.register.product;


/**
 * @author aheath
 *
 */
public class Product implements Comparable<Product> {
	
	private char productCode;
	private double unitPrice;
	private int volumeLimit;
	private double volumePrice;
	
	/**
	 * @return the productCode
	 */
	public char getProductCode() {
		return productCode;
	}
	
	/**
	 * @param productCode the productCode to set
	 */
	public void setProductCode(char productCode) {
		this.productCode = productCode;
	}
	
	/**
	 * @return the unitPrice
	 */
	public double getUnitPrice() {
		return unitPrice;
	}
	
	/**
	 * @param unitPrice the unitPrice to set
	 */
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	/**
	 * @return the volumeLimit
	 */
	public int getVolumeLimit() {
		return volumeLimit;
	}
	
	/**
	 * @param volumeLimit the volumeLimit to set
	 */
	public void setVolumeLimit(int volumeLimit) {
		this.volumeLimit = volumeLimit;
	}
	
	/**
	 * @return the volumePrice
	 */
	public double getVolumePrice() {
		return volumePrice;
	}
	
	/**
	 * @param volumePrice the volumePrice to set
	 */
	public void setVolumePrice(double volumePrice) {
		this.volumePrice = volumePrice;
	}

	@Override
	public int compareTo(Product o) {
		Character prodIdA = this.productCode;
		Character prodIdB = o.getProductCode();
		
		return prodIdA.compareTo(prodIdB);
		
	}

}
