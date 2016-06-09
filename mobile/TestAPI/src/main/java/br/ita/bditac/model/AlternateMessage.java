package br.ita.bditac.model;

import java.io.Serializable;


public class AlternateMessage implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String success;
    
    private String message;
    
    private String validationError;
    
    public AlternateMessage() {
    	super();
    	
    }

    public AlternateMessage(String success, String message, String validationError) {
        super();

    }
	
	public String getSuccess() {
		return success;
	}
	
	public void setSuccess(String success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getValidationError() {
		return validationError;
	}
	
	public void setValidationError(String validationError) {
		this.validationError = validationError;
	}

}
