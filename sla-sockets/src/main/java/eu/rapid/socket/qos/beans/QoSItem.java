/*******************************************************************************
 * Copyright © 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.rapid.socket.qos.beans;

public class QoSItem {
	
	
	private String variable;
	private String condition;
	private String value;

	//After: {"QoS":[{"operator":"CPU_UTIL", "term":"LT", "threshold":60}	
	//New: {"QoS":[{"term":"CPU_UTIL", "operator":"LT", "threshold":60}	
	
	public String getTerm() {
		return variable;
	}
	public void setTerm(String variable) {
		this.variable = variable;
	}
	public String getOperator() {
		return condition;
	}
	public void setOperator(String condition) {
		this.condition = condition;
	}
	public String getThreshold() {
		return value;
	}
	public void setThreshold(String value) {
		this.value = value;
	}
}
