/*******************************************************************************
 * Copyright � 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED �AS IS�, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.dao.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import eu.atos.sla.dao.IEnforcementJobDAO;
import eu.atos.sla.datamodel.IEnforcementJob;
import eu.atos.sla.datamodel.bean.EnforcementJob;


@Repository("EnforcementJobService")
public class EnforcementJobDAOJpa implements IEnforcementJobDAO {
	private static Logger logger = LoggerFactory.getLogger(EnforcementJobDAOJpa.class);
	private EntityManager entityManager;

	public EnforcementJob getById(Long id) {
		return entityManager.find(EnforcementJob.class, id);
	}

	@PersistenceContext(unitName = "slarepositoryDB")
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	public List<IEnforcementJob> getNotExecuted(Date since) {

		TypedQuery<IEnforcementJob> query = entityManager.createNamedQuery(EnforcementJob.QUERY_FIND_NOT_EXECUTED, IEnforcementJob.class);
		query.setParameter("since", since);
		List<IEnforcementJob> list = query.getResultList();

		if (list != null) {
			logger.debug("Number of enforcements:" + list.size());
		} else {
			logger.debug("No Result found.");
		}

		return list;

	}

	@Override
	public EnforcementJob getByAgreementId(String agreementId) {

		TypedQuery<EnforcementJob> query = entityManager.createNamedQuery(EnforcementJob.QUERY_FIND_BY_AGREEMENT_ID,EnforcementJob.class);
		query.setParameter("agreementId", agreementId);

		EnforcementJob result;
		try {
			result = query.getSingleResult();

		} catch (NoResultException e) {
			logger.debug("Null will returned due to no Result found: " + e);
			return null;
		}

		return result;
	}

	@Override
	public IEnforcementJob save(IEnforcementJob enforcementJob) {

		entityManager.persist(enforcementJob);
		entityManager.flush();

		return enforcementJob;
	}

	@Override
	public List<IEnforcementJob> getAll() {
		TypedQuery<IEnforcementJob> query = entityManager.createNamedQuery(	"EnforcementJob.findAll", IEnforcementJob.class);

		List<IEnforcementJob> enforcementJob = new ArrayList<IEnforcementJob>();
		enforcementJob = query.getResultList();

		if (enforcementJob != null) {
			logger.debug("Number of enforcementJob:" + enforcementJob.size());
		} else {
			logger.debug("No Result found.");
		}

		return enforcementJob;
	}

	@Override
	public boolean delete(IEnforcementJob enforcementJob) {
		try {
			enforcementJob = entityManager.getReference(EnforcementJob.class,
					enforcementJob.getId());
			entityManager.remove(enforcementJob);
			entityManager.flush();
			return true;
		} catch (EntityNotFoundException e) {
			logger.debug("enforcement[{}] not found", enforcementJob.getId());
			return false;
		}
	}

}
