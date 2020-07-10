package com.dunzo.coffee.machine.service;

import java.util.List;

import com.dunzo.coffee.machine.model.DisplayMessage;
import com.dunzo.coffee.machine.model.ItemQuantity;

import lombok.NonNull;

/**
 * service to handle the ingredients inventory
 * 
 * @author girishbhat.m7
 *
 */
public interface InventoryService {

	/**
	 * Refills the inventory with given list item quantity
	 * 
	 * @param itemQuantities
	 */
	void refill(@NonNull List<ItemQuantity> itemQuantities);

	/**
	 * 
	 * @param itemName item to fetch
	 * @param qunatity quantity of the item
	 * @return returns the required quantity else 0
	 */
	List<DisplayMessage> fetch(@NonNull List<ItemQuantity> itemQuantities);

	/**
	 * Checks if given item is running low below threshold quantity
	 * 
	 * @param itemName
	 * @param threshHold
	 * @return
	 */
	boolean isRunningLow(@NonNull String itemName, int threshHold);

}
