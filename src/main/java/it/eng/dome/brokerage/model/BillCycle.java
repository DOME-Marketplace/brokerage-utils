package it.eng.dome.brokerage.model;

import java.time.OffsetDateTime;

import it.eng.dome.tmforum.tmf678.v4.model.TimePeriod;

public class BillCycle {
	
	private OffsetDateTime billDate;
	private TimePeriod billingPeriod;
	private OffsetDateTime paymentDueDay;

	public OffsetDateTime getBillDate() {
		return billDate;
	}

	public void setBillDate(OffsetDateTime billDate) {
		this.billDate = billDate;
	}

	public TimePeriod getBillingPeriod() {
		return billingPeriod;
	}

	public void setBillingPeriod(TimePeriod billingPeriod) {
		this.billingPeriod = billingPeriod;
	}

	public OffsetDateTime getPaymentDueDay() {
		return paymentDueDay;
	}

	public void setPaymentDueDay(OffsetDateTime paymentDueDay) {
		this.paymentDueDay = paymentDueDay;
	}

}
