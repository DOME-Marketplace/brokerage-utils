package it.eng.dome.brokerage.markdown;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class AbstractMarkdownGenerator {
	
	private final Logger logger = LoggerFactory.getLogger(AbstractMarkdownGenerator.class);
	
	public StringBuilder generateMarkdownFromJson(String json) throws Exception {

		try {

			ObjectMapper mapper = new ObjectMapper();
			JsonNode root = mapper.readTree(json);

			String title = root.path("info").path("title").asText();
			String version = root.path("info").path("version").asText();
			String description = root.path("info").path("description").asText();

			// Header
			StringBuilder md = new StringBuilder();
			md.append("# ")
				.append(title).append("\n\n")
				.append("**Version:** ").append(version).append("  \n")
				.append("**Description:** ").append(description).append("  \n").append("\n\n")
				.append("## REST API Endpoints\n\n");

			// List of Endpoint
			Map<String, List<String>> tagToEndpoints = new LinkedHashMap<>();

			JsonNode paths = root.path("paths");
			paths.fieldNames().forEachRemaining(path -> {
				JsonNode methods = paths.path(path);
				methods.fieldNames().forEachRemaining(method -> {
					JsonNode operation = methods.path(method);
					String tag = operation.path("tags").get(0).asText();
					String opId = operation.path("operationId").asText();
					String line = String.format("| %s | `%s` | %s |", method.toUpperCase(), path, opId);
					tagToEndpoints.computeIfAbsent(tag, k -> new ArrayList<>()).add(line);
				});
			});

			// Display data
			for (Map.Entry<String, List<String>> entry : tagToEndpoints.entrySet()) {
				md.append("### ")
				.append(entry.getKey())
				.append("\n")
				.append("| Verb | Path | Task |\n")
				.append("|------|------|------|\n");
				
				entry.getValue().forEach(line -> md.append(line).append("\n"));
				md.append("\n");
			}

			return md;
		} catch (Exception e) {
			logger.error("Error: {}", e.getMessage());
			throw new Exception("Failed to generate API Markdown", e);
		}
	}

}
