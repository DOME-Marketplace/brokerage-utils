package it.eng.dome.brokerage.api.fetch;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import it.eng.dome.brokerage.exception.GenericApiException;


public class FetchUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(FetchUtils.class);

	/**
	 * Functional interface representing a fetcher that retrieves a list of objects
	 * of type {@code T}.
	 * <p>
	 * This interface is intended to be implemented with a lambda or method
	 * reference that fetches a subset of data based on specified fields, pagination
	 * parameters, and optional filters.
	 * </p>
	 *
	 * @param <T> the type of objects to be fetched
	 */
	@FunctionalInterface
	public interface ListedFetcher<T> {
		/**
		 * Fetches a list of objects of type {@code T} according to the specified
		 * parameters.
		 *
		 * @param fields a comma-separated list of fields to include in the result; may be {@code null} or empty
		 * @param offset the zero-based starting index from which to fetch results
		 * @param limit  the maximum number of results to return
		 * @param filter a map of filtering criteria where keys are field names and values are expected values; may be {@code null} or empty
		 * @return a {@link List} of objects of type {@code T} matching the criteria; never {@code null}, but may be empty
		 * @throws Exception if any error occurs while fetching the data, such as a network failure, invalid response, or API-related exception
		 */
		List<T> fetch(String fields, int offset, int limit, Map<String, String> filter) throws Exception;
	}

	/**
	 * Functional interface representing a processor that handles a batch of objects
	 * of type {@code T}.
	 * <p>
	 * This interface is intended to be implemented with a lambda or method
	 * reference that performs operations on a list of items, such as processing,
	 * transforming, or saving them.
	 * </p>
	 *
	 * @param <T> the type of objects in the batch
	 */
	@FunctionalInterface
	public interface BatchProcessor<T> {
		/**
		 * Processes a batch of objects.
		 *
		 * @param batch a {@link List} of objects of type {@code T} to be processed; never {@code null}, but may be empty
		 */
		void process(List<T> batch);
	}

	/**
	 * Streams all elements fetched in batches from a {@link ListedFetcher}, with robust
	 * error-recovery logic and lazy on-demand loading.
	 * 
	 * <p>This method returns a {@link Stream} that incrementally retrieves data in pages
	 * of size {@code pageSize}. Items are not fetched upfront: each batch is loaded only
	 * when required by the stream consumer. This allows efficient processing of very large
	 * datasets while keeping memory usage low.</p>
	 *
	 * <h3>Error Handling and Data Recovery</h3>
	 * <p>If fetching a batch fails due to malformed, incomplete or non-compliant data,
	 * the method automatically invokes a <em>divide-and-conquer fallback</em>:
	 * the failing range is recursively split into smaller ranges to isolate and discard
	 * only the corrupted elements, while successfully retrieving all valid ones.</p>
	 *
	 * <p>Certain exceptions (such as {@link java.net.UnknownHostException}) are treated as
	 * non-recoverable and immediately rethrown, aborting the stream.</p>
	 *
	* <h3>Iterator Behavior</h3>
	 * <ul>
	 *   <li>The stream is backed by a custom iterator that maintains its own
	 *       {@code offset}, {@code currentBatch}, and index within the batch.</li>
	 *   <li>When the current batch is exhausted, the iterator automatically fetches
	 *       the next batch.</li>
	 *   <li>Fetching stops when an empty batch is returned.</li>
	 *   <li>If the consumer calls {@code next()} after the stream is exhausted,
	 *       a {@link java.util.NoSuchElementException} is thrown.</li>
	 * </ul>
	 *
	 * @param <T>      the type of elements to fetch and stream
	 * @param fetcher  the {@link ListedFetcher} used to fetch items in batches
	 * @param fields   a comma-separated list of fields to include in each fetch; may be {@code null} or empty
	 * @param filter   a map of filtering criteria; may be {@code null} or empty
	 * @param pageSize the maximum number of items to fetch in each batch; must be greater than 0
	 * @return a lazily evaluated {@link Stream} that iterates through all available items;
	 *         never {@code null}, but may be empty
	 * @throws RuntimeException if an unrecoverable I/O error occurs (e.g., network unreachable)
	 * @throws NoSuchElementException if the iterator is exhausted and {@code next()} is called
	 */
	public static <T> Stream<T> streamAll(ListedFetcher<T> fetcher, String fields, Map<String, String> filter, int pageSize) {

		Iterator<T> iterator = new Iterator<>() {
			private int offset = 0;
			private List<T> currentBatch = Collections.emptyList();
			private int index = 0;

			@Override
			public boolean hasNext() {
				while (index >= currentBatch.size()) {

					List<T> batch = null;
					try {
						batch = fetcher.fetch(fields, offset, pageSize, filter);
						
						if (batch.isEmpty()) {
				            return false;
				        }
					} catch (Exception e) {
						// Recover valid items based on the type of exceptions at offset={offset} and limit={pageSize}
						batch = recoverValidItemsOnError(e, offset, pageSize, fetcher, fields, filter);				
						logger.info("Number of valid items retrieved in fallback: {} - based on a total: {}", batch.size(), pageSize);
					}
					
					currentBatch = batch;
					offset += pageSize;
					index = 0;
				}
				return index < currentBatch.size();
			}

			@Override
			public T next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
                return currentBatch.get(index++);
			}
		};

		return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, 0), false);
	}
		

	/**
	 * Fetches elements in batches from a {@link ListedFetcher} and processes each
	 * batch using a {@link BatchProcessor}.
	 * 
	 * <p>This method retrieves items page by page, using the given {@code fetcher}
	 * with the specified {@code pageSize}. Each non-empty batch is passed to the
	 * supplied {@code consumer} for processing. Fetching continues until an empty
	 * batch is returned, indicating that no further items are available.</p>
	 *
	 * <p>If an exception occurs during fetching, the method inspects the exception
	 * chain to detect unrecoverable errors such as {@link UnknownHostException}.
	 * In such cases, a {@link RuntimeException} is thrown and execution stops
	 * immediately. For other errors, the method applies a divide-and-conquer
	 * fallback strategy through {@code safeFetchRange} to recover any valid items
	 * within the failing batch.</p>
	 * 
	 * <p>If an exception occurs during batch processing, the method fails fast by
	 * throwing a {@link RuntimeException} that wraps the original cause.</p>
	 * </p>
	 *
	 * @param <T>       the type of elements to fetch and process
	 * @param fetcher   the {@link ListedFetcher} used to fetch items in batches
	 * @param fields    a comma-separated list of fields to include in each fetch; may be {@code null} or empty
	 * @param filter    a map of filtering criteria; may be {@code null} or empty
	 * @param pageSize the maximum number of items to fetch in each batch; must be greater than 0
	 * @param consumer  the {@link BatchProcessor} that processes each batch; must not be {@code null}
	 * @throws RuntimeException if fetching fails with an unrecoverable error, 
	 *                  if fallback recovery fails, 
	 *                  or if batch processing encounters an error
	 */
	public static <T> void fetchByBatch(ListedFetcher<T> fetcher, String fields, Map<String, String> filter, int pageSize, BatchProcessor<T> consumer) {

		int offset = 0;
		
		while (true) {
			List<T> batch = null;
			
	        try {
	            batch = fetcher.fetch(fields, offset, pageSize, filter);
	            
	            if (batch.isEmpty()) {
	            	break;
	            }
	            
	        } catch (Exception e) {	        	
				// Recover valid items based on the type of exceptions at offset={offset} and limit={pageSize}
				batch = recoverValidItemsOnError(e, offset, pageSize, fetcher, fields, filter);				
				logger.info("Number of valid items retrieved in fallback: {} - based on a total: {}", batch.size(), pageSize);
	        }

	        try {
	        	if (!batch.isEmpty()) {
	        		consumer.process(batch);
	        	}
	        } catch (Exception e) {
	            throw new RuntimeException("Error processing batch starting at offset " + offset, e);
	        }

	        offset += pageSize;
	    }
	}
	

	/**
	 * Fetches all elements from a {@link ListedFetcher} and returns them in a
	 * single {@link List}.
	 * <p>
	 * This method retrieves items in batches of size {@code pageSize} using the
	 * provided {@code fetcher}. Each non-empty batch is added to a list which is
	 * returned at the end. The fetching continues until the fetcher returns no more
	 * items.
	 * </p>
	 * 
	 * @param <T>      the type of elements to fetch
	 * @param fetcher  the {@link ListedFetcher} used to fetch items in batches; must not be {@code null}
	 * @param fields   a comma-separated list of fields to include in each fetch; may be {@code null} or empty
	 * @param filter   a map of filtering criteria; may be {@code null} or empty 
	 * @param pageSize the maximum number of items to fetch in each batch; must be greater than 0
	 * @return a {@link List} containing all elements fetched; never {@code null}, may be empty
	 */
	public static <T> List<T> fetchAll(ListedFetcher<T> fetcher, String fields, Map<String, String> filter, int pageSize) {
		List<T> allItems = new ArrayList<>();

		fetchByBatch(fetcher, fields, filter, pageSize, batch -> allItems.addAll(batch));

		return allItems;
	}
	
	/**
	 * Handles exceptions occurring during batch fetching and applies a divide-and-conquer
	 * fallback strategy to recover valid items.
	 * <p>
	 * The method manages different types of exceptions as follows:
	 * <ul>
	 *   <li>{@link UnknownHostException}: rethrows as {@link RuntimeException} to exit the loop.</li>
	 *   <li>{@link SocketTimeoutException}: logs a warning and proceeds with the fallback.</li>
	 *   <li>TMF {@link GenericApiException}: logs the TMForum ApiException message and proceeds with the fallback.</li>
	 *   <li>Other exceptions: logs the error and proceeds with the fallback.</li>
	 * </ul>
	 * The fallback strategy retrieves items in smaller batches using a divide-and-conquer
	 * approach to maximize the number of valid items recovered. 
	 * 
	 * @param <T> the type of items being fetched
	 * @param e the exception that occurred during the batch fetch
	 * @param offset the offset of the batch where the exception occurred
	 * @param pageSize the maximum number of items requested in the batch
	 * @param fetcher a {@link ListedFetcher} functional interface used to fetch items
	 * @param fields the fields to include in the fetched items
	 * @param filter a map of filters to apply to the fetch request
	 * @return a list of valid items successfully retrieved using the fallback strategy
	 */
	private static <T> List<T> recoverValidItemsOnError(Exception e, int offset, int pageSize, ListedFetcher<T> fetcher,
	        String fields, Map<String, String> filter) {
		
		// Error Management with Exception
		if (hasCause(e, UnknownHostException.class)) {	
			// exit from loop if UnknownHostException
	        throw new RuntimeException(e.getMessage());
	    }
		
		if (hasCause(e, SocketTimeoutException.class)) {						
			// SocketTimeoutException does not block the process and proceeds to the fallback strategy
			logger.warn("Consider reducing limit={} in the requests", pageSize);
			
		} else if (isApiException(e)){		
			// Use GenericApiException to logging error message and proceeds to the fallback strategy
			GenericApiException tmfEx = new GenericApiException(e);
			logger.error("GenericApiException: {}", tmfEx.getMessage());
			
			// TMForum Error Message strategies: 
			// 1. "Was not able to list entities" => malformed data => fallback strategy 
			// 2. "Request could not be answered due to an unexpected internal error" => bad query-string generates loop infinitive 
			if (!tmfEx.getMessage().contains("Was not able to list entities")) {
				logger.error("Error message body: {}", tmfEx.getResponseBody());
				throw new RuntimeException(e.getMessage());
			}
		} else {
			// Logging other types of exceptions and proceeds to the fallback strategy
			logger.error("Error fetching batch at offset {}: {}", offset, e.getMessage());
		}
        
		// fallback: divide & conquer to recover valid items 
        logger.warn("Batch fetch failed at offset {} and limit {}. Applying divide-and-conquer fallback to recover valid items", offset, pageSize);
        logger.info("Divide-and-conquer fallback strategy in progress ...");
		
		// retrieve items using divide-and-conquer fallback strategy at offset={offset} and limit={pageSize}
        return safeFetchRange(fetcher, fields, filter, offset, pageSize); 		
	}
	
	
	/**
	 * Safely fetches a range of items from a paginated API using a divide-and-conquer
	 * fallback strategy when a batch fetch fails.
	 * 
	 * @param <T>     the type of items returned by the fetcher
	 * @param fetcher the API abstraction used to retrieve paginated items
	 * @param fields  the field selector passed to the API
	 * @param filter  optional filter parameters passed to the API
	 * @param start   the starting offset (inclusive) of the requested range
	 * @param size    the number of items to retrieve
	 * @return a list containing all valid items within the requested range,
	 *         possibly empty if the entire range is invalid or corrupted
	 */
	private static <T> List<T> safeFetchRange(
	        ListedFetcher<T> fetcher,
	        String fields,
	        Map<String, String> filter,
	        int start,
	        int size) {

	    // base case - single element
	    if (size == 1) {
	        try {
	            List<T> res = fetcher.fetch(fields, start, 1, filter);
	            return res != null ? res : Collections.emptyList();
	        } catch (Exception e) {
	            return Collections.emptyList();
	        }
	    }

	    // batch
	    try {
	        List<T> res = fetcher.fetch(fields, start, size, filter);
	        return res != null ? res : Collections.emptyList();
	    } catch (Exception e) {

	        int half = size / 2;

	        List<T> left  = safeFetchRange(fetcher, fields, filter, start, half);
	        List<T> right = safeFetchRange(fetcher, fields, filter, start + half, size - half);

	        List<T> result = new ArrayList<>(left.size() + right.size());
	        result.addAll(left);
	        result.addAll(right);
	        return result;
	    }
	}
	
	
	/**
	 * Checks whether the given throwable or any of its nested causes is an instance
	 * of the specified exception type.
	 * <p>
	 * This method walks the exception cause chain starting from the provided
	 * {@code throwable} and returns {@code true} as soon as a cause matching the
	 * given {@code type} is found. If no matching cause exists in the chain, the
	 * method returns {@code false}.
	 * </p>
	 *
	 * <p>This utility is useful for detecting specific exceptions that may be
	 * wrapped inside higher-level exceptions, especially when using libraries or
	 * frameworks that nest the underlying root cause.</p>
	 *
	 * @param throwable the exception to inspect; may be {@code null}
	 * @param type the exception type to search for; must not be {@code null}
	 * @return {@code true} if the throwable or any cause in its chain is an instance
	 *         of the given type; {@code false} otherwise
	 */
	private static boolean hasCause(Throwable t, Class<? extends Throwable> type) {
	    while (t != null) {	    	
	        if (type.isInstance(t)) {
	        	logger.error("Error processing batch - {}", t.toString());
	            return true;
	        }
	        t = t.getCause();
	    }
	    return false;
	}
	
	
	/**
	 * Checks whether the given throwable or any of its causes is an instance
	 * of a class named "ApiException". This method does not rely on the package
	 * of the exception and works for any class with the simple name "ApiException".
	 *
	 * @param t the throwable to check, may be null
	 * @return true if the throwable or any of its causes has the simple class
	 *         name "ApiException", false otherwise
	 */
	private static boolean isApiException(Throwable t) {
	    while (t != null) {
	        if (t.getClass().getSimpleName().equals("ApiException")) {
	            return true;
	        }
	        t = t.getCause();
	    }
	    return false;
	}
}