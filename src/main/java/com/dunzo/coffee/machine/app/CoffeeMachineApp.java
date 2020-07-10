package com.dunzo.coffee.machine.app;

import java.io.File;
import java.io.IOException;

import com.dunzo.coffee.machine.model.CoffeeMachineConfig;
import com.dunzo.coffee.machine.service.CoffeeMachineService;
import com.dunzo.coffee.machine.service.CoffeeMachineServiceFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * Demo app
 * 
 * @author girishbhat.m7@gmail.com
 *
 */
@Slf4j
public class CoffeeMachineApp {

	public static void main(String[] args) {
		if (null == args || args.length == 0) {
			log.error("input file path is missing");
			return;
		}
		String inputeFilePath = args[0];

		ObjectMapper objectMapper = new ObjectMapper();
		try {
			CoffeeMachineConfig cfg = objectMapper.readValue(new File(inputeFilePath), CoffeeMachineConfig.class);
			CoffeeMachineService coffeeMachineService = CoffeeMachineServiceFactory.getCoffeeMachineService(cfg.getMachine().getOutlet().getCount(),
					cfg.getMachine().getTotalItemQuantity());
			
			coffeeMachineService.order(cfg.getMachine().getBeverages());
		} catch (IOException e) {
			log.error("error occured in processing request", e);
		}

	}

}
