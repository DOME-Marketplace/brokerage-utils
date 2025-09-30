package it.eng.dome.brokerage.api.page;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class PaginationUtils {

	public static <T> Stream<T> streamAll(PagedFetcher<T> fetcher, String fields, Map<String, String> filter, int pageSize) {

        Iterator<T> iterator = new Iterator<>() {
            private int offset = 0;
            private List<T> currentBatch = List.of();
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

                Page<T> page =  fetcher.fetch(fields, offset, pageSize, filter);

                currentBatch = page.getContent();
                if (currentBatch.isEmpty()) {
                    finished = true;
                    return false;
                }

                offset += pageSize;
                index = 0;

                if (!page.hasNext()) {
                    finished = true;
                }

                return true;
            }

            @Override
            public T next() {
                return currentBatch.get(index++);
            }
        };

        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, 0), false);
    }
}
