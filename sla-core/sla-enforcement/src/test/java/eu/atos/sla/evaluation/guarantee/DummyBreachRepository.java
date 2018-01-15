/*******************************************************************************
 * Copyright � 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED �AS IS�, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.evaluation.guarantee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import eu.atos.sla.datamodel.IAgreement;
import eu.atos.sla.datamodel.IBreach;
import eu.atos.sla.datamodel.bean.Breach;
import eu.atos.sla.evaluation.constraint.IConstraintEvaluator;
import eu.atos.sla.evaluation.constraint.simple.SimpleConstraintEvaluator;
import eu.atos.sla.evaluation.guarantee.IBreachRepository;
import eu.atos.sla.monitoring.IMonitoringMetric;

/**
 * Breach repository that takes a list of metrics and auto calculates which are breaches.  
 * 
 * Useful for testing
 */
public class DummyBreachRepository implements IBreachRepository {

	private List<IMonitoringMetric> metrics;
	private IConstraintEvaluator constraintEvaluator;
	private String constraint;
	
	public DummyBreachRepository() {
		constraintEvaluator = new SimpleConstraintEvaluator();
	}
	
	public DummyBreachRepository(String constraint) {
		this.metrics = null;
		constraintEvaluator = new SimpleConstraintEvaluator();
		this.constraint = constraint;
	}

	public DummyBreachRepository(String constraint, List<IMonitoringMetric> metrics) {
		this.metrics = metrics;
		constraintEvaluator = new SimpleConstraintEvaluator();
		this.constraint = constraint;
	}
	
	public void init(String constraint, List<IMonitoringMetric> metrics) {
		this.metrics = metrics;
		this.constraint = constraint;
	}
	
	@Override
	public List<IBreach> getBreachesByTimeRange(IAgreement agreement, String kpiName,
			Date begin, Date end) {

		if (metrics == null) {
			return Collections.<IBreach>emptyList();
		}
		
		List<IBreach> result = new ArrayList<IBreach>();
		for (IMonitoringMetric metric : metrics) {
			Date metricDate = metric.getDate();
			String metricValue = metric.getMetricValue();
			if (begin.before(metricDate) && end.after(metricDate) && isBreach(metric)) {
				result.add(newBreach(kpiName, metricValue, metricDate));
			}
		}
		
		return result;
	}

	@Override
	public void saveBreaches(List<IBreach> breaches) {

		System.out.println("Saving list of breaches: " + breaches.size());
	}
	
	private Breach newBreach(String kpiName, String value, Date date) {
		Breach b = new Breach();
		
		b.setKpiName(kpiName);
		b.setValue(value);
		b.setDatetime(date);

		return b;
	}

	/**
	 * Fast and dirty function to know if the value is a breach (to don't setup a ConstraintEvaluator).
	 */
	private boolean isBreach(IMonitoringMetric metric) {
		List<IMonitoringMetric> breaches = constraintEvaluator.evaluate(
				metric.getMetricKey(), 
				constraint, 
				Collections.singletonList(metric));
		
		return breaches.size() > 0;
	}
	
}