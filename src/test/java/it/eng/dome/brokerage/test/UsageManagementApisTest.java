package it.eng.dome.brokerage.test;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import it.eng.dome.brokerage.api.UsageManagementApis;
import it.eng.dome.brokerage.api.fetch.FetchUtils;
import it.eng.dome.tmforum.tmf635.v4.ApiClient;
import it.eng.dome.tmforum.tmf635.v4.Configuration;
import it.eng.dome.tmforum.tmf635.v4.model.TimePeriod;
import it.eng.dome.tmforum.tmf635.v4.model.Usage;
import it.eng.dome.tmforum.tmf635.v4.model.UsageCharacteristic;
import it.eng.dome.tmforum.tmf635.v4.model.UsageCreate;
import it.eng.dome.tmforum.tmf635.v4.model.UsageSpecification;
import it.eng.dome.tmforum.tmf635.v4.model.UsageSpecificationCreate;
import it.eng.dome.tmforum.tmf635.v4.model.UsageSpecificationUpdate;
import it.eng.dome.tmforum.tmf635.v4.model.UsageStatusType;
import it.eng.dome.tmforum.tmf635.v4.model.UsageUpdate;

public class UsageManagementApisTest {
	
	final static String tmf635UsagePath = "tmf-api/usageManagement/v4";
	final static String tmfEndpoint = "https://dome-dev.eng.it";

	public static void main(String[] args) {

		/**
		 * Create Usage
		 */
//		String id = TestCreateUsage();
//		if (id != null) {
//			Usage u = TestGetUsage(id);
//			System.out.println(u.getId() + " " + u.getStatus() + " " + u.getUsageCharacteristic());
//		}
		
		/**
		 * Update Usage
		 */
//		String id = "urn:ngsi-ld:usage:e58e3706-f583-43dc-aa7b-ec933ce553d5";
//		TestUpdateUsage(id);
		
		/**
		 * Get All Usages
		 */
		TestGetAllUsages();
		TestGetAllUsageFiltered();
		
		/**
		 * Create UsageSpecification
		 */
//		String id = TestCreateUsageSpecification();
//		if (id != null) {
//			UsageSpecification us = TestGetUsageSpecification(id);
//			System.out.println(us.getId() + " " + us.getName() + " " + us.getVersion());
//		}

		/**
		 * Update UsageSpecification
		 */
//		String id = "urn:ngsi-ld:usageSpecification:9d37cacd-7e4f-4c54-8736-e45a23a5b7d4";
//		TestUpdateUsageSpecification(id);
		
		/**
		 * Get All UsageSpecifications
		 */
//		TestGetAllUsageSpecifications();		
		
	}
	
	protected static String TestCreateUsageSpecification() {
		
		ApiClient apiClientTmf635 = Configuration.getDefaultApiClient();
		apiClientTmf635.setBasePath(tmfEndpoint + "/" + tmf635UsagePath);
		
		UsageManagementApis apis = new UsageManagementApis(apiClientTmf635);
		
		UsageSpecificationCreate usc = new UsageSpecificationCreate();
		usc.setDescription("The UsageSpecification");
		usc.setName("Just a new UsageSpecification");
		usc.setVersion("1.0.0");
		TimePeriod tp = new TimePeriod();
		tp.setStartDateTime(OffsetDateTime.now());
		tp.setEndDateTime(OffsetDateTime.now().plusDays(10));
		usc.setValidFor(tp);
		usc.setLastUpdate(OffsetDateTime.now());
		
		String id = apis.createUsageSpecification(usc);	
		System.out.println("ID: " + id);
		return id;
	}
	
	protected static void TestGetAllUsageSpecifications() {

		ApiClient apiClientTmf635 = Configuration.getDefaultApiClient();
		apiClientTmf635.setBasePath(tmfEndpoint + "/" + tmf635UsagePath);
		
		UsageManagementApis apis = new UsageManagementApis(apiClientTmf635);		
		AtomicInteger count = new AtomicInteger(0);
		
		FetchUtils.streamAll(
	        apis::listUsageSpecifications, 	// method reference
	        null,                       	// fields
	        null, 				    		// filter
	        100                         	// pageSize
		) 
		.forEach(us -> { 
			count.incrementAndGet();
			System.out.println(count + " " + us.getId() + " → " + us.getName() + " / " + us.getVersion());
			}
		);		
		
		System.out.println("UsageSpecification found: " + count);
	}
	
	protected static UsageSpecification TestGetUsageSpecification(String id) {

		ApiClient apiClientTmf635 = Configuration.getDefaultApiClient();
		apiClientTmf635.setBasePath(tmfEndpoint + "/" + tmf635UsagePath);

		UsageManagementApis apis = new UsageManagementApis(apiClientTmf635);
		return apis.getUsageSpecification(id, null);
	}
	
	protected static boolean TestUpdateUsageSpecification(String id) {

		ApiClient apiClientTmf635 = Configuration.getDefaultApiClient();
		apiClientTmf635.setBasePath(tmfEndpoint + "/" + tmf635UsagePath);

		UsageManagementApis apis = new UsageManagementApis(apiClientTmf635);
		
		UsageSpecificationUpdate usu = new UsageSpecificationUpdate();
		usu.setDescription("Just update");
		usu.setVersion("1.0.1");
		usu.setLastUpdate(OffsetDateTime.now());
		
		return apis.updateUsageSpecification(id, usu);
	}
	
	protected static void TestGetAllUsages() {

		ApiClient apiClientTmf635 = Configuration.getDefaultApiClient();
		apiClientTmf635.setBasePath(tmfEndpoint + "/" + tmf635UsagePath);
		
		UsageManagementApis apis = new UsageManagementApis(apiClientTmf635);
		AtomicInteger count = new AtomicInteger(0);
		
		FetchUtils.streamAll(
	        apis::listUsages, 				// method reference
	        null,                       	// fields
	        null, 				   		// filter
	        100                         	// pageSize
		) 
		.forEach(us -> { 
			count.incrementAndGet();
			System.out.println(count + " " + us.getId() + " → " + us.getUsageDate() + " / " + us.getStatus());
			}
		);		
		
		System.out.println("Usage found: " + count);
	}
	
	protected static Usage TestGetUsage(String id) {

		ApiClient apiClientTmf635 = Configuration.getDefaultApiClient();
		apiClientTmf635.setBasePath(tmfEndpoint + "/" + tmf635UsagePath);

		UsageManagementApis apis = new UsageManagementApis(apiClientTmf635);
		return apis.getUsage(id, null);
	}

	protected static boolean TestUpdateUsage(String id) {

		ApiClient apiClientTmf635 = Configuration.getDefaultApiClient();
		apiClientTmf635.setBasePath(tmfEndpoint + "/" + tmf635UsagePath);

		UsageManagementApis apis = new UsageManagementApis(apiClientTmf635);
		
		UsageUpdate uu = new UsageUpdate();
		uu.setDescription("Update usage");
		uu.setStatus(UsageStatusType.REJECTED);
		
		return apis.updateUsage(id, uu);
	}
	
	protected static String TestCreateUsage() {
		
		ApiClient apiClientTmf635 = Configuration.getDefaultApiClient();
		apiClientTmf635.setBasePath(tmfEndpoint + "/" + tmf635UsagePath);
		
		UsageManagementApis apis = new UsageManagementApis(apiClientTmf635);
		
		UsageCreate uc = new UsageCreate();
		uc.setDescription("Test UsageCharacteristic");
		uc.setStatus(UsageStatusType.GUIDED);
		uc.setUsageDate(OffsetDateTime.now());
		uc.setUsageType("VOICE");
		
		UsageCharacteristic uch = new UsageCharacteristic();
		uch.setId("a0ba-b214-234a-ba15");
		uch.setName("endDate");
		uch.setValue("2025-10-01T13:55:18.707725700Z");
		uch.setValueType("value");
		List<UsageCharacteristic> listUch = new ArrayList<UsageCharacteristic>();
		listUch.add(uch);
		uc.setUsageCharacteristic(listUch);

		String id = apis.createUsage(uc);
		System.out.println("Usage id: " + id);
		return id;
	}
	
	protected static void TestGetAllUsageFiltered() {
		
		ApiClient apiClientTmf635 = Configuration.getDefaultApiClient();
		apiClientTmf635.setBasePath(tmfEndpoint + "/" + tmf635UsagePath);
		
		UsageManagementApis apis = new UsageManagementApis(apiClientTmf635);
		AtomicInteger count = new AtomicInteger(0);
		
		Map<String, String> filter = new HashMap<String, String>();
		
		TimePeriod tp = new TimePeriod();
		tp.setEndDateTime(OffsetDateTime.parse("2024-12-20T22:16:57.964063500Z"));
		tp.setStartDateTime(OffsetDateTime.parse("2024-12-20T00:16:56.9640635+01:00"));
		
		filter.put("usageDate.lt", tp.getEndDateTime().toString());
		filter.put("usageDate.gt", tp.getStartDateTime().toString());
				
		FetchUtils.streamAll(
	        apis::listUsages, 				// method reference
	        null,                       	// fields
	        filter, 				   		// filter
	        100                         	// pageSize
		) 
		.forEach(us -> { 
			count.incrementAndGet();
			System.out.println(count + " " + us.getId() + " → " + us.getUsageType() + " / " + us.getUsageDate());
			}
		);		
		
		System.out.println("Usage found: " + count);
	}
	
}
