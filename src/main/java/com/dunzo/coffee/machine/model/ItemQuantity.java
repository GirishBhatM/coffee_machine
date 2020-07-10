package com.dunzo.coffee.machine.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Request mapping POJO
 * 
 * @author girishbhat.m7@gmail.com
 *
 */
@AllArgsConstructor
@Getter
public class ItemQuantity {

	private final String itemName;
	private final int quantity;
}
