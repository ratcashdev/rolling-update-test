/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ratcash.rollingupdate;

import javax.enterprise.context.ApplicationScoped;

/**
 *
 */
@ApplicationScoped
public class Configuration {
	
	public String getConfig(String name) {
		return name + "-productionConfig";
	}
}
