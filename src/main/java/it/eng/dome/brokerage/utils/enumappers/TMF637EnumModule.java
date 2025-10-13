package it.eng.dome.brokerage.utils.enumappers;

import com.fasterxml.jackson.databind.module.SimpleModule;

import it.eng.dome.tmforum.tmf637.v4.model.ProductStatusType;


public class TMF637EnumModule extends SimpleModule {

	private static final long serialVersionUID = -4687073645697521568L;

	public TMF637EnumModule() {		
        super(TMF637EnumModule.class.getName());
        
        // PRODUCT STATUS
		this.addDeserializer(ProductStatusType.class, new GenericEnumDeserializer<>(ProductStatusType.class));
		this.addSerializer(ProductStatusType.class, new GenericEnumSerializer<>(ProductStatusType.class));
    }

}
