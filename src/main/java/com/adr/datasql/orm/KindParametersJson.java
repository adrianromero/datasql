//    Data SQL is a light JDBC wrapper.
//    Copyright (C) 2014-2015 Adrián Romero Corchado.
//
//    This file is part of Data SQL
//
//     Licensed under the Apache License, Version 2.0 (the "License");
//     you may not use this file except in compliance with the License.
//     You may obtain a copy of the License at
//     
//         http://www.apache.org/licenses/LICENSE-2.0
//     
//     Unless required by applicable law or agreed to in writing, software
//     distributed under the License is distributed on an "AS IS" BASIS,
//     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//     See the License for the specific language governing permissions and
//     limitations under the License.

package com.adr.datasql.orm;

import com.adr.datasql.KindParameters;
import com.adr.datasql.data.MetaData;
import com.adr.datasql.link.DataLinkException;
import com.google.gson.JsonObject;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Base64;

/**
 *
 * @author adrian
 */
public class KindParametersJson implements KindParameters {

    private final JsonObject json;
    
    public KindParametersJson(JsonObject json) {
        this.json = json;  
    }

    @Override
    public void setInt(int paramIndex, Integer iValue) throws DataLinkException {
        setInt(Integer.toString(paramIndex - 1), iValue); 
    }
    @Override
    public void setInt(String paramName, Integer iValue) throws DataLinkException {
        json.addProperty(paramName, iValue); 
    }
    @Override
    public void setString(int paramIndex, String sValue) throws DataLinkException {
        setString(Integer.toString(paramIndex - 1), sValue); 
    }
    @Override
    public void setString(String paramName, String sValue) throws DataLinkException {
        json.addProperty(paramName, sValue);             
    }
    @Override
    public void setDouble(int paramIndex, Double dValue) throws DataLinkException {
        setDouble(Integer.toString(paramIndex - 1), dValue); 
    }
    @Override
    public void setDouble(String paramName, Double dValue) throws DataLinkException {
        json.addProperty(paramName, dValue);            
    }
    @Override
    public void setBigDecimal(int paramIndex, BigDecimal value) throws DataLinkException {
        setBigDecimal(Integer.toString(paramIndex - 1), value); 
    }
    @Override
    public void setBigDecimal(String paramName, BigDecimal value) throws DataLinkException {
        json.addProperty(paramName, value);             
    }
    @Override
    public void setBoolean(int paramIndex, Boolean bValue) throws DataLinkException {
        setBoolean(Integer.toString(paramIndex - 1), bValue); 
    }
    @Override
    public void setBoolean(String paramName, Boolean bValue) throws DataLinkException {
        json.addProperty(paramName, bValue);            
    }
    @Override
    public void setTimestamp(int paramIndex, java.util.Date dValue) throws DataLinkException {
        setTimestamp(Integer.toString(paramIndex - 1), dValue); 
    }
    @Override
    public void setTimestamp(String paramName, java.util.Date dValue) throws DataLinkException {
        json.addProperty(paramName, Instant.ofEpochMilli(dValue.getTime()).toString()); 
    }
    @Override
    public void setDate(int paramIndex, java.util.Date dValue) throws DataLinkException {
        setDate(Integer.toString(paramIndex - 1), dValue);      
    }
    @Override
    public void setDate(String paramName, java.util.Date dValue) throws DataLinkException {
        json.addProperty(paramName, LocalDate.from(Instant.ofEpochMilli(dValue.getTime())).toString());          
    }
    @Override
    public void setTime(int paramIndex, java.util.Date dValue) throws DataLinkException {
        setTime(Integer.toString(paramIndex - 1), dValue);
    }
    @Override
    public void setTime(String paramName, java.util.Date dValue) throws DataLinkException {
        json.addProperty(paramName, LocalTime.from(Instant.ofEpochMilli(dValue.getTime())).toString());          
    }        
    @Override
    public void setBytes(int paramIndex, byte[] value) throws DataLinkException {
        setBytes(Integer.toString(paramIndex - 1), value);
    }
    @Override
    public void setBytes(String paramName, byte[] value) throws DataLinkException {
        json.addProperty(paramName, Base64.getEncoder().encodeToString(value));
    }
    @Override
    public void setObject(int paramIndex, Object value) throws DataLinkException {
        setObject(Integer.toString(paramIndex - 1), value);
    }
    @Override
    public void setObject(String paramName, Object value) throws DataLinkException {
        json.addProperty(paramName, value == null ? null : value.toString());
    }

    @Override
    public MetaData[] getMetaData() throws DataLinkException {
        throw new UnsupportedOperationException("Not supported.");
    }
}
