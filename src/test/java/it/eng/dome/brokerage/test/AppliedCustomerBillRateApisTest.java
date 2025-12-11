package it.eng.dome.brokerage.test;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

import it.eng.dome.brokerage.api.AppliedCustomerBillRateApis;
import it.eng.dome.brokerage.api.fetch.FetchUtils;
import it.eng.dome.tmforum.tmf678.v4.ApiClient;
import it.eng.dome.tmforum.tmf678.v4.ApiException;
import it.eng.dome.tmforum.tmf678.v4.Configuration;
import it.eng.dome.tmforum.tmf678.v4.model.AppliedCustomerBillingRate;
import it.eng.dome.tmforum.tmf678.v4.model.AppliedCustomerBillingRateCreate;
import it.eng.dome.tmforum.tmf678.v4.model.AppliedCustomerBillingRateUpdate;
import it.eng.dome.tmforum.tmf678.v4.model.BillRef;
import it.eng.dome.tmforum.tmf678.v4.model.BillingAccountRef;
import it.eng.dome.tmforum.tmf678.v4.model.RelatedParty;
import it.eng.dome.tmforum.tmf678.v4.model.TimePeriod;


public class AppliedCustomerBillRateApisTest {

	final static String tmf678CustomerBillPath = "tmf-api/customerBillManagement/v4";
	final static String tmfEndpoint = "https://dome-dev.eng.it"; // "https://tmf.dome-marketplace-sbx.org";
	
	final static String SCHEMA = //"https://raw.githubusercontent.com/DOME-Marketplace/tmf-api/refs/heads/main/DOME/TrackedShareableEntity.schema.json"; 
			//"https://raw.githubusercontent.com/pasquy73/test-workflow/refs/heads/test_related/AppliedCustomerBillRate.schema.json"; 
			//"https://raw.githubusercontent.com/pasquy73/test-workflow/refs/heads/test_related/applied.schema.json";
	
			"https://raw.githubusercontent.com/DOME-Marketplace/tmf-api/refs/heads/main/test/AppliedCustomerBillingRate.schema.json";
	
    @Test
    public void RunTest() {
    	
//		TestAppliedCustomerBillRate();
//    	TestAppliedCustomerBillRateList();
    	
//		TestAppliedCustomerBillRateFetch();
		
//		TestAppliedCustomerBillRateFetchAll();

//		String id = "urn:ngsi-ld:applied-customer-billing-rate:14001008-6add-4c0b-8db1-a4daba3e9513";
//		TestUpdateApplyCustomerBillingRate(id);
//		TestAppliedCustomerBillRateById(id);
		
		
//		TestAppliedCustomerBillRateFilter();
//		TestAppliedCustomerBillRateWithFilter();
		
//		TestAppliedCustomerBillRateRevenueBilled();
		
//		TestCreateApplyRelatedParty();
//		TestCreateApplyCustomerBillingRate();
    }
    
	  public static void TestUpdateApplyCustomerBillingRate(String id) {
	    	
	    	ApiClient apiClientTmf678 = Configuration.getDefaultApiClient();
			apiClientTmf678.setBasePath(tmfEndpoint + "/" + tmf678CustomerBillPath);
			
			AppliedCustomerBillRateApis apis = new AppliedCustomerBillRateApis(apiClientTmf678);

			try {
				AppliedCustomerBillingRateUpdate acbru = new AppliedCustomerBillingRateUpdate();
				acbru.setIsBilled(true);
				BillRef br = new BillRef();
				br.setId("urn:ngsi-ld:customer-bill:32646090-fb77-4559-b5fc-aaa57a6559e9");
				acbru.setBill(br);
				
				acbru.setAtSchemaLocation(URI.create(SCHEMA));
				
				apis.updateAppliedCustomerBillingRate(id, acbru);
			} catch (ApiException e) {
				System.err.println("Error: " + e.getMessage());
			}
			
	  }
    
    public static void TestCreateApplyCustomerBillingRate() {
    	
    	ApiClient apiClientTmf678 = Configuration.getDefaultApiClient();
		apiClientTmf678.setBasePath(tmfEndpoint + "/" + tmf678CustomerBillPath);
		
		AppliedCustomerBillRateApis apis = new AppliedCustomerBillRateApis(apiClientTmf678);
		AppliedCustomerBillingRateCreate acbrc = new AppliedCustomerBillingRateCreate();
		
		acbrc.setType("recurring");
		acbrc.setName("New test");
		
		List<RelatedParty> relatedParty = new ArrayList<RelatedParty>();
		
		RelatedParty rp = new RelatedParty();
		rp.setRole("Buyer");
		rp.setId("urn:ngsi-ld:organization:caf3a7ce-00a0-4e3b-b2b4-bf1ad4a52eb7");
		rp.setName("IN2 INGENIERIA DE LA INFORMACION SOCIEDAD LIMITADA");
		rp.setAtReferredType("organization");
		relatedParty.add(rp);
		
		rp = new RelatedParty();
		rp.setRole("Seller");
		rp.setId("urn:ngsi-ld:organization:a195013a-a0e4-493a-810a-b040e10da58f");
		rp.setName("CloudFerro S.A.");
		rp.setAtReferredType("organization");
		relatedParty.add(rp);
			
		acbrc.setRelatedParty(relatedParty);
		
		acbrc.setIsBilled(false);
		BillingAccountRef ba = new BillingAccountRef();
		ba.setId("urn:ngsi-ld:billing-account:3bf025cb-1b58-48be-b0ae-bb0967d09d3b");
		acbrc.setBillingAccount(ba);
		
		//acbrc.setAtSchemaLocation(URI.create(SCHEMA));
		
		try {
			String id = apis.createAppliedCustomerBillingRate(acbrc);
			
			System.out.println("Applied id: " + id);
		} catch (ApiException e) {
			System.err.println("Error: " + e.getMessage());
		}
    	
    }
    
	
	public static void TestAppliedCustomerBillRateFetch() {
		
		ApiClient apiClientTmf678 = Configuration.getDefaultApiClient();
		apiClientTmf678.setBasePath(tmfEndpoint + "/" + tmf678CustomerBillPath);
		
		AppliedCustomerBillRateApis apis = new AppliedCustomerBillRateApis(apiClientTmf678);
		AtomicInteger count = new AtomicInteger(0);

		FetchUtils.fetchByBatch(
			apis::listAppliedCustomerBillingRates, 
			null, 
			Map.of("isBilled", "true"), 
			10,
			batch -> {
			    batch.forEach(rate -> {
			    	System.out.println(count.incrementAndGet() + " " + rate.getId() + " → " + rate.getName());
			    });
			}
		);
		
		System.out.println("AppliedCustomerBillingRate found: " + count.get());
	}
	
	
	public static void TestAppliedCustomerBillRateFetchAll() {
		
		ApiClient apiClientTmf678 = Configuration.getDefaultApiClient();
		apiClientTmf678.setBasePath(tmfEndpoint + "/" + tmf678CustomerBillPath);
		
		AppliedCustomerBillRateApis apis = new AppliedCustomerBillRateApis(apiClientTmf678);
		
		try {
			List<AppliedCustomerBillingRate> applied = FetchUtils.fetchAll(
			    apis::listAppliedCustomerBillingRates,
			    null,
			    null, // Map.of("isBilled", "true"),
			    10
			);
			
			int count = 0;
			for (AppliedCustomerBillingRate apply : applied) {
				System.out.println(++count + " " + apply.getId() + " → " + apply.getName());
			}
			
			System.out.println("AppliedCustomerBillingRate found: " + applied.size());
		}catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	@Test
	public static void TestAppliedCustomerBillRate() {
		
		ApiClient apiClientTmf678 = Configuration.getDefaultApiClient();
		apiClientTmf678.setBasePath(tmfEndpoint + "/" + tmf678CustomerBillPath);
		
		AppliedCustomerBillRateApis apis = new AppliedCustomerBillRateApis(apiClientTmf678);
		AtomicInteger count = new AtomicInteger(0);
		
		FetchUtils.streamAll(
		        apis::listAppliedCustomerBillingRates,    // method reference
		        null,                                     // fields
		        null, //Map.of("isBilled", "true"),               // filter
		        100                                       // pageSize
		) 
		//.filter(rate -> "recurring".equals(rate.getType()))
		//.limit(5)
		.forEach(rate -> { 
			count.incrementAndGet();
			System.out.println(count + " " + rate.getId() + " → " + rate.getName());
			}
		);
		
		System.out.println("AppliedCustomerBillingRate found: " + count.get());
	}
	
	@Test
	public static void TestAppliedCustomerBillRateList() {
		
		ApiClient apiClientTmf678 = Configuration.getDefaultApiClient();
		apiClientTmf678.setBasePath(tmfEndpoint + "/" + tmf678CustomerBillPath);
		
		AppliedCustomerBillRateApis apis = new AppliedCustomerBillRateApis(apiClientTmf678);
	
		List<AppliedCustomerBillingRate> acbrs = FetchUtils.streamAll(
		        apis::listAppliedCustomerBillingRates,  // method reference
		        null,                                   // fields
		        null,               					// filter
		        5                                       // pageSize
		).toList();
		
		System.out.println("AppliedCustomerBillingRateList found: " + acbrs.size());
	}
	
	
	public static void TestAppliedCustomerBillRateById(String id) {
		
		ApiClient apiClientTmf678 = Configuration.getDefaultApiClient();
		apiClientTmf678.setBasePath(tmfEndpoint + "/" + tmf678CustomerBillPath);
		
		AppliedCustomerBillRateApis apis = new AppliedCustomerBillRateApis(apiClientTmf678);
		try {
			AppliedCustomerBillingRate apply = apis.getAppliedCustomerBillingRate(id, null);
			if (apply != null) {
				System.out.println(apply.getName() + " " + apply.getLastUpdate());
				System.out.println("RelatedParty: " + apply.getRelatedParty().size());
			}	
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}


	public static void TestAppliedCustomerBillRateFilter() {
		ApiClient apiClientTmf678 = Configuration.getDefaultApiClient();
		apiClientTmf678.setBasePath(tmfEndpoint + "/" + tmf678CustomerBillPath);
		
		AppliedCustomerBillRateApis apis = new AppliedCustomerBillRateApis(apiClientTmf678);
		AtomicInteger count = new AtomicInteger(0);
		
		TimePeriod tp = new TimePeriod();
		tp.setEndDateTime(OffsetDateTime.parse("2025-07-01T10:04:38.983Z"));
		tp.setStartDateTime(OffsetDateTime.parse("2025-06-30T10:04:38.983Z"));
		
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("rateType", "pay-per-use");
		filter.put("periodCoverage.endDateTime.eq", tp.getEndDateTime().toString());
		filter.put("periodCoverage.startDateTime.eq", tp.getStartDateTime().toString());
		filter.put("product.id", "urn:ngsi-ld:product:f942601c-1902-4b09-a498-23d844d21972");
		
		FetchUtils.streamAll(
		        apis::listAppliedCustomerBillingRates,    // method reference
		        "isBilled,type",                          // fields
		        filter,    								  // filter
		        100                                       // pageSize
		) 
		.forEach(rate -> { 
			count.incrementAndGet();
			System.out.println(count + " " + rate.getId() + " → " + rate.getType());
			}
		);		
	}
	
	
	public static void TestAppliedCustomerBillRateWithFilter() {
		ApiClient apiClientTmf678 = Configuration.getDefaultApiClient();
		apiClientTmf678.setBasePath(tmfEndpoint + "/" + tmf678CustomerBillPath);
		
		AppliedCustomerBillRateApis apis = new AppliedCustomerBillRateApis(apiClientTmf678);
		AtomicInteger count = new AtomicInteger(0);
		
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("lastUpdate.gt", "2025-12-04T10:04:38.983Z");

		
		FetchUtils.streamAll(
		        apis::listAppliedCustomerBillingRates,    // method reference
		        "lastUpdate,name",                          // fields
		        filter,    								  // filter
		        100                                       // pageSize
		) 
		.forEach(rate -> { 
			count.incrementAndGet();
			System.out.println(count + " " + rate.getId() + " → " + rate.getName() + " / " + rate.getLastUpdate());
			}
		);		
	}
	
	public static void TestAppliedCustomerBillRateRevenueBilled() {
		ApiClient apiClientTmf678 = Configuration.getDefaultApiClient();
		apiClientTmf678.setBasePath(tmfEndpoint + "/" + tmf678CustomerBillPath);
		
		AppliedCustomerBillRateApis apis = new AppliedCustomerBillRateApis(apiClientTmf678);

		Map<String, String> filter = new HashMap<String, String>();
		
		TimePeriod tp = new TimePeriod();
		
		// January
//		tp.setStartDateTime(OffsetDateTime.parse("2025-01-01T00:00:00.0Z"));
//		tp.setEndDateTime(OffsetDateTime.parse("2025-01-28T23:59:59.999Z"));

		// February
//		tp.setStartDateTime(OffsetDateTime.parse("2025-02-01T00:00:00.0Z"));
//		tp.setEndDateTime(OffsetDateTime.parse("2025-02-28T23:59:59.999Z"));
		
		// March
//		tp.setStartDateTime(OffsetDateTime.parse("2025-03-01T00:00:00.0Z"));
//		tp.setEndDateTime(OffsetDateTime.parse("2025-03-28T23:59:59.999Z"));

		// April
//		tp.setStartDateTime(OffsetDateTime.parse("2025-04-01T00:00:00.0Z"));
//		tp.setEndDateTime(OffsetDateTime.parse("2025-04-30T23:59:59.999Z"));
		
		// May
//		tp.setStartDateTime(OffsetDateTime.parse("2025-05-01T00:00:00.0Z"));
//		tp.setEndDateTime(OffsetDateTime.parse("2025-05-31T23:59:59.999Z"));
		
		// June
//		tp.setStartDateTime(OffsetDateTime.parse("2025-06-01T00:00:00.0Z"));
//		tp.setEndDateTime(OffsetDateTime.parse("2025-06-30T23:59:59.999Z"));

		tp.setStartDateTime(OffsetDateTime.parse("2025-01-01T00:00:00.0Z"));
		tp.setEndDateTime(OffsetDateTime.parse("2025-07-24T23:59:59.999Z"));
		
		filter.put("isBilled", "true");
		filter.put("relatedParty", "urn:ngsi-ld:organization:f2ad85a5-9edf-497c-b343-f08899084ebb");
		//filter.put("relatedParty.role", "Seller");
		filter.put("date.lt", tp.getEndDateTime().toString());
		filter.put("date.gt", tp.getStartDateTime().toString());
		
		AtomicInteger count = new AtomicInteger(0);
		
		FetchUtils.streamAll(
		        apis::listAppliedCustomerBillingRates,    // method reference
		        "isBilled,type",                          // fields
		        filter,    								  // filter
		        100                                       // pageSize
		) 
		.forEach(rate -> { 
			count.incrementAndGet();
			System.out.println(count + " " + rate.getId() + " → " + rate.getTaxExcludedAmount() + " / " + rate.getName() );
			}
		);	

		System.out.println("AppliedCustomerBillingRate found: " + count);
	}
	
	public static String TestCreateApplyRelatedParty() {
		ApiClient apiClientTmf678 = Configuration.getDefaultApiClient();
		apiClientTmf678.setBasePath(tmfEndpoint + "/" + tmf678CustomerBillPath);

		AppliedCustomerBillRateApis apis = new AppliedCustomerBillRateApis(apiClientTmf678);
						
		try {
			AppliedCustomerBillingRateCreate create = AppliedCustomerBillingRateCreate.fromJson(getJson());
			System.out.println(create.toJson());
			
			String id = apis.createAppliedCustomerBillingRate(create);
			System.out.println("Applied id: " + id);
			return id;
			
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}

		return null;
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
}
