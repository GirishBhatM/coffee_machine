package com.dunzo.coffee.machine.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import com.dunzo.coffee.machine.exception.CoffeeMachineServiceException;
import com.dunzo.coffee.machine.model.DisplayMessage;
import com.dunzo.coffee.machine.model.ItemQuantity;
import com.dunzo.coffee.machine.service.BeverageTask;
import com.dunzo.coffee.machine.service.CoffeeMachineService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CoffeeMachineServiceImpl extends CofeeMachineInventoryService implements CoffeeMachineService {

	private static final int MAX_OUTLET_CAP = 10;

	private final ExecutorService executorService;

	public CoffeeMachineServiceImpl(int outlets, Map<String, Integer> inventory) {
		int machineOutlet = outlets <= 0 ? 1 : outlets % MAX_OUTLET_CAP;
		executorService = Executors.newFixedThreadPool(machineOutlet);
		fillInventory(inventory);
	}

	private void fillInventory(Map<String, Integer> inventory) {
		List<ItemQuantity> itemQuantities = inventory.entrySet().stream()
				.map(e -> new ItemQuantity(e.getKey(), e.getValue())).collect(Collectors.toList());
		super.refill(itemQuantities);
	}

	@Override
	public List<DisplayMessage> order(Map<String, Map<String, Integer>> beverages) {
		Map<String, Future<List<DisplayMessage>>> resultList = new HashMap<>();
		List<DisplayMessage> messages = new ArrayList<>();
		for (BeverageTask task : mapToTask(beverages)) {
			resultList.put(task.name(), executorService.submit(task));
		}

		for (Entry<String, Future<List<DisplayMessage>>> result : resultList.entrySet()) {
			try {
				List<DisplayMessage> status = result.getValue().get();
				Optional<DisplayMessage> op = status.stream().filter(DisplayMessage::isError).findFirst();
				if (op.isPresent()) {
					log.info("{} cannot be prepared due to {} is not available", result.getKey(), op.get().getMessage());
					messages.add(new DisplayMessage(String.format("%s cannot be prepared due to %s is not available", result.getKey(), op.get().getMessage()),true));
				} else {
					log.info("{} is prepared", result.getKey());
					messages.add(new DisplayMessage(String.format("%s is prepared",result.getKey()),false));
				}
			} catch (InterruptedException | ExecutionException e) {
				log.error("error occurred in order processing", e);
				throw new CoffeeMachineServiceException("error occured in order processing");
			}
		}

		return messages;
	}

	private List<BeverageTask> mapToTask(Map<String, Map<String, Integer>> beverages) {
		return beverages.entrySet().stream().map(e -> new BeverageTaskImpl(this, e.getValue(), e.getKey()))
				.collect(Collectors.toList());
	}
}
