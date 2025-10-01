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
	 * This method retrieves a specific PartyAccount by ID
	 * 
	 * @param partyAccountId - Identifier of the PartyAccount (required)
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'status,name')<br> 
	 * - use fields == null to get all attributes
	 * @return BillFormat
	 */
	public PartyAccount getPartyAccount(String partyAccountId, String fields) {
		logger.info("Request: getPartyAccount by id {}", partyAccountId);
		
		try {
			if (fields != null) {
				logger.debug("Fields required: [{}]", fields);
			}
			
			return partyAccountApi.retrievePartyAccount(partyAccountId, fields);
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
				logger.debug("Fields required: [{}]", fields);
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
	 * This method retrieves a specific BillingAccount by ID
	 * 
	 * @param billingAccountId - Identifier of the BillingAccount (required)
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'status,name')<br> 
	 * - use fields == null to get all attributes
	 * @return BillingAccount
	 */
	public BillingAccount getBillingAccount(String billingAccountId, String fields) {
		logger.info("Request: getBillingAccount by id {}", billingAccountId);
		
		try {
			if (fields != null) {
				logger.debug("Fields required: [{}]", fields);
			}
			
			return billingAccountApi.retrieveBillingAccount(billingAccountId, fields);
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
				logger.debug("Fields required: [{}]", fields);
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
	 * @param BillFormatCreate - BillFormatCreate object used in the creation request of the BillFormat (required) 
	 * @return BillFormat
	 */
	public BillFormat createBillFormat(BillFormatCreate billFormatCreate) {	
		logger.info("Create: BillFormat");
		
		try {
			return billFormatApi.createBillFormat(billFormatCreate);
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
	
	
	/**
	 * This method updates the BillFormat by Id
	 * 
	 * @param billFormatId - Identifier of the BillFormat (required) 
	 * @param billFormatUpdate - BillFormatUpdate object used to update the BillFormat (required) 
	 * @return boolean
	 */
	public boolean updateBillFormat(String billFormatId, BillFormatUpdate billFormatUpdate) {
		logger.info("Request: updateBillFormat by id {}", billFormatId);
		
		try {
			BillFormat billFormat = billFormatApi.patchBillFormat(billFormatId, billFormatUpdate);
			logger.debug("Update successfully BillFormat with id: {}", billFormat.getId());
			return true;
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return false;
		}
	}

	/**
	 * This method retrieves a specific BillFormat by ID
	 * 
	 * @param billFormatId - Identifier of the BillFormat (required)
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'status,name')<br> 
	 * - use fields == null to get all attributes
	 * @return BillFormat
	 */
	public BillFormat getBillFormat(String billFormatId, String fields) {
		logger.info("Request: getBillFormat by id {}", billFormatId);
		
		try {
			if (fields != null) {
				logger.debug("Fields required: [{}]", fields);
			}
			
			return billFormatApi.retrieveBillFormat(billFormatId, fields);
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
				logger.debug("Fields required: [{}]", fields);
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
