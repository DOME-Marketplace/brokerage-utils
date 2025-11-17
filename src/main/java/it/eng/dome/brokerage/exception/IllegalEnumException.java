package it.eng.dome.brokerage.exception;

public class IllegalEnumException extends RuntimeException {

	private static final long serialVersionUID = -8138969884546925798L;

	public IllegalEnumException(String message) {
		super(message);
	}

	public IllegalEnumException(String message, Throwable cause) {
		super(message, cause);
	}

}
