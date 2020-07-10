package com.dunzo.coffee.machine.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * POJO for displaying message
 * 
 * @author gmb0028
 *
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
public class DisplayMessage {
	private String message;
	private boolean error;
}
