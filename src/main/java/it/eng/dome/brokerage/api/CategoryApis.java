package it.eng.dome.brokerage.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.dome.tmforum.tmf620.v4.ApiClient;
import it.eng.dome.tmforum.tmf620.v4.ApiException;
import it.eng.dome.tmforum.tmf620.v4.api.CategoryApi;
import it.eng.dome.tmforum.tmf620.v4.model.Category;


public class CategoryApis {
	
	private final Logger logger = LoggerFactory.getLogger(CategoryApis.class);
    private final int LIMIT = 100;

    private CategoryApi categoryApi;

	/**
	 * Constructor
	 * @param apiClientTMF620
	 */
	public CategoryApis(ApiClient apiClientTMF620){
		logger.info("Init CategoryApis - apiClientTMF620 basePath: {}", apiClientTMF620.getBasePath());
		categoryApi = new CategoryApi(apiClientTMF620);
	}
	

    /**
     * This method retrieves a specific Category by ID
     *
     * @param id - Identifier of the Category (required)
     * @param fields - Comma-separated properties to be provided in response (optional)<br>
     * - use this string to get specific fields (separated by comma: i.e. 'name,description')<br>
     * - use fields == null to get all attributes
     * @return Category
     */
    public Category getCategory(String id, String fields) {
        try {
            return  categoryApi.retrieveCategory(id, fields);
        } catch (ApiException e) {
            logger.error("Error: {}", e.getResponseBody(), e);
            return null;
        }
    }

    /**
     * This method retrieves the list of Category
     *
     * @param fields - Comma-separated properties to be provided in response (optional)<br>
     * - use this string to get specific fields (separated by comma: i.e. 'name,description')<br>
     * - use fields == null to get all attributes
     * @param filter - HashMap<K,V> to set query string params (optional)<br>
     * @return List&lt;Category&gt;
     */
    public List<Category> getAllCategory(String fields, Map<String, String> filter) {
        logger.info("Request: getAllCategory");
        List<Category> all = new ArrayList<Category>();

        if (filter != null && !filter.isEmpty()) {
            logger.debug("Params used in the query-string filter: {}", filter);
        }

        getAllCategories(all, fields, 0, filter);
        logger.info("Number of getAllCategory: {}", all.size());
        return all;
    }

    private void getAllCategories(List<Category> list, String fields, int start, Map<String, String> filter) {
        int offset = start * LIMIT;

        try {
            List<Category> categoryList = categoryApi.listCategory(fields, offset, LIMIT, filter);

            if (!categoryList.isEmpty()) {
                list.addAll(categoryList);
                getAllCategories(list, fields, start + 1, filter);
            }else {
                return;
            }
        } catch (ApiException e) {
            logger.error("Error: {}", e.getResponseBody());
            return;
        }
    }
}
