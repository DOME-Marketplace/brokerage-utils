package it.eng.dome.brokerage.api.fetch;

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
	 * Streams all elements fetched in batches from a {@link ListedFetcher}.
	 * <p>This method lazily retrieves items in pages of size {@code pageSize} using the
	 * provided fetcher, producing a {@link Stream} that loads data on demand as the
	 * stream is consumed. Each batch is fetched only when the iterator requires it.</p>
	 *
	 * <p>If a batch fetch fails due to malformed or non-compliant data within the range,
	 * the method automatically applies a divide-and-conquer fallback strategy to
	 * recover all valid items: the batch is recursively split into smaller ranges
	 * until valid subsets are successfully fetched. Only the corrupted elements are
	 * discarded, ensuring that no valid items are lost.</p>
	 *
	 * @param <T>      the type of elements to fetch and stream
	 * @param fetcher  the {@link ListedFetcher} used to fetch items in batches
	 * @param fields   a comma-separated list of fields to include in each fetch; may be {@code null} or empty
	 * @param filter   a map of filtering criteria; may be {@code null} or empty
	 * @param pageSize the maximum number of items to fetch in each batch; must be greater than 0
	 * @return a {@link Stream} of all items fetched from the {@code fetcher}; never {@code null}, may be empty
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
						
			            logger.error("Error fetching batch at offset {}: {}", offset, e.getMessage());
						
						// fallback: divide & conquer
						logger.debug("Batch fetch failed at offset {} - applying divide-and-conquer fallback to recover valid items.", offset);
						batch = safeFetchRange(fetcher, fields, filter, offset, pageSize); 
						logger.debug("Items retrieved in fallback {}", batch.size());
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
	 * <p>
	 * This method retrieves items in pages of size {@code batchSize} using the
	 * given fetcher. Each successfully fetched, non-empty batch is then passed to
	 * the supplied {@code consumer} for processing. Fetching continues until the
	 * fetcher returns an empty or {@code null} result, indicating that no more
	 * items are available.
	 * </p>
	 *
	 * <p>
	 * If an exception occurs during fetching or processing, the method fails fast
	 * by throwing a {@link RuntimeException}, including the original cause. No
	 * additional recovery or fallback logic is applied.
	 * </p>
	 *
	 * @param <T>       the type of elements to fetch and process
	 * @param fetcher   the {@link ListedFetcher} used to fetch items in batches
	 * @param fields    a comma-separated list of fields to include in each fetch; may be {@code null} or empty
	 * @param filter    a map of filtering criteria; may be {@code null} or empty
	 * @param batchSize the maximum number of items to fetch in each batch; must be greater than 0
	 * @param consumer  the {@link BatchProcessor} that processes each batch; must not be {@code null}
	 * @throws RuntimeException if any error occurs while fetching or processing a batch (fail-fast)
	 */
	public static <T> void fetchByBatch(ListedFetcher<T> fetcher, String fields, Map<String, String> filter, int batchSize, BatchProcessor<T> consumer) {

		int offset = 0;
		
		while (true) {
			List<T> batch = null;
			
	        try {
	            batch = fetcher.fetch(fields, offset, batchSize, filter);
	            
	            if (batch.isEmpty()) {
	            	break;
	            }
	            
	        } catch (Exception e) {
	        	        	
	        	logger.error("Error fetching batch at offset {}: {}", offset, e.getMessage());

	        	// fallback: divide & conquer
	            logger.debug("Batch fetch failed at offset {} - applying divide-and-conquer fallback to recover valid items.", offset);
	            batch = safeFetchRange(fetcher, fields, filter, offset, batchSize);
	            logger.debug("Items retrieved in fallback {}", batch.size());
	        }

	        try {
	        	if (!batch.isEmpty()) {
	        		consumer.process(batch);
	        	}
	        } catch (Exception e) {
	            throw new RuntimeException("Error processing batch starting at offset " + offset, e);
	        }

	        offset += batchSize;
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
}