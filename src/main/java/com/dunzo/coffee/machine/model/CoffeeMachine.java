package com.dunzo.coffee.machine.model;

import java.util.HashMap;
import java.util.Map;

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
public class CoffeeMachine {

	@JsonProperty("outlets")
	private Outlet outlet;

	@JsonProperty("total_items_quantity")
	private Map<String, Integer> totalItemQuantity = new HashMap<>();

	@JsonProperty("beverages")
	private Map<String, Map<String, Integer>> beverages = new HashMap<>();

}
