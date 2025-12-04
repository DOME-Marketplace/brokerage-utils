package it.eng.dome.brokerage.test;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

import it.eng.dome.brokerage.api.ProductInventoryApis;
import it.eng.dome.brokerage.api.fetch.FetchUtils;
import it.eng.dome.tmforum.tmf637.v4.ApiClient;
import it.eng.dome.tmforum.tmf637.v4.ApiException;
import it.eng.dome.tmforum.tmf637.v4.Configuration;
import it.eng.dome.tmforum.tmf637.v4.model.ProductCreate;
import it.eng.dome.tmforum.tmf637.v4.model.ProductStatusType;
import it.eng.dome.tmforum.tmf637.v4.model.ProductUpdate;


public class ProductInventoryApisTest {

	final static String tmf637ProductInventoryPath = "tmf-api/productInventory/v4";
	final static String tmfEndpoint = "https://dome-dev.eng.it";

	final static String SCHEMA = "https://raw.githubusercontent.com/pasquy73/test-workflow/refs/heads/test_related/AppliedCustomerBillRate.schema.json";
	
	@Test
	public void RunTest() {

		/**
		 * Get All Products
		 */				
//		TestGetProducts();
		

		/**
		 * Get Filtered Products
		 */
//		TestGetFilteredProducts();
		
		
		/**
		 * Create Product
		 */
//		TestCreateProduct();
		
		
		/**
		 * Update Product
		 */
//		String id = "urn:ngsi-ld:product:0e5f87ea-462c-4155-b2d9-27dc78a505f6";
//		TestUpdateProduct(id);
	}
	
	
	protected static void TestGetFilteredProducts() {

		ApiClient apiClientTmf637 = Configuration.getDefaultApiClient();
		apiClientTmf637.setBasePath(tmfEndpoint + "/" + tmf637ProductInventoryPath);

		ProductInventoryApis apis = new ProductInventoryApis(apiClientTmf637);
		AtomicInteger count = new AtomicInteger(0);
		
		String fields = "name,description,status";
		
		FetchUtils.streamAll(
	        apis::listProducts,     	// method reference
	        fields,                     // fields
	        Map.of("status","active"),	// filter
	        100                         // pageSize
		) 
		.forEach(product -> { 
			count.incrementAndGet();
			System.out.println(count + " " + product.getId() + " → " + product.getName() + " / " + product.getStatus());
			}
		);		
		
		System.out.println("Product found: " + count);
	}

	
	protected static void TestGetProducts() {

		ApiClient apiClientTmf637 = Configuration.getDefaultApiClient();
		apiClientTmf637.setBasePath(tmfEndpoint + "/" + tmf637ProductInventoryPath);

		ProductInventoryApis apis = new ProductInventoryApis(apiClientTmf637);
		AtomicInteger count = new AtomicInteger(0);
		
		FetchUtils.streamAll(
	        apis::listProducts,     	// method reference
	        null,                     	// fields
	        null,						// filter
	        100                         // pageSize
		) 
		.forEach(product -> { 
			count.incrementAndGet();
			System.out.println(count + " " + product.getId() + " → " + product.getName() + " / " + product.getLastUpdate());
			}
		);		
		
		System.out.println("Product found: " + count);
	}
	
	
	protected static String TestCreateProduct() {

		ApiClient apiClientTmf637 = Configuration.getDefaultApiClient();
		apiClientTmf637.setBasePath(tmfEndpoint + "/" + tmf637ProductInventoryPath);

		ProductInventoryApis apis = new ProductInventoryApis(apiClientTmf637);
		
		ProductCreate pc = new ProductCreate();
		pc.setDescription("Simple product for testing lastUpdate with schema");
		
		pc.setAtSchemaLocation(URI.create(SCHEMA));
		
		String id = null;
		try {
			id = apis.createProduct(pc);
		} catch (ApiException e) {
			System.err.println("Error: " + e.getMessage());
		}

		System.out.println("Product id: " + id);
		return id;
	}
	
	protected static void TestUpdateProduct(String id) {

		ApiClient apiClientTmf637 = Configuration.getDefaultApiClient();
		apiClientTmf637.setBasePath(tmfEndpoint + "/" + tmf637ProductInventoryPath);

		ProductInventoryApis apis = new ProductInventoryApis(apiClientTmf637);

		
		ProductUpdate pu = new ProductUpdate();
		pu.setName("Update product to test LastUpdate");
		pu.setStatus(ProductStatusType.ABORTED_);
		
		try {
			apis.updateProduct(id, pu);
		} catch (ApiException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
}
