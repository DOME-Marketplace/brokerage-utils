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

public class FetchUtils {

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
	 * <p>
	 * This method retrieves items in pages of size {@code pageSize} using the
	 * provided fetcher, and returns a {@link Stream} that lazily iterates over all
	 * items, fetching new batches only when needed.
	 * </p>
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
			private boolean finished = false;

			@Override
			public boolean hasNext() {
				if (index < currentBatch.size()) {
					return true;
				}
				if (finished) {
					return false;
				}

				try {
					currentBatch = fetcher.fetch(fields, offset, pageSize, filter);
				} catch (Exception e) {
					throw new RuntimeException("Error fetching data from API at offset " + offset + " when getting " + pageSize + " items", e);
				}

				if (currentBatch == null || currentBatch.isEmpty()) {
					finished = true;
					return false;
				}

				offset += currentBatch.size();
				index = 0;
				return true;
			}

			@Override
			public T next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
				try {
	                return currentBatch.get(index++);
	            } catch (Exception e) {
	            	throw new RuntimeException("Error processing batch at offset " + offset + " when getting " + pageSize + " items", e);
	            }
			}
		};

		return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, 0), false);
	}

	/**
	 * Fetches elements in batches from a {@link ListedFetcher} and processes each
	 * batch using a {@link BatchProcessor}.
	 * <p>
	 * This method repeatedly fetches items in pages of size {@code batchSize} and
	 * passes each non-empty batch to the provided {@code consumer} for processing.
	 * Fetching continues until no more items are returned.
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
		List<T> batch;

		while (true) {
	        try {
	            batch = fetcher.fetch(fields, offset, batchSize, filter);
	        } catch (Exception e) {
	            throw new RuntimeException("Error fetching batch at offset " + offset + " when getting " + batchSize + " items", e);
	        }

	        if (batch == null || batch.isEmpty()) {
	            break;
	        }

	        try {
	            consumer.process(batch);
	        } catch (Exception e) {
	            throw new RuntimeException("Error processing batch starting at offset " + offset, e);
	        }

	        offset += batch.size();
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
}
