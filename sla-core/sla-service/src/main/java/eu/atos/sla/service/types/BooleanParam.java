/*******************************************************************************
 * Copyright © 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.service.types;

import javax.ws.rs.WebApplicationException;

public class BooleanParam {

	private final Boolean value;
	
	public static BooleanParam valueOf(String dateStr) {
		
		return new BooleanParam(dateStr);
	}

	public static Boolean getValue(BooleanParam instance) {

		return instance == null? null : instance.getValue();
	}
	
	public BooleanParam(String str) throws WebApplicationException {
		if ("".equals(str)) {
			this.value = null;
			return;
		}

		if ("0".equals(str) || "false".equalsIgnoreCase(str)) {
			
			this.value = Boolean.FALSE;
		}
		else {
			this.value = Boolean.TRUE;
		}
	}

	public Boolean getValue() {
		return value;
	}
}
