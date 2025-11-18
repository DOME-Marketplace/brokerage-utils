package it.eng.dome.brokerage.exception;

import java.net.URI;

import org.springframework.http.HttpStatus;


/**
 * Default Details object compliant with RFC 7807
 * Used as default error response format
 */

public class DefaultErrorResponse {

	/**
	 * A URI reference that identifies the problem type (default: "about:blank")
	 */
	private URI type;

	/**
	 * A short, human-readable summary of the problem
	 */
	private String title;

	/**
	 * The HTTP status code
	 */
	private int status;

	/**
	 * A human-readable explanation specific to this occurrence
	 */
	private String detail;

	/**
	 * A URI reference to the request instance
	 */
	private URI instance;

	public DefaultErrorResponse(String title, int status, String detail, URI instance) {
		this.title = title;
		this.status = status;
		this.detail = detail;
		this.instance = instance;
		this.type = URI.create("about:blank");
	}

	public DefaultErrorResponse(HttpStatus httpStatus, String detail, URI instance) {
		this(httpStatus, detail, instance, null);
    }

	public DefaultErrorResponse(HttpStatus httpStatus, String detail, URI instance, URI type) {
		this.type = (type != null) ? type : URI.create("about:blank");
        this.status = httpStatus.value();             
        this.title = httpStatus.getReasonPhrase();      
        this.detail = detail;
        this.instance = instance;
    }
	
	// Getters and setters

	public URI getType() {
		return type;
	}

	public void setType(URI type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public URI getInstance() {
		return instance;
	}

	public void setInstance(URI instance) {
		this.instance = instance;
	}
}
