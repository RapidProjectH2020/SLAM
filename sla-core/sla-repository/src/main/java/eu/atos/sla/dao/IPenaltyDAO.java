/*******************************************************************************
 * Copyright © 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.dao;

import java.util.Date;
import java.util.List;

import eu.atos.sla.datamodel.ICompensation.IPenalty;

/**
 * DAO interface to access to the Penalty information
 * 
 * @author rsosa
 */
public interface IPenaltyDAO {

	/**
	 * Returns penalty from database by its id
	 * @return existent penalty or null
	 */
	public IPenalty getById(Long id);

	/**
	 * Stores a Penalty into the database
	 * 
	 * @param penalty to save
	 * 
	 * @return the penalty stored in the database (id filled if was null in parameter)
	 */
	public IPenalty save(IPenalty penalty);
	
	/**
	 * Returns penalty from database by its uuid
	 * @return existent penalty or null
	 */
	public IPenalty getByUuid(String uuid);
	
	/**
	 * Returns penalties applied to an agreement
	 */
	public List<IPenalty> getByAgreement(String agreementId);
	
	public List<IPenalty> search(SearchParameters params);
	
	public static class SearchParameters {

		private String agreementId;
		private String guaranteeTermName;
		private Date begin;
		private Date end;
		
		public SearchParameters() {
		}
		
		public String getAgreementId() {
			return agreementId;
		}

		public String getGuaranteeTermName() {
			return guaranteeTermName;
		}

		public Date getBegin() {
			return begin;
		}

		public Date getEnd() {
			return end;
		}

		public void setAgreementId(String agreementId) {
			this.agreementId = agreementId;
		}

		public void setGuaranteeTermName(String guaranteeTermName) {
			this.guaranteeTermName = guaranteeTermName;
		}

		public void setBegin(Date begin) {
			this.begin = begin;
		}

		public void setEnd(Date end) {
			this.end = end;
		}
		
	}
	
}
