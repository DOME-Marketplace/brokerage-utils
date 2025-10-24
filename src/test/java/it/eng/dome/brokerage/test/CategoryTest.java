package it.eng.dome.brokerage.test;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import it.eng.dome.brokerage.api.ProductCatalogManagementApis;
import it.eng.dome.brokerage.api.fetch.FetchUtils;
import it.eng.dome.tmforum.tmf620.v4.ApiClient;
import it.eng.dome.tmforum.tmf620.v4.Configuration;
import it.eng.dome.tmforum.tmf620.v4.model.Category;

public class CategoryTest {

	final static String tmf620ProductOfferingPath = "tmf-api/productCatalogManagement/v4";
	final static String tmfEndpoint = "https://dome-dev.eng.it";

	public static void main(String[] args) {

		/**
		 * Get All Categories
		 */
//		TestGetAllCategoriesToList();
		
		
//		TestGetAllCategoriesStreamAll();
//		TestGetAllCategoriesFetchByBatch();		
		TestGetAllCategoriesFetchAll();
	}

	protected static void TestGetAllCategoriesStreamAll() {

		ApiClient apiClientTmf620 = Configuration.getDefaultApiClient();
		apiClientTmf620.setBasePath(tmfEndpoint + "/" + tmf620ProductOfferingPath);

		ProductCatalogManagementApis apis = new ProductCatalogManagementApis(apiClientTmf620);
		AtomicInteger count = new AtomicInteger(0);
		
		FetchUtils.streamAll(
	        apis::listCategories, 		// method reference
	        null,                       // fields
	        null, 				    	// filter
	        50                         	// pageSize
		).forEach(c -> { 
			count.incrementAndGet();
			System.out.println(count + " " + c.getId() + " → " + c.getName() + " / " + c.getLifecycleStatus());
			}
		);

		System.out.println("Categories found: " + count);
	}
	
	
	protected static void TestGetAllCategoriesFetchByBatch() {

		ApiClient apiClientTmf620 = Configuration.getDefaultApiClient();
		apiClientTmf620.setBasePath(tmfEndpoint + "/" + tmf620ProductOfferingPath);

		ProductCatalogManagementApis apis = new ProductCatalogManagementApis(apiClientTmf620);
		AtomicInteger count = new AtomicInteger(0);
		
		FetchUtils.fetchByBatch(
	        apis::listCategories, 		// method reference
	        null,                       // fields
	        null, 				    	// filter
	        50,                        	// pageSize
	        batch -> {            		// consumer batch
	            batch.forEach(c -> {
	            	count.incrementAndGet();
	                System.out.println(count + " " + c.getId() + " → " + c.getName() + " / " + c.getLifecycleStatus());
	            });
	        });

		System.out.println("Categories found: " + count);
	}
	
	
	protected static void TestGetAllCategoriesFetchAll() {

		ApiClient apiClientTmf620 = Configuration.getDefaultApiClient();
		apiClientTmf620.setBasePath(tmfEndpoint + "/" + tmf620ProductOfferingPath);

		ProductCatalogManagementApis apis = new ProductCatalogManagementApis(apiClientTmf620);
		AtomicInteger count = new AtomicInteger(0);
		
		List<Category> allCategories = FetchUtils.fetchAll(
	        apis::listCategories, 		// method reference
	        null,                       // fields
	        null, 				    	// filter
	        50                        	// pageSize
	    );

		allCategories.forEach(c -> {
			count.incrementAndGet();
		    System.out.println(count + " " + c.getId() + " → " + c.getName() + " / " + c.getDescription());
		});
		
		System.out.println("Categories found: " + count);
	}
	
	protected static void TestGetAllCategoriesToList() {

		ApiClient apiClientTmf620 = Configuration.getDefaultApiClient();
		apiClientTmf620.setBasePath(tmfEndpoint + "/" + tmf620ProductOfferingPath);

		ProductCatalogManagementApis apis = new ProductCatalogManagementApis(apiClientTmf620);
		
		List<Category> categories = FetchUtils.streamAll(
	        apis::listCategories, 	// method reference
	        null,                       // fields
	        null, 				    // filter
	        100                         // pageSize
		).toList(); 

		int count = 0;
		for (Category category : categories) {
			System.out.println(++count + " => " + category.getId() + " " + category.getName() + " "
					+ category.getLifecycleStatus());
		}
		System.out.println("Categories found: " + count);
	}

}
