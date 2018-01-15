/*******************************************************************************
 * Copyright © 2018 Atos Spain SA. All rights reserved.
 * This file is part of SLAM.
 * SLAM is free software: you can redistribute it and/or modify it under the terms of Apache 2.0
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT ANY WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT, IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * See LICENSE file for full license information in the project root.
 *******************************************************************************/
package eu.atos.sla.parser;
/**
 * 
 * @author Elena Garrido
 */
public interface IParser <T> {
	/*
	 * getWsagObject receives in serializedData the object information in an xml, json or any other format and 
	 * must return the T object (eu.atos.sla.parser.data.wsag.Agreement or an eu.atos.sla.parser.data.wsag.Template)
	 */
	public T getWsagObject(String serializedData) throws ParserException;

	/*
	 * getWsagAsSerializedData receives in serializedData the object information in an xml, json or any other format and 
	 * must return information following and xml in wsag standard.
	 */
	public String getWsagAsSerializedData(String serializedData) throws ParserException;
	
	/*
	 * getSerializedData receives in wsagSerialized the information in wsag standard as it was generated with the
	 * getWsagAsSerializedData method and returns it in a xml, json or any other format
	 */
	public String getSerializedData(String wsagSerialized) throws ParserException;
}
