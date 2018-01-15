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
import eu.atos.sla.datamodel.ICompensation;
import eu.atos.sla.datamodel.IGuaranteeTerm;
import eu.atos.sla.datamodel.IViolation;

/**
 * Assesses the compensations that are derived from a list of violations.
 * 
 * @author rsosa
 *
 */
public interface IBusinessValuesEvaluator {
	
	/**
	 * Assesses the compensations that are derived from a list of violations.
	 * 
	 * @param agreement agreement being evaluated.
	 * @param term of the agreement being evaluated.
	 * @param violations detected in the service level evaluation.
	 * @param now the evaluation period ends at <code>now</code>.
	 * @return list of compensations.
	 */
	List<? extends ICompensation> evaluate(
			IAgreement agreement, IGuaranteeTerm term, List<IViolation> violations, Date now);
}