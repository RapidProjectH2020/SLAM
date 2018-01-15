/*******************************************************************************
 * Copyright © 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.datamodel;

import java.util.Date;

import eu.atos.sla.datamodel.ICompensationDefinition.IPenaltyDefinition;

public interface ICompensation {

	public interface IPenalty extends ICompensation {
		
		IPenaltyDefinition getDefinition();

		/**
		 * Last violation that generated this penalty.
		 */
		IViolation getViolation();
	}
	
	public interface IReward extends ICompensation {
		
	}

	/*
	 * Internal generated ID
	 */
	Long getId();

	/**
	 * Internal generated UUID. The interested external parties are going to
	 * identify this violation by the UUID.
	 */
	String getUuid();

	/**
	 * AgreementId where this compensation has been enforced.
	 */
	String getAgreementId();

	/**
	 * Date and time when the compensation was raised.
	 */
	Date getDatetime();

	/**
	 * Name of the kpi that has generated this compensation.
	 */
	String getKpiName();
}
