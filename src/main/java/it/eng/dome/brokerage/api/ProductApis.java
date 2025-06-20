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
import it.eng.dome.tmforum.tmf637.v4.model.ProductCreate;
import it.eng.dome.tmforum.tmf637.v4.model.ProductUpdate;


public class ProductApis {
	
	private final Logger logger = LoggerFactory.getLogger(ProductApis.class);
	private final int LIMIT = 100;
	
	private ProductApi productInventory;
	
	/**
	 * Constructor
	 * @param apiClientTMF637
	 */
	public ProductApis(ApiClient apiClientTMF637){
		logger.info("Init ProductApis -  apiClientTMF637 basePath: {}", apiClientTMF637.getBasePath());
		productInventory = new ProductApi(apiClientTMF637);
	}
	
	
	/**
	 * This method creates a Product
	 * 
	 * @param ProductCreate - ProductCreate object used in the creation request of the Product (required) 
	 * @return Product
	 */
	public Product createProduct(ProductCreate productCreate) {		
		try {
			return productInventory.createProduct(productCreate);
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
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
	 * This method updates the Product by Id
	 * 
	 * @param productId - Identifier of the Product (required) 
	 * @param productUpdate - ProductUpdate object used to update the Product (required) 
	 * @return boolean
	 */
	public boolean updateProduct(String productId, ProductUpdate productUpdate) {
		logger.info("Request: updateProduct");
		try {
			Product product = productInventory.patchProduct(productId, productUpdate);
			logger.info("Update Product with id: {}", product.getId());
			return true;
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return false;
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
		
		if (filter != null && !filter.isEmpty()) {
			logger.debug("Params used in the query-string filter: {}", filter);
		}
		
		getAllProducts(all, fields, 0, filter);
		logger.info("Number of Products: {}", all.size());
		return all;
	}
	
	
	private void getAllProducts(List<Product> list, String fields, int start, Map<String, String> filter) {
		int offset = start * LIMIT;

		try {
			List<Product> appliedList =  productInventory.listProduct(fields, offset, LIMIT, filter);

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