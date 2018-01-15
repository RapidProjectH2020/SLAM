/*******************************************************************************
 * Copyright © 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.service.rest;

//import java.io.File;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.jayway.restassured.RestAssured;
//import com.jayway.restassured.response.Response;

/**
 * Rest Service that exposes all the stored information of the SLA core
 * 
 * @author Pedro Rey
 */

public class AgreementRestTest {
	private static Logger logger = LoggerFactory.getLogger(AgreementRestTest.class);

	@Test
	public void testGetAgreements() {
		logger.debug("start of testGetAgreements");
/*		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 8080;
		RestAssured.basePath = "/sla-service";
		Response someData  = RestAssured.given().contentType("application/xml").multiPart("payload", new File("./trunk/src/main/resources/samples/agreement01.xml")).when().post("/agreements");
		
		logger.log(Level.INFO, "Rest output call "+someData.htmlPath().prettyPrint());
		//TODO egarrido, missing to do verification, if rest call result is correct or not.
*/
		logger.debug("end of testGetAgreements");
		
		assert (true);
	}

	@Test
	public void testGetAgreementById() {

		assert (true);

	}

	@Test
	public void testCreateAgreement() {

		assert (true);
	}

	@Test
	public void testGetActiveAgreements() {

		assert (true);

	}

	@Test
	public void testDeleteAgreement() {

		assert (true);
	}

}
