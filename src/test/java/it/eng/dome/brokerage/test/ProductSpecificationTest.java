package it.eng.dome.brokerage.test;

import java.util.List;

import it.eng.dome.brokerage.api.ProductSpecificationApis;
import it.eng.dome.tmforum.tmf620.v4.ApiClient;
import it.eng.dome.tmforum.tmf620.v4.Configuration;
import it.eng.dome.tmforum.tmf620.v4.model.ProductSpecification;

public class ProductSpecificationTest {

	final static String tmf620ProductOfferingPath = "tmf-api/productCatalogManagement/v4";
	final static String tmfEndpoint = "https://dome-dev.eng.it";

	public static void main(String[] args) {
		
		/**
		 * Get All ProductSpecification
		 */
		// TestGetAllProductSpecification();
		

		/**
		 * Get ProductSpecification by ID
		 */
		String id = "urn:ngsi-ld:product-specification:538b1e8f-12bd-4c7c-a18e-62792bf3e0bc";
		TestGetProductSpecification(id);

	}

	protected static void TestGetAllProductSpecification() {

		ApiClient apiClientTmf620 = Configuration.getDefaultApiClient();
		apiClientTmf620.setBasePath(tmfEndpoint + "/" + tmf620ProductOfferingPath);

		ProductSpecificationApis apis = new ProductSpecificationApis(apiClientTmf620);
		
		List<ProductSpecification> pss = apis.getAllProductSpecification(null, null);
		
		int count = 0;
	 	for (ProductSpecification ps : pss) {
			System.out.println(++count + " => " + ps.getId() + " " + ps.getName() + " " + ps.getLifecycleStatus());
		}
	}
	
	protected static void TestGetProductSpecification(String id) {

		ApiClient apiClientTmf620 = Configuration.getDefaultApiClient();
		apiClientTmf620.setBasePath(tmfEndpoint + "/" + tmf620ProductOfferingPath);

		ProductSpecificationApis apis = new ProductSpecificationApis(apiClientTmf620);
				
		ProductSpecification ps = apis.getProductSpecification(id, null);
		if (ps != null) {
			System.out.println(ps.getId() + " " + ps.getName() + " " + ps.getLifecycleStatus());
		}
	}
}
