package com.dunzo.coffee.machine.service;

import java.util.List;
import java.util.concurrent.Callable;

import com.dunzo.coffee.machine.model.DisplayMessage;

/**
 * Beverage preparation task
 * 
 * @author girishbhat.m7@gmail.com
 *
 */
public interface BeverageTask extends Callable<List<DisplayMessage>> {

	String name();
}
