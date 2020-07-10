package com.dunzo.coffee.machine.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	private  Map<String, Integer> inventory = new HashMap<>();

	private Object lock = new Object();

	@Override
	public void refill(List<ItemQuantity> itemQuantities) {
		synchronized (lock) {
			itemQuantities.forEach(item -> inventory.put(item.getItemName(),
					inventory.getOrDefault(item.getItemName(), 0) + item.getQuantity()));
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
		return inventory.getOrDefault(itemName, 0) - quantity >= 0;
	}

	private void updateInventory(Map<String, Integer> update) {
		update.entrySet().forEach(e -> inventory.put(e.getKey(), inventory.get(e.getKey()) - e.getValue()));
	}

	@Override
	public boolean isRunningLow(String itemName, int threshHold) {
		return inventory.getOrDefault(itemName, 0) < threshHold;
	}

}
