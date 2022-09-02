package org.apache.coyote.http11;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

	private static final int URL_PATH_INDEX = 1;
	private static final String GAP = " ";
	private static final String START_QUERY_STRING_DELIMITER = "?";
	private static final String QUERY_CONNECT_DELIMITER = "&";
	private static final String QUERY_VALUE_DELIMITER = "=";
	private static final int NAME_INDEX = 0;
	private static final int VALUE_INDEX = 1;

	private String url;
	private Map<String, String> params;

	public HttpRequest(String url, Map<String, String> params) {
		this.url = url;
		this.params = new HashMap<>(params);
	}

	public static HttpRequest from(String url) {
		return new HttpRequest(findUrl(url), findParam(url));
	}

	private static String findUrl(String url) {
		return url.split(GAP)[URL_PATH_INDEX];
	}

	private static Map<String, String> findParam(String url) {
		String queryString = extractQueryString(url);
		return parseQueryString(queryString);
	}

	private static String extractQueryString(String url) {
		int index = url.indexOf(START_QUERY_STRING_DELIMITER);
		return url.substring(index + 1);
	}

	private static Map<String, String> parseQueryString(String queryString) {
		String[] values = queryString.split(QUERY_CONNECT_DELIMITER);
		Map<String, String> params = new HashMap<>();
		for (String param : values) {
			final String[] data = param.split(QUERY_VALUE_DELIMITER);
			params.put(data[NAME_INDEX], data[VALUE_INDEX]);
		}
		return params;
	}

	public String getUrl() {
		return url;
	}

	public Map<String, String> getParams() {
		return params;
	}
}
