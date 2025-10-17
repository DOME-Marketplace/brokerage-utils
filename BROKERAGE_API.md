# Brokerage APIs

### Description
Here is a list of wrapped APIs to manage **TMForum** object.


### Reference APIs


| TMF Reference |       API Class               |                       Object Reference Management                                                                 |
|:-------------:|-------------------------------|:------------------------------------------------------------------------------------------------------------------|
|    TMF620     | ProductCatalogManagementApis  | `productOffering`, `productOfferingPrice`, `productSpecification`, `catalog`, `category`   	|
|    TMF629     | CustomerManagementApis        | `customer`                                                                                                   	|
|    TMF632     | APIPartyApis       	        | `individual`, `organization`                     			                                	       		|
|    TMF633     | ServiceCatalogManagementApis  | `serviceSpecification`                                                                                  	|
|    TMF634     | ResourceCatalogManagementApis | `resourceSpecification`                                                                 	            	|
|    TMF635     | UsageManagementApis           | `usage`, `usageSpecification`                                                        	       		    	|
|    TMF637     | ProductInventoryApis          | `product`	                                                                                                    |
|    TMF651     | AgreementManagementApis       | `agreement`										                            							 	|
|    TMF666     | AccountManagementApis         | `billFormat`, `billingAccount`, `partyAccount`	                            						|
|    TMF678     | AppliedCustomerBillRateApis   | `appliedCustomerBillingRate`							                            				     	|
|    TMF678     | CustomerBillApis              | `customerBill`, `customerBillExtension` 			                            				     	|


### How to retrieve data with FetchUtils

### `fetchByBatch(...)`

##### How it works:

- Runs a `while(true)` loop that repeatedly calls `fetcher.fetch(...)` with an `offset` and `batchSize`.
- For each batch returned, it calls the `consumer.process(batch)` callback.
- Stops when the API returns an empty or null list.
- Increases `offset` by the number of items fetched.

##### Typical usage:

```
FetchUtils.fetchByBatch(
    api::fetch, 
    "id,name", 
    Map.of(), 
    100, 
    batch -> {
        batch.forEach(System.out::println);
    }
);
```

✔️ Pros:
- Efficient for large datasets.
- Processes data incrementally (low memory footprint).
- Good for ETL jobs, data migration, or batch processing.

❌ Cons:
 - Doesn't return results — everything happens through the callback.


### `fetchAll(...)`

##### How it works:

- A simple wrapper around `fetchByBatch(...)`.
- Creates an empty list `allItems`.
- Each batch returned by `fetchByBatch` is appended to `allItems`.
- Returns the complete list when finished.

##### Typical usage:

```
List<MyEntity> all = FetchUtils.fetchAll(
   api::fetch, 
   "id,name", 
   Map.of(), 
   100
);
```

✔️ Pros:
- Very easy to use.
- Returns a ready-to-use list of all items.

❌ Cons:
- Loads *everything* into memory — not suitable for large datasets.
- **User friendly** variant of `fetchByBatch`.


### `streamAll(...)`

##### How it works:

- Builds a custom `Iterator<T>` that:
  * Tracks `offset`, `currentBatch`, and `index`.
  * Fetches a new batch when the current one is exhausted.
  * Uses `StreamSupport.stream(...)` to expose a lazy Java `Stream<T>`.
- Data is fetched **on demand** — as you consume the stream.

##### Typical usage:

```
try (Stream<MyEntity> stream = FetchUtils.streamAll(
    api::fetch, 
    "id,name", 
    Map.of(), 
    100)) {
        stream.filter(MyEntity::isActive)
            .forEach(System.out::println);
}
```

✔️ Pros:
- Fully lazy: fetches only as needed.
- Very memory-efficient.
- Integrates naturally with Java Stream API (`filter`, `map`, etc.).

❌ Cons:
- Must be closed (`try-with-resources` recommended).
- Not ideal if you need batch-level processing.

#### Practical Summary
- Use `fetchByBatch` when you want to process data in chunks.
- Use `fetchAll` when the dataset is small enough to fit in memory.
- Use `streamAll` when you want to stream and process elements lazily via Java Streams.