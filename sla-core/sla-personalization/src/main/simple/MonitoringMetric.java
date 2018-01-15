/*******************************************************************************
 * Copyright © 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
 *******************************************************************************/
package eu.atos.sla.monitoring.simple;

import java.util.Date;

import eu.atos.sla.monitoring.IMonitoringMetric;

/**
 * Simple implementation of IMonitoringMetric that assumes the value is a double.
 */
public class MonitoringMetric implements IMonitoringMetric {

	private String metricKey;
	private String metricValue;
	private Date date;


	public MonitoringMetric(String metricKey, double metricValue, Date date) {

		this.metricKey = metricKey;
		this.metricValue = String.valueOf(metricValue);
		this.date = date;
	}

	@Override
	public String getMetricKey() {

		return metricKey;
	}

	@Override
	public String getMetricValue() {

		return metricValue;
	}

	@Override
	public Date getDate() {

		return date;
	}

	public void setMetricKey(String metricKey) {
		this.metricKey = metricKey;
	}

	public void setMetricValue(double metricValue) {
		this.metricValue = String.valueOf(metricValue);
	}

	public void setDate(Date date) {
		this.date = date;
	}

	
}
