package it.eng.dome.brokerage.test;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import it.eng.dome.brokerage.api.UsageManagementApis;
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

public class UsageTest {
	
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
//		String id = "urn:ngsi-ld:usage:cecdee70-4891-4781-af58-d80cee9d6528";
//		TestUpdateUsage(id);
		
		/**
		 * Get All Usages
		 */
//		TestGetAllUsages();
		
		
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
//		String id = "urn:ngsi-ld:usageSpecification:e51cbdaa-5d62-431f-b0f2-b532cd8b82a7";
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
		usc.setName("New UsageSpecification");
		usc.setVersion("0.1.12");
		TimePeriod tp = new TimePeriod();
		tp.setStartDateTime(OffsetDateTime.now());
		tp.setEndDateTime(OffsetDateTime.now().plusDays(10));
		usc.setValidFor(tp);
		usc.setLastUpdate(OffsetDateTime.now());
		
		UsageSpecification us = apis.createUsageSpecification(usc);	
		return us.getId();
	}
	
	protected static void TestGetAllUsageSpecifications() {

		ApiClient apiClientTmf635 = Configuration.getDefaultApiClient();
		apiClientTmf635.setBasePath(tmfEndpoint + "/" + tmf635UsagePath);
		
		UsageManagementApis apis = new UsageManagementApis(apiClientTmf635);
		
		List<UsageSpecification> specifications = apis.getAllUsageSpecifications(null, null);
		
		int count = 0;
	 	for (UsageSpecification specification : specifications) {
			System.out.println(++count + " => " + specification.getId() + " " + specification.getName() + " " + specification.getVersion());
		}
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
		usu.setVersion("1.2.3");
		usu.setLastUpdate(OffsetDateTime.now());
		
		return apis.updateUsageSpecification(id, usu);
	}
	
	protected static void TestGetAllUsages() {

		ApiClient apiClientTmf635 = Configuration.getDefaultApiClient();
		apiClientTmf635.setBasePath(tmfEndpoint + "/" + tmf635UsagePath);
		
		UsageManagementApis apis = new UsageManagementApis(apiClientTmf635);
		
		List<Usage> usages = apis.getAllUsages(null, null);
		
		int count = 0;
	 	for (Usage usage : usages) {
			System.out.println(++count + " => " + usage.getId() + " " + usage.getUsageDate() + " " + usage.getStatus());
		}
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
		uu.setDescription("Just update");
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
		uch.setId("a0ba-b532cd8b82a7");
		uch.setName("endDate");
		uch.setValue("2025-06-06T10:05:18.707725700Z");
		uch.setValueType("string");
		List<UsageCharacteristic> listUch = new ArrayList<UsageCharacteristic>();
		listUch.add(uch);
		uc.setUsageCharacteristic(listUch);

		Usage u = apis.createUsage(uc);
		
		return u.getId();
	}
}
