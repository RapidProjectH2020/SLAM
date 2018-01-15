/*******************************************************************************
 * Copyright © 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.dao;

import java.util.List;

import eu.atos.sla.datamodel.IService;

/**
 * DAO interface to access to the Service information
 * 
 * @author Pedro Rey - Atos
 * 
 */
public interface IServiceDAO  {

	/**
	 * Returns the Service from the database by its Id
	 * 
	 * @param id
	 *            of the Service
	 * @return the corresponding Service from the database
	 */
	public IService getById(Long id);

	/**
	 * Returns the Service from the database by its UUID
	 * 
	 * @param id
	 *            of the Service
	 * @return the corresponding Service from the database
	 */
	public IService getByUUID(String uuid);

	/**
	 * Returns the Service from the database by its name
	 * 
	 * @param id
	 *            of the Service
	 * @return the corresponding Service from the database
	 */
	public IService getByName(String serviceName);

	/**
	 * Returns all the Service stored in the database
	 * 
	 * @return all the Service stored in the database
	 */
	public List<IService> getAll();

	/**
	 * Stores a Service into the database
	 * 
	 * @param Service
	 *            - Service to be saved.
	 * @return <code>true</code> if the ServiceType was saved correctly
	 * @throws Exception 
	 */
	public IService save(IService service);

	/**
	 * Updates a Service in the database
	 * 
	 * @param Service
	 *            - Service to be updated
	 * @return <code>true</code> if the Service was saved correctly
	 */
	public boolean update(IService service);

	/**
	 * Deletes a Service from the database
	 * 
	 * @param Service
	 *            to be deleted
	 * @return <code>true</code> if the Service was deleted correctly
	 */
	public boolean delete(IService service);

}
