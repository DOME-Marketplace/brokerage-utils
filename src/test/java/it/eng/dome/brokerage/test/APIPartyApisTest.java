package it.eng.dome.brokerage.test;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;

import it.eng.dome.brokerage.api.APIPartyApis;
import it.eng.dome.brokerage.api.fetch.FetchUtils;
import it.eng.dome.tmforum.tmf632.v4.ApiClient;
import it.eng.dome.tmforum.tmf632.v4.ApiException;
import it.eng.dome.tmforum.tmf632.v4.Configuration;
import it.eng.dome.tmforum.tmf632.v4.model.Individual;
import it.eng.dome.tmforum.tmf632.v4.model.Organization;

public class APIPartyApisTest {

	final static String tmf63ePartyPath = "tmf-api/party/v4";
	final static String tmfEndpoint = "https://dome-dev.eng.it"; 

	@Test
	public void RunTest() {

		/**
		 * Test Organizations
		 */
//		TestOrganizations();
//		TestOrganization();
		
		
		/**
		 * Test Individuals
		 */
//		TestIndividuals();
//		TestIndividual();		
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
			System.out.println(count + " " + organization.getId() + " → " + organization.getTradingName());
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
			System.out.println(count + " " + individual.getId() + " → " + individual.getFamilyName());
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
}
