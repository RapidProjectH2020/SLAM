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

import eu.atos.sla.dao.IGuaranteeTermDAO;
import eu.atos.sla.datamodel.IGuaranteeTerm;
import eu.atos.sla.datamodel.bean.GuaranteeTerm;

@Repository("GuaranteeTermRepository")
public class GuaranteeTermDAOJpa implements IGuaranteeTermDAO {
	private static Logger logger = LoggerFactory.getLogger(GuaranteeTermDAOJpa.class);
	private EntityManager entityManager;

	@PersistenceContext(unitName = "slarepositoryDB")
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public GuaranteeTerm getById(Long id) {
		return entityManager.find(GuaranteeTerm.class, id);
	}

	public List<IGuaranteeTerm> getAll() {

		TypedQuery<IGuaranteeTerm> query = entityManager.createNamedQuery(
				"GuaranteeTerm.findAll", IGuaranteeTerm.class);
		List<IGuaranteeTerm> guaranteeTerms = null;
		guaranteeTerms = query.getResultList();

		if (guaranteeTerms != null) {
			logger.debug("Number of guaranteeTerms:" + guaranteeTerms.size());
		} else {
			logger.debug("No Result found.");
		}

		return guaranteeTerms;
	}

	@Override
	public IGuaranteeTerm save(IGuaranteeTerm guaranteeTerm) {

		entityManager.persist(guaranteeTerm);
		entityManager.flush();

		return guaranteeTerm;
	}

	public boolean update(IGuaranteeTerm guaranteeTerm) {
		entityManager.merge(guaranteeTerm);
		entityManager.flush();
		return true;
	}

	public boolean delete(IGuaranteeTerm guaranteeTerm) {
		Long id = guaranteeTerm.getId();
		try {
			guaranteeTerm = entityManager.getReference(GuaranteeTerm.class, id);
			entityManager.remove(guaranteeTerm);
			entityManager.flush();
			return true;
		} catch (EntityNotFoundException e) {
			logger.debug("GuaranteeTerm[{}] not found", id);
			return false;
		}
	}

}
