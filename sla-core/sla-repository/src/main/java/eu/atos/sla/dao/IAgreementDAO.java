/*******************************************************************************
 * Copyright � 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED �AS IS�, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.dao;

import java.util.List;

import eu.atos.sla.datamodel.IAgreement;

/**
 * DAO interface to access to the Agreement information
 * 
 * @author Pedro Rey - Atos
 * 
 */
public interface IAgreementDAO {

	/**
	 * Returns the Agreement from the database by its id
	 * 
	 * @param id
	 *            of the Agreement
	 * @return the corresponding Agreement from the database
	 */
	public IAgreement getByAgreementId(String agreementId);
	
	/**
	 * Returns the Agreement from the database by consumerId
	 * 
	 * @param consumerId
	 * @return the corresponding Agreement from the database
	 */
	public List<IAgreement> getByConsumer(String consumerId);

	/**
	 * Returns the agreements that match the parameters
	 * @param consumerId match consumerId if not null
	 * @param providerId match providerId if not null
	 * @param templateId match templateId if not null
	 * @param active match non-expired if not null
	 */
	public List<IAgreement> search(String consumerId, String providerId, String templateId, Boolean active);

	/**
	 * Returns Active Agreement from the database
	 * 
	 * @param actualDat
	 * @return list of active Agreement from the database
	 */
	public List<IAgreement> getByActiveAgreements(long actualDate);
	
	
	/**
	 * Returns all the Agreement stored in the database
	 * 
	 * @return all the Agreement stored in the database
	 */
	public List<IAgreement> getAll();
	/**
	 * Stores a Agreement into the database
	 * 
	 * @param Agreement Agreement to be saved.
	 * @return agreement if the AgreementType was saved correctly
	 * @throws Exception 
	 */
	public IAgreement save(IAgreement agreement);


	/**
	 * Deletes a Agreement from the database
	 * 
	 * @param Agreement
	 *            to be deleted
	 * @return <code>true</code> if the Agreement was deleted correctly
	 */
	public boolean delete(IAgreement agreement);

	
	/**
	 * Returns list of Agreement from the database per template
	 * 
	 * @param templateUUID
	 * @return list of Agreement from the database filtered per templateUUID
	 */
	public List<IAgreement> getByTemplate(String templateUUID);

	public IAgreement getById(Long id);
	
	/**
	 * Returns list of Agreement from the database per provider
	 * 
	 * @param providerUuid
	 * @return list of Agreement from the database filtered per providerUuid
	 */
	public List<IAgreement> getByProvider(String providerUuid);

	/**
	 * Returns list of Agreement from the database per consumerId and templateUUID
	 * 
	 * @param consumerId
	 * @param templateUUID
	 * @return list of Agreement from the database filtered per consumerId  and templateUUID
	 */
	public List<IAgreement> searchPerTemplateAndConsumer(String consumerId,	String templateUUID);

}
