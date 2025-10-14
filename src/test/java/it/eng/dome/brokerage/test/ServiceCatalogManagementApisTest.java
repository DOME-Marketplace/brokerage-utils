package it.eng.dome.brokerage.test;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import it.eng.dome.brokerage.api.ServiceCatalogManagementApis;
import it.eng.dome.brokerage.api.fetch.FetchUtils;
import it.eng.dome.tmforum.tmf633.v4.ApiClient;
import it.eng.dome.tmforum.tmf633.v4.Configuration;
import it.eng.dome.tmforum.tmf633.v4.model.ServiceSpecification;

public class ServiceCatalogManagementApisTest {
    final static String tmf633serviceCatalogManagementPath = "tmf-api/serviceCatalogManagement/v4";
    final static String tmfEndpoint = "https://dome-dev.eng.it";

    public static void main(String[] args) {

        /**
         * Get All ServiceSpecification
         */
//        TestGetAllServiceSpecification();        
//        TestGetListServiceSpecification();
//        TestExitListServiceSpecification();


        /**
         * Get ServiceSpecification by ID
         */
        String id = "urn:ngsi-ld:service-specification:44e32a74-94ee-4625-a6e3-ca69a66b3881";
        TestGetServiceSpecification(id);

    }

    protected static void TestExitListServiceSpecification() {

        ApiClient apiClientTmf634 = Configuration.getDefaultApiClient();
        apiClientTmf634.setBasePath(tmfEndpoint + "/" + tmf633serviceCatalogManagementPath);

        ServiceCatalogManagementApis apis = new ServiceCatalogManagementApis(apiClientTmf634); 
        AtomicInteger count = new AtomicInteger(0);

        FetchUtils.streamAll(
                apis::listServiceSpecifications,
                null,
                null,
                100
        )
        .anyMatch(rs -> {
            System.out.println(count.incrementAndGet() + " " + rs.getId() + " → " + rs.getName() + " / " + rs.getLifecycleStatus());
            return "urn:ngsi-ld:service-specification:44e32a74-94ee-4625-a6e3-ca69a66b3881".equals(rs.getId());
        });
    }
    
    
    protected static void TestGetListServiceSpecification() {

        ApiClient apiClientTmf634 = Configuration.getDefaultApiClient();
        apiClientTmf634.setBasePath(tmfEndpoint + "/" + tmf633serviceCatalogManagementPath);

        ServiceCatalogManagementApis apis = new ServiceCatalogManagementApis(apiClientTmf634);        
		
        List<ServiceSpecification> serviceSpecifications = FetchUtils.streamAll(
	        apis::listServiceSpecifications,		// method reference
	        null,                       			// fields
	        null, 									// filter
	        100                         			// pageSize
		).toList();		
		
		System.out.println("ServiceSpecification found: " + serviceSpecifications.size());

		int count = 0;
		for (ServiceSpecification rs : serviceSpecifications) {

		    System.out.println(++count + " " + rs.getId() + " → " + rs.getName() + " / " + rs.getLifecycleStatus());
		    
		    if ("urn:ngsi-ld:service-specification:44e32a74-94ee-4625-a6e3-ca69a66b3881".equals(rs.getId())) {
		    	System.out.println("exit");
		        break;
		    }
		}
    }

    
    protected static void TestGetAllServiceSpecification() {

        ApiClient apiClientTmf634 = Configuration.getDefaultApiClient();
        apiClientTmf634.setBasePath(tmfEndpoint + "/" + tmf633serviceCatalogManagementPath);

        ServiceCatalogManagementApis apis = new ServiceCatalogManagementApis(apiClientTmf634);        
        AtomicInteger count = new AtomicInteger(0);
		
        FetchUtils.streamAll(
	        apis::listServiceSpecifications,		// method reference
	        null,                       			// fields
	        null, 									// filter
	        100                         			// pageSize
		) 
		.forEach(rs -> { 
			count.incrementAndGet();
			System.out.println(count + " " + rs.getId() + " → " + rs.getName() + " / " + rs.getLifecycleStatus());
			}
		);		
		
		System.out.println("ServiceSpecification found: " + count);
    }
    
    protected static void TestGetServiceSpecification(String id) {

        ApiClient apiClientTmf633 = Configuration.getDefaultApiClient();
        apiClientTmf633.setBasePath(tmfEndpoint + "/" + tmf633serviceCatalogManagementPath);

        ServiceCatalogManagementApis apis = new ServiceCatalogManagementApis(apiClientTmf633);

        try {
	        ServiceSpecification ss = apis.getServiceSpecification(id, null);
	        if (ss != null) {
	            System.out.println(ss.getId() + " " + ss.getName() + " " + ss.getLifecycleStatus());
	        }
        } catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
    }
}
