package it.eng.dome.brokerage.test;

import it.eng.dome.brokerage.api.ServiceSpecificationApis;
import it.eng.dome.tmforum.tmf633.v4.ApiClient;
import it.eng.dome.tmforum.tmf633.v4.Configuration;
import it.eng.dome.tmforum.tmf633.v4.model.ServiceSpecification;

import java.util.List;

public class ServiceSpecificationTest {
    final static String tmf633serviceCatalogManagementPath = "tmf-api/serviceCatalogManagement/v4";
    final static String tmfEndpoint = "https://dome-dev.eng.it";

    public static void main(String[] args) {

        /**
         * Get All ServiceSpecification
         */
        TestGetAllServiceSpecification();


        /**
         * Get ServiceSpecification by ID
         */
        String id = "urn:ngsi-ld:service-specification:44e32a74-94ee-4625-a6e3-ca69a66b3881";
        TestGetServiceSpecification(id);

    }

    protected static void TestGetAllServiceSpecification() {

        ApiClient apiClientTmf634 = Configuration.getDefaultApiClient();
        apiClientTmf634.setBasePath(tmfEndpoint + "/" + tmf633serviceCatalogManagementPath);

        ServiceSpecificationApis apis = new ServiceSpecificationApis(apiClientTmf634);

        List<ServiceSpecification> sss = apis.getAllServiceSpecification(null, null);

        int count = 0;
        for (ServiceSpecification ss : sss) {
            System.out.println(++count + " => " + ss.getId() + " " + ss.getName() + " " + ss.getLifecycleStatus());
        }
    }

    protected static void TestGetServiceSpecification(String id) {

        ApiClient apiClientTmf633 = Configuration.getDefaultApiClient();
        apiClientTmf633.setBasePath(tmfEndpoint + "/" + tmf633serviceCatalogManagementPath);

        ServiceSpecificationApis apis = new ServiceSpecificationApis(apiClientTmf633);

        ServiceSpecification ss = apis.getServiceSpecification(id, null);
        if (ss != null) {
            System.out.println(ss.getId() + " " + ss.getName() + " " + ss.getLifecycleStatus());
        }
    }
}
