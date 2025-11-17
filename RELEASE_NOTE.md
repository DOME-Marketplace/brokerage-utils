# Release Notes

**Release Notes** of the *Brokerage Utils* software:


### <code>2.2.2</code> :calendar: 17/11/2025 (Work In Progress)
**BugFix**
* Update `ProductOfferingPriceUtils` to retrieve all fields of `ProductOfferingPrice`.
* Fixed handling of **non-compliant** objects in `FetchUtils` by applying a **divide-and-conquer** fallback in `safeFetchRange` method, ensuring valid items are retained.

**Improvements**
* Move `getChecksOnSelf` in the `AbstractHealthService` class to share for all `HealthService`. 


### <code>2.2.1</code> :calendar: 24/10/2025
**Improvements**
* Add `BillingResponseDTO` class to manage billing response
* Add `ProductOfferingPriceUtils` class to manage ProductOfferingPrice
* Add `it.eng.dome.brokerage.model` package
* Add `ApplyTaxesRequestDTO` class to manage invoicing service request

**BugFix**
* Set `offset={}, limit={}` params in the logger description for all **list** methods.


### <code>2.2.0</code> :calendar: 17/10/2025
**Feature**
* Remove the *recursive* functionality when retrieving the list of **TMForum objects**, and implement new features:
  - `streamAll`: **lazy stream processing**, suitable for using the Java Stream API (`map`, `filter`, `collect`).
  - `fetchByBatch`: **batch-by-batch processing**, applying a consumer (`BatchProcessor`) to each retrieved block of data.
  - `fetchAll`: **eager full loading**, aggregating all elements in memory and returning a complete list.
* Add a **throw Exception** in all `Brokerage APIs`.
* Manage *different types* in the `GenericEnumSerializer` and `GenericEnumDeserializer` classes.

**Improvements**
* Update Javadoc.
* Add `BillingPreviewRequestDTO` class to manage billing preview.


### <code>2.1.10</code> :calendar: 14/10/2025
**Feature**
* Add *TMF Enumeration Mapping* for **serialize** and **deserialize** in all *TMForum enum types*, and usage via **Spring Boot annotation @Bean**.


### <code>2.1.9</code> :calendar: 13/10/2025
**Feature**
* Add `CategoryApis` class to get **all categories** and get **by id**.


### <code>2.1.8</code> :calendar: 06/10/2025
**Feature**
* Add `AbstractHealthService` class for all **Billing Services** to retrieve `Info` and `Health` objects.


### <code>2.1.7</code> :calendar: 29/09/2025
**Feature**
* Add `ServiceSpecificationApis` class of *TMF633 specification* to manage a **ServiceSpecification** object.
* Add *CustomerBill* management of *TMF678 specification*: `getCustomerBill` and `getAllCustomerBills`.


### <code>2.1.6</code> :calendar: 23/09/2025
**Feature**
* Add `ProductSpecificationApis` class of *TMF620 specification* to manage a **ProductSpecification** object.
* Add `ResourceSpecificationApis` classes of *TMF634 specification* to manage a **ResourceSpecification** object.


### <code>2.1.5</code> :calendar: 08/09/2025
**Feature**
* Add `ProductOfferingApis` class of *TMF620 specification* to manage a **ProductOffering** object.

### <code>2.1.4</code> :calendar: 02/09/2025
**Improvements**
* Add `BillingPriceType` class to enumerate the allowed priceType values.

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

