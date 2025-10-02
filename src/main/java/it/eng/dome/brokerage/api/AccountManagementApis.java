package it.eng.dome.brokerage.api;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.dome.brokerage.api.page.Page;
import it.eng.dome.tmforum.tmf666.v4.ApiClient;
import it.eng.dome.tmforum.tmf666.v4.ApiException;
import it.eng.dome.tmforum.tmf666.v4.api.BillFormatApi;
import it.eng.dome.tmforum.tmf666.v4.api.BillingAccountApi;
import it.eng.dome.tmforum.tmf666.v4.api.PartyAccountApi;
import it.eng.dome.tmforum.tmf666.v4.model.BillFormat;
import it.eng.dome.tmforum.tmf666.v4.model.BillFormatCreate;
import it.eng.dome.tmforum.tmf666.v4.model.BillFormatUpdate;
import it.eng.dome.tmforum.tmf666.v4.model.BillingAccount;
import it.eng.dome.tmforum.tmf666.v4.model.PartyAccount;


public class AccountManagementApis {
	
	private final Logger logger = LoggerFactory.getLogger(AccountManagementApis.class);
	private PartyAccountApi partyAccountApi;
	private BillingAccountApi billingAccountApi;
	private BillFormatApi billFormatApi;

	/**
	 * Constructor
	 * 
	 * @param apiClientTMF666
	 */
	public AccountManagementApis(ApiClient apiClientTMF666) {
		logger.info("Init AccountManagementApis - apiClientTMF666 basePath: {}", apiClientTMF666.getBasePath());
		partyAccountApi = new PartyAccountApi(apiClientTMF666);
		billingAccountApi = new BillingAccountApi(apiClientTMF666);
		billFormatApi = new BillFormatApi(apiClientTMF666);
	}
	
	
	/**
	 * This method retrieves a specific PartyAccount by id
	 * 
	 * @param id - Identifier of the PartyAccount (required)
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'status,name')<br> 
	 * - use fields == null to get all attributes
	 * @return the {@link PartyAccount} with the given id,
	 *         or {@code null} if no PartyAccount is found
	 */
	public PartyAccount getPartyAccount(String id, String fields) {
		logger.info("Request: getPartyAccount by id {}", id);
		
		try {
			if (fields != null) {
				logger.debug("Selected attributes: [{}]", fields);
			}
			
			return partyAccountApi.retrievePartyAccount(id, fields);
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
	
		
	/**
	 * This method retrieves a paginated list of PartyAccount
	 * 
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'href,name')<br> 
	 * - use fields == null to get all attributes		
     * @param offset - the index of the first item to return (used for pagination)
     * @param limit - the maximum number of items to return
	 * @param filter - HashMap<K,V> to set query string params (optional)<br>  
	 * @return a {@link Page} containing a subset of PartyAccount
	 */
	public Page<PartyAccount> listPartyAccounts(String fields, int offset, int limit, Map<String, String> filter) {
		logger.info("Request: listPartyAccounts");
		
		try {
			
			if (filter != null && !filter.isEmpty()) {
				logger.debug("Params used in the query-string filter: {}", filter);
			}
			if (fields != null) {
				logger.debug("Selected attributes: [{}]", fields);
			}
			
			List<PartyAccount> items = partyAccountApi.listPartyAccount(fields, offset, limit, filter);
			boolean hasNext = items.size() == limit;
			
			return new Page<>(items, offset, limit, hasNext);
			
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}   
	}
	
	
	
	/**
	 * This method retrieves a specific BillingAccount by id
	 * 
	 * @param id - Identifier of the BillingAccount (required)
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'status,name')<br> 
	 * - use fields == null to get all attributes
	 * @return the {@link BillingAccount} with the given id,
	 *         or {@code null} if no PartyAccount is found
	 */
	public BillingAccount getBillingAccount(String id, String fields) {
		logger.info("Request: getBillingAccount by id {}", id);
		
		try {
			if (fields != null) {
				logger.debug("Selected attributes: [{}]", fields);
			}
			
			return billingAccountApi.retrieveBillingAccount(id, fields);
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
	
	
	/**
	 * This method retrieves a paginated list of BillingAccount
	 * 
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'state,name')<br>
	 * - use fields == null to get all attributes		
     * @param offset - the index of the first item to return (used for pagination)
     * @param limit - the maximum number of items to return
	 * @param filter - HashMap<K,V> to set query string params (optional)<br>  
	 * @return a {@link Page} containing a subset of BillingAccount
	 */
	public Page<BillingAccount> listBillingAccounts(String fields, int offset, int limit, Map<String, String> filter) {
		logger.info("Request: listBillingAccounts");
		
		try {
			
			if (filter != null && !filter.isEmpty()) {
				logger.debug("Params used in the query-string filter: {}", filter);
			}
			if (fields != null) {
				logger.debug("Selected attributes: [{}]", fields);
			}
			
			List<BillingAccount> items = billingAccountApi.listBillingAccount(fields, offset, limit, filter);
			boolean hasNext = items.size() == limit;
			
			return new Page<>(items, offset, limit, hasNext);
			
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}   
	}
	
		
	/**
	 * This method creates a BillFormat
	 * 
	 * @param billFormatCreate - BillFormatCreate object used in the creation request of the BillFormat (required) 
	 * @return the id of the created BillFormat, or {@code null} if the creation failed
	 */
	public String createBillFormat(BillFormatCreate billFormatCreate) {	
		logger.info("Create: BillFormat");
		
		try {
			BillFormat billFormat = billFormatApi.createBillFormat(billFormatCreate);
			logger.info("BillFormat saved successfully with id: {}", billFormat.getId());
			return billFormat.getId();
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
	
	
	/**
	 * This method updates the BillFormat by id
	 * 
	 * @param id - Identifier of the BillFormat (required) 
	 * @param billFormatUpdate - BillFormatUpdate object used to update the BillFormat (required) 
	 * @return {@code true} if the update was successful,
	 *         {@code false} otherwise
	 */
	public boolean updateBillFormat(String id, BillFormatUpdate billFormatUpdate) {
		logger.info("Request: updateBillFormat by id {}", id);
		
		try {
			BillFormat billFormat = billFormatApi.patchBillFormat(id, billFormatUpdate);
			logger.debug("Update successfully BillFormat with id: {}", billFormat.getId());
			return true;
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return false;
		}
	}
	

	/**
	 * This method retrieves a specific BillFormat by id
	 * 
	 * @param id - Identifier of the BillFormat (required)
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'status,name')<br> 
	 * - use fields == null to get all attributes
	 * @return the {@link BillFormat} with the given id,
	 *         or {@code null} if no PartyAccount is found
	 */
	public BillFormat getBillFormat(String id, String fields) {
		logger.info("Request: getBillFormat by id {}", id);
		
		try {
			if (fields != null) {
				logger.debug("Selected attributes: [{}]", fields);
			}
			
			return billFormatApi.retrieveBillFormat(id, fields);
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
	
	
	/**
	 * This method retrieves a paginated list of BillFormat
	 * 
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'href,name')<br>
	 * - use fields == null to get all attributes		
     * @param offset - the index of the first item to return (used for pagination)
     * @param limit - the maximum number of items to return
	 * @param filter - HashMap<K,V> to set query string params (optional)<br>  
	 * @return a {@link Page} containing a subset of BillFormat
	 */
	public Page<BillFormat> listBillFormats(String fields, int offset, int limit, Map<String, String> filter) {
		logger.info("Request: listBillFormats");
		
		try {
			
			if (filter != null && !filter.isEmpty()) {
				logger.debug("Params used in the query-string filter: {}", filter);
			}
			if (fields != null) {
				logger.debug("Selected attributes: [{}]", fields);
			}
			
			List<BillFormat> items = billFormatApi.listBillFormat(fields, offset, limit, filter);
			boolean hasNext = items.size() == limit;
			
			return new Page<>(items, offset, limit, hasNext);
			
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}   
	}

}
