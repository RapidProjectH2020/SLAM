/*******************************************************************************
 * Copyright © 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.parser.data.wsag;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import eu.atos.sla.parser.data.wsag.custom.CustomBusinessValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "BusinessValueList")
public class BusinessValueList {
	@XmlElement(name = "CustomBusinessValue")
	protected List<CustomBusinessValue> customBusinessValue;

	@XmlElement(name = "Importance")
	protected Integer importance;

	/**
	 * Gets the value of the importance property.
	 */
	public Integer getImportance() {
		return importance;
	}

	/**
	 * Sets the value of the importance property.
	 */
	public void setImportance(Integer value) {
		this.importance = value;
	}
	
	/**
	 * Gets the value of the customBusinessValue property.
	 */
	public List<CustomBusinessValue> getCustomBusinessValue() {
		if (customBusinessValue == null) {
			customBusinessValue = new ArrayList<CustomBusinessValue>();
		}
		return this.customBusinessValue;
	}

}
