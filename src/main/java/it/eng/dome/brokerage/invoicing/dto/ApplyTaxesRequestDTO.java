package it.eng.dome.brokerage.invoicing.dto;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import it.eng.dome.tmforum.tmf637.v4.model.Product;
import it.eng.dome.tmforum.tmf678.v4.model.AppliedCustomerBillingRate;

/**
 * This class represents the DTO used by the "invoicing/applyTaxes" API  to calculate a the taxes of the bill \n.
 * This class contains information about the Product (TMF637-v4) and the list of the AppliedCustomerBillingRate to which the taxes must be applied
 */
public class ApplyTaxesRequestDTO {
	
	private Product product;
	
	private List<AppliedCustomerBillingRate> appliedCustomerBillingRateList;
	
	/**
	 * The Product of the AppliedCustomerBillingRate list
	 * 
	 * @return the Product  of the AppliedCustomerBillingRate list 
	 */
	public Product getProduct() {
		return product;
	}
	
	/**
	 * Sets the Product of the AppliedCustomerBillingRate list
	 * 
	 * @param product the Product of the AppliedCustomerBillingRate list 
	 */
	public void setProduct(Product product) {
		this.product = product;
	}
	
	/**
	 * Returns the list of the AppliedCustomerBillingRate to which the taxes must be applied
	 * 
	 * @return the list of the AppliedCustomerBillingRate to which the taxes must be applied
	 */
	public List<AppliedCustomerBillingRate> getAppliedCustomerBillingRateList() {
		return appliedCustomerBillingRateList;
	}
	
	/**
	 * Sets the list of the AppliedCustomerBillingRate to which the taxes must be applied
	 * 
	 * @param appliedCustomerBillingRateList the list of the AppliedCustomerBillingRate to set
	 */
	public void setAppliedCustomerBillingRateList(List<AppliedCustomerBillingRate> appliedCustomerBillingRateList) {
		this.appliedCustomerBillingRateList = appliedCustomerBillingRateList;
	}

	
	/**
	 * Returns the ApplyTaxesRequestDTO to which the bill refers to
	 * 
	 * @return The Json (in string format) of the bill
	 */
	public String toJson() {
		
		// product
		String productJson = this.getProduct().toJson();

		// appliedCustomerBillingRateList
		StringBuilder appliedCustomerBillingRateList = new StringBuilder("[");
		for (int i = 0; i < this.getAppliedCustomerBillingRateList().size(); i++) {
			if (i > 0) {
				appliedCustomerBillingRateList.append(", ");
			}
			appliedCustomerBillingRateList.append(this.getAppliedCustomerBillingRateList().get(i).toJson());
		}
		appliedCustomerBillingRateList.append("]");

		return "{ \"product\": " + capitalizeStatus(productJson) + ", \"appliedCustomerBillingRateList\": " + appliedCustomerBillingRateList.toString() + "}";

	}
	
	// Bugfix: ProductStatusType must be uppercase
	private String capitalizeStatus(String json) {
		ObjectMapper objectMapper = new ObjectMapper();
		String capitalize = json;
		 try {
			ObjectNode jsonNode = (ObjectNode) objectMapper.readTree(json);
			 String status = jsonNode.get("status").asText();
			 jsonNode.put("status", status.toUpperCase());
			 return objectMapper.writeValueAsString(jsonNode);

		} catch (Exception e) {			
			return capitalize;
		}
	}
}
