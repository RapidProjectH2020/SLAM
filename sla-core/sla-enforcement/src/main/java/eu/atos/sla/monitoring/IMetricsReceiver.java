/*******************************************************************************
 * Copyright © 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.monitoring;

import eu.atos.sla.datamodel.IAgreement;

/**
 * An IMetricsReceiver<T> must enforce metrics in type T for an agreement.
 */
public interface IMetricsReceiver<T> {

	/**
	 * Enforces an agreement with some data.
	 */
	void run(IAgreement agreement, T data);
	
	/**
	 * Implements a IMetricsReceiver that receives data in type T, translates to 
	 * type expected by enforcement, and enforces the agreement.
	 */
//	public class MetricsReceiver<T> implements IMetricsReceiver<T> {
//
//		IAgreementEnforcement enforcement;
//		IMetricTranslator<T> translator;
//		
//		/**
//		 * Constructs the MetricsReceiver.
//		 * @param translator translates type T to Map<IGuaranteeTerm, List<IMonitoringMetric>>
//		 * @param enforcement IAgreementEnforcement properly initialized.
//		 */
//		public MetricsReceiver(IMetricTranslator<T> translator, IAgreementEnforcement enforcement) {
//			
//			this.translator = translator;
//			this.enforcement = enforcement;
//		}
//		
//		@Override
//		public void run(IAgreement agreement, T data) {
//
//			Map<IGuaranteeTerm, List<IMonitoringMetric>> metricsMap = translator.translate(agreement, data);
//			enforcement.enforce(agreement, metricsMap);
//		}
//	}
}
