/*******************************************************************************
 * Copyright © 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.evaluation.guarantee;

import java.util.Date;
import java.util.List;

import eu.atos.sla.datamodel.IAgreement;
import eu.atos.sla.datamodel.IBreach;

/**
 * Defines the access to a repository of breaches. This repository is needed for the PoliciedServiceLevelEvaluator.
 * 
 * @see PoliciedServiceLevelEvaluator
 * 
 * @author rsosa
 *
 */
public interface IBreachRepository {
	
	/**
	 * Get the agreement breaches in a specified interval (inclusive ends)
	 * 
	 * @param agreement to check.
	 * @param kpiName that generated the breach
	 * @param begin inclusive begin date.
	 * @param end  inclusive end date.
	 */
	List<IBreach> getBreachesByTimeRange(IAgreement agreement, String kpiName, Date begin, Date end);
	
	/**
	 * Persist in the repository a list of breaches.
	 */
	void saveBreaches(List<IBreach> breaches);
}