package it.eng.dome.brokerage.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.dome.tmforum.tmf637.v4.ApiClient;
import it.eng.dome.tmforum.tmf637.v4.ApiException;
import it.eng.dome.tmforum.tmf637.v4.api.ProductApi;
import it.eng.dome.tmforum.tmf637.v4.model.Product;


public class ProductApis {
	
	private final Logger logger = LoggerFactory.getLogger(ProductApis.class);
	private final int LIMIT = 10;
	
	private ProductApi productInventory;
	
	/**
	 * Constructor
	 * @param apiClientTMF637
	 */
	public ProductApis(ApiClient apiClientTMF637){
		logger.info("Init ProductUtils -  apiClientTMF637 basePath: {}", apiClientTMF637.getBasePath());
		productInventory = new ProductApi(apiClientTMF637);
	}
	
	
	public Product getProduct(String productId) {
		try {
			Product product = productInventory.retrieveProduct(productId, null);
			return product;
		} catch (ApiException e) {
			logger.error("Error: ", e.getMessage());
			return null;
		}
	}
	
	public List<Product> getAllProducts() {
		logger.info("Request: getAllProducts");
		List<Product> all = new ArrayList<Product>();
		getAllProducts(all, 0);
		Collections.reverse(all); //reverse order
		logger.info("Number of Products: {}", all.size());
		return all;
	}
	
	
	private void getAllProducts(List<Product> list, int start) {
		int offset = start * LIMIT;

		try {
			List<Product> appliedList =  productInventory.listProduct(null,  offset, LIMIT);

			if (!appliedList.isEmpty()) {
				Collections.reverse(appliedList); //reverse order
				getAllProducts(list, start + 1);
				list.addAll(appliedList);
			}else {
				return;
			}
		} catch (Exception e) {
			logger.error("Error: {}", e.getMessage());
			return;
		}		
	}

}