package it.eng.dome.brokerage.api;

import it.eng.dome.tmforum.tmf633.v4.ApiClient;
import it.eng.dome.tmforum.tmf633.v4.ApiException;
import it.eng.dome.tmforum.tmf633.v4.api.ServiceSpecificationApi;
import it.eng.dome.tmforum.tmf633.v4.model.ServiceSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ServiceSpecificationApis {

    private final Logger logger = LoggerFactory.getLogger(ServiceSpecificationApis.class);
    private final int LIMIT = 100;

    private ServiceSpecificationApi ssApi;

    /**
     * Constructor
     * @param apiClientTMF633
     */
    public ServiceSpecificationApis(ApiClient apiClientTMF633){
        logger.info("Init ServiceSpecificationApis - apiClientTMF633 basePath: {}", apiClientTMF633.getBasePath());
        ssApi = new ServiceSpecificationApi(apiClientTMF633);
    }

    /**
     * This method retrieves a specific ServiceSpecification by ID
     *
     * @param ssId - Identifier of the ServiceSpecification (required)
     * @param fields - Comma-separated properties to be provided in response (optional)<br>
     * - use this string to get specific fields (separated by comma: i.e. 'name,description')<br>
     * - use fields == null to get all attributes
     * @return ServiceSpecification
     */
    public ServiceSpecification getServiceSpecification(String ssId, String fields) {
        try {
            return  ssApi.retrieveServiceSpecification(ssId, fields);
        } catch (ApiException e) {
            logger.error("Error: {}", e.getResponseBody(), e);
            return null;
        }
    }

    /**
     * This method retrieves the list of ServiceSpecification
     *
     * @param fields - Comma-separated properties to be provided in response (optional)<br>
     * - use this string to get specific fields (separated by comma: i.e. 'name,description')<br>
     * - use fields == null to get all attributes
     * @param filter - HashMap<K,V> to set query string params (optional)<br>
     * @return List&lt;ServiceSpecification&gt;
     */
    public List<ServiceSpecification> getAllServiceSpecification(String fields, Map<String, String> filter) {
        logger.info("Request: getAllServiceSpecification");
        List<ServiceSpecification> all = new ArrayList<ServiceSpecification>();

        if (filter != null && !filter.isEmpty()) {
            logger.debug("Params used in the query-string filter: {}", filter);
        }

        getAllServiceSpecifications(all, fields, 0, filter);
        logger.info("Number of ServiceSpecifications: {}", all.size());
        return all;
    }

    private void getAllServiceSpecifications(List<ServiceSpecification> list, String fields, int start, Map<String, String> filter) {
        int offset = start * LIMIT;

        try {
            List<ServiceSpecification> productSpecificationgList = ssApi.listServiceSpecification(fields, offset, LIMIT, filter);

            if (!productSpecificationgList.isEmpty()) {
                list.addAll(productSpecificationgList);
                getAllServiceSpecifications(list, fields, start + 1, filter);
            }else {
                return;
            }
        } catch (ApiException e) {
            logger.error("Error: {}", e.getResponseBody());
            return;
        }
    }
}
