package it.eng.dome.brokerage.billing.utils;

public class UrlPathUtils {
	
	public static String removeFinalSlash(String s) {
		String path = s;
		while (path.endsWith("/")) {
			path = path.substring(0, path.length() - 1);
		}
		return path;
	}
	
	public static String removeInitialSlash(String s) {
		String path = s;
		while (path.startsWith("/")) {
			path = path.substring(1);
		}				
		return path;
	}
}
