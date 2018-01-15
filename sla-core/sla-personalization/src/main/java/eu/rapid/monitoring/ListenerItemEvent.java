/*******************************************************************************
 * Copyright © 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
 
package eu.rapid.monitoring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.atos.sla.datamodel.IAgreement;
import eu.atos.sla.datamodel.IProvider;
import eu.atos.sla.datamodel.ITemplate;

public class ListenerItemEvent implements IItemEvent {
	private static Logger logger = LoggerFactory.getLogger(ListenerItemEvent.class);

	@Override
	public void onEndOfTemplateCreation(ITemplate template)
			throws ItemEventException {
		logger.debug("onEndOfTemplateCreation: dummy version, nothing is done");
	}

	@Override
	public void onEndOfEnforcementJobCreation(String agreementUUID)
			throws ItemEventException {
		logger.debug("onEndOfEnforcementJobCreation: dummy version, nothing is done");
	}

	@Override
	public void onEndOfProviderCreation(IProvider provider)
			throws ItemEventException {
		logger.debug("onEndOfProviderCreation: dummy version, nothing is done");
	}

	@Override
	public void onEndOfAgreementCreation(IAgreement agreement)
			throws ItemEventException {
		logger.debug("onEndOfAgreementCreation: dummy version, nothing is done");
	}

	@Override
	public void onStartOfEnforcementJobDone(String agreementUUID)
			throws ItemEventException {
		logger.debug("onStartOfEnforcementJobDone: dummy version, nothing is done");
	}

	@Override
	public void onStopOfEnforcementJobDone(String agreementUUID)
			throws ItemEventException {
		logger.debug("onStopOfEnforcementJobDone: dummy version, nothing is done");
	}

}
