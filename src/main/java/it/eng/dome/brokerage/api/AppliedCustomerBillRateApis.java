package it.eng.dome.brokerage.api;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import it.eng.dome.tmforum.tmf678.v4.ApiClient;
import it.eng.dome.tmforum.tmf678.v4.ApiException;
import it.eng.dome.tmforum.tmf678.v4.api.AppliedCustomerBillingRateApi;
import it.eng.dome.tmforum.tmf678.v4.api.CustomerBillExtensionApi;
import it.eng.dome.tmforum.tmf678.v4.model.AppliedCustomerBillingRate;
import it.eng.dome.tmforum.tmf678.v4.model.AppliedCustomerBillingRateUpdate;
import it.eng.dome.tmforum.tmf678.v4.model.CustomerBill;
import it.eng.dome.tmforum.tmf678.v4.model.CustomerBillCreate;

public class AppliedCustomerBillRateUtils implements InitializingBean {
	
	private final Logger logger = LoggerFactory.getLogger(AppliedCustomerBillRateUtils.class);
	private final int LIMIT = 10;

	@Autowired
	private ApiClient apiClientTMF678;

	private AppliedCustomerBillingRateApi appliedCustomerBillingRate;
	private CustomerBillExtensionApi customerBillExtension;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		appliedCustomerBillingRate = new AppliedCustomerBillingRateApi(apiClientTMF678);	
		customerBillExtension = new CustomerBillExtensionApi(apiClientTMF678);
	}
	
	
	public List<AppliedCustomerBillingRate> getAllAppliedCustomerBillingRates() {
		logger.info("Get all AppliedCustomerBillingRates");
		List<AppliedCustomerBillingRate> all = new ArrayList<AppliedCustomerBillingRate>();
		getAllApplied(all, 0);
		Collections.reverse(all); //reverse order
		logger.info("Number of AppliedCustomerBillingRates: {}", all.size());
		return all;
	}
	
	public boolean updateAppliedCustomerBillingRate(String appliedId, AppliedCustomerBillingRateUpdate update) {
		try {
			AppliedCustomerBillingRate billUpdate = appliedCustomerBillingRate.updateAppliedCustomerBillingRate(appliedId, update);
			logger.info("Update AppliedCustomerBillingRate with id: {}", billUpdate.getId());
			return true;
		} catch (ApiException e) {
			logger.error("Error: ", e.getMessage());
			return false;
		}
	}
	
	public String createCustomerBill(CustomerBillCreate customerBillCreate) {
		logger.info("Saving the customerBill ...");
		try {
			CustomerBill customerBill = customerBillExtension.createCustomerBill(customerBillCreate);
			logger.info("CustomerBill saved with id: {}", customerBill.getId());
			return customerBill.getId();
		} catch (ApiException e) {
			logger.info("CustomerBill not saved: {}", customerBillCreate.toString());
			logger.error("Error: {}", e.getMessage());
			return null;
		}
	}
	
	private void getAllApplied(List<AppliedCustomerBillingRate> list, int start) {
		int offset = start * LIMIT;

		try {
			List<AppliedCustomerBillingRate> appliedList = appliedCustomerBillingRate.listAppliedCustomerBillingRate(null, offset, LIMIT);
			if (!appliedList.isEmpty()) {
				Collections.reverse(appliedList); //reverse order
				getAllApplied(list, start + 1);
				list.addAll(appliedList);
			}else {
				return;
			}
		} catch (Exception e) {
			return;
		}		
	}
}
