package it.eng.dome.brokerage.api;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.dome.brokerage.api.page.Page;
import it.eng.dome.tmforum.tmf637.v4.ApiClient;
import it.eng.dome.tmforum.tmf637.v4.ApiException;
import it.eng.dome.tmforum.tmf637.v4.api.ProductApi;
import it.eng.dome.tmforum.tmf637.v4.model.Product;
import it.eng.dome.tmforum.tmf637.v4.model.ProductCreate;
import it.eng.dome.tmforum.tmf637.v4.model.ProductUpdate;


public class ProductInventoryApis {
	
	private final Logger logger = LoggerFactory.getLogger(ProductInventoryApis.class);
	private ProductApi productApi;
	
	/**
	 * Constructor
	 * @param apiClientTMF637
	 */
	public ProductInventoryApis(ApiClient apiClientTMF637){
		logger.info("Init ProductApis -  apiClientTMF637 basePath: {}", apiClientTMF637.getBasePath());
		productApi = new ProductApi(apiClientTMF637);
	}
	
	
	/**
	 * This method creates a Product
	 * 
	 * @param ProductCreate - ProductCreate object used in the creation request of the Product (required) 
	 * @return productId
	 */
	public String createProduct(ProductCreate productCreate) {		
		try {
			
			Product product = productApi.createProduct(productCreate);
			logger.info("Product saved successfully with id: {}", product.getId());
			return product.getId();
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
		logger.info("Request: getProduct by id {}", productId);
		
		try {
			if (fields != null) {
				logger.debug("Selected attributes: [{}]", fields);
			}
			
			return productApi.retrieveProduct(productId, fields);
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
	
	
	/**
	 * This method updates the Product by ID
	 * 
	 * @param productId - Identifier of the Product (required) 
	 * @param productUpdate - ProductUpdate object used to update the Product (required) 
	 * @return boolean
	 */
	public boolean updateProduct(String productId, ProductUpdate productUpdate) {
		logger.info("Request: updateProduct by id {}", productId);
		
		try {
			Product product = productApi.patchProduct(productId, productUpdate);
			logger.info("Update successfully Product with id: {}", product.getId());
			return true;
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return false;
		}
	}
	
	
	/**
	 * This method retrieves a paginated list of Product
	 * 
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'name,description')<br>
	 * - use fields == null to get all attributes		
     * @param offset - the index of the first item to return (used for pagination)
     * @param limit - the maximum number of items to return
	 * @param filter - HashMap<K,V> to set query string params (optional)<br>  
	 * @return a {@link Page} containing a subset of Product
	 */
	public Page<Product> listProducts(String fields, int offset, int limit, Map<String, String> filter) {
		logger.info("Request: listAgreements");
		
		try {
			
			if (filter != null && !filter.isEmpty()) {
				logger.debug("Params used in the query-string filter: {}", filter);
			}
			if (fields != null) {
				logger.debug("Selected attributes: [{}]", fields);
			}
			
			List<Product> items = productApi.listProduct(fields, offset, limit, filter);
			boolean hasNext = items.size() == limit;
			
			return new Page<>(items, offset, limit, hasNext);
			
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}   
	}
	
}