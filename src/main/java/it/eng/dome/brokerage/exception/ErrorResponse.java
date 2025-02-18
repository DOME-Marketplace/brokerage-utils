package it.eng.dome.brokerage.exception;

import java.time.OffsetDateTime;
import java.util.Date;

import org.springframework.http.HttpStatus;

/**
 * Custom error response class to structure error messages 
 */
public class ErrorResponse {
	
	/**
	 * The HTTP status code series
	 */
	HttpStatus status;
	
	/**
	 * The integer error code to identify the type of error
	 */
	int statusCode;
	
	/**
	 * A message that describes the error
	 */
	String message;
	
	/**
	 * The time the response was generated
	 */
	OffsetDateTime timstamp;
	
	/**
	 * Class default constructor
	 */
	public ErrorResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Class constructor 
	 * 
	 * @param status The HTTP status code series
	 * @param exc The thrown exception 
	 */
	public ErrorResponse(HttpStatus status, Throwable exc) {
		this(status, exc.getMessage());
	}

	/**
	 * Class constructor 
	 * @param status The HTTP status code series
	 * @param message The message of the thrown exception
	 */
	public ErrorResponse(HttpStatus status, String message) {
		this.status = status;
		this.statusCode = status.value();
		this.message = message;
		this.timstamp = OffsetDateTime.now();
	}

	/**
	 * Gets the type of the error 
	 * @return A string representing the type of error
	 */
	public HttpStatus getStatus() {
		return status;
	}


	/**
	 * Gets the status code
	 * @return The integer error code to identify the type of error
	 */
	public int getStatusCode() {
		return statusCode;
	}

	/**
	 * Gets the message describing the error
	 * @return A message that describes the error
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Gets the error timestamp
	 * @return The time the response was generated
	 */
	public OffsetDateTime getTimstamp() {
		return timstamp;
	}

}
