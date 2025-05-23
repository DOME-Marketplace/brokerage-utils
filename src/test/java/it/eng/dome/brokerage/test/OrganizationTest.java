package it.eng.dome.brokerage.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.eng.dome.brokerage.api.OrganizationApis;
import it.eng.dome.tmforum.tmf632.v4.ApiClient;
import it.eng.dome.tmforum.tmf632.v4.Configuration;
import it.eng.dome.tmforum.tmf632.v4.model.Organization;

public class OrganizationTest {

	final static String tmf63ePartyPath = "tmf-api/party/v4";
	final static String tmfEndpoint = "https://<tmforum-endpoint>"; 

	public static void main(String[] args) {

		//TestApis();
		TestListApis();
	}

	public static void TestListApis() {

		ApiClient apiClientTmf637 = Configuration.getDefaultApiClient();
		apiClientTmf637.setBasePath(tmfEndpoint + "/" + tmf63ePartyPath);
		
		OrganizationApis apis = new OrganizationApis(apiClientTmf637);

		Map<String, String> filter = new HashMap<String, String>();
		filter.put("isBundle", "false");
		
		List<Organization> organizations = apis.getOrganizations(null, filter);
				
		int count = 0;
		for (Organization organization : organizations) {
			System.out.println(++count + " => " + organization.getId() + " / " + organization.getName());
		}
	
	}
	
	public static void TestApis() {

		ApiClient apiClientTmf637 = Configuration.getDefaultApiClient();
		apiClientTmf637.setBasePath(tmfEndpoint + "/" + tmf63ePartyPath);
		
		OrganizationApis apis = new OrganizationApis(apiClientTmf637);
		
		String id = "urn:ngsi-ld:organization:38817de3-8c3e-4141-a344-86ffd915cc3b";
		Organization organization = apis.getOrganization(id, null);
		System.out.println(organization.getTradingName() );
	
	}

}
