package it.eng.dome.brokerage.test;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("isBundle", "false");
		String mydate = "2025-04-22T15:18:52.065849400Z";
		OffsetDateTime dateRef = OffsetDateTime.parse(mydate);
		filter.put("startDate.lt", mydate); // gt, lt, eq OK
		
		List<Product> products = apis.getAllProducts(null, null);

		int count = 0;
		
		for (Product product : products) {
			System.out.println(++count + " => " + product.getId() + " / " + product.getName());
			if (dateRef.isBefore(product.getStartDate())) {
				System.out.println(dateRef  + " is before " + product.getStartDate());
			} else {
				System.out.println(dateRef  + " is after " + product.getStartDate());
			}
		}
	}
}
