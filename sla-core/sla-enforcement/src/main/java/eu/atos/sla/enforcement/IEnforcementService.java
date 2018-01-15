/*******************************************************************************
 * Copyright © 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.enforcement;

import java.util.List;
import java.util.Map;

import eu.atos.sla.datamodel.IAgreement;
import eu.atos.sla.datamodel.IEnforcementJob;
import eu.atos.sla.datamodel.IGuaranteeTerm;
import eu.atos.sla.evaluation.guarantee.GuaranteeTermEvaluator.GuaranteeTermEvaluationResult;
import eu.atos.sla.monitoring.IMonitoringMetric;


/**
 * Business layer for the enforcement processes.
 * 
 * @author rsosa
 *
 */
public interface IEnforcementService {

	/**
	 * Store a new EnforcementJob.
	 * 
	 * @param job
	 * @return created enforcement job
	 */
	IEnforcementJob createEnforcementJob(IEnforcementJob job);

	/**
	 * Create a disabled enforcement job for an agreement. 
	 * 
	 * @param agreementId of agreement to enforce
	 * @return created enforcement job
	 */
	IEnforcementJob createEnforcementJob(String agreementId);
	
	/**
	 * Delete an existing EnforcementJob
	 * 
	 * @param agreementId AgreementId of the associated agreement
	 * @return <code>true</code> if job deleted; else <code>false</code>
	 */
	boolean deleteEnforcementJobByAgreementId(String agreementId);
	
	/**
	 * Get job by id
	 */
	IEnforcementJob getEnforcementJob(Long id);
	
	/**
	 * Get job by related agreementId
	 */
	IEnforcementJob getEnforcementJobByAgreementId(String agreementId);
	
	/**
	 * Get all enforcement jobs
	 */
	List<IEnforcementJob> getEnforcementJobs();
	
	/**
	 * Start the enforcement job with related agrementId
	 */
	boolean startEnforcement(String agreementId);
	
	/**
	 * Stop the enforcement job with related agrementId
	 */
	boolean stopEnforcement(String agreementId);

	/**
	 * Save to repository the result of an enforcement process.
	 * 
	 * @param agreement Agreement enforced.
	 * @param result List of violations and business violations that were raised in the enforcement.
	 */
	void saveEnforcementResult(IAgreement agreement, Map<IGuaranteeTerm, GuaranteeTermEvaluationResult> result);
	
	/**
	 * Enforce an agreement with the given metrics.
	 * @param agreement to enforce
	 * @param metrics to enforce.
	 */
	void doEnforcement(IAgreement agreement, Map<IGuaranteeTerm, List<IMonitoringMetric>> metrics);
	
	
	void saveCheckedGuaranteeTerm(IGuaranteeTerm term);
	
}
