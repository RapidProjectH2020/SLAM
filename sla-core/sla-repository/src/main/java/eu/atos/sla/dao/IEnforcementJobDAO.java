/*******************************************************************************
 * Copyright © 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.dao;

import java.util.Date;
import java.util.List;

import eu.atos.sla.datamodel.IEnforcementJob;

public interface IEnforcementJobDAO {
	/**
	 * Retrieves enabled jobs not executed since <code>since</code>
	 */
	List<IEnforcementJob> getNotExecuted(Date since);

	/**
	 * Returns the GuaranteeTerm from the database by its Id
	 * 
	 * @param id
	 *            of the GuaranteeTerm
	 * @return the corresponding GuaranteeTerm from the database
	 */
	IEnforcementJob getById(Long id);

	/**
	 * Retrieves the job associated with <code>agreementId</code>.
	 * 
	 * @return EnforcementJob if exists; else <code>null</code>
	 */
	IEnforcementJob getByAgreementId(String agreementId);

	/**
	 * Returns all the EnforcementJob stored in the database
	 * 
	 * @return all the EnforcementJob stored in the database
	 */
	public List<IEnforcementJob> getAll();

	/**
	 * Stores an EnforcementJob into the database
	 * 
	 * @param EnforcementJob
	 *            EnforcementJob to be saved.
	 * @return <code>true</code> if the EnforcementJob was saved correctly
	 * @throws Exception 
	 */
	public IEnforcementJob save(IEnforcementJob expected);

	/**
	 * Deletes an EnforcementJob from the database
	 * 
	 * @param EnforcementJob
	 *            to be deleted
	 * @return <code>true</code> if the EnforcementJob was deleted correctly
	 */
	public boolean delete(IEnforcementJob enforcementJob);



}
