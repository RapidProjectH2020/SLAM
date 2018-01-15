/*******************************************************************************
 * Copyright © 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.monitoring;

import java.util.Date;
import java.util.List;
import java.util.Map;

import eu.atos.sla.datamodel.IGuaranteeTerm;

/**
 * Get variables (metrics) from a metric repository between a time interval.
 * 
 * Every service provider that wants to use the SLA module has to provide a MetricsRetriever, 
 * in order to use the enforcement feature. 
 * 
 * The implementer needs to translate the terms from the SLA domain (agreementId, serviceScope) to
 * the Provider domain, connect to the repository (e.g. REST, db), and return the proper metrics.
 *  
 * Deprecates IMetricsRetriever: this interface is intended to minimize network usage, as queries all the
 * metrics in a single call.
 * 
 */
public interface IMetricsRetrieverV2 extends IMetricsRetriever {
	
	/**
	 * Englobes parameters for a single metrics retrieval.
	 * 
	 * The meaning of each property is the same as the equivalent parameter of 
	 * {@link IMetricsRetriever#getMetrics(String, String, String, Date, Date, int)}
	 *
	 * @see IMetricsRetriever#getMetrics(String, String, String, Date, Date, int)
	 */
	public interface RetrievalItem {
		IGuaranteeTerm getGuaranteeTerm();
		String getVariable();
		Date getBegin();
		Date getEnd();
	}

	/**
	 * Get variables (metrics) from a metric repository between a time interval.
	 * 
	 * @param agreementId
	 * @param retrievalItems list of {@link RetrievalItem} that specify the metrics to retrieve.
	 * @param maxResults are returned as much. If there are more than maxResults samples
	 *   in the interval, the results must be sorted using as distance function f(t) = end - t
	 *   and returning the first maxResults samples. 
	 * @return map of metrics per guarantee term.
	 */
	public Map<IGuaranteeTerm, List<IMonitoringMetric>> 
			getMetrics(String agreementId, List<RetrievalItem> retrievalItems, int maxResults);
}