package it.eng.dome.brokerage.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.dome.tmforum.tmf637.v4.ApiClient;
import it.eng.dome.tmforum.tmf637.v4.ApiException;
import it.eng.dome.tmforum.tmf637.v4.api.ProductApi;
import it.eng.dome.tmforum.tmf637.v4.model.Product;


public class ProductApis {
	
	private final Logger logger = LoggerFactory.getLogger(ProductApis.class);
	private final int LIMIT = 100;
	
	private ProductApi productInventory;
	
	/**
	 * Constructor
	 * @param apiClientTMF637
	 */
	public ProductApis(ApiClient apiClientTMF637){
		logger.info("Init ProductUtils -  apiClientTMF637 basePath: {}", apiClientTMF637.getBasePath());
		productInventory = new ProductApi(apiClientTMF637);
	}
	
	/**
	 * This method retrieves a specific Product by ID
	 * 
	 * @param productId - Identifier of the Product (required)
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'name,periodCoverage')<br> 
	 * - use fields == null to get all attributes
	 * @return Product
	 */
	public Product getProduct(String productId, String fields) {
		try {
			
			return productInventory.retrieveProduct(productId, fields);
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
	
	/**
	 * This method retrieves the list of Product
	 * 
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'name,description')<br> 
	 * - use fields == null to get all attributes
	 * @param filter - HashMap<K,V> to set query string params (optional)<br> 
	 * @return List&lt;Product&gt;
	 */
	public List<Product> getAllProducts(String fields, Map<String, String> filter) {
		logger.info("Request: getAllProducts");
		List<Product> all = new ArrayList<Product>();
		getAllProducts(all, fields, 0, filter);
		logger.info("Number of Products: {}", all.size());
		return all;
	}
	
	
	private void getAllProducts(List<Product> list, String fields, int start, Map<String, String> filter) {
		int offset = start * LIMIT;

		try {
			List<Product> appliedList =  productInventory.listProduct(fields,  offset, LIMIT, filter);

			if (!appliedList.isEmpty()) {
				list.addAll(appliedList);
				getAllProducts(list, fields, start + 1, filter);				
			}else {
				return;
			}
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return;
		}		
	}

}