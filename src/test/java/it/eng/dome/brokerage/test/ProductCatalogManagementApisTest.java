package it.eng.dome.brokerage.test;

import java.time.OffsetDateTime;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import it.eng.dome.brokerage.api.ProductCatalogManagementApis;
import it.eng.dome.brokerage.api.fetch.FetchUtils;
import it.eng.dome.tmforum.tmf620.v4.ApiClient;
import it.eng.dome.tmforum.tmf620.v4.Configuration;
import it.eng.dome.tmforum.tmf620.v4.model.ProductOffering;
import it.eng.dome.tmforum.tmf620.v4.model.ProductOfferingCreate;
import it.eng.dome.tmforum.tmf620.v4.model.ProductOfferingPrice;
import it.eng.dome.tmforum.tmf620.v4.model.ProductOfferingUpdate;
import it.eng.dome.tmforum.tmf620.v4.model.ProductSpecification;
import it.eng.dome.tmforum.tmf620.v4.model.TimePeriod;

public class ProductCatalogManagementApisTest {

	final static String tmf620ProductCatalogPath = "tmf-api/productCatalogManagement/v4";
	final static String tmfEndpoint = "https://dome-dev.eng.it";
	

	public static void main(String[] args) {
		
		
		/**
		 * ProductOffering
		 */
//		TestCreateProductOffering();
//		TestGetAllProductOffering();
//		TestGetFilteredProductOffering();		
//		String id = "urn:ngsi-ld:product-offering:d2d6e74e-1d18-478f-a435-6b56e6f44dd3";
//		TestGetProductOffering(id);		
//		TestUpdateProductOffering(id);
		
		/**
		 * ProductOfferingPrice
		 */
		TestGetAllProductOfferingPrice();
//		String id = "urn:ngsi-ld:product-offering-price:356144e6-03de-4a80-9d37-953d0f4ec83c";
//		TestGetProductOfferingPrice(id);
		
		
		/**
		 * ProductSpecification
		 */
//		TestGetAllProductSpecification();
//		String id = "urn:ngsi-ld:product-specification:538b1e8f-12bd-4c7c-a18e-62792bf3e0bc";
//		TestGetProductSpecification(id);

	}
	
	protected static String TestCreateProductOffering() {
		
		ApiClient apiClientTmf620 = Configuration.getDefaultApiClient();
		apiClientTmf620.setBasePath(tmfEndpoint + "/" + tmf620ProductCatalogPath);
		
		ProductCatalogManagementApis apis = new ProductCatalogManagementApis(apiClientTmf620);
		
		ProductOfferingCreate poc = new ProductOfferingCreate();
		poc.setName("New Product Offering");
		poc.setDescription("Use Case: creation of Product Offering for testing");
		poc.isBundle(false);
		poc.setLastUpdate(OffsetDateTime.now());
		poc.setLifecycleStatus("Launched");
		poc.setVersion("1.0");

		TimePeriod tp = new TimePeriod();
		tp.setStartDateTime(OffsetDateTime.now());
		tp.setEndDateTime(OffsetDateTime.now().plusDays(10));
		poc.setValidFor(tp);
		
		String id = apis.createProductOffering(poc);
		System.out.println("ProductOffering id: " + id);
		return id;
	}
	
	protected static void TestGetAllProductOffering() {

		ApiClient apiClientTmf620 = Configuration.getDefaultApiClient();
		apiClientTmf620.setBasePath(tmfEndpoint + "/" + tmf620ProductCatalogPath);

		ProductCatalogManagementApis apis = new ProductCatalogManagementApis(apiClientTmf620);		
		AtomicInteger count = new AtomicInteger(0);
		
		FetchUtils.streamAll(
	        apis::listProductOfferings,		// method reference
	        null,                       	// fields
	        null, 				    		// filter
	        100                         	// pageSize
		) 
		.forEach(po -> { 
			count.incrementAndGet();
			System.out.println(count + " " + po.getId() + " → " + po.getName() + " / " + po.getLifecycleStatus());
			}
		);		
		
		System.out.println("ProductOffering found: " + count);
	}
	
	protected static void TestGetFilteredProductOffering() {

		ApiClient apiClientTmf620 = Configuration.getDefaultApiClient();
		apiClientTmf620.setBasePath(tmfEndpoint + "/" + tmf620ProductCatalogPath);

		ProductCatalogManagementApis apis = new ProductCatalogManagementApis(apiClientTmf620);		
		AtomicInteger count = new AtomicInteger(0);
		
		FetchUtils.streamAll(
	        apis::listProductOfferings,				// method reference
	        null,                       			// fields
	        Map.of("lifecycleStatus","Obsolete"), 	// filter //Obsolete, Retired, Launched
	        100                         			// pageSize
		) 
		.forEach(po -> { 
			count.incrementAndGet();
			System.out.println(count + " " + po.getId() + " → " + po.getName() + " / " + po.getLifecycleStatus());
			}
		);		
		
		System.out.println("ProductOffering found: " + count);
	}
	
	protected static boolean TestUpdateProductOffering(String id) {

		ApiClient apiClientTmf620 = Configuration.getDefaultApiClient();
		apiClientTmf620.setBasePath(tmfEndpoint + "/" + tmf620ProductCatalogPath);

		ProductCatalogManagementApis apis = new ProductCatalogManagementApis(apiClientTmf620);
		
		ProductOfferingUpdate pou = new ProductOfferingUpdate();
		pou.setLifecycleStatus("Launched");
		
		return apis.updateProductOffering(id, pou);
	}
	
	protected static void TestGetProductOffering(String id) {

		ApiClient apiClientTmf620 = Configuration.getDefaultApiClient();
		apiClientTmf620.setBasePath(tmfEndpoint + "/" + tmf620ProductCatalogPath);

		ProductCatalogManagementApis apis = new ProductCatalogManagementApis(apiClientTmf620);
		
		ProductOffering po = apis.getProductOffering(id, null);
		if (po != null) {
			System.out.println(po.getId() + " " + po.getName() + " " + po.getLifecycleStatus());
		}
	}
	
	
	protected static void TestGetAllProductOfferingPrice() {
		
		ApiClient apiClientTmf620 = Configuration.getDefaultApiClient();
		apiClientTmf620.setBasePath(tmfEndpoint + "/" + tmf620ProductCatalogPath);

		ProductCatalogManagementApis apis = new ProductCatalogManagementApis(apiClientTmf620);		
		AtomicInteger count = new AtomicInteger(0);
		
		FetchUtils.streamAll(
	        apis::listProductOfferingPrices,	// method reference
	        null,                       		// fields
	        null, 				    			// filter
	        100                         		// pageSize
		) 
		.forEach(pop -> { 
			count.incrementAndGet();
			System.out.println(count + " " + pop.getId() + " → " + pop.getName() + " / " + pop.getLifecycleStatus());
			}
		);		
		
		System.out.println("ProductOfferingPrice found: " + count);
	}
	
	protected static void TestGetProductOfferingPrice(String id) {

		ApiClient apiClientTmf620 = Configuration.getDefaultApiClient();
		apiClientTmf620.setBasePath(tmfEndpoint + "/" + tmf620ProductCatalogPath);

		ProductCatalogManagementApis apis = new ProductCatalogManagementApis(apiClientTmf620);	
		String fields = "name,version,priceType";
				
		ProductOfferingPrice pos = apis.getProductOfferingPrice(id, fields);
		if (pos != null) {
			System.out.println(pos.getId() + " " + pos.getName() + " " + pos.getPriceType());
		}
	}
	
	
	protected static void TestGetAllProductSpecification() {

		ApiClient apiClientTmf620 = Configuration.getDefaultApiClient();
		apiClientTmf620.setBasePath(tmfEndpoint + "/" + tmf620ProductCatalogPath);

		ProductCatalogManagementApis apis = new ProductCatalogManagementApis(apiClientTmf620);		
		AtomicInteger count = new AtomicInteger(0);
		
		FetchUtils.streamAll(
	        apis::listProductSpecifications,	// method reference
	        null,                       		// fields
	        null, 				    			// filter
	        100                         		// pageSize
		) 
		.forEach(ps -> { 
			count.incrementAndGet();
			System.out.println(count + " " + ps.getId() + " → " + ps.getName() + " / " + ps.getLifecycleStatus());
			}
		);		
		
		System.out.println("ProductSpecification found: " + count);
	}
	
	protected static void TestGetProductSpecification(String id) {

		ApiClient apiClientTmf620 = Configuration.getDefaultApiClient();
		apiClientTmf620.setBasePath(tmfEndpoint + "/" + tmf620ProductCatalogPath);

		ProductCatalogManagementApis apis = new ProductCatalogManagementApis(apiClientTmf620);	
				
		ProductSpecification ps = apis.getProductSpecification(id, null);
		if (ps != null) {
			System.out.println(ps.getId() + " " + ps.getName() + " " + ps.getLifecycleStatus());
		}
	}
}
