package it.eng.dome.brokerage.test;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import it.eng.dome.brokerage.api.ProductOfferingApis;
import it.eng.dome.tmforum.tmf620.v4.ApiClient;
import it.eng.dome.tmforum.tmf620.v4.Configuration;
import it.eng.dome.tmforum.tmf620.v4.model.ProductOffering;
import it.eng.dome.tmforum.tmf620.v4.model.ProductOfferingCreate;
import it.eng.dome.tmforum.tmf620.v4.model.ProductOfferingPriceRefOrValue;
import it.eng.dome.tmforum.tmf620.v4.model.ProductOfferingUpdate;
import it.eng.dome.tmforum.tmf620.v4.model.ProductSpecificationRef;
import it.eng.dome.tmforum.tmf620.v4.model.TimePeriod;

public class ProductOfferingTest {

	final static String tmf620ProductOfferingPath = "tmf-api/productCatalogManagement/v4";
//	final static String tmfEndpoint = "https://dome-dev.eng.it";
	final static String tmfEndpoint = "https://tmf.dome-marketplace-sbx.org";

	public static void main(String[] args) {
		
		/*
		 * //TEST CREATE PO String id = TestCreateProductOffering(); if (id != null) {
		 * ProductOffering po = TestGetProductOffering(id);
		 * System.out.println(po.getId() + " " + po.getName() + " " + po.getVersion());
		 * }
		 */
		
		/**
		 * UPDATE PO
		 */
//		String idPo = "urn:ngsi-ld:product-offering:2ba20357-d556-438c-9df9-7c9b399c2f5f";
//		TestUpdateProductOffering(idPo);
		
		
		// TEST GET PO by ID
		String id2 = "urn:ngsi-ld:product-offering:2ba20357-d556-438c-9df9-7c9b399c2f5f";
		ProductOffering po = TestGetProductOffering(id2);
		System.out.println(po.getId() + " " + po.getName() + " " + po.getDescription());
		
		// TEST GET all POs
		TestGetAllProductOffering();
	}
	
	protected static String TestCreateProductOffering() {
		
		ApiClient apiClientTmf620 = Configuration.getDefaultApiClient();
		apiClientTmf620.setBasePath(tmfEndpoint + "/" + tmf620ProductOfferingPath);
		
		ProductOfferingApis apis = new ProductOfferingApis(apiClientTmf620);
		
		ProductOfferingCreate poc = new ProductOfferingCreate();
		poc.setName("Test name to test PO create");
		poc.setDescription("Offering Memberiship Test Apis");
		poc.isBundle(false);
		poc.setLastUpdate(OffsetDateTime.now());
		poc.setLifecycleStatus("Launched");
		poc.setVersion("1");
		
		ProductOfferingPriceRefOrValue popRef = new ProductOfferingPriceRefOrValue();
		popRef.setId("urn:ngsi-ld:product-offering-price:91a5b7f3-afb1-427c-bed1-85332ee1448d");
		try {
			popRef.setHref(new URI("urn:ngsi-ld:product-offering-price:91a5b7f3-afb1-427c-bed1-85332ee1448d"));
		} catch (URISyntaxException e) {
			System.out.println(e);
		}
		popRef.setName("Plan");
		
		List<ProductOfferingPriceRefOrValue> popsRef = new ArrayList<>();
		popsRef.add(popRef);
		poc.setProductOfferingPrice(popsRef);
		
		ProductSpecificationRef psRef = new ProductSpecificationRef();
		psRef.setId("urn:ngsi-ld:product-specification:10ebf2c1-fe79-4191-81a3-b58207307c5a");
		try {
			psRef.setHref(new URI("urn:ngsi-ld:product-specification:10ebf2c1-fe79-4191-81a3-b58207307c5a"));
		} catch (URISyntaxException e) {
			System.out.println(e);
		}
		psRef.setName("Basic Plan Test product specification");
		psRef.setVersion("1");
		
		poc.setProductSpecification(psRef);
		
		TimePeriod tp = new TimePeriod();
		tp.setStartDateTime(OffsetDateTime.now());
		tp.setEndDateTime(OffsetDateTime.now().plusDays(10));
		poc.setValidFor(tp);
		
		ProductOffering po = apis.createProductOffering(poc);
		if(po!=null) {
			System.out.println("PO creation success");
		}
		else {
			System.out.println("PO creation failed");
		}
		return po.getId();
	}
	
	protected static boolean TestUpdateProductOffering(String id) {

		ApiClient apiClientTmf620 = Configuration.getDefaultApiClient();
		apiClientTmf620.setBasePath(tmfEndpoint + "/" + tmf620ProductOfferingPath);

		ProductOfferingApis apis = new ProductOfferingApis(apiClientTmf620);
		
		ProductOfferingUpdate pou = new ProductOfferingUpdate();
		pou.setName("Test name to test PO create");
		pou.setDescription("Just Upadate to test");
		pou.setVersion("1.1");
		
		return apis.updateProductOffering(id, pou);
	}
	
	protected static ProductOffering TestGetProductOffering(String id) {

		ApiClient apiClientTmf620 = Configuration.getDefaultApiClient();
		apiClientTmf620.setBasePath(tmfEndpoint + "/" + tmf620ProductOfferingPath);

		ProductOfferingApis apis = new ProductOfferingApis(apiClientTmf620);
		return apis.getProductOffering(id, null);
	}
	
	protected static void TestGetAllProductOffering() {

		ApiClient apiClientTmf620 = Configuration.getDefaultApiClient();
		apiClientTmf620.setBasePath(tmfEndpoint + "/" + tmf620ProductOfferingPath);

		ProductOfferingApis apis = new ProductOfferingApis(apiClientTmf620);
		
		List<ProductOffering> pos = apis.getAllProductOfferings(null, null);
		
		int count = 0;
	 	for (ProductOffering po : pos) {
			System.out.println(++count + " => " + po.getId() + " " + po.getName() + " " + po.getDescription());
		}
	}
}
