/*******************************************************************************
 * Copyright © 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.datamodel;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Ignore;
import org.junit.Test;

import eu.atos.sla.datamodel.IAgreement.AgreementStatus;
import eu.atos.sla.datamodel.bean.Agreement;
import eu.atos.sla.datamodel.bean.Provider;
import eu.atos.sla.datamodel.bean.Template;

public class AgreementTest {

	@Ignore
	@Test()
	public void testPojo() {

		StringBuilder agreementText = new StringBuilder();

		agreementText.append("text....");

		UUID uuid = UUID.randomUUID();
		AgreementStatus status = AgreementStatus.PENDING;
		String templateUuid = UUID.randomUUID().toString();

		Template template = new Template();
		template.setText("Template name 1");
		template.setUuid(templateUuid);

		Provider provider = new Provider(null, UUID.randomUUID().toString(),
				"Provider 2");

		Agreement agreement = new Agreement();
		agreement.setAgreementId(uuid.toString());
		agreement.setConsumer("Consumer2");
		agreement.setProvider(provider);
		agreement.setStatus(status);
		agreement.setTemplate(template);
		agreement.setText(agreementText.toString());

		assertEquals(uuid.toString(), agreement.getAgreementId());

	}
}
