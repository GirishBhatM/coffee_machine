package com.dunzo.coffee.machine.service;

import java.util.Map;

import com.dunzo.coffee.machine.service.impl.CoffeeMachineServiceImpl;

/**
 * Factory class for building the {@linkplain CoffeeMachineService}
 * 
 * @author girishbhat.m7@gmail.com
 *
 */
public class CoffeeMachineServiceFactory {

	public static CoffeeMachineService getCoffeeMachineService(int outlets, Map<String, Integer> inventory) {
		return new CoffeeMachineServiceImpl(outlets, inventory);
	}

	private CoffeeMachineServiceFactory() {
	}
}
