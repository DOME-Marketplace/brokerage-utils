package it.eng.dome.brokerage.test;

import java.util.List;

import it.eng.dome.brokerage.api.ResourceSpecificationApis;
import it.eng.dome.tmforum.tmf634.v4.ApiClient;
import it.eng.dome.tmforum.tmf634.v4.Configuration;
import it.eng.dome.tmforum.tmf634.v4.model.ResourceSpecification;


public class ResourceSpecificationTest {

	final static String tmf634resourceCatalogPath = "tmf-api/resourceCatalog/v4";
	final static String tmfEndpoint = "https://dome-dev.eng.it";

	public static void main(String[] args) {
		
		/**
		 * Get All ResourceSpecification
		 */
		TestGetAllResourceSpecification();
		

		/**
		 * Get ResourceSpecification by ID
		 */
		String id = "urn:ngsi-ld:resource-specification:df2ab428-60ce-459c-ac68-40ef2b8f1734";
		TestGetResourceSpecification(id);

	}

	protected static void TestGetAllResourceSpecification() {

		ApiClient apiClientTmf634 = Configuration.getDefaultApiClient();
		apiClientTmf634.setBasePath(tmfEndpoint + "/" + tmf634resourceCatalogPath);

		ResourceSpecificationApis apis = new ResourceSpecificationApis(apiClientTmf634);
		
		List<ResourceSpecification> rss = apis.getAllResourceSpecification(null, null);
		
		int count = 0;
	 	for (ResourceSpecification rs : rss) {
			System.out.println(++count + " => " + rs.getId() + " " + rs.getName() + " " + rs.getLifecycleStatus());
		}
	}
	
	
	protected static void TestGetResourceSpecification(String id) {

		ApiClient apiClientTmf634 = Configuration.getDefaultApiClient();
		apiClientTmf634.setBasePath(tmfEndpoint + "/" + tmf634resourceCatalogPath);

		ResourceSpecificationApis apis = new ResourceSpecificationApis(apiClientTmf634);
				
		ResourceSpecification rs = apis.getResourceSpecification(id, null);
		if (rs != null) {
			System.out.println(rs.getId() + " " + rs.getName() + " " + rs.getLifecycleStatus());
		}
	}
}
