package it.eng.dome.brokerage.test;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

import it.eng.dome.brokerage.api.ResourceCatalogManagementApis;
import it.eng.dome.brokerage.api.fetch.FetchUtils;
import it.eng.dome.tmforum.tmf634.v4.ApiClient;
import it.eng.dome.tmforum.tmf634.v4.Configuration;
import it.eng.dome.tmforum.tmf634.v4.model.ResourceSpecification;


public class ResourceCatalogManagementApisTest {

	final static String tmf634ResourceCatalogPath = "tmf-api/resourceCatalog/v4";
	final static String tmfEndpoint = "https://dome-dev.eng.it";

	@Test
	public void RunTest() {
		
		/**
		 * Get All ResourceSpecification
		 */
//		TestGetAllResourceSpecification();
		
		/**
		 * Get ResourceSpecification Filtered
		 */
//		TestGetAllResourceSpecificationFiltered();
		

		/**
		 * Get ResourceSpecification by ID
		 */
//		String id = "urn:ngsi-ld:resource-specification:df2ab428-60ce-459c-ac68-40ef2b8f1734";
//		TestGetResourceSpecification(id);

	}

	protected static void TestGetAllResourceSpecification() {

		ApiClient apiClientTmf634 = Configuration.getDefaultApiClient();
		apiClientTmf634.setBasePath(tmfEndpoint + "/" + tmf634ResourceCatalogPath);
		
		ResourceCatalogManagementApis apis = new ResourceCatalogManagementApis(apiClientTmf634);
		AtomicInteger count = new AtomicInteger(0);
		
		FetchUtils.streamAll(
	        apis::listResourceSpecifications,	// method reference
	        null,                       		// fields
	        null, 				    			// filter
	        100                         		// pageSize
		) 
		.forEach(rs -> { 
			count.incrementAndGet();
			System.out.println(count + " " + rs.getId() + " → " + rs.getName() + " / " + rs.getLifecycleStatus());
			}
		);		
		
		System.out.println("ResourceSpecification found: " + count);
	}
	
	
	protected static void TestGetAllResourceSpecificationFiltered() {

		ApiClient apiClientTmf634 = Configuration.getDefaultApiClient();
		apiClientTmf634.setBasePath(tmfEndpoint + "/" + tmf634ResourceCatalogPath);
		
		ResourceCatalogManagementApis apis = new ResourceCatalogManagementApis(apiClientTmf634);
		AtomicInteger count = new AtomicInteger(0);
		
		FetchUtils.streamAll(
	        apis::listResourceSpecifications,		// method reference
	        null,                       			// fields
	        Map.of("lifecycleStatus", "Obsolete"), 	// filter
	        100                         			// pageSize
		) 
		.forEach(rs -> { 
			count.incrementAndGet();
			System.out.println(count + " " + rs.getId() + " → " + rs.getName() + " / " + rs.getLifecycleStatus());
			}
		);		
		
		System.out.println("ResourceSpecification found: " + count);
	}
	
	protected static void TestGetResourceSpecification(String id) {

		ApiClient apiClientTmf634 = Configuration.getDefaultApiClient();
		apiClientTmf634.setBasePath(tmfEndpoint + "/" + tmf634ResourceCatalogPath);

		ResourceCatalogManagementApis apis = new ResourceCatalogManagementApis(apiClientTmf634);
				
		try {
			ResourceSpecification rs = apis.getResourceSpecification(id, null);
			if (rs != null) {
				System.out.println(rs.getId() + " " + rs.getName() + " " + rs.getLifecycleStatus());
			}
	
			rs = apis.getResourceSpecification(id, null);
		} catch (it.eng.dome.tmforum.tmf634.v4.ApiException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
}
