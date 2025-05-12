package it.eng.dome.brokerage.test;

import java.util.List;

import it.eng.dome.brokerage.api.ProductApis;
import it.eng.dome.tmforum.tmf637.v4.ApiClient;
import it.eng.dome.tmforum.tmf637.v4.Configuration;
import it.eng.dome.tmforum.tmf637.v4.model.Product;

public class ProducTest {

	final static String tmf637ProductInventoryPath = "tmf-api/productInventory/v4";
	final static String tmfEndpoint = "https://<tmforum-endpoint>";

	public static void main(String[] args) {

		TestApis();
	}

	private static void TestApis() {

		ApiClient apiClientTmf637 = Configuration.getDefaultApiClient();
		apiClientTmf637.setBasePath(tmfEndpoint + "/" + tmf637ProductInventoryPath);

		ProductApis apis = new ProductApis(apiClientTmf637);
		List<Product> products = apis.getAllProducts(null);

		System.out.println(products.size());
	}
}
