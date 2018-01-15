/*******************************************************************************
 * Copyright © 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.util;

import eu.atos.sla.datamodel.IAgreement;
import eu.atos.sla.datamodel.ICompensation.IPenalty;
import eu.atos.sla.datamodel.IEnforcementJob;
import eu.atos.sla.datamodel.IProvider;
import eu.atos.sla.datamodel.ITemplate;
import eu.atos.sla.datamodel.IViolation;
import eu.atos.sla.parser.data.EnforcementJob;
import eu.atos.sla.parser.data.Penalty;
import eu.atos.sla.parser.data.Provider;
import eu.atos.sla.parser.data.Violation;
import eu.atos.sla.parser.data.wsag.Agreement;
import eu.atos.sla.parser.data.wsag.Context;
import eu.atos.sla.parser.data.wsag.Template;

public interface IModelConverter {

	public IAgreement getAgreementFromAgreementXML(Agreement agreementXML, String payload) throws ModelConversionException;

	public ITemplate getTemplateFromTemplateXML(Template templateXML, String payload) throws ModelConversionException;

	public IEnforcementJob getEnforcementJobFromEnforcementJobXML(EnforcementJob enforcementJobXML) throws ModelConversionException;

	public Context getContextFromAgreement(IAgreement agreement) throws ModelConversionException;
	
	public IProvider getProviderFromProviderXML(Provider providerXML);

	public EnforcementJob getEnforcementJobXML(IEnforcementJob enforcementJob);

	public Provider getProviderXML(IProvider provider);
	
	public Violation getViolationXML(IViolation violation);
	
	public Penalty getPenaltyXML(IPenalty penalty);

}