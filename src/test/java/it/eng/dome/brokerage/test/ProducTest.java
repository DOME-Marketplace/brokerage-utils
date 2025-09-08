package it.eng.dome.brokerage.test;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.eng.dome.brokerage.api.ProductApis;
import it.eng.dome.brokerage.api.ProductOfferingPriceApis;
import it.eng.dome.tmforum.tmf620.v4.model.ProductOfferingPrice;
import it.eng.dome.tmforum.tmf637.v4.model.Product;

public class ProducTest {

	final static String tmf637ProductInventoryPath = "tmf-api/productInventory/v4";
	final static String tmf620ProductCatalogPath = "tmf-api/productCatalogManagement/v4";
	final static String tmfEndpoint = "https://dome-dev.eng.it";

	public static void main(String[] args) {

		//TestApis();
		
		//TestPOPApis();
		
		TestGetFilteredProducts();
	}
	
	
	protected static void TestGetFilteredProducts() {

		it.eng.dome.tmforum.tmf637.v4.ApiClient apiClientTmf637 = it.eng.dome.tmforum.tmf637.v4.Configuration.getDefaultApiClient();
		apiClientTmf637.setBasePath(tmfEndpoint + "/" + tmf637ProductInventoryPath);

		ProductApis apis = new ProductApis(apiClientTmf637);

		Map<String, String> filter = new HashMap<String, String>();
		filter.put("status", "created");
		List<Product> products = apis.getAllProducts(null, filter);

		int count = 0;
		for (Product product : products) {
			System.out.println(++count + " => " + product.getId() + " " + product.getStatus() + " " + product.getName());
		}
	}

	protected static void TestPOPApis() {
		
		it.eng.dome.tmforum.tmf620.v4.ApiClient apiClientTmf620 = it.eng.dome.tmforum.tmf620.v4.Configuration.getDefaultApiClient();
		apiClientTmf620.setBasePath(tmfEndpoint + "/" + tmf620ProductCatalogPath);
		
		ProductOfferingPriceApis apis = new ProductOfferingPriceApis(apiClientTmf620);
		
		//POP id with isBundle = false
		String id = "urn:ngsi-ld:product-offering-price:3da19c94-f884-49e2-ae3e-2dd35dc09f96";
		ProductOfferingPrice pop = apis.getProductOfferingPrice(id, null);
		if (pop != null) {
			System.out.println("POP: " + pop.getName() + " " + pop.getIsBundle() + " " + pop.getLastUpdate());
		} else {
			System.out.println("POP not found with id: " + id);
		}
		
		//POP id with isBundle = true
		id = "urn:ngsi-ld:product-offering-price:1b9b98b4-6c90-494a-b599-7456d87ad371";
		pop = apis.getProductOfferingPrice(id, null);
		if (pop != null) {
			System.out.println("POP: " + pop.getName() + " " + pop.getIsBundle() + " " + pop.getLastUpdate());
		} else {
			System.out.println("POP not found with id: " + id);
		}

	}
	
	protected static void TestApis() {

		it.eng.dome.tmforum.tmf637.v4.ApiClient apiClientTmf637 = it.eng.dome.tmforum.tmf637.v4.Configuration.getDefaultApiClient();
		apiClientTmf637.setBasePath(tmfEndpoint + "/" + tmf637ProductInventoryPath);

		ProductApis apis = new ProductApis(apiClientTmf637);
		
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("isBundle", "false");
		String mydate = "2025-04-22T15:18:52.065849400Z";
		OffsetDateTime dateRef = OffsetDateTime.parse(mydate);
		filter.put("startDate.lt", mydate); // gt, lt, eq OK
		
		List<Product> products = apis.getAllProducts(null, null);

		int count = 0;
		
		for (Product product : products) {
			System.out.println(++count + " => " + product.getId() + " / " + product.getName());
			if (dateRef.isBefore(product.getStartDate())) {
				System.out.println(dateRef  + " is before " + product.getStartDate());
			} else {
				System.out.println(dateRef  + " is after " + product.getStartDate());
			}
		}
	}
}
