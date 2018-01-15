/*******************************************************************************
 * Copyright © 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.datamodel;

import java.util.List;

import eu.atos.sla.datamodel.ICompensationDefinition.IPenaltyDefinition;

public interface IBusinessValueList {

	/*
	 * Internally generated id
	 */
	Long getId();

	/**
	 * Relative importance of meeting an objective.
	 * 
	 * This core assumes the higher, the more important, with 0 as minimum value.
	 * @return
	 */
	public int getImportance();
	
	public List<IPenaltyDefinition> getPenalties();
	
	public void addPenalty(IPenaltyDefinition penalty);
}
