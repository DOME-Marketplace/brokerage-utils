package it.eng.dome.brokerage.observability;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import it.eng.dome.brokerage.billing.utils.DateUtils;
import it.eng.dome.brokerage.observability.health.Check;
import it.eng.dome.brokerage.observability.health.Health;
import it.eng.dome.brokerage.observability.health.HealthStatus;
import it.eng.dome.brokerage.observability.info.Info;

@Service
public abstract class AbstractHealthService {

    @Autowired
    private BuildProperties buildProperties;
    
    private static final ObjectMapper mapper = new ObjectMapper()
    	.registerModule(new JavaTimeModule())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    /**
     * Returns basic service information based on BuildProperties.
     *
     * @return Info object containing name, version, and release time.
     */
    public Info getInfo() {
        Info info = new Info();
        info.setName(buildProperties.getName());        
        info.setVersion(buildProperties.getVersion());
        info.setReleaseTime(DateUtils.getFormatterTimestamp(buildProperties.getTime()));

        return info;
    }

    /**
     * Fully implement this method by adding Checks on internal status and dependencies, as well as notes and output.
     * For more information, see: https://datatracker.ietf.org/doc/html/draft-inadarei-api-health-check-06
     * 
     * <p>
     * A typical implementation might look like this:
     * </p>
     * <pre>{@code
     * Health h = new Health();
     * h.setDescription("Health for " + buildProperties.getName());
     * h.setStatus(HealthStatus.UNKNOWN);
     * h.setReleaseId(buildProperties.getVersion());
     * return h;
     * }</pre>
     * 
     * @return a {@link Health} object representing the health status of the component
     */
    public abstract Health getHealth();
    
    
    // ------------------------
    // Utility methods
    // ------------------------
    
    
    /**
     * Creates and initializes a new {@link Check} with main fields preconfigured.
     *
     * @param componentName    logical component name (e.g., "self", "tmf-api")
     * @param measurementName  specific measurement or sub-component (e.g., "scheduler", "getInfo")
     * @param componentType    technical type or category (e.g., "scheduler", "tmf637", "api")
     * @return a new {@link Check} object with time set to now
     */
    protected Check createCheck(String componentName, String measurementName, String componentType) {
        Check c = new Check();
        c.setComponentName(componentName);
        c.setMeasurementName(measurementName);
        c.setComponentType(componentType);
        c.setTime(OffsetDateTime.now());
        return c;
    }

    
    /**
     * Creates and initializes a new {@link Check} instance with the main fields pre-configured,
     * including its {@link HealthStatus} and output message.
     * <p>
     * This helper method provides a standardized way to create {@code Check} objects
     * with a predefined status and output, ensuring consistency across all health checks
     * in services extending {@code AbstractHealthService}.
     * </p>
     *
     * <p>
     * The check will have its {@code componentName}, {@code measurementName}, {@code componentType},
     * timestamp set to the current time, and an empty list of {@code affectedEndpoints}.
     * </p>
     *
     * @param componentName    logical name of the component being checked (e.g., "self", "tmf-api")
     * @param measurementName  specific measurement or sub-component of the component (e.g., "scheduler", "getInfo")
     * @param componentType    technical type or category of the component (e.g., "scheduler", "tmf637", "api")
     * @param status           initial {@link HealthStatus} of the check (e.g., PASS, WARN, FAIL)
     * @param output           descriptive message or result associated with the check
     * @return a new {@link Check} object with the provided status, output, and time set to {@link java.time.OffsetDateTime#now()}
     */
	protected Check createCheck(String componentName, String measurementName, String componentType, HealthStatus status, String output) {
		Check c = createCheck(componentName, measurementName, componentType);
		c.setStatus(status);
		c.setOutput(output);
		return c;
	}
    
    /**
     * Marks an existing Check as PASS and sets the output message.
     *
     * @param check  the Check to update
     * @param output descriptive output
     */
    protected void markCheckAsPass(Check check, String output) {
        check.setStatus(HealthStatus.PASS);
        check.setOutput(output);
    }

    /**
     * Marks an existing Check as FAIL and sets the output message.
     *
     * @param check  the Check to update
     * @param output descriptive output or error
     */
    protected void markCheckAsFail(Check check, String output) {
        check.setStatus(HealthStatus.FAIL);
        check.setOutput(output);
    }

    /**
     * Builds human-readable notes from a Health object.
     * Can be overridden in child classes for custom messages.
     *
     * @param health the Health object containing all checks
     * @return list of strings describing the current health status
     */
    protected List<String> buildNotes(Health health) {
    	List<String> notes = new ArrayList<>();

        for (Check c : health.getAllChecks()) {
            String checkId = c.getComponentName() + "/" + c.getMeasurementName() + "/" + c.getComponentType();
            switch (c.getStatus()) {
                case PASS:
                    //notes.add(checkId + " is OK");
                    break;
                case WARN:
                    notes.add(checkId + " has warnings or degraded performance");
                    break;
                case FAIL:
                    notes.add(checkId + " failed" + (c.getOutput() != null ? ": " + c.getOutput() : ""));
                    break;
                case UNKNOWN:
                default:
                    notes.add(checkId + " status is unknown");
            }

            if (c.getAffectedEndpoints() != null && !c.getAffectedEndpoints().isEmpty()) {
                notes.add("Affected endpoints: " + String.join(", ", c.getAffectedEndpoints()));
            }
        }
        
        return notes;
    }
    
    /**
     * Serializes a Java object into its JSON representation.
     * 
     * @param obj the Java object to be serialized (e.g., {@link Info}, {@link Health}) 
     * @return a JSON string representing the object, or an error message if serialization fails
     */
    protected String toJson(Object obj) {
        if (obj == null) {
            return "null";
        }
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            return "Error serializing object: " + e.getMessage();
        }
    }
}


	
