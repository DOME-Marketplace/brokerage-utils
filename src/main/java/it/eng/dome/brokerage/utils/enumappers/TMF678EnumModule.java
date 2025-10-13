package it.eng.dome.brokerage.utils.enumappers;

import com.fasterxml.jackson.databind.module.SimpleModule;

import it.eng.dome.tmforum.tmf678.v4.model.StateValue;
import it.eng.dome.tmforum.tmf678.v4.model.StateValues;


public class TMF678EnumModule extends SimpleModule {

	private static final long serialVersionUID = 7475681692655850207L;

	public TMF678EnumModule() {
		super(TMF678EnumModule.class.getName());
		
        // STATE VALUE
		this.addDeserializer(StateValue.class, new GenericEnumDeserializer<>(StateValue.class));
		this.addSerializer(StateValue.class, new GenericEnumSerializer<>(StateValue.class));
		
        // STATE VALUES
		this.addDeserializer(StateValues.class, new GenericEnumDeserializer<>(StateValues.class));
		this.addSerializer(StateValues.class, new GenericEnumSerializer<>(StateValues.class));
	}

}
