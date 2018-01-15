/*******************************************************************************
 * Copyright © 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.parser.data.wsag.custom;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * Generic representation of a penalty to be applied in case of a violation.
 * 
 * The format is:
 * <pre><code>
 * &lt;sla:Penalty
 *   type="xs:string"
 *   expression="xs:string"
 *   unit="xs:string"
 *   validity="xs:string"
 * >
 * &lt;/sla:Penalty>
 * </code></pre>
 * 
 * Only the type attribute is mandatory. The rest of attributes have a default value of "".
 * 
 * The interpretation of every attribute is up to an external accounting module, but the intended meaning is:
 * <li>type: kind of penalty (f.e: discount, service, terminate)
 * <li>expression, unit: value of the penalty (f.e. discount of (50, euro), discount(100, %), service(sms))
 * <li>validity: interval of time where the penalty is applied
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Penalty {
	@XmlAttribute(name="type", required=true)
	private String type = "";
	
	@XmlAttribute(name="expression")
	private String expression = "";
	
	@XmlAttribute(name="unit")
	private String unit  = "";
	
	@XmlAttribute(name="validity")
	private String validity = "";

	/**
	 * Constructs a Penalty with default values.
	 */
	public Penalty() {
	}

	public Penalty(String type, String expression, String unit, String validity) {
		this.type = type;
		this.expression = expression;
		this.unit = unit;
		this.validity = validity;
	}

	public String getType() {
		return type;
	}
	
	public String getExpression() {
		return expression;
	}
	
	public String getUnit() {
		return unit;
	}
	
	public String getValidity() {
		return validity;
	}

	@Override
	public String toString() {
		return String.format(
				"Action [type=%s, expression=%s, unit=%s, validity=%s]",
				type, expression, unit, validity);
	}
}