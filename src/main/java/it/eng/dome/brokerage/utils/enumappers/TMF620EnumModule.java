package it.eng.dome.brokerage.utils.enumappers;

import com.fasterxml.jackson.databind.module.SimpleModule;

import it.eng.dome.tmforum.tmf620.v4.model.JobStateType;


public class TMF620EnumModule extends SimpleModule {

	private static final long serialVersionUID = 3168644462810667395L;
	
	public TMF620EnumModule() {
		super(TMF620EnumModule.class.getName());
		
        // JOB STATE
		this.addDeserializer(JobStateType.class, new GenericEnumDeserializer<>(JobStateType.class));
		this.addSerializer(JobStateType.class, new GenericEnumSerializer<>(JobStateType.class));
	}

}
