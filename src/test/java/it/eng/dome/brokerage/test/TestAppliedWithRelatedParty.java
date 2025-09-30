package it.eng.dome.brokerage.test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import it.eng.dome.brokerage.api.AppliedCustomerBillRateApisX;
import it.eng.dome.tmforum.tmf678.v4.ApiClient;
import it.eng.dome.tmforum.tmf678.v4.Configuration;
import it.eng.dome.tmforum.tmf678.v4.model.AppliedCustomerBillingRate;
import it.eng.dome.tmforum.tmf678.v4.model.AppliedCustomerBillingRateCreate;
import it.eng.dome.tmforum.tmf678.v4.model.AppliedCustomerBillingRateUpdate;
import it.eng.dome.tmforum.tmf678.v4.model.BillRef;
import it.eng.dome.tmforum.tmf678.v4.model.BillingAccountRef;
import it.eng.dome.tmforum.tmf678.v4.model.CustomerBillCreate;
import it.eng.dome.tmforum.tmf678.v4.model.Money;
import it.eng.dome.tmforum.tmf678.v4.model.RelatedParty;

public class TestAppliedWithRelatedParty {

	final static String tmf678CustomerBillPath = "tmf-api/customerBillManagement/v4";
	final static String tmfEndpoint = "https://dome-dev.eng.it";

	public static void main(String[] args) {

		/**
		 *  Create RelatedParty
		 */
		
		String id = TestCreateApplyRelatedParty();
		if (id != null) {
			System.out.println(id);
			getApplied(id);
		}
		
		
		/**
		 *  Update RelatedParty
		 */
		/*
		//String id = "urn:ngsi-ld:applied-customer-billing-rate:8f99c450-139f-40be-b7ec-8fbd48e357de";
		String id = "urn:ngsi-ld:applied-customer-billing-rate:bbeaaf1d-fea1-46c1-8b1a-a6f972c1823d";
		TestRelatedParty(id);
		getApplied(id);
		*/
		
		/**
		 * Create CustomerBill
		 */
		//TestCustomerBill();
	
		
	}
	
	public static String TestCreateApplyRelatedParty() {
		ApiClient apiClientTmf678 = Configuration.getDefaultApiClient();
		apiClientTmf678.setBasePath(tmfEndpoint + "/" + tmf678CustomerBillPath);

		AppliedCustomerBillRateApisX apis = new AppliedCustomerBillRateApisX(apiClientTmf678);
						
		try {
			AppliedCustomerBillingRateCreate create = AppliedCustomerBillingRateCreate.fromJson(getJson());
			System.out.println(create.toJson());
			
			AppliedCustomerBillingRate applied = apis.createAppliedCustomerBillingRate(create);
			if (applied != null)
				return applied.getId();
			
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}

		return null;
	}
	


	public static void TestRelatedParty(String id) {
		ApiClient apiClientTmf678 = Configuration.getDefaultApiClient();
		apiClientTmf678.setBasePath(tmfEndpoint + "/" + tmf678CustomerBillPath);

		AppliedCustomerBillRateApisX apis = new AppliedCustomerBillRateApisX(apiClientTmf678);

		

		AppliedCustomerBillingRate applied = apis.getAppliedCustomerBillingRate(id, null);
		System.out.println("Applied Name: " + applied.getName());

		AppliedCustomerBillingRateUpdate update = new AppliedCustomerBillingRateUpdate();

		try {
			
			//update.setAtSchemaLocation(new URI("https://raw.githubusercontent.com/FIWARE/tmforum-api/refs/heads/main/extension-schemas/related-party-extension.json"));
			update.setAtSchemaLocation(new URI("https://raw.githubusercontent.com/DOME-Marketplace/dome-odrl-profile/refs/heads/add-related-party-ref/schemas/simplified/RelatedPartyRef.schema.json"));
			//update.setAtSchemaLocation(new URI("https://raw.githubusercontent.com/DOME-Marketplace/dome-odrl-profile/refs/heads/applied-with-related-party/schemas/simplified/AppliedCustomerBillingRateWithRelatedParty.schema.json"));
		} catch (URISyntaxException e) {
			System.out.println("Error: " + e.getMessage());
		}

		
		List<RelatedParty> parties = getRelatedParties();
				
		update.setRelatedParty(parties);

		update.setIsBilled(true);
		BillRef bill = new BillRef();
		if (applied.getBill() != null) {
			bill.setId(applied.getBill().getId());
			bill.setHref(applied.getBill().getId());
			
		}else {
			bill.setId("urn:ngsi-ld:customer-bill:d30f6b36-5f5b-4ece-bbc1-725ddc1b4bc9");
			bill.setHref("urn:ngsi-ld:customer-bill:d30f6b36-5f5b-4ece-bbc1-725ddc1b4bc9");
		}
		update.setBill(bill);
		update.setBillingAccount(applied.getBillingAccount());

		apis.updateAppliedCustomerBillingRate(applied.getId(), update);

	}

	
	public static void getApplied(String id) {
		
		ApiClient apiClientTmf678 = Configuration.getDefaultApiClient();
		apiClientTmf678.setBasePath(tmfEndpoint + "/" + tmf678CustomerBillPath);
		AppliedCustomerBillRateApisX apis = new AppliedCustomerBillRateApisX(apiClientTmf678);
		
		AppliedCustomerBillingRate apply = apis.getAppliedCustomerBillingRate(id, null);
		System.out.println(apply.toJson());
		System.out.println("RelatedParty: " + apply.getRelatedParty().size());
		if (apply != null) {
			for (int i = 0; i < apply.getRelatedParty().size(); i++) {
				System.out.println( (i+1) + " " + apply.getRelatedParty().get(i).getId()  + " " +  apply.getRelatedParty().get(i).getRole() + " " + apply.getRelatedParty().get(i).getName());
				
			}
		}
	}
	
	public static void getAppliedList() {
		
		ApiClient apiClientTmf678 = Configuration.getDefaultApiClient();
		apiClientTmf678.setBasePath(tmfEndpoint + "/" + tmf678CustomerBillPath);
		AppliedCustomerBillRateApisX apis = new AppliedCustomerBillRateApisX(apiClientTmf678);
		
		List<AppliedCustomerBillingRate> applied = apis.getAllAppliedCustomerBillingRates(null, null);
		
		int count = 0;
	 	for (AppliedCustomerBillingRate apply : applied) {
			System.out.print(++count + " => " + apply.getId());
			if (apply.getRelatedParty() != null) {
				System.out.println("-> " + apply.getRelatedParty().size());
			}else {
				System.out.println("-> 0");
			}
		}
	}

	public static void TestCustomerBill() {
		
		CustomerBillCreate customerBill = new CustomerBillCreate();
		BillingAccountRef ba = new BillingAccountRef();
		ba.setId("urn:ngsi-ld:billing-account:3bf025cb-1b58-48be-b0ae-bb0967d09d3b");
		ba.setName("Test create customer bill");
		customerBill.setBillingAccount(ba);
		Money money = new Money();
		money.setUnit("EUR");
		money.setValue(Float.valueOf("1.2"));
		customerBill.setAmountDue(money);
	
		
		List<RelatedParty> parties = getRelatedParties();
		
		customerBill.setRelatedParty(parties);
		
		System.out.println(customerBill.toJson());
		
		ApiClient apiClientTmf678 = Configuration.getDefaultApiClient();
		apiClientTmf678.setBasePath(tmfEndpoint + "/" + tmf678CustomerBillPath);

		AppliedCustomerBillRateApisX apis = new AppliedCustomerBillRateApisX(apiClientTmf678);
		
		String idCustomerBill = apis.createCustomerBill(customerBill);
		System.out.println("ID Customer Bill: " + idCustomerBill);
	}
	
	private static String getJson() {
		String file = "src/test/resources/applied.json";
		try {
			return new String(Files.readAllBytes(Paths.get(file)));
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
			return null;
		}
	}
	
	private static List<RelatedParty> getRelatedParties() {
		List<RelatedParty> parties = new ArrayList<RelatedParty>();
		RelatedParty rp = new RelatedParty();
		try {
			// first party
			rp.setId("urn:ngsi-ld:organization:f2ad85a5-9edf-497c-b343-f08899084ebb");
			rp.setHref(new URI("urn:ngsi-ld:organization:f2ad85a5-9edf-497c-b343-f08899084ebb"));
			rp.setRole("Buyer");
			rp.setName("Engineering Ingegneria Informatica S.p.A.");		
			parties.add(rp);
			
			// second party
			rp = new RelatedParty();
			rp.setId("urn:ngsi-ld:organization:38817de3-8c3e-4141-a344-86ffd915cc3b");
			rp.setHref(new URI("urn:ngsi-ld:organization:38817de3-8c3e-4141-a344-86ffd915cc3b"));
			rp.setRole("Seller");
			rp.setName("DHUB, Engineering D.HUB S.p.A.");
			parties.add(rp);		
	
			// third party
			rp = new RelatedParty();
			rp.setId("urn:ngsi-ld:organization:98a67a91-0e05-4dda-af43-253de1e4863d");
			rp.setHref(new URI("urn:ngsi-ld:organization:98a67a91-0e05-4dda-af43-253de1e4863d"));
			rp.setRole("BuyerOperator");
			rp.setName("CSI Piemonte - Consorzio per il Sistema Informativo del Piemonte");
			parties.add(rp);		
	
			// forty party
			rp = new RelatedParty();
			rp.setId("urn:ngsi-ld:organization:8d1c2262-7a5f-4c73-a56d-1861c4594bb7");
			rp.setHref(new URI("urn:ngsi-ld:organization:8d1c2262-7a5f-4c73-a56d-1861c4594bb7"));
			rp.setRole("SellerOperator");
			rp.setName("OLIMPO");
			parties.add(rp);
			
		} catch (URISyntaxException e) {
			System.out.println("Error: " + e.getMessage());
		}
		
		return parties;
	}
}
