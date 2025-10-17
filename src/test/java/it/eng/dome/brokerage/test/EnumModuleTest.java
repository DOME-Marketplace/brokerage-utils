package it.eng.dome.brokerage.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.eng.dome.brokerage.utils.enumappers.TMF620EnumModule;
import it.eng.dome.brokerage.utils.enumappers.TMF622EnumModule;
import it.eng.dome.tmforum.tmf620.v4.model.JobStateType;
import it.eng.dome.tmforum.tmf622.v4.model.ProductOrderStateType;

public class EnumModuleTest {
	
	public static void main(String[] args) {
		
		TMF620EnumModuleTest();
		TMF622EnumModuleTest();
		
	}

	protected static void TMF620EnumModuleTest() {
		
		ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new TMF620EnumModule());

        JobStateType state = JobStateType.SUCCEEDED;

        try {
        	String json = mapper.writeValueAsString(state);
        	System.out.println("Serialized JSON: " + json);
        	
		} catch (JsonProcessingException e) {
			System.err.println(e.getMessage());
		}		
	}
	
	protected static void TMF622EnumModuleTest() {
		
		ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new TMF622EnumModule());

        ProductOrderStateType state = ProductOrderStateType.CANCELLED;

        try {
        	String json = mapper.writeValueAsString(state);
        	System.out.println("Serialized JSON: " + json);
        	
		} catch (JsonProcessingException e) {
			System.err.println(e.getMessage());
		}		
	}
}
