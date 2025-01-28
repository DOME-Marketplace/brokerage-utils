package it.eng.dome.brokerage.test;

import java.util.ArrayList;
import java.util.List;

import it.eng.dome.brokerage.invoicing.dto.ApplyTaxesRequestDTO;
import it.eng.dome.tmforum.tmf637.v4.model.BillingAccountRef;
import it.eng.dome.tmforum.tmf637.v4.model.Product;
import it.eng.dome.tmforum.tmf678.v4.JSON;
import it.eng.dome.tmforum.tmf678.v4.model.AppliedCustomerBillingRate;

public class CreateApplyTaxesRequestDTO {
	
	public static void main(String[] args) {
		
	CreateApplyTaxesRequestDTO test=new CreateApplyTaxesRequestDTO();
	
		test.testSerializationApplyTaxesRequestDTO();
		test.testDeserializationApplyTaxesRequestDTO();	

	}

    private void testSerializationApplyTaxesRequestDTO() {
    	Product product=new Product();
		product.setId("1234");
		product.setDescription("Test Product 1");
		
		BillingAccountRef billingAccountRef=new BillingAccountRef();
		billingAccountRef.setId("4567");
		billingAccountRef.setName("BillingAccountRef Test");
		
		product.setBillingAccount(billingAccountRef);
		product.setIsBundle(false);
		
		
		ArrayList<AppliedCustomerBillingRate> acbrList=new ArrayList<AppliedCustomerBillingRate>();
		
		ApplyTaxesRequestDTO atrDT= new ApplyTaxesRequestDTO();
		atrDT.setProduct(product);
		
		AppliedCustomerBillingRate acbr=new AppliedCustomerBillingRate();
		acbr.setId("test");
		acbr.setDescription("Test1");
		
		AppliedCustomerBillingRate acbr2=new AppliedCustomerBillingRate();
		acbr2.setId("test2");
		acbr2.setDescription("Test2");
		
		acbrList.add(acbr);
		acbrList.add(acbr2);
		
		atrDT.setAppliedCustomerBillingRate(acbrList);
		
		String str=JSON.getGson().toJson(atrDT);
		System.out.println(str);
    }

    private void testDeserializationApplyTaxesRequestDTO() {
    	String test=new String("{\"product\":{\"id\":\"1234\",\"description\":\"Test Product 1\",\"isBundle\":false,\"billingAccount\":{\"id\":\"4567\",\"name\":\"BillingAccountRef Test\"}},\"appliedCustomerBillingRate\":[{\"id\":\"test\",\"description\":\"Test1\"},{\"id\":\"test2\",\"description\":\"Test2\"}]}");
		ApplyTaxesRequestDTO obj=(ApplyTaxesRequestDTO)JSON.deserialize(test, ApplyTaxesRequestDTO.class);
		System.out.println(obj.toString());
    }
  
}