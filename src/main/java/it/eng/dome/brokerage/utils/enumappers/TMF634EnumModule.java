package it.eng.dome.brokerage.utils.enumappers;

import com.fasterxml.jackson.databind.module.SimpleModule;

import it.eng.dome.tmforum.tmf634.v4.model.JobStateType;


public class TMF634EnumModule extends SimpleModule {

	private static final long serialVersionUID = 3932514894348065652L;

	public TMF634EnumModule() {
		super(TMF634EnumModule.class.getName());
		
        // JOB STATE
		this.addDeserializer(JobStateType.class, new GenericEnumDeserializer<>(JobStateType.class));
		this.addSerializer(JobStateType.class, new GenericEnumSerializer<>(JobStateType.class));
	}

}