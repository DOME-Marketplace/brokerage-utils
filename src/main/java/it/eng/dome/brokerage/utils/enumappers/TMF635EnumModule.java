package it.eng.dome.brokerage.utils.enumappers;

import com.fasterxml.jackson.databind.module.SimpleModule;

import it.eng.dome.tmforum.tmf635.v4.model.UsageStatusType;


public class TMF635EnumModule extends SimpleModule {

	private static final long serialVersionUID = -3617467722075697823L;

	public TMF635EnumModule() {
		super(TMF635EnumModule.class.getName());
		
        // USAGE STATUS
		this.addDeserializer(UsageStatusType.class, new GenericEnumDeserializer<>(UsageStatusType.class));
		this.addSerializer(UsageStatusType.class, new GenericEnumSerializer<>(UsageStatusType.class));
	}

}
