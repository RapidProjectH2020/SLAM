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

@SuppressWarnings("rawtypes")
public class NullParser implements IParser {


	@Override
	public Object getWsagObject(String serializedData) throws ParserException{
		throw new ParserException("Method not implemented");
	}

	@Override
	public String getWsagAsSerializedData(String serializedData) throws ParserException {
		throw new ParserException("Method not implemented");
	}

	@Override
	public String getSerializedData(String wsagSerialized) throws ParserException{
		throw new ParserException("Method not implemented");
	}
	
	
}
