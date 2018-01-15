/*******************************************************************************
 * Copyright © 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.parser.data;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import eu.atos.sla.datamodel.ICompensation.IPenalty;
import eu.atos.sla.datamodel.ICompensationDefinition.IPenaltyDefinition;
import eu.atos.sla.parser.DateTimeDeserializerJSON;
import eu.atos.sla.parser.DateTimeSerializerJSON;

/**
 * A POJO Object that stores all the information from a Penalty
 * 
 * @author rsosa
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "penalty")
public class Penalty  {

	@XmlElement(name = "uuid")
	private String uuid;
	@XmlElement(name = "agreement_id")
	private String agreementId;
	@JsonSerialize(using=DateTimeSerializerJSON.class)
	@JsonDeserialize(using=DateTimeDeserializerJSON.class)
	@XmlElement(name = "datetime")
	private Date datetime;
	@XmlElement(name = "definition")
	private eu.atos.sla.parser.data.wsag.custom.Penalty definition;

	public Penalty() {
	}
	
	public Penalty(IPenalty penalty) {
		this.uuid = penalty.getUuid();
		this.agreementId = penalty.getAgreementId();
		this.datetime = penalty.getDatetime();
		
		IPenaltyDefinition def = penalty.getDefinition();
		this.definition = new eu.atos.sla.parser.data.wsag.custom.Penalty(
				def.getAction(), def.getValueExpression(), def.getValueUnit(), def.getValidity());
	}

	public String getUuid() {
		return uuid;
	}

	public String getAgreementId() {
		return agreementId;
	}

	public Date getDatetime() {
		return datetime;
	}

	public eu.atos.sla.parser.data.wsag.custom.Penalty getDefinition() {
		return definition;
	}
}
