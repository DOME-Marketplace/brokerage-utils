package it.eng.dome.brokerage.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private final int LIMIT = 100;
	
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
		try {
			return partyAccountApi.retrievePartyAccount(partyAccountId, fields);
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
	
	
	/**
	 * This method retrieves the list of PartyAccount
	 * 
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'href,name')<br> 
	 * - use fields == null to get all attributes
	 * @param filter - HashMap<K,V> to set query string params (optional)<br> 
	 * @return List&lt;PartyAccount&gt;
	 */
	public List<PartyAccount> getAllPartyAccounts(String fields, Map<String, String> filter) {
		logger.info("Request: getAllPartyAccounts");
		List<PartyAccount> all = new ArrayList<PartyAccount>();
		
		if (filter != null && !filter.isEmpty()) {
			logger.debug("Params used in the query-string filter: {}", filter);
		}
		
		getAllPartyAccounts(all, fields, 0, filter);
		logger.info("Number of PartyAccounts: {}", all.size());
		return all;
	}
		
	private void getAllPartyAccounts(List<PartyAccount> list, String fields, int start, Map<String, String> filter) {
		int offset = start * LIMIT;

		try {
			List<PartyAccount> partyAccountList =  partyAccountApi.listPartyAccount(fields, offset, LIMIT, filter);

			if (!partyAccountList.isEmpty()) {
				list.addAll(partyAccountList);
				getAllPartyAccounts(list, fields, start + 1, filter);				
			}else {
				return;
			}
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return;
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
		try {
			return billingAccountApi.retrieveBillingAccount(billingAccountId, fields);
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
	
	/**
	 * This method retrieves the list of BillingAccount
	 * 
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'state,name')<br> 
	 * - use fields == null to get all attributes
	 * @param filter - HashMap<K,V> to set query string params (optional)<br> 
	 * @return List&lt;BillingAccount&gt;
	 */
	public List<BillingAccount> getAllBillingAccounts(String fields, Map<String, String> filter) {
		logger.info("Request: getAllBillingAccounts");
		List<BillingAccount> all = new ArrayList<BillingAccount>();
		
		if (filter != null && !filter.isEmpty()) {
			logger.debug("Params used in the query-string filter: {}", filter);
		}
		
		getAllBillingAccounts(all, fields, 0, filter);
		logger.info("Number of BillingAccounts: {}", all.size());
		return all;
	}
		
	private void getAllBillingAccounts(List<BillingAccount> list, String fields, int start, Map<String, String> filter) {
		int offset = start * LIMIT;

		try {
			List<BillingAccount> agreementList =  billingAccountApi.listBillingAccount(fields, offset, LIMIT, filter);

			if (!agreementList.isEmpty()) {
				list.addAll(agreementList);
				getAllBillingAccounts(list, fields, start + 1, filter);				
			}else {
				return;
			}
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return;
		}		
	}
	
	
	/**
	 * This method creates a BillFormat
	 * 
	 * @param BillFormatCreate - BillFormatCreate object used in the creation request of the BillFormat (required) 
	 * @return BillFormat
	 */
	public BillFormat createBillFormat(BillFormatCreate billFormatCreate) {		
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
		logger.info("Request: updateBillFormat");
		try {
			BillFormat billFormat = billFormatApi.patchBillFormat(billFormatId, billFormatUpdate);
			logger.info("Update BillFormat with id: {}", billFormat.getId());
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
		try {
			return billFormatApi.retrieveBillFormat(billFormatId, fields);
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return null;
		}
	}
	
	
	/**
	 * This method retrieves the list of BillFormat
	 * 
	 * @param fields - Comma-separated properties to be provided in response (optional)<br> 
	 * - use this string to get specific fields (separated by comma: i.e. 'href,name')<br> 
	 * - use fields == null to get all attributes
	 * @param filter - HashMap<K,V> to set query string params (optional)<br> 
	 * @return List&lt;BillFormat&gt;
	 */
	public List<BillFormat> getAllBillFormats(String fields, Map<String, String> filter) {
		logger.info("Request: getAllAgreements");
		List<BillFormat> all = new ArrayList<BillFormat>();
		
		if (filter != null && !filter.isEmpty()) {
			logger.debug("Params used in the query-string filter: {}", filter);
		}
		
		getAllBillFormats(all, fields, 0, filter);
		logger.info("Number of BillFormats: {}", all.size());
		return all;
	}
		
	private void getAllBillFormats(List<BillFormat> list, String fields, int start, Map<String, String> filter) {
		int offset = start * LIMIT;

		try {
			List<BillFormat> billFormatList =  billFormatApi.listBillFormat(fields, offset, LIMIT, filter);

			if (!billFormatList.isEmpty()) {
				list.addAll(billFormatList);
				getAllBillFormats(list, fields, start + 1, filter);				
			}else {
				return;
			}
		} catch (ApiException e) {
			logger.error("Error: {}", e.getResponseBody());
			return;
		}		
	}

}
