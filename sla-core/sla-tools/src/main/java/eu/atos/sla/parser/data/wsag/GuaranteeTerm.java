/*******************************************************************************
 * Copyright © 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.parser.data.wsag;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "GuaranteeTerm")
public class GuaranteeTerm {

	@XmlAttribute(name = "Name")
	private String name;
	@XmlElement(name = "ServiceScope")
	private ServiceScope serviceScope;
	@XmlElement(name = "ServiceLevelObjective")
	private ServiceLevelObjective serviceLevelObjective;
	@XmlElement(name = "QualifyingCondition")
	private String qualifyingCondition;
	@XmlElement(name="BusinessValueList")
	private BusinessValueList businessValueList;

	public GuaranteeTerm() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ServiceScope getServiceScope() {
		return serviceScope;
	}

	public void setServiceScope(ServiceScope serviceScope) {
		this.serviceScope = serviceScope;
	}

	public ServiceLevelObjective getServiceLevelObjetive() {
		return serviceLevelObjective;
	}

	public void setServiceLevelObjetive(
			ServiceLevelObjective serviceLevelObjetive) {
		this.serviceLevelObjective = serviceLevelObjetive;
	}

	public String getQualifyingCondition() {
		return qualifyingCondition;
	}

	public void setQualifyingCondition(String qualifyingCondition) {
		this.qualifyingCondition = qualifyingCondition;
	}

	public BusinessValueList getBusinessValueList() {
		return businessValueList;
	}
	
	public void setBusinessValueList(BusinessValueList businessValueList) {
		this.businessValueList = businessValueList;
	}
}
