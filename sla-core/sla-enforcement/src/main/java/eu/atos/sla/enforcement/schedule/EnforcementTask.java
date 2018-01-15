/*******************************************************************************
 * Copyright © 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.enforcement.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import eu.atos.sla.datamodel.IAgreement;
import eu.atos.sla.datamodel.IEnforcementJob;
import eu.atos.sla.enforcement.AgreementEnforcement;

@Scope("prototype")
public class EnforcementTask implements Runnable {
	private static Logger logger = LoggerFactory.getLogger(EnforcementTask.class);

	private IEnforcementJob job;
	@Autowired
	AgreementEnforcement agreementEnforcement;

	public EnforcementTask(){
		
	}

	public EnforcementTask(IEnforcementJob job) {
		this.job = job;
	}

	@Override
	public void run() {
		try{
            IAgreement agreement = job.getAgreement();

            agreementEnforcement.enforce(agreement, job.getLastExecuted(), false);
		}catch(Exception e){
			logger.error("Error with thread " + Thread.currentThread().getName(), e);
		}
	}
	

}	
