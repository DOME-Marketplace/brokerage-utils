package it.eng.dome.brokerage.exception;


public class BadRelatedPartyException extends RuntimeException {

	private static final long serialVersionUID = 5761316523032139633L;

	public BadRelatedPartyException(String message) {
		super(message);
	}

	public BadRelatedPartyException(String message, Throwable cause) {
		super(message, cause);
	}

}
