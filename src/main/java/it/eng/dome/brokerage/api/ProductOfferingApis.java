package it.eng.dome.brokerage.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.dome.tmforum.tmf620.v4.ApiClient;
import it.eng.dome.tmforum.tmf620.v4.ApiException;
import it.eng.dome.tmforum.tmf620.v4.api.ProductOfferingApi;
import it.eng.dome.tmforum.tmf620.v4.model.ProductOffering;
import it.eng.dome.tmforum.tmf620.v4.model.ProductOfferingCreate;
import it.eng.dome.tmforum.tmf620.v4.model.ProductOfferingUpdate;

public class ProductOfferingApis {
	
	private final Logger logger = LoggerFactory.getLogger(ProductOfferingApis.class);
	private final int LIMIT = 100;
	
	private ProductOfferingApi productOfferingApi;
	
	/**
	 * Constructor
	 * @param apiClientTMF620
	 */
	public ProductOfferingApis(ApiClient apiClientTMF620){
		logger.info("Init ProductOfferingApis - apiClientTMF620 basePath: {}", apiClientTMF620.getBasePath());
		productOfferingApi = new ProductOfferingApi(apiClientTMF620);
	}
	
	/**
	 * This method creates an ProductOffering
	 * 
	 * @param ProductOfferingCreate - ProductOfferingCreate object used in the creation request of the ProductOffering (required) 
	 * @return ProductOffering
	 */
	public ProductOffering createProductOffering(ProductOfferingCreate productOfferingCreate) {		
		try {
			return productOfferingApi.createProductOffering(productOfferingCreate);
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
	
	/**
	 * This method updates the ProductOffering by Id
	 * 
	 * @param productOfferingId - Identifier of the ProductOffering (required) 
	 * @param productOfferingUpdate - ProductOfferingUpdate object used to update the ProductOffering (required) 
	 * @return boolean
	 */
	public boolean updateProductOffering(String productOfferingId, ProductOfferingUpdate productOfferingUpdate) {
		logger.info("Request: updateProductOffering");
		try {
			ProductOffering productOffering = productOfferingApi.patchProductOffering(productOfferingId, productOfferingUpdate);
			logger.info("Update ProductOffering with id: {}", productOffering.getId());
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
	 * - use this string to get specific fields (separated by comma: i.e. 'status,usageType')<br> 
	 * - use fields == null to get all attributes
	 * @return ProductOffering
	 */
	public ProductOffering getProductOffering(String productOfferingId, String fields) {
		try {
			return productOfferingApi.retrieveProductOffering(productOfferingId, fields);
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
	
	/**
	 * This method retrieves the list of ProductOffering
	 * 
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'status,usageType')<br> 
	 * - use fields == null to get all attributes
	 * @param filter - HashMap<K,V> to set query string params (optional)<br> 
	 * @return List&lt;ProductOffering&gt;
	 */
	public List<ProductOffering> getAllProductOfferings(String fields, Map<String, String> filter) {
		logger.info("Request: getAllProductOfferings");
		List<ProductOffering> all = new ArrayList<ProductOffering>();
		
		if (filter != null && !filter.isEmpty()) {
			logger.debug("Params used in the query-string filter: {}", filter);
		}
		
		getAllProductOfferings(all, fields, 0, filter);
		logger.info("Number of ProductOfferings: {}", all.size());
		return all;
	}
		
	private void getAllProductOfferings(List<ProductOffering> list, String fields, int start, Map<String, String> filter) {
		int offset = start * LIMIT;

		try {
			List<ProductOffering> productOfferingList = productOfferingApi.listProductOffering(fields, offset, LIMIT, filter);

			if (!productOfferingList.isEmpty()) {
				list.addAll(productOfferingList);
				getAllProductOfferings(list, fields, start + 1, filter);				
			}else {
				return;
			}
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return;
		}		
	}

}