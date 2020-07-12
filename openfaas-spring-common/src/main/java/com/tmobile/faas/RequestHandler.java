package com.tmobile.faas;

public interface RequestHandler {

	String handle(byte[] requestPayload);

	String handle();


}
