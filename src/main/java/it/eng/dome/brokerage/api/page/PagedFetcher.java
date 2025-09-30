package it.eng.dome.brokerage.api.page;

import java.util.Map;

@FunctionalInterface
public interface PagedFetcher<T> {
	Page<T> fetch(String fields, int offset, int limit, Map<String, String> filter);
}