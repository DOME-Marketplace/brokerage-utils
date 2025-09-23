package it.eng.dome.brokerage.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.dome.tmforum.tmf620.v4.ApiClient;
import it.eng.dome.tmforum.tmf620.v4.ApiException;
import it.eng.dome.tmforum.tmf620.v4.api.ProductSpecificationApi;
import it.eng.dome.tmforum.tmf620.v4.model.ProductSpecification;



public class ProductSpecificationApis {
	
	private final Logger logger = LoggerFactory.getLogger(ProductSpecificationApis.class);
	private final int LIMIT = 100;
	
	private ProductSpecificationApi psApi;
	
	/**
	 * Constructor
	 * @param apiClientTMF620
	 */
	public ProductSpecificationApis(ApiClient apiClientTMF620){
		logger.info("Init ProductSpecificationApis -  apiClientTMF620 basePath: {}", apiClientTMF620.getBasePath());
		psApi = new ProductSpecificationApi(apiClientTMF620);
	}
	
	/**
	 * This method retrieves a specific ProductSpecification by ID
	 * 
	 * @param posId - Identifier of the ProductSpecification (required) 
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'name,description')<br> 
	 * - use fields == null to get all attributes
	 * @return ProductSpecification
	 */
	public ProductSpecification getProductSpecification(String posId, String fields) {
		try {
			return  psApi.retrieveProductSpecification(posId, fields);
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
	
	
	/**
	 * This method retrieves the list of ProductSpecification
	 * 
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'status,usageType')<br> 
	 * - use fields == null to get all attributes
	 * @param filter - HashMap<K,V> to set query string params (optional)<br> 
	 * @return List&lt;ProductSpecification&gt;
	 */
	public List<ProductSpecification> getAllProductSpecification(String fields, Map<String, String> filter) {
		logger.info("Request: getAllProductSpecification");
		List<ProductSpecification> all = new ArrayList<ProductSpecification>();
		
		if (filter != null && !filter.isEmpty()) {
			logger.debug("Params used in the query-string filter: {}", filter);
		}
		
		getAllProductSpecifications(all, fields, 0, filter);
		logger.info("Number of ProductSpecifications: {}", all.size());
		return all;
	}
		
	private void getAllProductSpecifications(List<ProductSpecification> list, String fields, int start, Map<String, String> filter) {
		int offset = start * LIMIT;

		try {
			List<ProductSpecification> productSpecificationgList = psApi.listProductSpecification(fields, offset, LIMIT, filter);

			if (!productSpecificationgList.isEmpty()) {
				list.addAll(productSpecificationgList);
				getAllProductSpecifications(list, fields, start + 1, filter);				
			}else {
				return;
			}
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return;
		}		
	}
}
