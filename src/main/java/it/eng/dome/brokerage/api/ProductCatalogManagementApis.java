package it.eng.dome.brokerage.api;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.dome.brokerage.api.page.Page;
import it.eng.dome.tmforum.tmf620.v4.ApiClient;
import it.eng.dome.tmforum.tmf620.v4.ApiException;
import it.eng.dome.tmforum.tmf620.v4.api.ProductOfferingApi;
import it.eng.dome.tmforum.tmf620.v4.api.ProductOfferingPriceApi;
import it.eng.dome.tmforum.tmf620.v4.api.ProductSpecificationApi;
import it.eng.dome.tmforum.tmf620.v4.model.ProductOffering;
import it.eng.dome.tmforum.tmf620.v4.model.ProductOfferingCreate;
import it.eng.dome.tmforum.tmf620.v4.model.ProductOfferingPrice;
import it.eng.dome.tmforum.tmf620.v4.model.ProductOfferingUpdate;
import it.eng.dome.tmforum.tmf620.v4.model.ProductSpecification;

public class ProductCatalogManagementApis {
	
	private final Logger logger = LoggerFactory.getLogger(ProductCatalogManagementApis.class);
	private ProductOfferingApi productOfferingApi;
	private ProductOfferingPriceApi productOfferingPriceApi;
	private ProductSpecificationApi productSpecificationApi;
	
	
	/**
	 * Constructor
	 * @param apiClientTMF620
	 */
	public ProductCatalogManagementApis(ApiClient apiClientTMF620){
		logger.info("Init ProductOfferingApis - apiClientTMF620 basePath: {}", apiClientTMF620.getBasePath());
		productOfferingApi = new ProductOfferingApi(apiClientTMF620);
		productOfferingPriceApi = new ProductOfferingPriceApi(apiClientTMF620);
		productSpecificationApi = new ProductSpecificationApi(apiClientTMF620);
	}
	
	
	/**
	 * This method creates an ProductOffering
	 * 
	 * @param ProductOfferingCreate - ProductOfferingCreate object used in the creation request of the ProductOffering (required) 
	 * @return ProductOffering
	 */
	public ProductOffering createProductOffering(ProductOfferingCreate productOfferingCreate) {		
		logger.info("Create: createProductOffering");
		
		try {
			return productOfferingApi.createProductOffering(productOfferingCreate);
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
	
	
	/**
	 * This method updates the ProductOffering by ID
	 * 
	 * @param productOfferingId - Identifier of the ProductOffering (required) 
	 * @param productOfferingUpdate - ProductOfferingUpdate object used to update the ProductOffering (required) 
	 * @return boolean
	 */
	public boolean updateProductOffering(String productOfferingId, ProductOfferingUpdate productOfferingUpdate) {
		logger.info("Request: updateProductOffering by id {}", productOfferingId);
		
		try {
			ProductOffering productOffering = productOfferingApi.patchProductOffering(productOfferingId, productOfferingUpdate);
			logger.info("Update successfully ProductOffering with id: {}", productOffering.getId());
			return true;
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return false;
		}
	}
	

	/**
	 * This method retrieves a specific ProductOffering by ID
	 * 
	 * @param productOfferingId - Identifier of the ProductOffering (required)
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'name,lifecycleStatus')<br> 
	 * - use fields == null to get all attributes
	 * @return ProductOffering
	 */
	public ProductOffering getProductOffering(String productOfferingId, String fields) {
		logger.info("Request: getProductOffering by id {}", productOfferingId);

		try {
			if (fields != null) {
				logger.debug("Selected attributes: [{}]", fields);
			}

			return productOfferingApi.retrieveProductOffering(productOfferingId, fields);
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
	
	
	/**
	 * This method retrieves a paginated list of ProductOffering
	 * 
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'name,lifecycleStatus')<br>
	 * - use fields == null to get all attributes		
     * @param offset - the index of the first item to return (used for pagination)
     * @param limit - the maximum number of items to return
	 * @param filter - HashMap<K,V> to set query string params (optional)<br>  
	 * @return a {@link Page} containing a subset of ProductOffering
	 */
	public Page<ProductOffering> listProductOfferings(String fields, int offset, int limit, Map<String, String> filter) {
		logger.info("Request: listProductOfferings");
		
		try {
			
			if (filter != null && !filter.isEmpty()) {
				logger.debug("Params used in the query-string filter: {}", filter);
			}
			if (fields != null) {
				logger.debug("Selected attributes: [{}]", fields);
			}
			
			List<ProductOffering> items = productOfferingApi.listProductOffering(fields, offset, limit, filter);
			boolean hasNext = items.size() == limit;
			
			return new Page<>(items, offset, limit, hasNext);
			
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}   
	}
	
	
	/**
	 * This method retrieves a specific ProductOfferingPrice by ID
	 * 
	 * @param productOfferingPriceId - Identifier of the ProductOfferingPrice (required) 
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'name,version')<br> 
	 * - use fields == null to get all attributes
	 * @return ProductOfferingPrice
	 */
	public ProductOfferingPrice getProductOfferingPrice(String productOfferingPriceId, String fields) {
		logger.info("Request: getProductOfferingPrice by id {}", productOfferingPriceId);
		
		try {
			if (fields != null) {
				logger.debug("Selected attributes: [{}]", fields);
			}
			
			return  productOfferingPriceApi.retrieveProductOfferingPrice(productOfferingPriceId, fields);
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
	
	
	/**
	 * This method retrieves a paginated list of ProductOfferingPrice
	 * 
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'name,version')<br>
	 * - use fields == null to get all attributes		
     * @param offset - the index of the first item to return (used for pagination)
     * @param limit - the maximum number of items to return
	 * @param filter - HashMap<K,V> to set query string params (optional)<br>  
	 * @return a {@link Page} containing a subset of ProductOfferingPrice
	 */
	public Page<ProductOfferingPrice> listProductOfferingPrices(String fields, int offset, int limit, Map<String, String> filter) {
		logger.info("Request: listProductOfferingPrices");
		
		try {
			
			if (filter != null && !filter.isEmpty()) {
				logger.debug("Params used in the query-string filter: {}", filter);
			}
			if (fields != null) {
				logger.debug("Selected attributes: [{}]", fields);
			}
			
			List<ProductOfferingPrice> items = productOfferingPriceApi.listProductOfferingPrice(fields, offset, limit, filter);
			boolean hasNext = items.size() == limit;
			
			return new Page<>(items, offset, limit, hasNext);
			
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}   
	}
	
	
	/**
	 * This method retrieves a specific ProductSpecification by ID
	 * 
	 * @param productSpedicificationId - Identifier of the ProductSpecification (required) 
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'name,description')<br> 
	 * - use fields == null to get all attributes
	 * @return ProductSpecification
	 */
	public ProductSpecification getProductSpecification(String productSpedicificationId, String fields) {
		logger.info("Request: getProductSpecification by id {}", productSpedicificationId);
		
		try {
			if (fields != null) {
				logger.debug("Selected attributes: [{}]", fields);
			}
			
			return  productSpecificationApi.retrieveProductSpecification(productSpedicificationId, fields);
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
	
	
	/**
	 * This method retrieves a paginated list of ProductSpecification
	 * 
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'name,description')<br> 
	 * - use fields == null to get all attributes		
     * @param offset - the index of the first item to return (used for pagination)
     * @param limit - the maximum number of items to return
	 * @param filter - HashMap<K,V> to set query string params (optional)<br> 
	 * @return List&lt;ProductSpecification&gt;
	 */
	public Page<ProductSpecification> listProductSpecifications(String fields, int offset, int limit, Map<String, String> filter) {
		logger.info("Request: listProductSpecifications");
		
		try {
			
			if (filter != null && !filter.isEmpty()) {
				logger.debug("Params used in the query-string filter: {}", filter);
			}
			if (fields != null) {
				logger.debug("Selected attributes: [{}]", fields);
			}
			
			List<ProductSpecification> items = productSpecificationApi.listProductSpecification(fields, offset, limit, filter);
			boolean hasNext = items.size() == limit;
			
			return new Page<>(items, offset, limit, hasNext);
			
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}   
	}
}