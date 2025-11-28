package it.eng.dome.brokerage.api;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.dome.brokerage.api.config.DomeTmfSchemaConfig;
import it.eng.dome.brokerage.billing.utils.DateTimeUtils;
import it.eng.dome.tmforum.tmf620.v4.ApiClient;
import it.eng.dome.tmforum.tmf620.v4.ApiException;
import it.eng.dome.tmforum.tmf620.v4.api.CatalogApi;
import it.eng.dome.tmforum.tmf620.v4.api.CategoryApi;
import it.eng.dome.tmforum.tmf620.v4.api.ProductOfferingApi;
import it.eng.dome.tmforum.tmf620.v4.api.ProductOfferingPriceApi;
import it.eng.dome.tmforum.tmf620.v4.api.ProductSpecificationApi;
import it.eng.dome.tmforum.tmf620.v4.model.Catalog;
import it.eng.dome.tmforum.tmf620.v4.model.CatalogCreate;
import it.eng.dome.tmforum.tmf620.v4.model.CatalogUpdate;
import it.eng.dome.tmforum.tmf620.v4.model.Category;
import it.eng.dome.tmforum.tmf620.v4.model.CategoryCreate;
import it.eng.dome.tmforum.tmf620.v4.model.CategoryUpdate;
import it.eng.dome.tmforum.tmf620.v4.model.ProductOffering;
import it.eng.dome.tmforum.tmf620.v4.model.ProductOfferingCreate;
import it.eng.dome.tmforum.tmf620.v4.model.ProductOfferingPrice;
import it.eng.dome.tmforum.tmf620.v4.model.ProductOfferingPriceCreate;
import it.eng.dome.tmforum.tmf620.v4.model.ProductOfferingPriceUpdate;
import it.eng.dome.tmforum.tmf620.v4.model.ProductOfferingUpdate;
import it.eng.dome.tmforum.tmf620.v4.model.ProductSpecification;


public class ProductCatalogManagementApis {
	
	private final Logger logger = LoggerFactory.getLogger(ProductCatalogManagementApis.class);
	private ProductOfferingApi productOfferingApi;
	private ProductOfferingPriceApi productOfferingPriceApi;
	private ProductSpecificationApi productSpecificationApi;
	private CategoryApi categoryApi;
	private CatalogApi catalogApi;
	
	private final String categorySchemaLocation = DomeTmfSchemaConfig.get("category");
	private final String productofferingSchemaLocation = DomeTmfSchemaConfig.get("productoffering");
	private final String productofferingpriceSchemaLocation = DomeTmfSchemaConfig.get("productofferingprice");
		
	/**
	 * Constructor
	 * @param apiClientTMF620
	 */
	public ProductCatalogManagementApis(ApiClient apiClientTMF620){
		logger.info("Init ProductCatalogManagementApis - apiClientTMF620 basePath: {}", apiClientTMF620.getBasePath());
		productOfferingApi = new ProductOfferingApi(apiClientTMF620);
		productOfferingPriceApi = new ProductOfferingPriceApi(apiClientTMF620);
		productSpecificationApi = new ProductSpecificationApi(apiClientTMF620);
		categoryApi = new CategoryApi(apiClientTMF620);
		catalogApi = new CatalogApi(apiClientTMF620);
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
	 * This method queries the Product Catalog Management API and returns a paginated subset of results 
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
		logger.info("Request: listProductOfferings: offset={}, limit={}", offset, limit);
				
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
	 * This method sends a creation request to the Product Catalog Management API using
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
		
		if (productOfferingCreate.getAtSchemaLocation() == null) {
			logger.debug("Setting default schemaLocation to {}", productofferingSchemaLocation);
			productOfferingCreate.setAtSchemaLocation(URI.create(productofferingSchemaLocation));
		}

		ProductOffering offering = productOfferingApi.createProductOffering(productOfferingCreate);
		logger.info("ProductOffering saved successfully with id: {}", offering.getId());
		
		return offering.getId();
	}
	
	
	/**
	 * Updates an existing {@link ProductOffering} resource by its unique identifier.
	 * <p>
	 * This method sends a PATCH request to the Product Catalog Management API to update
	 * the specified {@link ProductOffering} with the provided {@link ProductOfferingUpdate} data.
	 * </p>
	 *
	 * @param id the unique identifier of the {@link ProductOffering} to update (required)
	 * @param ProductOfferingUpdate the {@link ProductOfferingUpdate} object containing the updated fields (required)
	 * @throws ApiException if the API call fails or the resource cannot be updated
	 */
	public void updateProductOffering(String id, ProductOfferingUpdate productOfferingUpdate) throws ApiException {
		logger.info("Request: updateProductOffering by id {}", id);
		
		if (productOfferingUpdate.getAtSchemaLocation() == null) {
			logger.debug("Setting default schemaLocation to {}", productofferingSchemaLocation);
			productOfferingUpdate.setAtSchemaLocation(URI.create(productofferingSchemaLocation));
		}
		
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
	 * This method queries the Product Catalog Management API and returns a paginated subset of results 
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
		logger.info("Request: listProductOfferingPrices: offset={}, limit={}", offset, limit);
		
		if (filter != null && !filter.isEmpty()) {
			logger.debug("Params used in the query-string filter: {}", filter);
		}
		if (fields != null) {
			logger.debug("Selected attributes: [{}]", fields);
		}
		
		return productOfferingPriceApi.listProductOfferingPrice(fields, offset, limit, filter);  
	}
	
	
	/**
	 * Creates a new {@link ProductOfferingPrice} resource.
	 * <p>
	 * This method sends a creation request to the Product Catalog Management API using
	 * the provided {@link ProductOfferingPriceCreate} payload.
	 * If the creation is successful, it returns the identifier of the newly created resource.
	 * </p>
	 * 
	 * @param productOfferingPriceCreate the {@link ProductOfferingPriceCreate} object used to create the new ProductOfferingPrice (required)
	 * @return the unique identifier ({@code id}) of the created {@link ProductOfferingPrice}
	 * @throws ApiException if the API call fails or the resource cannot be retrieved  
	 */
	public String createProductOfferingPrice(ProductOfferingPriceCreate productOfferingPriceCreate) throws ApiException {		
		logger.info("Create: ProductOfferingPrice");
		
		if (productOfferingPriceCreate.getAtSchemaLocation() == null) {
			logger.debug("Setting default schemaLocation to {}", productofferingpriceSchemaLocation);
			productOfferingPriceCreate.setAtSchemaLocation(URI.create(productofferingpriceSchemaLocation));
		}

		ProductOfferingPrice offering = productOfferingPriceApi.createProductOfferingPrice(productOfferingPriceCreate);
		logger.info("ProductOfferingPrice saved successfully with id: {}", offering.getId());
		
		return offering.getId();
	}
	
	
	/**
	 * Updates an existing {@link ProductOfferingPrice} resource by its unique identifier.
	 * <p>
	 * This method sends a PATCH request to the Product ProductOfferingPrice Management API to update
	 * the specified {@link ProductOfferingPrice} with the provided {@link ProductOfferingPriceUpdate} data.
	 * </p>
	 *
	 * @param id the unique identifier of the {@link ProductOfferingPrice} to update (required)
	 * @param productOfferingPriceUpdate the {@link ProductOfferingPriceUpdate} object containing the updated fields (required)
	 * @throws ApiException if the API call fails or the resource cannot be updated
	 */
	public void updateProductOfferingPrice(String id, ProductOfferingPriceUpdate productOfferingPriceUpdate) throws ApiException {
		logger.info("Request: updateProductOfferingPrice by id {}", id);

		if (productOfferingPriceUpdate.getAtSchemaLocation() == null) {
			logger.debug("Setting default schemaLocation to {}", productofferingpriceSchemaLocation);
			productOfferingPriceUpdate.setAtSchemaLocation(URI.create(productofferingpriceSchemaLocation));
		}
		
		ProductOfferingPrice ProductOfferingPrice = productOfferingPriceApi.patchProductOfferingPrice(id, productOfferingPriceUpdate);

		boolean success = (ProductOfferingPrice != null && ProductOfferingPrice.getId() != null);
		if (success) {
			logger.debug("Successfully updated ProductOfferingPrice with id: {}", id);
		} else {
			logger.warn("Update may have failed for ProductOfferingPrice id: {}", id);
		}
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
	 * This method queries the Product Catalog Management API and returns a paginated subset of results 
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
		logger.info("Request: listProductSpecifications: offset={}, limit={}", offset, limit);
		
		if (filter != null && !filter.isEmpty()) {
			logger.debug("Params used in the query-string filter: {}", filter);
		}
		if (fields != null) {
			logger.debug("Selected attributes: [{}]", fields);
		}
		
		return productSpecificationApi.listProductSpecification(fields, offset, limit, filter);
	}
	
	
	/**
	 * Retrieves a specific {@link Category} by its unique identifier.
	 *
	 * @param id      the identifier of the {@code Category} to retrieve (required)
	 * @param fields  a comma-separated list of properties to include in the response (optional) <br>
	 *                - use this parameter to request specific attributes (e.g., {@code "name,lifecycleStatus"}) <br>
	 *                - use {@code null} or an empty string to retrieve all available attributes
	 * @return the {@link Category} matching the given {@code id}
	 * @throws ApiException if the API call fails or the resource cannot be retrieved
	 */
	public Category getCategory(String id, String fields) throws ApiException {
		logger.info("Request: getCategory by id {}", id);

		if (fields != null) {
			logger.debug("Selected attributes: [{}]", fields);
		}

		return categoryApi.retrieveCategory(id, fields);
	}

		
	/**
	 * Retrieves a list of {@link Category} resources.
	 * <p>
	 * This method queries the Product Catalog Management API and returns a paginated subset of results 
	 * based on the provided {@code offset}, {@code limit}, and optional filter criteria.
	 * </p>
	 *
	 * @param fields a comma-separated list of properties to include in the response (optional)<br>
	 *               - use this string to select specific fields (e.g. {@code "name,lifecycleStatus"})<br>
	 *               - use {@code null} to retrieve all attributes
	 * @param offset the index of the first item to return 
	 * @param limit  the maximum number of items to return 
	 * @param filter a {@link Map} of query parameters used for filtering results (optional)
	 * @return a {@link List} containing the retrieved {@link Category} resources
	 * @throws ApiException if the API call fails or the resources cannot be retrieved
	 */
	public List<Category> listCategories(String fields, int offset, int limit, Map<String, String> filter) throws ApiException {
		logger.info("Request: listCategories: offset={}, limit={}", offset, limit);
				
		if (filter != null && !filter.isEmpty()) {
			logger.debug("Params used in the query-string filter: {}", filter);
		}
		if (fields != null) {
			logger.debug("Selected attributes: [{}]", fields);
		}
		
		return categoryApi.listCategory(fields, offset, limit, filter);
	}
	
	
	/**
	 * Creates a new {@link Category} resource.
	 * <p>
	 * This method sends a creation request to the Product Catalog Management API using
	 * the provided {@link CategoryCreate} payload.
	 * If the creation is successful, it returns the identifier of the newly created resource.
	 * </p>
	 * 
	 * @param categoryCreate the {@link CategoryCreate} object used to create the new Category (required)
	 * @return the unique identifier ({@code id}) of the created {@link Category}
	 * @throws ApiException if the API call fails or the resource cannot be retrieved  
	 */
	public String createCategory(CategoryCreate categoryCreate) throws ApiException {		
		logger.info("Create: Category");

		categoryCreate.setLastUpdate(DateTimeUtils.getCurrentUtcTime());
		
		if (categoryCreate.getAtSchemaLocation() == null) {
			logger.debug("Setting default schemaLocation to {}", categorySchemaLocation);
			categoryCreate.setAtSchemaLocation(URI.create(categorySchemaLocation));
		}
		
		Category offering = categoryApi.createCategory(categoryCreate);
		logger.info("Category saved successfully with id: {}", offering.getId());
		
		return offering.getId();
	}
	
	
	/**
	 * Updates an existing {@link Category} resource by its unique identifier.
	 * <p>
	 * This method sends a PATCH request to the Product Category Management API to update
	 * the specified {@link Category} with the provided {@link CategoryUpdate} data.
	 * </p>
	 *
	 * @param id the unique identifier of the {@link Category} to update (required)
	 * @param categoryUpdate the {@link CategoryUpdate} object containing the updated fields (required)
	 * @throws ApiException if the API call fails or the resource cannot be updated
	 */
	public void updateCategory(String id, CategoryUpdate categoryUpdate) throws ApiException {
		logger.info("Request: updateCategory by id {}", id);
		
		categoryUpdate.setLastUpdate(DateTimeUtils.getCurrentUtcTime());
		
		if (categoryUpdate.getAtSchemaLocation() == null) {
			logger.debug("Setting default schemaLocation to {}", categorySchemaLocation);
			categoryUpdate.setAtSchemaLocation(URI.create(categorySchemaLocation));
		}

		Category Category = categoryApi.patchCategory(id, categoryUpdate);

		boolean success = (Category != null && Category.getId() != null);
		if (success) {
			logger.debug("Successfully updated Category with id: {}", id);
		} else {
			logger.warn("Update may have failed for Category id: {}", id);
		}
	}
	
	
	/**
	 * Retrieves a specific {@link Catalog} by its unique identifier.
	 *
	 * @param id      the identifier of the {@code Catalog} to retrieve (required)
	 * @param fields  a comma-separated list of properties to include in the response (optional) <br>
	 *                - use this parameter to request specific attributes (e.g., {@code "name,lifecycleStatus"}) <br>
	 *                - use {@code null} or an empty string to retrieve all available attributes
	 * @return the {@link Catalog} matching the given {@code id}
	 * @throws ApiException if the API call fails or the resource cannot be retrieved
	 */
	public Catalog getCatalog(String id, String fields) throws ApiException {
		logger.info("Request: getCatalog by id {}", id);

		if (fields != null) {
			logger.debug("Selected attributes: [{}]", fields);
		}

		return catalogApi.retrieveCatalog(id, fields);
	}

		
	/**
	 * Retrieves a list of {@link Catalog} resources.
	 * <p>
	 * This method queries the Product Catalog Management API and returns a paginated subset of results 
	 * based on the provided {@code offset}, {@code limit}, and optional filter criteria.
	 * </p>
	 *
	 * @param fields a comma-separated list of properties to include in the response (optional)<br>
	 *               - use this string to select specific fields (e.g. {@code "name,lifecycleStatus"})<br>
	 *               - use {@code null} to retrieve all attributes
	 * @param offset the index of the first item to return 
	 * @param limit  the maximum number of items to return 
	 * @param filter a {@link Map} of query parameters used for filtering results (optional)
	 * @return a {@link List} containing the retrieved {@link Catalog} resources
	 * @throws ApiException if the API call fails or the resources cannot be retrieved
	 */
	public List<Catalog> listCatalogs(String fields, int offset, int limit, Map<String, String> filter) throws ApiException {
		logger.info("Request: listCatalogs: offset={}, limit={}", offset, limit);
				
		if (filter != null && !filter.isEmpty()) {
			logger.debug("Params used in the query-string filter: {}", filter);
		}
		if (fields != null) {
			logger.debug("Selected attributes: [{}]", fields);
		}
		
		return catalogApi.listCatalog(fields, offset, limit, filter);
	}
	
	
	/**
	 * Creates a new {@link Catalog} resource.
	 * <p>
	 * This method sends a creation request to the Product Catalog Management API using
	 * the provided {@link CatalogCreate} payload.
	 * If the creation is successful, it returns the identifier of the newly created resource.
	 * </p>
	 * 
	 * @param catalogCreate the {@link CatalogCreate} object used to create the new Catalog (required)
	 * @return the unique identifier ({@code id}) of the created {@link Catalog}
	 * @throws ApiException if the API call fails or the resource cannot be retrieved  
	 */
	public String createCatalog(CatalogCreate catalogCreate) throws ApiException {		
		logger.info("Create: Catalog");
		
		Catalog offering = catalogApi.createCatalog(catalogCreate);
		logger.info("Catalog saved successfully with id: {}", offering.getId());
		
		return offering.getId();
	}
	
	
	/**
	 * Updates an existing {@link Catalog} resource by its unique identifier.
	 * <p>
	 * This method sends a PATCH request to the Product Catalog Management API to update
	 * the specified {@link Catalog} with the provided {@link CatalogUpdate} data.
	 * </p>
	 *
	 * @param id the unique identifier of the {@link Catalog} to update (required)
	 * @param catalogUpdate the {@link CatalogUpdate} object containing the updated fields (required)
	 * @throws ApiException if the API call fails or the resource cannot be updated
	 */
	public void updateCatalog(String id, CatalogUpdate catalogUpdate) throws ApiException {
		logger.info("Request: updateCatalog by id {}", id);

		Catalog Catalog = catalogApi.patchCatalog(id, catalogUpdate);

		boolean success = (Catalog != null && Catalog.getId() != null);
		if (success) {
			logger.debug("Successfully updated Catalog with id: {}", id);
		} else {
			logger.warn("Update may have failed for Catalog id: {}", id);
		}
	}
}