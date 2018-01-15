/*******************************************************************************
 * Copyright © 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.service.jpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Date;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.atos.sla.dao.IAgreementDAO;
import eu.atos.sla.dao.IViolationDAO;
import eu.atos.sla.dao.IViolationDAO.SearchParameters;
import eu.atos.sla.datamodel.IViolation;
import eu.atos.sla.datamodel.bean.Violation;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/sla-repository-db-JPA-test-context.xml")
public class ViolationDAOJpaTest extends
		AbstractTransactionalJUnit4SpringContextTests {
	@Autowired
	IViolationDAO violationDAO;

	@Autowired
	IAgreementDAO agreementDAO;

	@Test
	public void notNull() {
		if (violationDAO == null)
			fail();
	}

	@Test
	public void getById() {

		String violationUuid = UUID.randomUUID().toString();
		Date dateTime = new Date(2323L);
		IViolation violation = new Violation();
		IViolation violationSaved = new Violation();

		violation.setUuid(violationUuid);
		violation.setContractUuid(UUID.randomUUID().toString());
		violation.setServiceName("Service name");
		violation.setServiceScope("Service scope");
		violation.setKpiName("response time");
		violation.setDatetime(dateTime);
		violation.setExpectedValue("3.0");
		violation.setActualValue("5.0");

		try {
			violationSaved = violationDAO.save(violation);
		} catch (Exception e) {
			fail();
		}

		Long id = violationSaved.getId();
		violationSaved = violationDAO.getById(id);

		assertEquals("response time", violationSaved.getKpiName());
		assertEquals("3.0", violationSaved.getExpectedValue());

		IViolation nullBreach = violationDAO.getById(new Long(30000));
		assertEquals(null, nullBreach);

	}
	
	@Test
	public void testSearch() {
		
		SearchParameters params = new IViolationDAO.SearchParameters();
		params.setAgreementId(null);
		params.setGuaranteeTermName(null);
		params.setProviderUuid(null);
		params.setBegin(null);
		params.setEnd(null);
		
		violationDAO.search(params);
	}
}
