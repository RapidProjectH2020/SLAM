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
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ServiceReference")
public class ServiceReference {

	@XmlAttribute(name = "Name", required = true)
	private String name;

	@XmlAttribute(name = "ServiceName", required = true)
	private String serviceName;

	public ServiceReference() {
	}

	/**
	 * Gets the value of the name property.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the value of the name property.
	 */
	public void setName(String value) {
		this.name = value;
	}

	/**
	 * Gets the value of the serviceName property.
	 * 
	 */
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * Sets the value of the serviceName property.
	 */
	public void setServiceName(String value) {
		this.serviceName = value;
	}

}
