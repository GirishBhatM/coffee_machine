package com.dunzo.coffee.machine.it;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.dunzo.coffee.machine.model.CoffeeMachineConfig;
import com.dunzo.coffee.machine.model.DisplayMessage;
import com.dunzo.coffee.machine.service.CoffeeMachineService;
import com.dunzo.coffee.machine.service.CoffeeMachineServiceFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * integration test for coffee machine operation
 * 
 * @author girishbhat.m7@gmail.com
 *
 */
public class CoffeeMachineOperationIT {

	@Test
	public void testWithAtMost2BeveragePossible() throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();
		String filePath = Paths.get("").toAbsolutePath() + "/src/test/resources/testinput_at_most_2.json";
		CoffeeMachineConfig cfg = objectMapper.readValue(new File(filePath),CoffeeMachineConfig.class);
		
		CoffeeMachineService service = CoffeeMachineServiceFactory.getCoffeeMachineService(
				cfg.getMachine().getOutlet().getCount(), cfg.getMachine().getTotalItemQuantity());
		List<DisplayMessage> output =service.order(cfg.getMachine().getBeverages());
		
		Assert.assertEquals(2, output.stream().filter(DisplayMessage::isError).count());
		Assert.assertEquals(2, output.stream().filter(m->!m.isError()).count());
	}
	
	@Test
	public void testWithZeroBeveragePossible() throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();
		String filePath = Paths.get("").toAbsolutePath() + "/src/test/resources/testinput_zero_beverage.json";
		CoffeeMachineConfig cfg = objectMapper.readValue(new File(filePath),CoffeeMachineConfig.class);
		
		CoffeeMachineService service = CoffeeMachineServiceFactory.getCoffeeMachineService(
				cfg.getMachine().getOutlet().getCount(), cfg.getMachine().getTotalItemQuantity());
		List<DisplayMessage> output =service.order(cfg.getMachine().getBeverages());
		
		Assert.assertEquals(2, output.stream().filter(DisplayMessage::isError).count());
		Assert.assertEquals(0, output.stream().filter(m->!m.isError()).count());
	}
	
	@Test
	public void testWithAllBeveragePossible() throws Exception {

		ObjectMapper objectMapper = new ObjectMapper();
		String filePath = Paths.get("").toAbsolutePath() + "/src/test/resources/testinput_all_beverage.json";
		CoffeeMachineConfig cfg = objectMapper.readValue(new File(filePath),CoffeeMachineConfig.class);
		
		CoffeeMachineService service = CoffeeMachineServiceFactory.getCoffeeMachineService(
				cfg.getMachine().getOutlet().getCount(), cfg.getMachine().getTotalItemQuantity());
		List<DisplayMessage> output =service.order(cfg.getMachine().getBeverages());
		
		Assert.assertEquals(0, output.stream().filter(DisplayMessage::isError).count());
		Assert.assertEquals(4, output.stream().filter(m->!m.isError()).count());
		
		Assert.assertEquals(true,service.isRunningLow("hot_water", 10));
	}
	
	

}
