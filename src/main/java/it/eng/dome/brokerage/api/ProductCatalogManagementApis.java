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
	 * @param productOfferingCreate - ProductOfferingCreate object used in the creation request of the ProductOffering (required) 
	 * @return the id of the created ProductOffering, or {@code null} if the creation failed
	 */
	public String createProductOffering(ProductOfferingCreate productOfferingCreate) {		
		logger.info("Create: ProductOffering");
		
		try {
			ProductOffering offering = productOfferingApi.createProductOffering(productOfferingCreate);
			logger.info("ProductOffering saved successfully with id: {}", offering.getId());
			return offering.getId();
		} catch (ApiException e) {
			logger.info("ProductOffering not saved: {}", productOfferingCreate.toString());
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
	
	
	/**
	 * This method updates the ProductOffering by id
	 * 
	 * @param id - Identifier of the ProductOffering (required) 
	 * @param productOfferingUpdate - ProductOfferingUpdate object used to update the ProductOffering (required) 
	 * @return {@code true} if the update was successful,
	 *         {@code false} otherwise
	 */
	public boolean updateProductOffering(String id, ProductOfferingUpdate productOfferingUpdate) {
		logger.info("Request: updateProductOffering by id {}", id);
		
		try {
			ProductOffering productOffering = productOfferingApi.patchProductOffering(id, productOfferingUpdate);
			logger.info("Update successfully ProductOffering with id: {}", productOffering.getId());
			return true;
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return false;
		}
	}
	

	/**
	 * This method retrieves a specific ProductOffering by id
	 * 
	 * @param id - Identifier of the ProductOffering (required)
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'name,lifecycleStatus')<br> 
	 * - use fields == null to get all attributes
	 * @return the {@link ProductOffering} with the given id,
	 *         or {@code null} if no ProductOffering is found
	 */
	public ProductOffering getProductOffering(String id, String fields) {
		logger.info("Request: getProductOffering by id {}", id);

		try {
			if (fields != null) {
				logger.debug("Selected attributes: [{}]", fields);
			}

			return productOfferingApi.retrieveProductOffering(id, fields);
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
	 * This method retrieves a specific ProductOfferingPrice by id
	 * 
	 * @param id - Identifier of the ProductOfferingPrice (required) 
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'name,version')<br> 
	 * - use fields == null to get all attributes
	 * @return the {@link ProductOfferingPrice} with the given id,
	 *         or {@code null} if no ProductOfferingPrice is found
	 */
	public ProductOfferingPrice getProductOfferingPrice(String id, String fields) {
		logger.info("Request: getProductOfferingPrice by id {}", id);
		
		try {
			if (fields != null) {
				logger.debug("Selected attributes: [{}]", fields);
			}
			
			return  productOfferingPriceApi.retrieveProductOfferingPrice(id, fields);
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
	 * This method retrieves a specific ProductSpecification by id
	 * 
	 * @param id - Identifier of the ProductSpecification (required) 
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'name,description')<br> 
	 * - use fields == null to get all attributes
	 * @return the {@link ProductSpecification} with the given id,
	 *         or {@code null} if no ProductSpecification is found
	 */
	public ProductSpecification getProductSpecification(String id, String fields) {
		logger.info("Request: getProductSpecification by id {}", id);
		
		try {
			if (fields != null) {
				logger.debug("Selected attributes: [{}]", fields);
			}
			
			return  productSpecificationApi.retrieveProductSpecification(id, fields);
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
	 * @return a {@link Page} containing a subset of ProductSpecification
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