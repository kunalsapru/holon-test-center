package com.htc.JavaExceptions;


/**
 * A customized Exception class to notify the user that the Arrays under operation do have the same length!
 * @author Achal
 *
 */
public class ArrayLengthMismatchException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String message;
	
	//Default Constructor also being provided
	public ArrayLengthMismatchException(){
		
	}
	
	public ArrayLengthMismatchException(String message){
		this.setMessage(message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
