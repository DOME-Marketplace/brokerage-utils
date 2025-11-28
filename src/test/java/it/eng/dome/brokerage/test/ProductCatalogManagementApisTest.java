package it.eng.dome.brokerage.test;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

import it.eng.dome.brokerage.api.ProductCatalogManagementApis;
import it.eng.dome.brokerage.api.fetch.FetchUtils;
import it.eng.dome.tmforum.tmf620.v4.ApiClient;
import it.eng.dome.tmforum.tmf620.v4.ApiException;
import it.eng.dome.tmforum.tmf620.v4.Configuration;
import it.eng.dome.tmforum.tmf620.v4.model.Catalog;
import it.eng.dome.tmforum.tmf620.v4.model.CatalogCreate;
import it.eng.dome.tmforum.tmf620.v4.model.CatalogUpdate;
import it.eng.dome.tmforum.tmf620.v4.model.Category;
import it.eng.dome.tmforum.tmf620.v4.model.CategoryCreate;
import it.eng.dome.tmforum.tmf620.v4.model.CategoryRef;
import it.eng.dome.tmforum.tmf620.v4.model.CategoryUpdate;
import it.eng.dome.tmforum.tmf620.v4.model.PricingLogicAlgorithm;
import it.eng.dome.tmforum.tmf620.v4.model.ProductOffering;
import it.eng.dome.tmforum.tmf620.v4.model.ProductOfferingCreate;
import it.eng.dome.tmforum.tmf620.v4.model.ProductOfferingPrice;
import it.eng.dome.tmforum.tmf620.v4.model.ProductOfferingPriceCreate;
import it.eng.dome.tmforum.tmf620.v4.model.ProductOfferingPriceUpdate;
import it.eng.dome.tmforum.tmf620.v4.model.ProductOfferingUpdate;
import it.eng.dome.tmforum.tmf620.v4.model.ProductSpecification;
import it.eng.dome.tmforum.tmf620.v4.model.RelatedParty;
import it.eng.dome.tmforum.tmf620.v4.model.TimePeriod;

public class ProductCatalogManagementApisTest {

	final static String tmf620ProductCatalogPath = "tmf-api/productCatalogManagement/v4";
	final static String tmfEndpoint = "https://dome-dev.eng.it";//"https://an-dhub-sbx.dome-project.eu"; /*"https://dome-dev.eng.it"; */ //"https://tmf.dome-marketplace-sbx.org";
	
	final static String SCHEMA = "https://raw.githubusercontent.com/pasquy73/test-workflow/refs/heads/test_related/AppliedCustomerBillRate.schema.json";
	
	@Test
	public void RunTest() {
		
		/**
		 * ProductOffering
		 */
//		TestCreateProductOffering();
//		TestGetAllProductOffering();
//		TestGetAllProductOfferingFetchByBatch();
//=>		TestGetAllProductOfferingStream();
//		TestGetFilteredProductOffering();		
//		String id = "urn:ngsi-ld:product-offering:769f160f-0185-4f6c-9065-0e34d71d9903";
//		TestGetProductOffering(id);		
//		TestUpdateProductOffering(id);	
		
		/**
		 * ProductOfferingPrice
		 */
//		TestCreateProductOfferingPrice();
//		TestGetAllProductOfferingPrice();
//		String id = "urn:ngsi-ld:product-offering-price:1c83c61d-bc44-4398-9e26-d78b11114caf";
//		TestUpdateProductOfferingPrice(id);
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
//		TestCreateCategory();
//		TestGetAllCategories();
//		String id = "urn:ngsi-ld:category:9ce36b6a-36a0-49f9-8129-b88b9757c201";
//		TestUpdateCategory(id);
//		TestGetCategory(id);
		
		
		/**
		 * Catalog
		 */
//		TestGetAllCatalogs();
//		TestCreateCatalog();
//		String id = "urn:ngsi-ld:catalog:0374cbca-553e-450b-af7b-efbedd322eb9";
//		TestUpdateCatalog(id);
//		TestGetCatalog(id);
	}
	
	
	protected static String TestCreateProductOfferingPrice() {

		ApiClient apiClientTmf620 = Configuration.getDefaultApiClient();
		apiClientTmf620.setBasePath(tmfEndpoint + "/" + tmf620ProductCatalogPath);

		ProductCatalogManagementApis apis = new ProductCatalogManagementApis(apiClientTmf620);

		String id = null;
		
		ProductOfferingPriceCreate popc = new ProductOfferingPriceCreate();
		popc.setName("Product Offering Price for testing");
		
		RelatedParty rp = new RelatedParty();
		rp.setAtReferredType("Organization");
		rp.setHref(URI.create("urn:ngsi-ld:organization:659798bd-9933-47ac-9164-3e30443f6b59"));
		rp.setId("urn:ngsi-ld:organization:659798bd-9933-47ac-9164-3e30443f6b59");
		rp.setName("VATIT-12622480155");
		rp.setRole("Buyer");
		
		List<RelatedParty> parties = new ArrayList<RelatedParty>();
		parties.add(rp);
		
		rp = new RelatedParty();
		rp.setAtReferredType("Organization");
		rp.setHref(URI.create("urn:ngsi-ld:organization:552e270a-453c-4acf-8402-fda8bdc7da72"));
		rp.setId("urn:ngsi-ld:organization:552e270a-453c-4acf-8402-fda8bdc7da72");
		rp.setName("VATIT-74665900000");
		rp.setRole("Seller");
		parties.add(rp);
		
		popc.setRelatedParty(parties);
		
		popc.setAtSchemaLocation(URI.create(SCHEMA));
		
		try {
			id = apis.createProductOfferingPrice(popc);
		} catch (ApiException e) {
			System.err.println("Error: " + e.getMessage());
		}

		System.out.println("ProductOfferingPrice id: " + id);
		return id;
	}
	
	protected static void TestUpdateProductOfferingPrice(String id) {

		ApiClient apiClientTmf620 = Configuration.getDefaultApiClient();
		apiClientTmf620.setBasePath(tmfEndpoint + "/" + tmf620ProductCatalogPath);

		ProductCatalogManagementApis apis = new ProductCatalogManagementApis(apiClientTmf620);
		
		ProductOfferingPriceUpdate popu = new ProductOfferingPriceUpdate();
		popu.description("Updating description");
		
		try {
			apis.updateProductOfferingPrice(id, popu);
		} catch (ApiException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	protected static String TestCreateProductOffering() {
		
		ApiClient apiClientTmf620 = Configuration.getDefaultApiClient();
		apiClientTmf620.setBasePath(tmfEndpoint + "/" + tmf620ProductCatalogPath);
		
		ProductCatalogManagementApis apis = new ProductCatalogManagementApis(apiClientTmf620);
		
		ProductOfferingCreate poc = new ProductOfferingCreate();
		poc.setName("Final Product Offering ");
		poc.setDescription("Use Case: Product Offering for testing with ExternallyBilled.schema.json");
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
		rp.setHref(URI.create("urn:ngsi-ld:organization:e4fa0e9f-1779-49c0-9e8a-66a02bf1fe4e"));
		rp.setId("urn:ngsi-ld:organization:e4fa0e9f-1779-49c0-9e8a-66a02bf1fe4e");
		rp.setName("VATES-B87798617");
		rp.setRole("Buyer");
		
		List<RelatedParty> parties = new ArrayList<RelatedParty>();
		parties.add(rp);
		
		rp = new RelatedParty();
		rp.setAtReferredType("Organization");
		rp.setHref(URI.create("urn:ngsi-ld:organization:eb6647da-84f2-4645-8d9f-c2905775b561"));
		rp.setId("urn:ngsi-ld:organization:eb6647da-84f2-4645-8d9f-c2905775b561");
		rp.setName("did:elsi:VATIT-12622480155");
		rp.setRole("Seller");
		//parties.add(rp);
		
		//poc.setLastUpdate(OffsetDateTime.now());
		poc.setRelatedParty(parties);
		//String schemaLocation = "https://raw.githubusercontent.com/DOME-Marketplace/tmf-api/refs/heads/main/DOME/ShareableEntity.schema.json"; //OK
		//String schemaLocation = "https://raw.githubusercontent.com/Sh3rd3n/SchemaValidationTest/refs/heads/main/ProductOfferingExtension.schema.json"; //OK creazione -> no array
//		String schemaLocation = "https://raw.githubusercontent.com/pasquy73/test-workflow/refs/heads/test_related/ProductOfferingExtensionTest.schema.json"; //OK
		String schemaLocation = "https://raw.githubusercontent.com/pasquy73/test-workflow/refs/heads/test_related/ProductOfferingTestAll.schema.json"; //OK
//		String schemaLocation = "https://raw.githubusercontent.com/DOME-Marketplace/tmf-api/refs/heads/main/DOME/ExternallyBilled.schema.json"; // OK creazione -> GET OK pla, KO relatedParty (only one)
		poc.setAtSchemaLocation(URI.create(schemaLocation));
		
		List<PricingLogicAlgorithm> plaList = new ArrayList<PricingLogicAlgorithm>();
		PricingLogicAlgorithm pla = new PricingLogicAlgorithm();
		pla.setId("1234567890");
		pla.setName("NO set LastUpdate");
		pla.setPlaSpecId("pla-ID");
		plaList.add(pla);
		poc.setPricingLogicAlgorithm(plaList);
		
		String id = null;
		try {
			System.out.println(poc.toJson());
			id = apis.createProductOffering(poc);
		} catch (ApiException e) {
			System.err.println("Error: " + e.getMessage());
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
			1,
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
	
	
	protected static void TestGetAllProductOfferingStream() {

		ApiClient apiClientTmf620 = Configuration.getDefaultApiClient();
		apiClientTmf620.setBasePath(tmfEndpoint + "/" + tmf620ProductCatalogPath);

		ProductCatalogManagementApis apis = new ProductCatalogManagementApis(apiClientTmf620);		
		AtomicInteger count = new AtomicInteger(0);
		
		FetchUtils.streamAll(
		        apis::listProductOfferings,				// method reference
		        null,                       			// fields
		        null, 									// filter 	
		        150                         			// pageSize
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
	
	protected static void TestUpdateProductOffering(String id) {

		ApiClient apiClientTmf620 = Configuration.getDefaultApiClient();
		apiClientTmf620.setBasePath(tmfEndpoint + "/" + tmf620ProductCatalogPath);

		ProductCatalogManagementApis apis = new ProductCatalogManagementApis(apiClientTmf620);
		
		ProductOfferingUpdate pou = new ProductOfferingUpdate();
		//pou.setLifecycleStatus("Launched");
		pou.setName("Test to check lastUpdate attribute");
//		pou.setAtSchemaLocation(URI.create("https://raw.githubusercontent.com/DOME-Marketplace/tmf-api/refs/heads/main/DOME/ShareableEntity.schema.json"));
		
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
				System.out.println(po.getId() + " " + po.getName() + " >> " + po.getLastUpdate());
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
		String fields = "name,version,lastUpdate";
				
		try {
			ProductOfferingPrice pos = apis.getProductOfferingPrice(id, fields);
			if (pos != null) {
				System.out.println(pos.getId() + " " + pos.getName() + " " + pos.getLastUpdate());
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
	
	
	protected static void TestCreateCategory() {

		ApiClient apiClientTmf620 = Configuration.getDefaultApiClient();
		apiClientTmf620.setBasePath(tmfEndpoint + "/" + tmf620ProductCatalogPath);

		ProductCatalogManagementApis apis = new ProductCatalogManagementApis(apiClientTmf620);	
		
		CategoryCreate cc = new CategoryCreate();
		cc.setName("New Category Test");
		
		String id = null;
		
		try {
			id = apis.createCategory(cc);
		} catch (ApiException e) {
			System.err.println("Error: " + e.getMessage());
		}
		System.out.println("Catalog id: " + id);
	}
	
	
	protected static void TestUpdateCategory(String id) {

		ApiClient apiClientTmf620 = Configuration.getDefaultApiClient();
		apiClientTmf620.setBasePath(tmfEndpoint + "/" + tmf620ProductCatalogPath);

		ProductCatalogManagementApis apis = new ProductCatalogManagementApis(apiClientTmf620);
	
		CategoryUpdate cu = new CategoryUpdate();
		cu.description("Updating category description");
		//cu.setAtSchemaLocation(URI.create(SCHEMA));
		
		try {
			apis.updateCategory(id, cu);
		} catch (ApiException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	protected static void TestGetCategory(String id) {

		ApiClient apiClientTmf620 = Configuration.getDefaultApiClient();
		apiClientTmf620.setBasePath(tmfEndpoint + "/" + tmf620ProductCatalogPath);

		ProductCatalogManagementApis apis = new ProductCatalogManagementApis(apiClientTmf620);	
				
		try {
			Category c = apis.getCategory(id, null);
			if (c != null) {
				System.out.println(c.getId() + " " + c.getName() + " " + c.getLastUpdate());
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
			System.out.println(count + " " + c.getId() + " → " + c.getName() + " / " + c.getLastUpdate());
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
				System.out.println(c.getId() + " " + c.getName() + " " + c.getLastUpdate());
			}
		} catch (ApiException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	
	protected static void TestCreateCatalog() {

		ApiClient apiClientTmf620 = Configuration.getDefaultApiClient();
		apiClientTmf620.setBasePath(tmfEndpoint + "/" + tmf620ProductCatalogPath);

		ProductCatalogManagementApis apis = new ProductCatalogManagementApis(apiClientTmf620);	
				
		CatalogCreate cc = new CatalogCreate();
		cc.setName("Test MyCatalog");
		cc.setDescription("Test lastUpdate");
		cc.setLifecycleStatus("Launched");

		RelatedParty rp = new RelatedParty();
		rp.setAtReferredType("Organization");
		rp.setHref(URI.create("urn:ngsi-ld:organization:659798bd-9933-47ac-9164-3e30443f6b59"));
		rp.setId("urn:ngsi-ld:organization:659798bd-9933-47ac-9164-3e30443f6b59");
		rp.setName("VATIT-12622480155");
		rp.setRole("Buyer");
		
		List<RelatedParty> parties = new ArrayList<RelatedParty>();
		parties.add(rp);
		
		rp = new RelatedParty();
		rp.setAtReferredType("Organization");
		rp.setHref(URI.create("urn:ngsi-ld:organization:552e270a-453c-4acf-8402-fda8bdc7da72"));
		rp.setId("urn:ngsi-ld:organization:552e270a-453c-4acf-8402-fda8bdc7da72");
		rp.setName("VATIT-74665900000");
		rp.setRole("Seller");
		parties.add(rp);
		
		cc.setRelatedParty(parties);
		
		CategoryRef cf = new CategoryRef();
		cf.setId("urn:ngsi-ld:category:517dc7d2-3cb6-4fc7-b722-0efef846278e");
		List<CategoryRef> list = new ArrayList<CategoryRef>();
		list.add(cf);
		cc.setCategory(list);
		
		String id = null;
		try {
			//System.out.println(cc.toJson());
			id = apis.createCatalog(cc);
		} catch (ApiException e) {
			System.err.println("Error: " + e.getMessage());
		}
		System.out.println("Catalog id: " + id);
	}
	
	protected static void TestUpdateCatalog(String id) {

		ApiClient apiClientTmf620 = Configuration.getDefaultApiClient();
		apiClientTmf620.setBasePath(tmfEndpoint + "/" + tmf620ProductCatalogPath);

		ProductCatalogManagementApis apis = new ProductCatalogManagementApis(apiClientTmf620);
	
		CatalogUpdate cu = new CatalogUpdate();
		cu.description("Updating description");
		cu.setName("Test checking the lastUpdate field");
		
		try {
			apis.updateCatalog(id, cu);
		} catch (ApiException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
}
