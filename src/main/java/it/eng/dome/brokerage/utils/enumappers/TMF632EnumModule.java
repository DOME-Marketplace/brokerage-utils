package it.eng.dome.brokerage.utils.enumappers;

import com.fasterxml.jackson.databind.module.SimpleModule;

import it.eng.dome.tmforum.tmf632.v4.model.IndividualStateType;
import it.eng.dome.tmforum.tmf632.v4.model.OrganizationStateType;


public class TMF632EnumModule extends SimpleModule {

	private static final long serialVersionUID = 1895661328749544349L;

	public TMF632EnumModule() {
        super(TMF632EnumModule.class.getName());

        // INDIVIDUAL STATE
        this.addSerializer(IndividualStateType.class, new GenericEnumSerializer<>(IndividualStateType.class));
        this.addDeserializer(IndividualStateType.class, new GenericEnumDeserializer<>(IndividualStateType.class));

        // ORGANIZATION STATE
        this.addSerializer(OrganizationStateType.class, new GenericEnumSerializer<>(OrganizationStateType.class));
        this.addDeserializer(OrganizationStateType.class, new GenericEnumDeserializer<>(OrganizationStateType.class));

	}

}
