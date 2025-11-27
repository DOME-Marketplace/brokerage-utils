package it.eng.dome.brokerage.api.config;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DomeTmfSchemaConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(DomeTmfSchemaConfig.class);

	private static final String PREFIX = "dome.tmf.schema.";
	private static final Properties props = new Properties();
	private static final Map<String, String> schemaMap = new HashMap<>();

	static {
		try (InputStream is = DomeTmfSchemaConfig.class.getClassLoader()
				.getResourceAsStream("dome-tmf-schema.properties")) {

			if (is != null) {
				logger.debug("Loading properties file dome-tmf-schema.properties");
				props.load(is);
			}

			// Override ENV + system properties
			/*props.stringPropertyNames().forEach(key -> {
				if (key.startsWith(PREFIX)) {
					override(key);
				}
			});*/

			// Build dynamic schema map
			props.stringPropertyNames().forEach(key -> {
				if (key.startsWith(PREFIX)) {
					schemaMap.put(key.substring(PREFIX.length()), props.getProperty(key));
				}
			});

			printProperties();

		} catch (Exception e) {
			throw new RuntimeException("Cannot load dome-tmf-schema.properties", e);
		}
	}

	/*private static void override(String key) {
		String envKey = key.replace(".", "_").toUpperCase();
		String envValue = System.getenv(envKey);
		if (envValue != null)
			props.setProperty(key, envValue);

		String sysValue = System.getProperty(key);
		if (sysValue != null)
			props.setProperty(key, sysValue);
	}*/

	public static Map<String, String> getSchemas() {
		return schemaMap;
	}

	public static String get(String schemaName) {
		return schemaMap.get(schemaName);
	}

	public static void printProperties() {
		logger.info("=========== Dome TMF Schema Properties ===========");
		schemaMap.forEach((k, v) -> logger.info(k + " = " + v));
		logger.info("==================================================");
	}
}
