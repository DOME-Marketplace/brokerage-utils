package it.eng.dome.brokerage.invoicing.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
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
	
	private List<AppliedCustomerBillingRate> appliedCustomerBillingRate;
	
	/** 
	* Class constructor.
	*/
	public ApplyTaxesRequestDTO() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Class constructor specifying the Product, the TimePeriod and the list of ProductPrice for which the bill will be calculated
	 */
	@JsonCreator
	public ApplyTaxesRequestDTO(@JsonProperty("product") Product pr, @JsonProperty("appliedCustomerBillingRate") List<AppliedCustomerBillingRate> acbrl) {
		this.setProduct(pr);
		this.setAppliedCustomerBillingRate(acbrl);
	}
	
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
	public List<AppliedCustomerBillingRate> getAppliedCustomerBillingRate() {
		return appliedCustomerBillingRate;
	}
	
	/**
	 * Sets the list of the AppliedCustomerBillingRate to which the taxes must be applied
	 * 
	 * @param appliedCustomerBillingRate the list of the AppliedCustomerBillingRate to set
	 */
	public void setAppliedCustomerBillingRate(List<AppliedCustomerBillingRate> appliedCustomerBillingRate) {
		this.appliedCustomerBillingRate = appliedCustomerBillingRate;
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
		for (int i = 0; i < this.getAppliedCustomerBillingRate().size(); i++) {
			if (i > 0) {
				appliedCustomerBillingRateList.append(", ");
			}
			appliedCustomerBillingRateList.append(this.getAppliedCustomerBillingRate().get(i).toJson());
		}
		appliedCustomerBillingRateList.append("]");

		return "{ \"product\": " + capitalizeStatus(productJson) + ", \"appliedCustomerBillingRate\": " + appliedCustomerBillingRateList.toString() + "}";

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
