/*******************************************************************************
 * Copyright © 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.datamodel.bean;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Table;

import eu.atos.sla.datamodel.ICompensationDefinition.IPenaltyDefinition;

@Entity
@Table(name="penaltydefinition")
@Access(AccessType.FIELD)
public class PenaltyDefinition extends CompensationDefinition implements IPenaltyDefinition {
	private static final long serialVersionUID = 1L;

	public PenaltyDefinition() {
		super();
	}
	
	public PenaltyDefinition(Date timeInterval,
			String valueUnit, String valueExpression) {

		super(CompensationKind.PENALTY, timeInterval, valueUnit, valueExpression);
	}

	public PenaltyDefinition(int count, String valueUnit, String valueExpression) {	

		super(CompensationKind.PENALTY, count, valueUnit, valueExpression);
	}

	public PenaltyDefinition(int count, Date timeInterval, String action, String valueUnit, String valueExpression, 
			String validity) {
		
		super(CompensationKind.CUSTOM_PENALTY, count, timeInterval, action, valueUnit, valueExpression, validity);
	}
}
