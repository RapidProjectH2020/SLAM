/*******************************************************************************
 * Copyright © 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.service.rest;


import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import eu.atos.sla.parser.data.Penalty;
import eu.atos.sla.service.rest.exception.InternalException;
import eu.atos.sla.service.rest.helpers.PenaltyHelperE;
import eu.atos.sla.service.rest.helpers.exception.ParserHelperException;
import eu.atos.sla.service.types.DateParam;

/**
 * Rest Service that exposes applied penalties to agreements.
 * 
 * @author rsosa
 */
@Path("/penalties")
@Component
@Scope("request")
public class PenaltyRestEntity extends AbstractSLARest {
	@Autowired
	private PenaltyHelperE helper;

	public static Logger logger = LoggerFactory.getLogger(PenaltyRestEntity.class);

	public PenaltyRestEntity() {
	}
	
	
	private PenaltyHelperE getPenaltyHelper() {
		return helper;
	}

	
	/**
	 * Returns the information of an specific penalty given an uuid. 
	 * 
	 * @return violations according to parameters in the query string.
	 */
	@GET
	@Path("{uuid}")
	public Penalty getPenaltyByUuid(@PathParam("uuid") UUID uuid) {
		logger.debug("StartOf getPenaltyByUuid - REQUEST for /penalties/{}", uuid);
		PenaltyHelperE penaltyRestHelper = getPenaltyHelper();
		Penalty result = penaltyRestHelper.getPenaltyByUuid(uuid);
		logger.debug("EndOf getPenaltyByUuid");
		return result;
	}

	/**
	 * Search penalties given several query terms.
	 * 
	 * If no parameters specified, return all penalties.
	 * 
	 * @param agreementId
	 * @param guaranteeTerm
	 * @param begin
	 * @param end
	 * @return penalties according to parameters in the query string.
	 */
	@GET
	public List<Penalty> getPenalties(
			@QueryParam("agreementId") String agreementId,
			@QueryParam("guaranteeTerm") String guaranteeTerm,
			@QueryParam("begin") DateParam begin, @QueryParam("end") DateParam end) throws InternalException{

		logger.debug("StartOf  getPenalties REQUEST for /penalties/?agreementId={}&guaranteeTerm={}&begin={}&end={}",
				agreementId, guaranteeTerm, begin, end);

		Date dBegin = (begin == null)? null : begin.getDate();
		Date dEnd = (end == null)? null : end.getDate();
		PenaltyHelperE penaltyRestHelper = getPenaltyHelper();
		List<Penalty> penalties;
		try{
			penalties = penaltyRestHelper.getPenalties(agreementId, guaranteeTerm, dBegin, dEnd);
		} catch (ParserHelperException e) {
			throw new InternalException(e.getMessage());
		}
		logger.debug("EndOf getPenalties");
		return penalties;
	}
	
}
