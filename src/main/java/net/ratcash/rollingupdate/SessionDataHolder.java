/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ratcash.rollingupdate;

import java.io.Serializable;
import java.util.Random;
import javax.enterprise.context.SessionScoped;

/**
 *
 */
@SessionScoped
public class SessionDataHolder implements Serializable {

	private static final long serialVersionUID = -3918774591843057160L;
	
	int temperature = 0;
	
	static Random r = new Random();

	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}
	
	public int getAndChage() {
		int oldTemp = temperature;
		temperature = r.nextInt(999)+1;
		return oldTemp;
	}
}
