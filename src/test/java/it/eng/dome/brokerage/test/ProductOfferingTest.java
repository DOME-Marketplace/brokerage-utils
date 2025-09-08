package it.eng.dome.brokerage.test;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.eng.dome.brokerage.api.ProductOfferingApis;
import it.eng.dome.tmforum.tmf620.v4.ApiClient;
import it.eng.dome.tmforum.tmf620.v4.Configuration;
import it.eng.dome.tmforum.tmf620.v4.model.ProductOffering;
import it.eng.dome.tmforum.tmf620.v4.model.ProductOfferingCreate;
import it.eng.dome.tmforum.tmf620.v4.model.ProductOfferingUpdate;
import it.eng.dome.tmforum.tmf620.v4.model.TimePeriod;

public class ProductOfferingTest {

	final static String tmf620ProductOfferingPath = "tmf-api/productCatalogManagement/v4";
	final static String tmfEndpoint = "https://dome-dev.eng.it";
	

	public static void main(String[] args) {
		
		
		/**
		 * Create ProductOffering
		 */
		//TestCreateProductOffering();
		
		/**
		 * Get All ProductOffering
		 */
		//TestGetAllProductOffering();
		
		/**
		 * Update ProductOffering
		 */
//		String id = "urn:ngsi-ld:product-offering:9d796a50-bd97-4415-9021-ab02268feab61";
//		TestUpdateProductOffering(id);
		
		/**
		 * Get ProductOffering by ID
		 */
//		TestGetProductOffering(id);
		
		/**
		 * Get Filtered Customers
		 */
		TestGetFilteredProductOffering();
		

	}
	
	protected static String TestCreateProductOffering() {
		
		ApiClient apiClientTmf620 = Configuration.getDefaultApiClient();
		apiClientTmf620.setBasePath(tmfEndpoint + "/" + tmf620ProductOfferingPath);
		
		ProductOfferingApis apis = new ProductOfferingApis(apiClientTmf620);
		
		ProductOfferingCreate poc = new ProductOfferingCreate();
		poc.setName("Test - Create Product Offering");
		poc.setDescription("Test - Use Case: creation of Product Offering");
		poc.isBundle(false);
		poc.setLastUpdate(OffsetDateTime.now());
		poc.setLifecycleStatus("Launched");
		poc.setVersion("1.0");

		TimePeriod tp = new TimePeriod();
		tp.setStartDateTime(OffsetDateTime.now());
		tp.setEndDateTime(OffsetDateTime.now().plusDays(10));
		poc.setValidFor(tp);
		
		ProductOffering po = apis.createProductOffering(poc);
		System.out.println("ProductOffering id: " + po.getId());
		return po.getId();
	}
	
	protected static boolean TestUpdateProductOffering(String id) {

		ApiClient apiClientTmf620 = Configuration.getDefaultApiClient();
		apiClientTmf620.setBasePath(tmfEndpoint + "/" + tmf620ProductOfferingPath);

		ProductOfferingApis apis = new ProductOfferingApis(apiClientTmf620);
		
		ProductOfferingUpdate pou = new ProductOfferingUpdate();
		pou.setLifecycleStatus("Launched");
		
		return apis.updateProductOffering(id, pou);
	}
	
	protected static void TestGetProductOffering(String id) {

		ApiClient apiClientTmf620 = Configuration.getDefaultApiClient();
		apiClientTmf620.setBasePath(tmfEndpoint + "/" + tmf620ProductOfferingPath);

		ProductOfferingApis apis = new ProductOfferingApis(apiClientTmf620);
		
		ProductOffering po = apis.getProductOffering(id, null);
		if (po != null) {
			System.out.println(po.getId() + " " + po.getName() + " " + po.getLifecycleStatus());
		}
	}
	
	protected static void TestGetAllProductOffering() {

		ApiClient apiClientTmf620 = Configuration.getDefaultApiClient();
		apiClientTmf620.setBasePath(tmfEndpoint + "/" + tmf620ProductOfferingPath);

		ProductOfferingApis apis = new ProductOfferingApis(apiClientTmf620);
		
		List<ProductOffering> pos = apis.getAllProductOfferings(null, null);
		
		int count = 0;
	 	for (ProductOffering po : pos) {
			System.out.println(++count + " => " + po.getId() + " " + po.getName() + " " + po.getLifecycleStatus());
		}
	}
	
	protected static void TestGetFilteredProductOffering() {

		ApiClient apiClientTmf620 = Configuration.getDefaultApiClient();
		apiClientTmf620.setBasePath(tmfEndpoint + "/" + tmf620ProductOfferingPath);

		ProductOfferingApis apis = new ProductOfferingApis(apiClientTmf620);
		
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("lifecycleStatus", "Retired"); //Obsolete, Retired, Launched
		
		List<ProductOffering> pos = apis.getAllProductOfferings(null, filter);
		
		int count = 0;
	 	for (ProductOffering po : pos) {
			System.out.println(++count + " => " + po.getId() + " " + po.getName() + " " + po.getLifecycleStatus());
		}
	}
}
