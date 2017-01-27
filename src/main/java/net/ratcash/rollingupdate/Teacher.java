/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ratcash.rollingupdate;

import com.airhacks.porcupine.execution.boundary.Dedicated;
import java.util.concurrent.ExecutorService;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;


/**
 *
 */
@Stateless
public class Teacher {
	
	@EJB
	Laborant laborant;
	
	public String getSubject() {
		return "physics";
	}
	
	public String getExperimentResult() {
		return laborant.getExperimentResult();
	}
}
