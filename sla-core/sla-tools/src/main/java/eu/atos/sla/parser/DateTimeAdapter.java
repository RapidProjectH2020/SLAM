/*******************************************************************************
 * Copyright © 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.parser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateTimeAdapter extends XmlAdapter<String, Date>{ 
	private static Logger logger = LoggerFactory.getLogger(DateTimeAdapter.class);
	
	static private String dateFormat;

	static private String unmarshallTimezone;

	static private String marshallTimezone;
	
	public String getDateFormat() {
		return dateFormat;
	}

	public String getUnmarshallTimezone() {
		return unmarshallTimezone;
	}

	public String getMarshallTimezone() {
		return marshallTimezone;
	}

	public void setDateFormat(String dateFormat) {
		DateTimeAdapter.dateFormat = dateFormat;
	}

	public void setUnmarshallTimezone(String unmarshallTimezone) {
		DateTimeAdapter.unmarshallTimezone = unmarshallTimezone;
	}

	public void setMarshallTimezone(String marshallTimezone) {
		DateTimeAdapter.marshallTimezone = marshallTimezone;
	}

	@Override
	public Date unmarshal(String dateAsString) throws Exception {
		Date date = null;
		try{
			SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
			formatter.setTimeZone(TimeZone.getTimeZone(unmarshallTimezone));
			date = formatter.parse(dateAsString);
		}catch(Throwable t){
			throw new ParserException(
					"Date " + dateAsString + " couldn't be unmarshal with " + dateFormat + 
					" and timezone "+unmarshallTimezone, t);
		}
		logger.debug(date +"{} has been unmarshalled to {}", dateAsString, date);
		return date;
	}

	
	@Override
	public String marshal(Date date) throws Exception {
		String dateAsString = null; 
		try{
			SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
			formatter.setTimeZone(TimeZone.getTimeZone(marshallTimezone));
			dateAsString = formatter.format(date);
		}catch(Throwable t){
			throw new ParserException(
					"Date " + date + " couldn't be marshal with " + dateFormat + 
					" and timezone " + marshallTimezone, t);
		}
		logger.debug(date +"{} has been mashalled to {}", date, dateAsString);
		return dateAsString;
	}

}
