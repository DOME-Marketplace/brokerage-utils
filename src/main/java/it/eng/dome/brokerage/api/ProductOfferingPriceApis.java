package it.eng.dome.brokerage.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.dome.tmforum.tmf620.v4.ApiClient;
import it.eng.dome.tmforum.tmf620.v4.ApiException;
import it.eng.dome.tmforum.tmf620.v4.api.ProductOfferingPriceApi;
import it.eng.dome.tmforum.tmf620.v4.model.ProductOfferingPrice;


public class ProductOfferingPriceApis {
	
	private final Logger logger = LoggerFactory.getLogger(ProductOfferingPriceApis.class);
	
	private ProductOfferingPriceApi popApi;
	
	/**
	 * Constructor
	 * @param apiClientTMF620
	 */
	public ProductOfferingPriceApis(ApiClient apiClientTMF620){
		logger.info("Init ProductOfferingPriceApis -  apiClientTMF620 basePath: {}", apiClientTMF620.getBasePath());
		popApi = new ProductOfferingPriceApi(apiClientTMF620);
	}
	
	/**
	 * This method retrieves a specific ProductOfferingPrice by ID
	 * 
	 * @param popId - Identifier of the ProductOfferingPrice (required) 
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'name,description')<br> 
	 * - use fields == null to get all attributes
	 * @return ProductOfferingPrice
	 */
	public ProductOfferingPrice getProductOfferingPrice(String popId, String fields) {
		try {
			return  popApi.retrieveProductOfferingPrice(popId, fields);
		} catch (ApiException e) {
			logger.error("Error: ", e.getMessage());
			return null;
		}
	}
}
