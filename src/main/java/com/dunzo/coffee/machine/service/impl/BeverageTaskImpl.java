package com.dunzo.coffee.machine.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.dunzo.coffee.machine.model.DisplayMessage;
import com.dunzo.coffee.machine.model.ItemQuantity;
import com.dunzo.coffee.machine.service.BeverageTask;

/**
 * Beverage preparation task implementation
 * 
 * @author girishbhat.m7@gmail.com
 *
 */
public class BeverageTaskImpl implements BeverageTask {

	private CofeeMachineInventoryService inventoryService;
	private Map<String, Integer> ingredients;
	private String name;

	public BeverageTaskImpl(CofeeMachineInventoryService inventoryService, Map<String, Integer> ingedients,String name) {
		this.inventoryService = inventoryService;
		this.ingredients = ingedients;
		this.name = name;
	}

	@Override
	public List<DisplayMessage> call() throws Exception {
		List<ItemQuantity> itemquantities = ingredients.entrySet().stream()
				.map(e -> new ItemQuantity(e.getKey(), e.getValue())).collect(Collectors.toList());
		return inventoryService.fetch(itemquantities);
	}

	@Override
	public String name() {
		return this.name;
	}

}
