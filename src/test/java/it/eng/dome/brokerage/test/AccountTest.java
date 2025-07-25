package it.eng.dome.brokerage.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.eng.dome.brokerage.api.AccountManagementApis;
import it.eng.dome.tmforum.tmf666.v4.ApiClient;
import it.eng.dome.tmforum.tmf666.v4.Configuration;
import it.eng.dome.tmforum.tmf666.v4.model.BillFormat;
import it.eng.dome.tmforum.tmf666.v4.model.BillingAccount;
import it.eng.dome.tmforum.tmf666.v4.model.PartyAccount;


public class AccountTest {

	final static String tmf666AccountPath = "tmf-api/accountManagement/v4";
	final static String tmfEndpoint = "https://dome-dev.eng.it";

	public static void main(String[] args) {

		/**
		 * Get All BillingAccounts
		 */
		TestGetAllBillingAccounts();
		TestGetFilteredBillingAccounts();
		
		
		/**
		 * Get All PartyAccounts
		 */
		//TestGetAllPartyAccounts();
		
		
		/**
		 * Get All BillFormats
		 */
		//TestGetAllBillFormats();
		
	}
	
	protected static void TestGetAllPartyAccounts() {

		ApiClient apiClientTmf666 = Configuration.getDefaultApiClient();
		apiClientTmf666.setBasePath(tmfEndpoint + "/" + tmf666AccountPath);

		AccountManagementApis apis = new AccountManagementApis(apiClientTmf666);

		List<PartyAccount> partyAccounts = apis.getAllPartyAccounts(null, null);

		int count = 0;
		for (PartyAccount partyAccount : partyAccounts) {
			System.out.println(	++count + " => " + partyAccount.getId() + " " + partyAccount.getName());
		}
	}
	
	
	protected static void TestGetFilteredBillingAccounts() {

		ApiClient apiClientTmf666 = Configuration.getDefaultApiClient();
		apiClientTmf666.setBasePath(tmfEndpoint + "/" + tmf666AccountPath);

		AccountManagementApis apis = new AccountManagementApis(apiClientTmf666);
		
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("relatedParty.id", "urn:ngsi-ld:organization:c9fcb8fa-3d7b-4fb8-b999-01ca987ceb16");

		List<BillingAccount> billingAccounts = apis.getAllBillingAccounts(null, filter);

		int count = 0;
		for (BillingAccount billingAccount : billingAccounts) {
			System.out.println(	++count + " => " + billingAccount.getId() + " " + billingAccount.getName() + " " + billingAccount.getLastModified());
		}
	}
	
	protected static void TestGetAllBillingAccounts() {

		ApiClient apiClientTmf666 = Configuration.getDefaultApiClient();
		apiClientTmf666.setBasePath(tmfEndpoint + "/" + tmf666AccountPath);

		AccountManagementApis apis = new AccountManagementApis(apiClientTmf666);

		List<BillingAccount> billingAccounts = apis.getAllBillingAccounts(null, null);

		int count = 0;
		for (BillingAccount billingAccount : billingAccounts) {
			System.out.println(	++count + " => " + billingAccount.getId() + " " + billingAccount.getName() + " " + billingAccount.getLastModified());
		}
	}
	
	protected static void TestGetAllBillFormats() {

		ApiClient apiClientTmf666 = Configuration.getDefaultApiClient();
		apiClientTmf666.setBasePath(tmfEndpoint + "/" + tmf666AccountPath);

		AccountManagementApis apis = new AccountManagementApis(apiClientTmf666);

		List<BillFormat> billFormats = apis.getAllBillFormats(null, null);

		int count = 0;
		for (BillFormat billFormat : billFormats) {
			System.out.println(	++count + " => " + billFormat.getId() + " " + billFormat.getName());
		}
	}
}
