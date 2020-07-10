package com.dunzo.coffee.machine.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Request mapping POJO
 * 
 * @author girishbhat.m7@gmail.com
 *
 */
@Getter
@Setter
@ToString
public class Outlet {

	@JsonProperty("count_n")
	private int count;
}
