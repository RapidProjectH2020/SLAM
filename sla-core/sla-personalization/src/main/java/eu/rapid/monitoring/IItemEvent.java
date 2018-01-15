/*******************************************************************************
 * Copyright © 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
 
package eu.rapid.monitoring;

import eu.atos.sla.datamodel.IAgreement;
import eu.atos.sla.datamodel.IProvider;
import eu.atos.sla.datamodel.ITemplate;

public interface IItemEvent {
	void onEndOfTemplateCreation(ITemplate template) throws ItemEventException;
	void onEndOfEnforcementJobCreation(String agreementUUID) throws ItemEventException;
	void onEndOfProviderCreation(IProvider provider) throws ItemEventException;
	void onEndOfAgreementCreation(IAgreement agreement) throws ItemEventException;
	
	void onStartOfEnforcementJobDone(String agreementUUID) throws ItemEventException;
	void onStopOfEnforcementJobDone(String agreementUUID) throws ItemEventException;
	
}
