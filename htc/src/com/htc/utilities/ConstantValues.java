package com.htc.utilities;

public class ConstantValues {
	
	public static final String MAINLINE="MAINLINE";
	public static final String SUBLINE="SUBLINE";
	public static final String UNDEFINED = "No Power Source Assigned";
	public static final Integer HOLON_CO_RED = 1;
	public static final Integer HOLON_CO_YELLOW = 2;
	public static final Integer HOLON_CO_BLUE = 3;
	public static final Integer HOLON_CO_GREEN = 4;
	public static final String POWERSUBLINE = "POWERSUBLINE";
	public static final String ACCEPTED = "ACCEPTED"; //Request was accepted by holon object(Producer)
	public static final String REJECTED = "REJECTED"; //Request was rejected by holon object(Producer)
	public static final String PENDING = "PENDING"; //Request is pending with holon object(Producer)
	/*This means message request was processed by some other holon object (Producer) or Holon Coordinator*/
	public static final String PROCESSED = "PROCESSED BY SOME OTHER PRODUCER";
	public static final String CONNECTION_RESET = "CONNECTION RESET";
	public static final String SUCCESS = "SUCCESS";
	public static final String FAILURE = "FAILURE";
	public static final String COMMUNICATION_MODE_DIRECT = "DIRECT via PEER";
	public static final String COMMUNICATION_MODE_COORDINATOR = "via HOLON COORDINATOR";
	
}
