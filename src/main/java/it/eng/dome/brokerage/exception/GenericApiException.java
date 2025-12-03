package it.eng.dome.brokerage.exception;

import java.lang.reflect.Method;

public class GenericApiException extends Exception {

	private static final long serialVersionUID = -5291644032058156158L;

	private final int code;
    private final String responseBody;

    public GenericApiException(Throwable cause) {
        super(cause);
        int tmpCode = -1;
        String tmpBody = null;

        try {
            
            Method getCodeMethod = cause.getClass().getMethod("getCode");
            tmpCode = (Integer) getCodeMethod.invoke(cause);

            Method getBodyMethod = cause.getClass().getMethod("getResponseBody");
            tmpBody = (String) getBodyMethod.invoke(cause);
        } catch (Exception e) {
            
        }

        this.code = tmpCode;
        this.responseBody = tmpBody;
    }

    public int getCode() {
        return code;
    }

    public String getResponseBody() {
        return responseBody;
    }
    
    public String getMessage() {
        return String.format("HTTP error code: %s - HTTP body: %s", getCode(), getResponseBody());
    }
}
