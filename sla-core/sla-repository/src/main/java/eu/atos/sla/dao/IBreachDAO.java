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
import java.util.UUID;

import eu.atos.sla.datamodel.IAgreement;
import eu.atos.sla.datamodel.IBreach;
import eu.atos.sla.datamodel.bean.Breach;

/**
 * DAO interface to access to the Breach information
 * 
 * @author Pedro Rey - Atos
 * 
 */
public interface IBreachDAO  {

	/**
	 * Returns the Breach from the database by its Id
	 * 
	 * @param id
	 *            of the Breach
	 * @return the corresponding Breach from the database
	 */
	public Breach getById(Long id);

	/**
	 * Returns the Breach from the database by its Id
	 * 
	 * @param id
	 *            of the Breach
	 * @return the corresponding Breach from the database
	 */
	public IBreach getBreachByUUID(UUID uuid);

	/**
	 * Returns all the Breach stored in the database
	 * 
	 * @return all the Breach stored in the database
	 */
	public List<IBreach> getAll();

	/**
	 * Stores a Breach into the database
	 * 
	 * @param Breach
	 *            Breach to be saved.
	 * @return <code>true</code> if the BreachType was saved correctly
	 * @throws Exception 
	 */
	public IBreach save(IBreach breach);

	/**
	 * Updates a Breach in the database
	 * 
	 * @param Breach
	 *            Breach to be updated
	 * @return <code>true</code> if the Breach was saved correctly
	 */
	public boolean update(IBreach breach);

	/**
	 * Deletes a Breach from the database
	 * 
	 * @param Breach
	 *            to be deleted
	 * @return <code>true</code> if the Breach was deleted correctly
	 */
	public boolean delete(IBreach breach);

	/**
	 * Retrieves all the breaches associated with the given contract and
	 * variable, within a time interval (inclusive ends)
	 */
	public List<IBreach> getByTimeRange(IAgreement contract,
			String variable, Date begin, Date end);
}
