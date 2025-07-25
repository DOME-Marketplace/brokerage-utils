# Release Notes

**Release Notes** of the *Brokerage Utils* software:

### <code>2.1.3</code> :calendar: 22/07/2025
**Feature**
* Add `AccountManagementApis` class of *TMF666 specification* to manage a **Accounts** object (`partyAccount`, `billingAccount`, and `billFormat`).


### <code>2.1.2</code> :calendar: 20/06/2025
**Feature**
* Add `CustomerManagementApis` class of *TMF629 specification* to manage a **Customer** object.
* Add `AgreementManagementApis` class of *TMF651 specification* to manage a **Agreement** object.


### <code>2.1.1</code> :calendar: 05/06/2025
**Feature**
* Add `UsageManagementApis` class of *TMF635 specification* to manage **Usage** and **UsageSpecification** objects.

**Bug Fixing**
* Test of `RelatedParty` in **AppliedCustomerBillRate** and **CustomerBill** objects.


### <code>2.1.0</code> :calendar: 23/05/2025
**Improvements**
* Add **filtering** functionality in the **AppliedCustomerBillRateApis** and **ProductApis**.


### <code>2.0.2</code> :calendar: 12/05/2025
**Bug Fixing**
* Set **getResponseBody** in the **logger.error**.


### <code>2.0.1</code> :calendar: 01/04/2025
**Improvements**
* Improvement in the **recursive functions**.


### <code>2.0.0</code> :calendar: 31/03/2025
**Feature**
* Added `AppliedCustomerBillRateApis`, `OrganizationApis`, `ProductOfferingPriceApis`, and `ProductApis` classes to manage the Brokerage API.

### <code>1.0.0</code> :calendar: 18/02/2025
**Feature**
* Added `ErrorResponse` class to manage exception with a custom error response

### <code>0.0.9</code> :calendar: 31/01/2025
**Feature**
* Added `DateUtils` class.


### <code>0.0.6</code> :calendar: 28/01/2025
**Feature**
* Added `BillingUtils` class.

### <code>0.0.3</code> :calendar: 27/01/2025
**Bug Fixing**
* Bug fixing of ApplyTaxesRequestDTO to manage the HTTP POST request body of the "invoicing/applyTaxes" REST API


### <code>0.0.2</code> :calendar: 24/01/2025
**Feature**
* Added ApplyTaxesRequestDTO to manage the HTTP POST request body of the "invoicing/applyTaxes" REST API


### <code>0.0.1</code> :calendar: 19/12/2024
**Feature**
* Init project.
* Added BillRequestDTO to manage the HTTP POST request body of the "billing/bill" REST API

