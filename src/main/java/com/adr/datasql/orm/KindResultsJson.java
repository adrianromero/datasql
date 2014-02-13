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
import com.adr.datasql.KindResults;
import com.adr.datasql.MetaData;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

/**
 *
 * @author adrian
 */
public final class KindResultsJson implements KindResults {
    
    private final JsonObject json;
    
    private static final DateTimeFormatter fmtisodatetime = ISODateTimeFormat.dateTime();
    private static final DateTimeFormatter fmtisodate= ISODateTimeFormat.date();
    private static final DateTimeFormatter fmtisotime=  ISODateTimeFormat.time();
    
    public KindResultsJson(JsonObject json) {
        this.json = json;
    }
    
    @Override
    public Integer getInt(int columnIndex) throws SQLException {
        return getInt(Integer.toString(columnIndex -1));
    }
    @Override
    public Integer getInt(String columnName) throws SQLException {
        JsonElement element = json.get(columnName);
        return element == null || element.isJsonNull() 
                ? null 
                : element.getAsInt();
    }
    @Override
    public String getString(int columnIndex) throws SQLException {
        return getString(Integer.toString(columnIndex -1));
    }
    @Override
    public String getString(String columnName) throws SQLException {
        JsonElement element = json.get(columnName);
        return element == null || element.isJsonNull() 
                ? null 
                : element.getAsString();
    }
    @Override
    public Double getDouble(int columnIndex) throws SQLException {
        return getDouble(Integer.toString(columnIndex -1));
    }
    @Override
    public Double getDouble(String columnName) throws SQLException {
        JsonElement element = json.get(columnName);
        return element == null || element.isJsonNull() 
                ? null 
                : element.getAsDouble();
    }
    @Override
    public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
        return getBigDecimal(Integer.toString(columnIndex -1));
    }
    @Override
    public BigDecimal getBigDecimal(String columnName) throws SQLException {
        JsonElement element = json.get(columnName);
        return element == null || element.isJsonNull() 
                ? null 
                : element.getAsBigDecimal();
    }
    @Override
    public Boolean getBoolean(int columnIndex) throws SQLException {
        return getBoolean(Integer.toString(columnIndex -1));
    }
    @Override
    public Boolean getBoolean(String columnName) throws SQLException {
        JsonElement element = json.get(columnName);
        return element == null || element.isJsonNull() 
                ? null 
                : element.getAsBoolean();

    }
    @Override
    public java.util.Date getTimestamp(int columnIndex) throws SQLException {
        return getTimestamp(Integer.toString(columnIndex -1));
    }
    @Override
    public java.util.Date getTimestamp(String columnName) throws SQLException {
        JsonElement element = json.get(columnName);
        return element == null || element.isJsonNull() 
                ? null 
                : new Date(fmtisodatetime.parseMillis(element.getAsString()));
    }
    @Override
    public java.util.Date getDate(int columnIndex) throws SQLException {
        return getDate(Integer.toString(columnIndex -1));
    }
    @Override
    public java.util.Date getDate(String columnName) throws SQLException {
        JsonElement element = json.get(columnName);
        return element == null || element.isJsonNull() 
                ? null 
                : new Date(fmtisodate.parseMillis(element.getAsString()));

    }
    @Override
    public java.util.Date getTime(int columnIndex) throws SQLException {
        return getTime(Integer.toString(columnIndex -1));
    }
    @Override
    public java.util.Date getTime(String columnName) throws SQLException {
        JsonElement element = json.get(columnName);
        return element == null || element.isJsonNull() 
                ? null 
                : new Date(fmtisotime.parseMillis(element.getAsString()));
    }        
    @Override
    public byte[] getBytes(int columnIndex) throws SQLException {
        return getBytes(Integer.toString(columnIndex -1));
    }
    @Override
    public byte[] getBytes(String columnName) throws SQLException {
        JsonElement element = json.get(columnName);
        return element == null || element.isJsonNull() 
                ? null 
                : EncodeUtils.decode(element.getAsString());
    }
    @Override
    public Object getObject(int columnIndex) throws SQLException {
        return getObject(Integer.toString(columnIndex -1));
    }
    @Override
    public Object getObject(String columnName) throws SQLException {
        JsonElement element = json.get(columnName);
        return element == null || element.isJsonNull() 
                ? null 
                : element.getAsString();
    }
    
    @Override
    public MetaData[] getMetaData() throws SQLException {
        throw new UnsupportedOperationException("Not supported.");
    }    
}
