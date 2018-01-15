/*******************************************************************************
 * Copyright © 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import eu.atos.sla.dao.IPolicyDAO;
import eu.atos.sla.datamodel.IPolicy;
import eu.atos.sla.datamodel.bean.Policy;

@Repository("PolicyRepository")
public class PolicyDAOJpa implements IPolicyDAO {
	private static Logger logger = LoggerFactory.getLogger(PolicyDAOJpa.class);
	private EntityManager entityManager;

	@PersistenceContext(unitName = "slarepositoryDB")
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	@Override
	public Policy getById(Long id) {
		return entityManager.find(Policy.class, id);
	}

	@Override
	public List<IPolicy> getAll() {
		TypedQuery<IPolicy> query = entityManager.createNamedQuery(
				"Policy.findAll", IPolicy.class);
		List<IPolicy> policys = null;
		policys = query.getResultList();

		if (policys != null) {
			logger.debug("Number of policys:" + policys.size());
		} else {
			logger.debug("No Result found.");
		}

		return policys;
	}

	@Override
	public IPolicy save(IPolicy policy) {

		entityManager.persist(policy);
		entityManager.flush();

		return policy;
	}

	@Override
	public boolean update(IPolicy policy) {
		entityManager.merge(policy);
		entityManager.flush();
		return true;
	}

	@Override
	public boolean delete(IPolicy policy) {
		Long id = policy.getId();
		try {
			policy = entityManager.getReference(Policy.class, id);
			entityManager.remove(policy);
			entityManager.flush();
			return true;
		} catch (EntityNotFoundException e) {
			logger.debug("Policy[{}] not found", id);
			return false;
		}
	}

}
