package it.eng.dome.brokerage.observability;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import it.eng.dome.brokerage.billing.utils.DateUtils;
import it.eng.dome.brokerage.observability.health.Health;
import it.eng.dome.brokerage.observability.info.Info;

@Service
public abstract class AbstractHealthService {

    @Autowired
    private BuildProperties buildProperties;
    
    private static final ObjectMapper mapper = new ObjectMapper()
    	.registerModule(new JavaTimeModule())
         .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

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


	
