package com.dunzo.coffee.machine.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Request mapping POJO
 * 
 * @author girishbhat.m7@gmail.com
 *
 */
@Setter
@Getter
@ToString
public class CoffeeMachineConfig {

	@JsonProperty("machine")
	private CoffeeMachine machine;
}
