/*******************************************************************************
 * Copyright © 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.dao;

import java.util.List;

import eu.atos.sla.datamodel.IGuaranteeTerm;

/**
 * DAO interface to access to the GuaranteeTerm information
 * 
 * @author Pedro Rey - Atos
 * 
 */
public interface IGuaranteeTermDAO {

	/**
	 * Returns the GuaranteeTerm from the database by its Id
	 * 
	 * @param id
	 *            of the GuaranteeTerm
	 * @return the corresponding GuaranteeTerm from the database
	 */
	public IGuaranteeTerm getById(Long id);

	/**
	 * Returns all the GuaranteeTerm stored in the database
	 * 
	 * @return all the GuaranteeTerm stored in the database
	 */
	public List<IGuaranteeTerm> getAll();

	/**
	 * Stores a GuaranteeTerm into the database
	 * 
	 * @param GuaranteeTerm
	 *            GuaranteeTerm to be saved.
	 * @return <code>true</code> if the GuaranteeTermType was saved correctly
	 * @throws Exception
	 */
	public IGuaranteeTerm save(IGuaranteeTerm guaranteeTerm);

	/**
	 * Updates a GuaranteeTerm in the database
	 * 
	 * @param GuaranteeTerm
	 *            GuaranteeTerm to be updated
	 * @return <code>true</code> if the GuaranteeTerm was saved correctly
	 */
	public boolean update(IGuaranteeTerm guaranteeTerm);

	/**
	 * Deletes a GuaranteeTerm from the database
	 * 
	 * @param GuaranteeTerm
	 *            to be deleted
	 * @return <code>true</code> if the GuaranteeTerm was deleted correctly
	 */
	public boolean delete(IGuaranteeTerm guaranteeTerm);

}
