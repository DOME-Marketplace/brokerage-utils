package it.eng.dome.brokerage.api;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	 * Retrieves a specific {@link ProductOffering} by its unique identifier.
	 *
	 * @param id      the identifier of the {@code ProductOffering} to retrieve (required)
	 * @param fields  a comma-separated list of properties to include in the response (optional) <br>
	 *                - use this parameter to request specific attributes (e.g., {@code "name,lifecycleStatus"}) <br>
	 *                - use {@code null} or an empty string to retrieve all available attributes
	 * @return the {@link ProductOffering} matching the given {@code id}
	 * @throws ApiException if the API call fails or the resource cannot be retrieved
	 */
	public ProductOffering getProductOffering(String id, String fields) throws ApiException {
		logger.info("Request: getProductOffering by id {}", id);

		if (fields != null) {
			logger.debug("Selected attributes: [{}]", fields);
		}

		return productOfferingApi.retrieveProductOffering(id, fields);
	}

		
	/**
	 * Retrieves a list of {@link ProductOffering} resources.
	 * <p>
	 * This method queries the ProductOffering API and returns a paginated subset of results 
	 * based on the provided {@code offset}, {@code limit}, and optional filter criteria.
	 * </p>
	 *
	 * @param fields a comma-separated list of properties to include in the response (optional)<br>
	 *               - use this string to select specific fields (e.g. {@code "name,lifecycleStatus"})<br>
	 *               - use {@code null} to retrieve all attributes
	 * @param offset the index of the first item to return 
	 * @param limit  the maximum number of items to return 
	 * @param filter a {@link Map} of query parameters used for filtering results (optional)
	 * @return a {@link List} containing the retrieved {@link ProductOffering} resources
	 * @throws ApiException if the API call fails or the resources cannot be retrieved
	 */
	public List<ProductOffering> listProductOfferings(String fields, int offset, int limit, Map<String, String> filter) throws ApiException {
		logger.info("Request: listProductOfferings");
				
		if (filter != null && !filter.isEmpty()) {
			logger.debug("Params used in the query-string filter: {}", filter);
		}
		if (fields != null) {
			logger.debug("Selected attributes: [{}]", fields);
		}
		
		return productOfferingApi.listProductOffering(fields, offset, limit, filter);
	}
	
	
	/**
	 * Creates a new {@link ProductOffering} resource.
	 * <p>
	 * This method sends a creation request to the Billing Management API using
	 * the provided {@link ProductOfferingCreate} payload.
	 * If the creation is successful, it returns the identifier of the newly created resource.
	 * </p>
	 * 
	 * @param productOfferingCreate the {@link ProductOfferingCreate} object used to create the new ProductOffering (required)
	 * @return the unique identifier ({@code id}) of the created {@link ProductOffering}
	 * @throws ApiException if the API call fails or the resource cannot be retrieved  
	 */
	public String createProductOffering(ProductOfferingCreate productOfferingCreate) throws ApiException {		
		logger.info("Create: ProductOffering");

		ProductOffering offering = productOfferingApi.createProductOffering(productOfferingCreate);
		logger.info("ProductOffering saved successfully with id: {}", offering.getId());
		
		return offering.getId();
	}
	
	
	/**
	 * This method updates the ProductOffering by id
	 * 
	 * @param id - Identifier of the ProductOffering (required) 
	 * @param productOfferingUpdate - ProductOfferingUpdate object used to update the ProductOffering (required) 
	 * @throws ApiException if the API call fails or the resource cannot be retrieved 
	 */
	public void updateProductOffering(String id, ProductOfferingUpdate productOfferingUpdate) throws ApiException {
		logger.info("Request: updateProductOffering by id {}", id);
		
		ProductOffering productOffering = productOfferingApi.patchProductOffering(id, productOfferingUpdate);

		boolean success = (productOffering != null && productOffering.getId() != null);
		if (success) {
			logger.debug("Successfully updated ProductOffering with id: {}", id);
		} else {
			logger.warn("Update may have failed for ProductOffering id: {}", id);
		}
	}
	
	
	/**
	 * Retrieves a specific {@link ProductOfferingPrice} by its unique identifier.
	 *
	 * @param id      the identifier of the {@code ProductOfferingPrice} to retrieve (required)
	 * @param fields  a comma-separated list of properties to include in the response (optional) <br>
	 *                - use this parameter to request specific attributes (e.g., {@code "name,version"}) <br>
	 *                - use {@code null} or an empty string to retrieve all available attributes
	 * @return the {@link ProductOfferingPrice} matching the given {@code id}
	 * @throws ApiException if the API call fails or the resource cannot be retrieved
	 */
	public ProductOfferingPrice getProductOfferingPrice(String id, String fields) throws ApiException {
		logger.info("Request: getProductOfferingPrice by id {}", id);

		if (fields != null) {
			logger.debug("Selected attributes: [{}]", fields);
		}
		
		return productOfferingPriceApi.retrieveProductOfferingPrice(id, fields);
	}
	

	/**
	 * Retrieves a list of {@link ProductOfferingPrice} resources.
	 * <p>
	 * This method queries the ProductOfferingPrice API and returns a paginated subset of results 
	 * based on the provided {@code offset}, {@code limit}, and optional filter criteria.
	 * </p>
	 *
	 * @param fields a comma-separated list of properties to include in the response (optional)<br>
	 *               - use this string to select specific fields (e.g. {@code "name,version"})<br>
	 *               - use {@code null} to retrieve all attributes
	 * @param offset the index of the first item to return 
	 * @param limit  the maximum number of items to return 
	 * @param filter a {@link Map} of query parameters used for filtering results (optional)
	 * @return a {@link List} containing the retrieved {@link ProductOfferingPrice} resources
	 * @throws ApiException if the API call fails or the resources cannot be retrieved
	 */
	public List<ProductOfferingPrice> listProductOfferingPrices(String fields, int offset, int limit, Map<String, String> filter) throws ApiException {
		logger.info("Request: listProductOfferingPrices");
		
		if (filter != null && !filter.isEmpty()) {
			logger.debug("Params used in the query-string filter: {}", filter);
		}
		if (fields != null) {
			logger.debug("Selected attributes: [{}]", fields);
		}
		
		return productOfferingPriceApi.listProductOfferingPrice(fields, offset, limit, filter);  
	}
	
	
	/**
	 * This method retrieves a specific ProductSpecification by id
	 * 
	 * @param id - Identifier of the ProductSpecification (required) 
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'name,description')<br> 
	 * - use fields == null to get all attributes
	 * @return the {@link ProductSpecification} with the given id, or {@code null} if no ProductSpecification is found
	 * @throws ApiException if the API call fails or the resource cannot be retrieved
	 */
	public ProductSpecification getProductSpecification(String id, String fields) throws ApiException {
		logger.info("Request: getProductSpecification by id {}", id);
	
		if (fields != null) {
			logger.debug("Selected attributes: [{}]", fields);
		}
		
		return productSpecificationApi.retrieveProductSpecification(id, fields);
	}
	
		
	/**
	 * Retrieves a list of {@link ProductSpecification} resources.
	 * <p>
	 * This method queries the ProductSpecification API and returns a paginated subset of results 
	 * based on the provided {@code offset}, {@code limit}, and optional filter criteria.
	 * </p>
	 *
	 * @param fields a comma-separated list of properties to include in the response (optional)<br>
	 *               - use this string to select specific fields (e.g. {@code "name,description"})<br>
	 *               - use {@code null} to retrieve all attributes
	 * @param offset the index of the first item to return 
	 * @param limit  the maximum number of items to return 
	 * @param filter a {@link Map} of query parameters used for filtering results (optional)
	 * @return a {@link List} containing the retrieved {@link ProductSpecification} resources
	 * @throws ApiException if the API call fails or the resources cannot be retrieved
	 */
	public List<ProductSpecification> listProductSpecifications(String fields, int offset, int limit, Map<String, String> filter) throws ApiException {
		logger.info("Request: listProductSpecifications");
		
		if (filter != null && !filter.isEmpty()) {
			logger.debug("Params used in the query-string filter: {}", filter);
		}
		if (fields != null) {
			logger.debug("Selected attributes: [{}]", fields);
		}
		
		return productSpecificationApi.listProductSpecification(fields, offset, limit, filter);
	}
	
}