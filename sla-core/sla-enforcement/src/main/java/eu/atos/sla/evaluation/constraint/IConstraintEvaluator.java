/*******************************************************************************
 * Copyright © 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.evaluation.constraint;

import java.util.List;

import eu.atos.sla.monitoring.IMonitoringMetric;

/**
 * Evaluates if a list of metrics fulfill the constraint of a service level.
 * 
 * The constraint evaluator needs to parse the service level.
 * 
 * @author rsosa
 *
 */
public interface IConstraintEvaluator {
	
	/**
	 * Evaluate what metrics are considered breaches.
	 * @param kpiName KPI of the Service Level.
	 * @param constraint to fulfill.
	 * @param metrics to evaluate
	 * @return input metrics that do not fulfill the constraint.
	 * 
	 * @see eu.atos.sla.datamodel.IGuaranteeTerm
	 */
	List<IMonitoringMetric> evaluate(String kpiName, String constraint, List<IMonitoringMetric> metrics);

	/**
	 * Given a constraint, returns the variable (service property) that has to be retrieved from the monitoring
	 * module.
	 * 
	 * It is a restriction of the core that only one variable can be retrieved from monitoring per constraint
	 * (although some hacks could be performed to overcome this limitation).
	 * 
	 * Example:
	 * getConstraintVariable("uptime GT 99") -> "uptime"
	 */
	String getConstraintVariable(String constraint);

	
}
