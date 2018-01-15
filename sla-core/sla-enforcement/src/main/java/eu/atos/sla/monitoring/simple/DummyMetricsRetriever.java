/*******************************************************************************
 * Copyright © 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.monitoring.simple;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import eu.atos.sla.monitoring.IMetricsRetriever;
import eu.atos.sla.monitoring.IMonitoringMetric;

/**
 * Dummy retriver that returns metric values in [0,1)
 * 
 * @author rsosa
 *
 */
public class DummyMetricsRetriever implements IMetricsRetriever {

	private final Random rnd = new Random();
	
	public DummyMetricsRetriever() {
	}
	
	@Override
	public List<IMonitoringMetric> getMetrics(String serviceId, String serviceScope,
			final String variable, Date begin, final Date end, int maxResults) {

		if (begin == null) {
			
			begin = new Date(end.getTime() - 1000);
		}
		List<IMonitoringMetric> result = new ArrayList<IMonitoringMetric>();
		for (int i = 0; i < 2; i++) {
			result.add(getRandomMetric(variable, i == 0? begin : end));
		}
		return result;
	}
	
	public IMonitoringMetric getRandomMetric(String metricName, Date date) {
		
		return new MonitoringMetric(metricName, rnd.nextDouble(), date);
	}

}
