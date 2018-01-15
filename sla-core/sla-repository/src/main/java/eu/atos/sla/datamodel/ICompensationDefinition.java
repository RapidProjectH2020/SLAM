/*******************************************************************************
 * Copyright © 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.datamodel;

import java.util.Date;

/**
 * This element expresses the reward or penalty to be assessed for meeting (or not) an objetive.
 *
 */
public interface ICompensationDefinition {

	public static enum CompensationKind {
		REWARD,
		CUSTOM_REWARD,
		PENALTY,
		CUSTOM_PENALTY,
		UNKNOWN
	}
	
	public interface IPenaltyDefinition extends ICompensationDefinition {
		
	}
	
	public interface IRewardDefinition extends ICompensationDefinition {
		
	}
	

	/*
	 * Internally generated id
	 */
	Long getId();

	/**
	 * Type of compensation: reward or penalty.
	 */
	CompensationKind getKind();
	
	/**
	 * When present, defines the assessment interval as a duration.
	 * 
	 * One of timeInterval or count MUST be specified. 
	 */
	Date getTimeInterval();
	
	/**
	 * When present, defines the assessment interval as a service specific count, such as
	 * number of invocations. 
	 * 
	 * One of timeInterval or count MUST be specified. 
	 */
	Integer getCount();
	
	/**
	 * Optional element that defines the unit for assessing penalty, such as USD.
	 */
	String getValueUnit();

	/**
	 * This element defines the assessment amount, which can be an integer, a float or an 
	 * arbitrary domain-specific expression.
	 */
	String getValueExpression();

	/**
	 * In extended compensations, defines the domain-specific type of compensation, such as 
	 * "discount", "terminate", "service"...
	 */
	String getAction();

	/**
	 * In extended compensations, defines the time interval where the action should take place. E.g., 
	 * a discount of 10% with a validity of one day (i.e., the prize of that day will have a discount of a 10%).
	 * 
	 * The validity must be expressed in xs:duration format.
	 * 
	 * @see http://www.w3.org/TR/xmlschema-2/#duration
	 */
	String getValidity();
}
