//    Data SQL is a light JDBC wrapper.
//    Copyright (C) 2014 Adrián Romero Corchado.
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

import com.adr.datasql.EncodeUtils;
import com.adr.datasql.KindParameters;
import com.adr.datasql.MetaData;
import com.google.gson.JsonObject;
import java.math.BigDecimal;
import java.sql.SQLException;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/**
 *
 * @author adrian
 */
public class KindParametersJson implements KindParameters {

    private final JsonObject json;
    
    private static final DateTimeFormatter fmtisodatetime = ISODateTimeFormat.dateTime();
    private static final DateTimeFormatter fmtisodate= ISODateTimeFormat.date();
    private static final DateTimeFormatter fmtisotime=  ISODateTimeFormat.time();
    
    public KindParametersJson(JsonObject json) {
        this.json = json;  
    }

    @Override
    public void setInt(int paramIndex, Integer iValue) throws SQLException {
        setInt(Integer.toString(paramIndex - 1), iValue); 
    }
    @Override
    public void setInt(String paramName, Integer iValue) throws SQLException {
        json.addProperty(paramName, iValue); 
    }
    @Override
    public void setString(int paramIndex, String sValue) throws SQLException {
        setString(Integer.toString(paramIndex - 1), sValue); 
    }
    @Override
    public void setString(String paramName, String sValue) throws SQLException {
        json.addProperty(paramName, sValue);             
    }
    @Override
    public void setDouble(int paramIndex, Double dValue) throws SQLException {
        setDouble(Integer.toString(paramIndex - 1), dValue); 
    }
    @Override
    public void setDouble(String paramName, Double dValue) throws SQLException {
        json.addProperty(paramName, dValue);            
    }
    @Override
    public void setBigDecimal(int paramIndex, BigDecimal value) throws SQLException {
        setBigDecimal(Integer.toString(paramIndex - 1), value); 
    }
    @Override
    public void setBigDecimal(String paramName, BigDecimal value) throws SQLException {
        json.addProperty(paramName, value);             
    }
    @Override
    public void setBoolean(int paramIndex, Boolean bValue) throws SQLException {
        setBoolean(Integer.toString(paramIndex - 1), bValue); 
    }
    @Override
    public void setBoolean(String paramName, Boolean bValue) throws SQLException {
        json.addProperty(paramName, bValue);            
    }
    @Override
    public void setTimestamp(int paramIndex, java.util.Date dValue) throws SQLException {
        setTimestamp(Integer.toString(paramIndex - 1), dValue); 
    }
    @Override
    public void setTimestamp(String paramName, java.util.Date dValue) throws SQLException {
        json.addProperty(paramName, fmtisodatetime.print(dValue.getTime())); 
    }
    @Override
    public void setDate(int paramIndex, java.util.Date dValue) throws SQLException {
        setDate(Integer.toString(paramIndex - 1), dValue);      
    }
    @Override
    public void setDate(String paramName, java.util.Date dValue) throws SQLException {
        json.addProperty(paramName, fmtisodate.print(dValue.getTime()));          
    }
    @Override
    public void setTime(int paramIndex, java.util.Date dValue) throws SQLException {
        setTime(Integer.toString(paramIndex - 1), dValue);
    }
    @Override
    public void setTime(String paramName, java.util.Date dValue) throws SQLException {
        json.addProperty(paramName, fmtisotime.print(dValue.getTime()));    
    }        
    @Override
    public void setBytes(int paramIndex, byte[] value) throws SQLException {
        setBytes(Integer.toString(paramIndex - 1), value);
    }
    @Override
    public void setBytes(String paramName, byte[] value) throws SQLException {
        json.addProperty(paramName, EncodeUtils.encode(value));
    }
    @Override
    public void setObject(int paramIndex, Object value) throws SQLException {
        setObject(Integer.toString(paramIndex - 1), value);
    }
    @Override
    public void setObject(String paramName, Object value) throws SQLException {
        json.addProperty(paramName, value == null ? null : value.toString());
    }

    @Override
    public MetaData[] getMetaData() throws SQLException {
        throw new UnsupportedOperationException("Not supported.");
    }
}
