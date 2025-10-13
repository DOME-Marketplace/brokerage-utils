package it.eng.dome.brokerage.utils.enumappers;

import com.fasterxml.jackson.databind.module.SimpleModule;

import it.eng.dome.tmforum.tmf633.v4.model.JobStateType;

public class TMF633EnumModule extends SimpleModule {

	private static final long serialVersionUID = -7309359707057468994L;

	public TMF633EnumModule() {
		super(TMF633EnumModule.class.getName());
		
        // JOB STATE
		this.addDeserializer(JobStateType.class, new GenericEnumDeserializer<>(JobStateType.class));
		this.addSerializer(JobStateType.class, new GenericEnumSerializer<>(JobStateType.class));
	}

}
