package it.eng.dome.brokerage.test;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import it.eng.dome.brokerage.api.ProductCatalogManagementApis;
import it.eng.dome.brokerage.api.fetch.FetchUtils;
import it.eng.dome.tmforum.tmf620.v4.ApiClient;
import it.eng.dome.tmforum.tmf620.v4.ApiException;
import it.eng.dome.tmforum.tmf620.v4.Configuration;
import it.eng.dome.tmforum.tmf620.v4.model.Catalog;
import it.eng.dome.tmforum.tmf620.v4.model.Category;
import it.eng.dome.tmforum.tmf620.v4.model.ProductOffering;
import it.eng.dome.tmforum.tmf620.v4.model.ProductOfferingCreate;
import it.eng.dome.tmforum.tmf620.v4.model.ProductOfferingPrice;
import it.eng.dome.tmforum.tmf620.v4.model.ProductOfferingUpdate;
import it.eng.dome.tmforum.tmf620.v4.model.ProductSpecification;
import it.eng.dome.tmforum.tmf620.v4.model.RelatedParty;
import it.eng.dome.tmforum.tmf620.v4.model.TimePeriod;

public class ProductCatalogManagementApisTest {

	final static String tmf620ProductCatalogPath = "tmf-api/productCatalogManagement/v4";
	final static String tmfEndpoint = /*"https://dome-dev.eng.it"; */ "https://tmf.dome-marketplace-sbx.org";
	

	public static void main(String[] args) {
		
		
		/**
		 * ProductOffering
		 */
//		TestCreateProductOffering();
		TestGetAllProductOffering();
//		TestGetAllProductOfferingFetchByBatch();
//		TestGetFilteredProductOffering();		
//		String id = "urn:ngsi-ld:product-offering:ac2d9699-2b55-402d-8028-c229b9683dba";
//		TestGetProductOffering(id);		
//		TestUpdateProductOffering(id);
		
		/**
		 * ProductOfferingPrice
		 */
//		TestGetAllProductOfferingPrice();
//		String id = "urn:ngsi-ld:product-offering-price:356144e6-03de-4a80-9d37-953d0f4ec83c";
//		TestGetProductOfferingPrice(id);
		
		
		/**
		 * ProductSpecification
		 */
//		TestGetAllProductSpecification();
//		String id = "urn:ngsi-ld:product-specification:538b1e8f-12bd-4c7c-a18e-62792bf3e0bc";
//		TestGetProductSpecification(id);

		
		/**
		 * Category
		 */
//		TestGetAllCategories();
//		String id = "urn:ngsi-ld:category:ace3054c-a354-44e8-b8e3-b47966211cc7";
//		TestGetCategory(id);
		
		
		/**
		 * Catalog
		 */
//		TestGetAllCatalogs();
//		String id = "urn:ngsi-ld:catalog:1bc0f7e6-fc2f-4455-8868-d0c6e7f1a30e";
//		TestGetCatalog(id);
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
		
		RelatedParty rp = new RelatedParty();
		rp.setAtReferredType("Organization");
		rp.setHref(URI.create("urn:ngsi-ld:organization:eb6647da-84f2-4645-8d9f-c2905775b561"));
		rp.setId("urn:ngsi-ld:organization:eb6647da-84f2-4645-8d9f-c2905775b561");
		rp.setName("did:elsi:VATIT-12622480155");
		rp.setRole("Seller");
		
		List<RelatedParty> parties = new ArrayList<RelatedParty>();
		parties.add(rp);
		poc.setRelatedParty(parties);
		String schemaLocation = "https://raw.githubusercontent.com/DOME-Marketplace/tmf-api/refs/heads/main/DOME/ShareableEntity.schema.json"; //OK
		//String schemaLocation = "https://raw.githubusercontent.com/Sh3rd3n/SchemaValidationTest/refs/heads/main/ProductOfferingExtension.schema.json"; //OK creazione -> no array
		//String schemaLocation = "https://raw.githubusercontent.com/pasquy73/test-workflow/refs/heads/test_related/ProductOfferingExtensionTest.schema.json"; //OK
		poc.setAtSchemaLocation(URI.create(schemaLocation));
		
		String id = null;
		try {
			System.out.println(poc.toJson());
			id = apis.createProductOffering(poc);
		} catch (ApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	        10                         	// pageSize
		) 
		.forEach(po -> { 
			count.incrementAndGet();
			System.out.print(count + " " + po.getId() + " → " + po.getName() + " / " + po.getLifecycleStatus() + " / ");

			if (po.getRelatedParty() != null) {
				System.out.println(po.getRelatedParty().get(0).getName());
			}else {
				System.out.println("no relatedParty");
			}

		}
		);		
		
		System.out.println("ProductOffering found: " + count);
	}
	
	protected static void TestGetAllProductOfferingFetchByBatch() {

		ApiClient apiClientTmf620 = Configuration.getDefaultApiClient();
		apiClientTmf620.setBasePath(tmfEndpoint + "/" + tmf620ProductCatalogPath);

		ProductCatalogManagementApis apis = new ProductCatalogManagementApis(apiClientTmf620);		
		AtomicInteger count = new AtomicInteger(0);
		
		FetchUtils.fetchByBatch(
			apis::listProductOfferings, 
			null, 
			null, 
			10,
			batch -> {
			    batch.forEach(po -> {
			    	count.incrementAndGet();
			    	System.out.print(count + " " + po.getId() + " → " + po.getName() + " / " + po.getLifecycleStatus() + " / ");

					if (po.getRelatedParty() != null) {
						System.out.println(po.getRelatedParty().get(0).getName());
					}else {
						System.out.println("no relatedParty");
					}

			    });
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
	
	protected static void TestUpdateProductOffering(String id) {

		ApiClient apiClientTmf620 = Configuration.getDefaultApiClient();
		apiClientTmf620.setBasePath(tmfEndpoint + "/" + tmf620ProductCatalogPath);

		ProductCatalogManagementApis apis = new ProductCatalogManagementApis(apiClientTmf620);
		
		ProductOfferingUpdate pou = new ProductOfferingUpdate();
		pou.setLifecycleStatus("Launched");
		
		try {
			apis.updateProductOffering(id, pou);
		} catch (ApiException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	protected static void TestGetProductOffering(String id) {

		ApiClient apiClientTmf620 = Configuration.getDefaultApiClient();
		apiClientTmf620.setBasePath(tmfEndpoint + "/" + tmf620ProductCatalogPath);

		ProductCatalogManagementApis apis = new ProductCatalogManagementApis(apiClientTmf620);
		
		try {
			ProductOffering po = apis.getProductOffering(id, null);
			if (po != null) {
				System.out.println(po.getId() + " " + po.getName() + " " + po.getRelatedParty().size());
			}
		} catch (ApiException e) {
			System.err.println("Error: " + e.getMessage());
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
	        10                         		// pageSize
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
				
		try {
			ProductOfferingPrice pos = apis.getProductOfferingPrice(id, fields);
			if (pos != null) {
				System.out.println(pos.getId() + " " + pos.getName() + " " + pos.getPriceType());
			}
		} catch (ApiException e) {
			System.err.println("Error: " + e.getMessage());
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
				
		try {
			ProductSpecification ps = apis.getProductSpecification(id, null);
			if (ps != null) {
				System.out.println(ps.getId() + " " + ps.getName() + " " + ps.getLifecycleStatus());
			}
		} catch (ApiException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	
	protected static void TestGetAllCategories() {

		ApiClient apiClientTmf620 = Configuration.getDefaultApiClient();
		apiClientTmf620.setBasePath(tmfEndpoint + "/" + tmf620ProductCatalogPath);

		ProductCatalogManagementApis apis = new ProductCatalogManagementApis(apiClientTmf620);		
		AtomicInteger count = new AtomicInteger(0);
		
		FetchUtils.streamAll(
	        apis::listCategories,			// method reference
	        null,                       	// fields
	        null, 				    		// filter
	        100                         	// pageSize
		) 
		.forEach(c -> { 
			count.incrementAndGet();
			System.out.println(count + " " + c.getId() + " → " + c.getName() + " / " + c.getLifecycleStatus());
			}
		);		
		
		System.out.println("Categories found: " + count);
	}
	
	
	protected static void TestGetCategory(String id) {

		ApiClient apiClientTmf620 = Configuration.getDefaultApiClient();
		apiClientTmf620.setBasePath(tmfEndpoint + "/" + tmf620ProductCatalogPath);

		ProductCatalogManagementApis apis = new ProductCatalogManagementApis(apiClientTmf620);	
				
		try {
			Category c = apis.getCategory(id, null);
			if (c != null) {
				System.out.println(c.getId() + " " + c.getName() + " " + c.getLifecycleStatus());
			}
		} catch (ApiException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	
	protected static void TestGetAllCatalogs() {

		ApiClient apiClientTmf620 = Configuration.getDefaultApiClient();
		apiClientTmf620.setBasePath(tmfEndpoint + "/" + tmf620ProductCatalogPath);

		ProductCatalogManagementApis apis = new ProductCatalogManagementApis(apiClientTmf620);		
		AtomicInteger count = new AtomicInteger(0);
		
		FetchUtils.streamAll(
	        apis::listCatalogs,			// method reference
	        null,                      	// fields
	        null, 				   		// filter
	        100                        	// pageSize
		) 
		.forEach(c -> { 
			count.incrementAndGet();
			System.out.println(count + " " + c.getId() + " → " + c.getName() + " / " + c.getLifecycleStatus());
			if (c.getRelatedParty() != null) {
				System.out.println(c.getRelatedParty().size());
			}else {
				System.out.println("no relatedParty");
			}
		}
		);		
		
		System.out.println("Catlogs found: " + count);
	}
	
	protected static void TestGetCatalog(String id) {

		ApiClient apiClientTmf620 = Configuration.getDefaultApiClient();
		apiClientTmf620.setBasePath(tmfEndpoint + "/" + tmf620ProductCatalogPath);

		ProductCatalogManagementApis apis = new ProductCatalogManagementApis(apiClientTmf620);	
				
		try {
			Catalog c = apis.getCatalog(id, null);
			if (c != null) {
				System.out.println(c.getId() + " " + c.getName() + " " + c.getLifecycleStatus());
			}
		} catch (ApiException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
}
