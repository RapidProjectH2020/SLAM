/*******************************************************************************
 * Copyright © 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.service.rest.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.atos.sla.parser.ParserException;
@Provider
public class WebApplicationExceptionHandler implements ExceptionMapper<WebApplicationException>{
	private static Logger logger = LoggerFactory.getLogger(WebApplicationExceptionHandler.class);

	@Override
	public Response toResponse(WebApplicationException exception) {
		if (exception.getCause()!=null){ 	
			if (exception.getCause() instanceof ParserException){
				ParserException pe = (ParserException)exception.getCause();
				return  ExceptionHandlerUtils.buildResponse(Status.NOT_ACCEPTABLE, pe);
			}
			if (exception.getCause().getCause()!=null){
				if (exception.getCause().getCause().getCause()!=null){
					if (exception.getCause().getCause().getCause() instanceof ParserException){
						ParserException pe = (ParserException)exception.getCause().getCause().getCause();
						return  ExceptionHandlerUtils.buildResponse(Status.NOT_ACCEPTABLE, pe);
					}
				}
			}
		}
		logger.info("Not found exception will be thrown");
		return  ExceptionHandlerUtils.buildResponse(Status.NOT_FOUND, exception);
	}

}
