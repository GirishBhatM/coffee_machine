package com.dunzo.coffee.machine.service;

import java.util.List;
import java.util.Map;

import com.dunzo.coffee.machine.model.DisplayMessage;

/**
 * Service class for handling CoffeeMachine orders
 * 
 * @author girishbhat.m7@gmail.com
 *
 */
public interface CoffeeMachineService extends InventoryService {

	List<DisplayMessage> order(Map<String, Map<String, Integer>> beverages);

}
