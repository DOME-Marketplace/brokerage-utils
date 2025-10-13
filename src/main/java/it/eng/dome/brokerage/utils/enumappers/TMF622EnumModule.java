package it.eng.dome.brokerage.utils.enumappers;

import com.fasterxml.jackson.databind.module.SimpleModule;

import it.eng.dome.tmforum.tmf622.v4.model.OrderItemActionType;
import it.eng.dome.tmforum.tmf622.v4.model.ProductOrderItemStateType;
import it.eng.dome.tmforum.tmf622.v4.model.ProductOrderStateType;
import it.eng.dome.tmforum.tmf622.v4.model.ProductStatusType;
import it.eng.dome.tmforum.tmf622.v4.model.TaskStateType;


public class TMF622EnumModule extends SimpleModule {

	private static final long serialVersionUID = -3868878167945898729L;

	public TMF622EnumModule() {
        super(TMF622EnumModule.class.getName());

        // PRODUCT ORDER STATE
		this.addDeserializer(ProductOrderStateType.class, new GenericEnumDeserializer<>(ProductOrderStateType.class));
		this.addSerializer(ProductOrderStateType.class, new GenericEnumSerializer<>(ProductOrderStateType.class));

		// PRODUCT ORDER ITEM STATE
		this.addDeserializer(ProductOrderItemStateType.class, new GenericEnumDeserializer<>(ProductOrderItemStateType.class));
		this.addSerializer(ProductOrderItemStateType.class, new GenericEnumSerializer<>(ProductOrderItemStateType.class));

		// ORDER ITEM ACTION
		this.addDeserializer(OrderItemActionType.class, new GenericEnumDeserializer<>(OrderItemActionType.class));
		this.addSerializer(OrderItemActionType.class, new GenericEnumSerializer<>(OrderItemActionType.class));

		// PRODUCT STATUS
		this.addDeserializer(ProductStatusType.class, new GenericEnumDeserializer<>(ProductStatusType.class));
		this.addSerializer(ProductStatusType.class, new GenericEnumSerializer<>(ProductStatusType.class));

		// TASK STATE
		this.addDeserializer(TaskStateType.class, new GenericEnumDeserializer<>(TaskStateType.class));
		this.addSerializer(TaskStateType.class, new GenericEnumSerializer<>(TaskStateType.class));

    }

}
