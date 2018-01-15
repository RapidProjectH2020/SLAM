/*******************************************************************************
 * Copyright © 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.datamodel;

import java.util.Date;

public interface IBreach {

	/*
	 * Internal generated ID
	 */
	Long getId();

	/**
	 * Date and time of the metric that has generated this breach.
	 * 
	 * @return
	 */
	Date getDatetime();

	/**
	 * Name of the kpiName that has generated this breach.
	 */
	String getKpiName();

	String getValue();

	/**
	 * Value of the metric that has generated this breach.
	 * 
	 * @return
	 */

	void setId(Long id);

	void setValue(String value);

	/**
	 * Date and time of the metric that has generated this breach.
	 * 
	 * @return
	 */
	void setDatetime(Date date);

	/**
	 * Name of the metric that has generated this breach.
	 */
	void setKpiName(String metric);

	String getAgreementUuid();

	void setAgreementUuid(String agreementUuid);

}