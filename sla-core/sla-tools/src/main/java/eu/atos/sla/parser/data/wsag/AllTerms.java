/*******************************************************************************
 * Copyright � 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED �AS IS�, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.parser.data.wsag;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "All")
public class AllTerms {

	@XmlElement(name = "ServiceDescriptionTerm")
	private ServiceDescriptionTerm serviceDescriptionTerm;
	@XmlElement(name = "ServiceProperties")
	private List<ServiceProperties> serviceProperties;
	@XmlElement(name = "GuaranteeTerm")
	private List<GuaranteeTerm> guaranteeTerms;

	public AllTerms() {
	}

	public ServiceDescriptionTerm getServiceDescriptionTerm() {
		return serviceDescriptionTerm;
	}

	public void setServiceDescriptionTerm(
			ServiceDescriptionTerm serviceDescriptionTerm) {
		this.serviceDescriptionTerm = serviceDescriptionTerm;
	}

	public List<ServiceProperties> getServiceProperties() {
		return serviceProperties;
	}

	public void setServiceProperties(List<ServiceProperties> serviceProperties) {
		this.serviceProperties = serviceProperties;
	}

	public List<GuaranteeTerm> getGuaranteeTerms() {
		return guaranteeTerms;
	}

	public void setGuaranteeTerms(List<GuaranteeTerm> guaranteeTerms) {
		this.guaranteeTerms = guaranteeTerms;
	}

	

}
