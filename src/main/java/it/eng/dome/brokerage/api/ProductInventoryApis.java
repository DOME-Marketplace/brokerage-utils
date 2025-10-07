package it.eng.dome.brokerage.api;

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
	 * Retrieves a specific {@link Product} by its unique identifier.
	 *
	 * @param id      the identifier of the {@code Product} to retrieve (required)
	 * @param fields  a comma-separated list of properties to include in the response (optional) <br>
	 *                - use this parameter to request specific attributes (e.g., {@code "name,periodCoverage"}) <br>
	 *                - use {@code null} or an empty string to retrieve all available attributes
	 * @return the {@link Product} matching the given {@code id}
	 * @throws ApiException if the API call fails or the resource cannot be retrieved
	 */
	public Product getProduct(String id, String fields) throws ApiException {
		logger.info("Request: getProduct by id {}", id);

		if (fields != null) {
			logger.debug("Selected attributes: [{}]", fields);
		}
		
		return productApi.retrieveProduct(id, fields);
	}
	
		
	/**
	 * Retrieves a list of {@link Product} resources.
	 * <p>
	 * This method queries the Product API and returns a paginated subset of results 
	 * based on the provided {@code offset}, {@code limit}, and optional filter criteria.
	 * </p>
	 *
	 * @param fields a comma-separated list of properties to include in the response (optional)<br>
	 *               - use this string to select specific fields (e.g. {@code "name,description"})<br>
	 *               - use {@code null} to retrieve all attributes
	 * @param offset the index of the first item to return 
	 * @param limit  the maximum number of items to return 
	 * @param filter a {@link Map} of query parameters used for filtering results (optional)
	 * @return a {@link List} containing the retrieved {@link Product} resources
	 * @throws ApiException if the API call fails or the resources cannot be retrieved
	 */
	public List<Product> listProducts(String fields, int offset, int limit, Map<String, String> filter) throws ApiException {
		logger.info("Request: listAgreements");
		
		if (filter != null && !filter.isEmpty()) {
			logger.debug("Params used in the query-string filter: {}", filter);
		}
		if (fields != null) {
			logger.debug("Selected attributes: [{}]", fields);
		}
		
		return productApi.listProduct(fields, offset, limit, filter);
	}
	
	
	/**
	 * This method creates a Product
	 * 
	 * @param productCreate - ProductCreate object used in the creation request of the Product (required) 
	 * @return the id of the created Product, or {@code null} if the creation failed
	 * @throws ApiException if the API call fails or the resource cannot be retrieved 
	 */
	public String createProduct(ProductCreate productCreate) throws ApiException {
		logger.info("Create: Product");
			
		Product product = productApi.createProduct(productCreate);
		logger.info("Product saved successfully with id: {}", product.getId());
		
		return product.getId();
	}
	
	
	
	
	
	/**
	 * This method updates the Product by id
	 * 
	 * @param id - Identifier of the Product (required) 
	 * @param productUpdate - ProductUpdate object used to update the Product (required) 
	 * @throws ApiException if the API call fails or the resource cannot be retrieved
	 */
	public void updateProduct(String id, ProductUpdate productUpdate) throws ApiException {
		logger.info("Request: updateProduct by id {}", id);

		Product product = productApi.patchProduct(id, productUpdate);

		boolean success = (product != null && product.getId() != null);
		if (success) {
			logger.debug("Successfully updated Product with id: {}", id);
		} else {
			logger.warn("Update may have failed for Product id: {}", id);
		}
	}
	
}