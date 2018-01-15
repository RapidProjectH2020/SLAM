/*******************************************************************************
 * Copyright © 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.evaluation;

import java.util.Collection;

import eu.atos.sla.datamodel.IAgreement;
import eu.atos.sla.monitoring.IMonitoringMetric;


/**
 * Interface that any sla provider has to implement in order to evaluate if any given metrics
 * are breaches or not.
 *  
 * @author rsosa
 * @deprecated
 */
public interface IMetricsValidator {

	/**
	 * Given a constraint, returns the variable (service property) that has to be retrieved from the monitoring
	 * module.
	 * 
	 * It is a restriction of the core that only one variable can be retrieved from monitoring per constraint.
	 * 
	 * Example:
	 * getConstraintVariable("uptime GT 99") -> "uptime"
	 */
	String getConstraintVariable(String constraint);
	
	/**
	 * Check that a constraint is being fulfilled.
	 * @param agrement: Agreement being enforced. The value of a variable may be in the ServiceDescriptionTerms.
	 * @param kpiName: Name of the KPI.
	 * @param metrics: List of metrics retrieved from the monitoring module.
	 * @param constraint: constraint to be enforced.
	 * @return Metrics that do not fulfill the constraint.
	 * 
	 * @see eu.atos.sla.datamodel.IGuaranteeTerm
	 */
	Iterable<IMonitoringMetric> getBreaches(
			IAgreement agrement, String kpiName, Collection<IMonitoringMetric> metrics, String constraint);
}
