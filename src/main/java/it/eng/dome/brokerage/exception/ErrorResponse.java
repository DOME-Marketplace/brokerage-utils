package it.eng.dome.brokerage.exception;

import java.time.OffsetDateTime;

import org.springframework.http.HttpStatus;

import jakarta.servlet.http.HttpServletRequest;

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
	OffsetDateTime timestamp;
	
	/**
	 * The custom exception type
	 */
	String exceptionType;
	
	/**
	 * The path of the request
	 */
	String path;
	
	/**
	 * Class default constructor
	 */
	public ErrorResponse() {
		super();
	}


	/** Class constructor 
	 * 
	 * @param request The request that generates the error
	 * @param status The HTTP status code series
	 * @param exc The thrown exception
	 */
	public ErrorResponse(HttpServletRequest request, HttpStatus status, Throwable exc) {
		this.exceptionType=exc.getClass().getSimpleName();
		this.path=request.getServletPath();
		this.status = status;
		this.statusCode = status.value();
		this.message = exc.getMessage();
		this.timestamp = OffsetDateTime.now();
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
		return timestamp;
	}

	/**
	 * Gets the type of custom exception
	 * @return A string representing the custom exception
	 */
	public String getExceptionType() {
		return exceptionType;
	}

	/**
	 * Gets the path of the request that generates the exception
	 * @return A string representing the path of the request
	 */
	public String getPath() {
		return path;
	}

	

}
