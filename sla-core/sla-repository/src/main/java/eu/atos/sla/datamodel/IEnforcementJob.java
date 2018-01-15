/*******************************************************************************
 * Copyright © 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.datamodel;

import java.util.Date;

public interface IEnforcementJob {

	/*
	 * Internally generated id
	 */
	Long getId();
	
	/**
	 * Date of last enforcement start
	 */
	Date getFirstExecuted();
	
	void setFirstExecuted(Date date);
	
	/**
	 * Last datetime where the job was executed
	 */
	Date getLastExecuted();
	
	void setLastExecuted(Date date);
	
	/**
	 * EnforcementJob enabled or not 
	 */
	boolean getEnabled();
	
	void setEnabled(boolean enabled);
	
	/**
	 * Agreement being enforced.
	 */
	IAgreement getAgreement();
	
	void setAgreement(IAgreement agreement);

}
