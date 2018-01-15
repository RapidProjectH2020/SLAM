/*******************************************************************************
 * Copyright © 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.dao.jpa;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import eu.atos.sla.dao.IBreachDAO;
import eu.atos.sla.datamodel.IAgreement;
import eu.atos.sla.datamodel.IBreach;
import eu.atos.sla.datamodel.bean.Breach;

@Repository("BreachRepository")
public class BreachDAOJpa implements IBreachDAO {
	private static Logger logger = LoggerFactory.getLogger(BreachDAOJpa.class);
	private EntityManager entityManager;

	@PersistenceContext(unitName = "slarepositoryDB")
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;

	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public Breach getById(Long id) {
		return entityManager.find(Breach.class, id);
	}

	public Breach getBreachByUUID(UUID uuid) {
		try {
			Query query = entityManager
					.createNamedQuery(Breach.QUERY_FIND_BY_UUID);
			query.setParameter("uuid", uuid);
			Breach breach = null;

			breach = (Breach) query.getSingleResult();

			return breach;

		} catch (NoResultException e) {
			logger.debug("No Result found: " + e);
			return null;
		}
	}

	public List<IBreach> getAll() {
		TypedQuery<IBreach> query = entityManager.createNamedQuery(
				Breach.QUERY_FIND_ALL, IBreach.class);
		List<IBreach> breaches = null;
		breaches = query.getResultList();

		if (breaches != null) {
			logger.debug("Number of active breaches:" + breaches.size());
		} else {
			logger.debug("No Result found.");
		}

		return breaches;
	}

	public IBreach save(IBreach breach) {

		entityManager.persist(breach);
		entityManager.flush();

		return breach;

	}

	public boolean update(IBreach breach) {
		entityManager.merge(breach);
		entityManager.flush();
		return true;
	}

	public boolean delete(IBreach breach) {
		Long id = breach.getId();
		try {
			breach = entityManager.getReference(Breach.class, id);
			entityManager.remove(breach);
			entityManager.flush();
			return true;
		} catch (EntityNotFoundException e) {
			logger.debug("breach[{}] not found", id);
			return false;
		}
	}

	public List<IBreach> getByTimeRange(IAgreement contract, String variable,
			Date begin, Date end) {
		TypedQuery<IBreach> query = entityManager.createNamedQuery(
				Breach.QUERY_FIND_BY_TIME_RANGE, IBreach.class);
		query.setParameter("uuid", contract.getAgreementId());
		query.setParameter("variable", variable);
		query.setParameter("begin", begin);
		query.setParameter("end", end);
		
	
		List<IBreach> breaches = null;
		breaches = query.getResultList();

		if (breaches != null) {
			logger.debug("Number of breaches given a contract and range of dates:" + breaches.size());
		} else {
			logger.debug("No Result found.");
		}

		return breaches;

	}

}
