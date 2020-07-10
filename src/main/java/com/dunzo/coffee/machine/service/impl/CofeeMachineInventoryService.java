package com.dunzo.coffee.machine.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.dunzo.coffee.machine.model.DisplayMessage;
import com.dunzo.coffee.machine.model.ItemQuantity;
import com.dunzo.coffee.machine.service.InventoryService;

import lombok.NonNull;

/**
 * {@linkplainInventoryService} reference implementation
 * 
 * @author girishbhat.m7@gmail.com
 *
 */
public class CofeeMachineInventoryService implements InventoryService {

	private static final Map<String, Integer> INVENTORY = new ConcurrentHashMap<>();

	private Object lock = new Object();

	@Override
	public void refill(List<ItemQuantity> itemQuantities) {
		synchronized (lock) {
			itemQuantities.forEach(item -> INVENTORY.put(item.getItemName(),
					INVENTORY.getOrDefault(item.getItemName(), 0) + item.getQuantity()));
		}
	}

	@Override
	public List<DisplayMessage> fetch(@NonNull List<ItemQuantity> itemQuantities) {
		synchronized (lock) {
			Map<String, Integer> toReduce = new HashMap<>();
			List<DisplayMessage> messages = new ArrayList<>();
			for (ItemQuantity itemQuantity : itemQuantities) {
				if (!isAvaialble(itemQuantity.getItemName(), itemQuantity.getQuantity())) {
					messages.add(new DisplayMessage(itemQuantity.getItemName(), true));
					return messages;
				} else {
					messages.add(new DisplayMessage(itemQuantity.getItemName(), false));
					toReduce.put(itemQuantity.getItemName(), itemQuantity.getQuantity());
				}
			}
			updateInventory(toReduce);
			return messages;
		}
	}

	private boolean isAvaialble(String itemName, int quantity) {
		return INVENTORY.getOrDefault(itemName, 0) - quantity >= 0;
	}

	private void updateInventory(Map<String, Integer> inventory) {
		inventory.entrySet().forEach(e -> INVENTORY.put(e.getKey(), INVENTORY.get(e.getKey()) - e.getValue()));
	}

	@Override
	public boolean isRunningLow(String itemName, int threshHold) {
		return INVENTORY.getOrDefault(itemName, 0) < threshHold;
	}

}
