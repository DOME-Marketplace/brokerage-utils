package it.eng.dome.brokerage.test;

import java.net.URI;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

import it.eng.dome.brokerage.api.APIPartyApis;
import it.eng.dome.brokerage.api.fetch.FetchUtils;
import it.eng.dome.tmforum.tmf632.v4.ApiClient;
import it.eng.dome.tmforum.tmf632.v4.ApiException;
import it.eng.dome.tmforum.tmf632.v4.Configuration;
import it.eng.dome.tmforum.tmf632.v4.model.Individual;
import it.eng.dome.tmforum.tmf632.v4.model.IndividualCreate;
import it.eng.dome.tmforum.tmf632.v4.model.IndividualUpdate;
import it.eng.dome.tmforum.tmf632.v4.model.Organization;
import it.eng.dome.tmforum.tmf632.v4.model.OrganizationCreate;
import it.eng.dome.tmforum.tmf632.v4.model.OrganizationUpdate;

public class APIPartyApisTest {

	final static String tmf63ePartyPath = "tmf-api/party/v4";
	final static String tmfEndpoint = "https://dome-dev.eng.it"; 
	
	final static String SCHEMA = "https://raw.githubusercontent.com/pasquy73/test-workflow/refs/heads/test_related/AppliedCustomerBillRate.schema.json";

	@Test
	public void RunTest() {

		/**
		 * Test Organizations
		 */
//		TestOrganization();
//		TestCreateOrganization();
//		String id = "urn:ngsi-ld:organization:d78d17c0-996e-4cee-a100-6d6f65a50459";
//		TestUpdateOrganization(id);
//		TestOrganizations();
		
		/**
		 * Test Individuals
		 */
//		String id = "urn:ngsi-ld:individual:88437d57-cdf3-4986-b91e-c793b4f4d1c3";
//		TestUpdateIndividual(id);
//		TestIndividuals();
//		TestIndividual();	
//		TestCreateIndividual();
	}
	
	public static void TestCreateOrganization() {

		ApiClient apiClientTmf637 = Configuration.getDefaultApiClient();
		apiClientTmf637.setBasePath(tmfEndpoint + "/" + tmf63ePartyPath);
		
		APIPartyApis apis = new APIPartyApis(apiClientTmf637);
		
		OrganizationCreate oc = new OrganizationCreate();
		oc.setName("Organization for testing lastUpdate with schema");
		oc.setTradingName("Dome Test API");
		
		oc.setAtSchemaLocation(URI.create(SCHEMA));
		
		String id = null;
		try {
			id = apis.createOrganization(oc);
		} catch (ApiException e) {
			System.err.println("Error: " + e.getMessage());
		}

		System.out.println("Product id: " + id);
	}

	
	public static void TestUpdateOrganization(String id) {

		ApiClient apiClientTmf637 = Configuration.getDefaultApiClient();
		apiClientTmf637.setBasePath(tmfEndpoint + "/" + tmf63ePartyPath);
		
		APIPartyApis apis = new APIPartyApis(apiClientTmf637);
		
		OrganizationUpdate ou = new OrganizationUpdate();
		ou.setIsLegalEntity(true);
		 		
		try {
			apis.updateOrganization(id, ou);
		} catch (ApiException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	public static void TestOrganizations() {

		ApiClient apiClientTmf637 = Configuration.getDefaultApiClient();
		apiClientTmf637.setBasePath(tmfEndpoint + "/" + tmf63ePartyPath);
		
		APIPartyApis apis = new APIPartyApis(apiClientTmf637);
		AtomicInteger count = new AtomicInteger(0);
		
		FetchUtils.streamAll(
	        apis::listOrganizations,    // method reference
	        null,                       // fields
	        null,            			// filter
	        100                         // pageSize
		) 
		.forEach(organization -> { 
			count.incrementAndGet();
			System.out.println(count + " " + organization.getId() + " → " + organization.getLastUpdate());
			}
		);
	
		System.out.println("Organization found: " + count.get());
	}
	
	public static void TestOrganization() {

		ApiClient apiClientTmf637 = Configuration.getDefaultApiClient();
		apiClientTmf637.setBasePath(tmfEndpoint + "/" + tmf63ePartyPath);
		
		APIPartyApis apis = new APIPartyApis(apiClientTmf637);
		
		String id = "urn:ngsi-ld:organization:38817de3-8c3e-4141-a344-86ffd915cc3b";
		
		try {
			Organization organization = apis.getOrganization(id, null);
			if (organization != null) {
				System.out.println(organization.getTradingName() );	
			}
			
		} catch (ApiException e) {
			System.err.println("Error: " + e.getMessage());
		}
		
	}

	
	public static void TestIndividuals() {

		ApiClient apiClientTmf637 = Configuration.getDefaultApiClient();
		apiClientTmf637.setBasePath(tmfEndpoint + "/" + tmf63ePartyPath);
		
		APIPartyApis apis = new APIPartyApis(apiClientTmf637);
		AtomicInteger count = new AtomicInteger(0);
	
		FetchUtils.streamAll(
	        apis::listIndividuals,      // method reference
	        null,                       // fields
	        null,              			// filter
	        100                         // pageSize
		) 
		.forEach(individual -> { 
			count.incrementAndGet();
			System.out.println(count + " " + individual.getId() + " → " + individual.getLastUpdate());
			}
		);
	
		System.out.println("Individual found: " + count.get());
	}
	
	public static void TestIndividual() {

		ApiClient apiClientTmf637 = Configuration.getDefaultApiClient();
		apiClientTmf637.setBasePath(tmfEndpoint + "/" + tmf63ePartyPath);
		
		APIPartyApis apis = new APIPartyApis(apiClientTmf637);
		
		String id = "urn:ngsi-ld:individual:88437d57-cdf3-4986-b91e-c793b4f4d1c3";
		try {
			Individual individual = apis.getIndividual(id, null);
			if (individual != null) {
				System.out.println(individual.getFamilyName() );	
			}
			
		} catch (ApiException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	public static void TestCreateIndividual() {

		ApiClient apiClientTmf637 = Configuration.getDefaultApiClient();
		apiClientTmf637.setBasePath(tmfEndpoint + "/" + tmf63ePartyPath);
		
		APIPartyApis apis = new APIPartyApis(apiClientTmf637);
		
		IndividualCreate ic = new IndividualCreate();
		ic.setFullName("DOME TEST");
		ic.setCountryOfBirth("Italy");
		
		ic.setAtSchemaLocation(URI.create(SCHEMA));
		
		String id = null;
		try {
			id = apis.createIndividual(ic);
		} catch (ApiException e) {
			System.err.println("Error: " + e.getMessage());
		}

		System.out.println("Product id: " + id);
	}
	
	
	public static void TestUpdateIndividual(String id) {

		ApiClient apiClientTmf637 = Configuration.getDefaultApiClient();
		apiClientTmf637.setBasePath(tmfEndpoint + "/" + tmf63ePartyPath);
		
		APIPartyApis apis = new APIPartyApis(apiClientTmf637);
		
		IndividualUpdate iu = new IndividualUpdate();
		iu.setCountryOfBirth("Italy");
		 		
		try {
			apis.updateIndividual(id, iu);
		} catch (ApiException e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
}
