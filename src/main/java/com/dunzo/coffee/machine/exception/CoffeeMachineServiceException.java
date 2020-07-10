package com.dunzo.coffee.machine.exception;

import lombok.Getter;

/**
 * Application exception class
 * 
 * @author girishbhat.m7@gmail.com
 *
 */

@Getter
public class CoffeeMachineServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7255759197728192094L;

	private final String message;

	public CoffeeMachineServiceException(String message) {
		super(message);
		this.message = message;
	}

}
