package it.eng.dome.brokerage.test;

import java.util.List;

import it.eng.dome.brokerage.api.CategoryApis;
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
		TestGetAllCategories();

		/**
		 * Get Category by ID
		 */
		String id = "urn:ngsi-ld:category:c3289c15-635b-44a6-90e7-93a6d2591b4f";
		TestGetCategory(id);
	}

	protected static void TestGetAllCategories() {

		ApiClient apiClientTmf620 = Configuration.getDefaultApiClient();
		apiClientTmf620.setBasePath(tmfEndpoint + "/" + tmf620ProductOfferingPath);

		CategoryApis apis = new CategoryApis(apiClientTmf620);

		List<Category> categories = apis.getAllCategory(null, null);

		int count = 0;
		for (Category category : categories) {
			System.out.println(++count + " => " + category.getId() + " " + category.getName() + " "
					+ category.getLifecycleStatus());
		}
		System.out.println("Categories found: " + count);
	}

	protected static void TestGetCategory(String id) {

		ApiClient apiClientTmf620 = Configuration.getDefaultApiClient();
		apiClientTmf620.setBasePath(tmfEndpoint + "/" + tmf620ProductOfferingPath);

		CategoryApis apis = new CategoryApis(apiClientTmf620);

		Category category = apis.getCategory(id, null);

		if (category != null) {
			System.out.println(category.getId() + " " + category.getName() + " " + category.getLifecycleStatus());
		}
	}
}
