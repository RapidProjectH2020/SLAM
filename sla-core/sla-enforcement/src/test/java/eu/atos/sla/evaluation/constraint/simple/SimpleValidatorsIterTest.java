/*******************************************************************************
 * Copyright © 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.evaluation.constraint.simple;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.junit.Test;

import eu.atos.sla.evaluation.constraint.simple.Operator;
import eu.atos.sla.evaluation.constraint.simple.SimpleConstraintEvaluator.SimpleAverageValidatorIter;
import eu.atos.sla.evaluation.constraint.simple.SimpleConstraintEvaluator.SimpleValidatorIter;
import eu.atos.sla.monitoring.IMonitoringMetric;
import eu.atos.sla.monitoring.simple.MonitoringMetric;

public class SimpleValidatorsIterTest {

	private Collection<IMonitoringMetric> generateMetricsCollection(double[] array) {

		ArrayList<IMonitoringMetric> result = new ArrayList<IMonitoringMetric>();
		
		int t = 0;
		for (double value : array) {
			
			IMonitoringMetric m = new MonitoringMetric("metric", value, new Date(t));
			result.add(m);
			
			t++;
		}
		return result;
	}
	
	
	public void checkMetric(IMonitoringMetric metric, String expectedKey, double expectedValue,
			long expectedTime) {
		
		assertEquals(expectedKey, metric.getMetricKey());
		assertEquals(expectedValue, Double.parseDouble(metric.getMetricValue()), 0);
		assertEquals(new Date(expectedTime), metric.getDate());
		
	}
	
	public void checkMetrics(Iterable<IMonitoringMetric> iterable, String expectedKey,
			double[] expectedValues, long[] expectedTimes) {
		
		int i = 0;
		for (IMonitoringMetric m : iterable) {
			
			if (m == null) {
				continue;
			}
			assertEquals("metric", m.getMetricKey());
			assertEquals(expectedValues[i], Double.parseDouble(m.getMetricValue()), 0);
			assertEquals(new Date(expectedTimes[i]), m.getDate());
			
			i++;
		}
		
	}
	
	@Test
	public void testSimpleValidatorIter() {

		Collection<IMonitoringMetric> metrics = generateMetricsCollection(new double[] {
				1, 0, -1, 0, 2, 0, -2, 0, 3, 0, -3, 0
		});
		
		SimpleValidatorIter it = new SimpleValidatorIter(metrics, Operator.GE, new double[] {0});
		
		Iterable<IMonitoringMetric> iterable = it.iterable();
		
		/*
		 * These are the times and values that breaches should have
		 */
		long[] times = new long[] { 2, 6, 10 };
		double[] values = new double[] { -1, -2, -3 };
		
		checkMetrics(iterable, "metric", values, times);
	}

	@Test
	public void testSimpleValidatorAverageIter() {
		
		Collection<IMonitoringMetric> metrics = generateMetricsCollection(new double[] {
				1, 0, -1, 0, 2, 0, -2, 0, 3, 0, -3, 0
		});
		
		SimpleAverageValidatorIter it = new SimpleAverageValidatorIter(metrics, Operator.GT, new double[] {0}, 10);
		
		Iterable<IMonitoringMetric> iterable = it.iterable();
		
		/*
		 * These are the times and values that breaches should have
		 */
		long[] times = new long[] { 11 };
		double[] values = new double[] { 0.0 };
		
		checkMetrics(iterable, "metric", values, times);
	}


}
