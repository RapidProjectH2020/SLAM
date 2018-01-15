/*******************************************************************************
 * Copyright © 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.service.jpa;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import eu.atos.sla.dao.IAgreementDAO;
import eu.atos.sla.dao.IBreachDAO;
import eu.atos.sla.dao.IGuaranteeTermDAO;
import eu.atos.sla.dao.IPolicyDAO;
import eu.atos.sla.dao.ITemplateDAO;
import eu.atos.sla.dao.IViolationDAO;
import eu.atos.sla.datamodel.bean.Agreement;

public class MysqlJpaIT {

	@SuppressWarnings({ "resource", "unused" })
	public static void main(String args[]) throws InterruptedException {
		// Load Spring configuration
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"/sla-repository-db-JPA-test-context.xml");
		IAgreementDAO agreementDAO = (IAgreementDAO) context
				.getBean("AgreementService");
		IBreachDAO breachDAO = (IBreachDAO) context.getBean("BreachService");
		IGuaranteeTermDAO guaranteeTerm = (IGuaranteeTermDAO) context
				.getBean("GuaranteeTermService");

		IPolicyDAO slaPolicyDAO = (IPolicyDAO) context
				.getBean("SLAPolicyService");
		ITemplateDAO templateDAO = (ITemplateDAO) context
				.getBean("TemplateService");
		IViolationDAO violationDAO = (IViolationDAO) context
				.getBean("ViolationService");

		Agreement agreement = new Agreement();
		agreement.setId(1L);

		Agreement agreementSaved = null;

		try {
			agreementDAO.save(agreement);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		if (agreementSaved != null) {
			System.out.println("IMPOSIBLE TO SAVE AGREEMENT!!!!");
			Thread.sleep(600000l);
		}
	}
}
