/*******************************************************************************
 * Copyright © 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.evaluation.guarantee;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import eu.atos.sla.evaluation.guarantee.PoliciedServiceLevelEvaluatorTest.MonitoringMetric;
import eu.atos.sla.monitoring.IMonitoringMetric;

public class TestMetricsGenerator {
	Date now;
	Date[] dates;
	String[] values;
	
	public TestMetricsGenerator(Date now, String[] values) {
		this.now = now;
		this.values = values;
		this.dates = getDates(values);
	}
	
	/**
	 * One sample per second
	 */
	private Date[] getDates(String[] values) {
		
		Date[] result = new Date[values.length];
		
		for (int i = 0; i < values.length; i++) {
			long ms = now.getTime() - (values.length - i) * 1000;
			result[i] = new Date(ms);
		}
		return result;
	}
	
	public List<IMonitoringMetric> getMetrics(String metricKey) {
		ArrayList<IMonitoringMetric> result = new ArrayList<IMonitoringMetric>();
		
		for (int i = 0; i < values.length; i++) {
			result.add(new MonitoringMetric(metricKey, values[i], dates[i]));
		}
		return result;
	}

}