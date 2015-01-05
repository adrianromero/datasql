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

import com.adr.datasql.KindResults;
import com.adr.datasql.data.MetaData;
import com.adr.datasql.link.DataLinkException;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

/**
 *
 * @author adrian
 */
public final class KindResultsJson implements KindResults {
    
    private final JsonObject json;
    
    public KindResultsJson(JsonObject json) {
        this.json = json;
    }
    
    @Override
    public Integer getInt(int columnIndex) throws DataLinkException {
        return getInt(Integer.toString(columnIndex -1));
    }
    @Override
    public Integer getInt(String columnName) throws DataLinkException {
        JsonElement element = json.get(columnName);
        return element == null || element.isJsonNull() 
                ? null 
                : element.getAsInt();
    }
    @Override
    public String getString(int columnIndex) throws DataLinkException {
        return getString(Integer.toString(columnIndex -1));
    }
    @Override
    public String getString(String columnName) throws DataLinkException {
        JsonElement element = json.get(columnName);
        return element == null || element.isJsonNull() 
                ? null 
                : element.getAsString();
    }
    @Override
    public Double getDouble(int columnIndex) throws DataLinkException {
        return getDouble(Integer.toString(columnIndex -1));
    }
    @Override
    public Double getDouble(String columnName) throws DataLinkException {
        JsonElement element = json.get(columnName);
        return element == null || element.isJsonNull() 
                ? null 
                : element.getAsDouble();
    }
    @Override
    public BigDecimal getBigDecimal(int columnIndex) throws DataLinkException {
        return getBigDecimal(Integer.toString(columnIndex -1));
    }
    @Override
    public BigDecimal getBigDecimal(String columnName) throws DataLinkException {
        JsonElement element = json.get(columnName);
        return element == null || element.isJsonNull() 
                ? null 
                : element.getAsBigDecimal();
    }
    @Override
    public Boolean getBoolean(int columnIndex) throws DataLinkException {
        return getBoolean(Integer.toString(columnIndex -1));
    }
    @Override
    public Boolean getBoolean(String columnName) throws DataLinkException {
        JsonElement element = json.get(columnName);
        return element == null || element.isJsonNull() 
                ? null 
                : element.getAsBoolean();

    }
    @Override
    public java.util.Date getTimestamp(int columnIndex) throws DataLinkException {
        return getTimestamp(Integer.toString(columnIndex -1));
    }
    @Override
    public java.util.Date getTimestamp(String columnName) throws DataLinkException {
        JsonElement element = json.get(columnName);
        return element == null || element.isJsonNull() 
                ? null 
                : new Date(Instant.parse(element.getAsString()).toEpochMilli());
    }
    @Override
    public java.util.Date getDate(int columnIndex) throws DataLinkException {
        return getDate(Integer.toString(columnIndex -1));
    }
    @Override
    public java.util.Date getDate(String columnName) throws DataLinkException {
        JsonElement element = json.get(columnName);
        return element == null || element.isJsonNull() 
                ? null 
                : new Date(LocalDate.parse(element.getAsString()).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());

    }
    @Override
    public java.util.Date getTime(int columnIndex) throws DataLinkException {
        return getTime(Integer.toString(columnIndex -1));
    }
    @Override
    public java.util.Date getTime(String columnName) throws DataLinkException {
        JsonElement element = json.get(columnName);
        return element == null || element.isJsonNull() 
                ? null 
                : new Date(LocalTime.parse(element.getAsString()).atDate(LocalDate.ofEpochDay(0L)).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
    }        
    @Override
    public byte[] getBytes(int columnIndex) throws DataLinkException {
        return getBytes(Integer.toString(columnIndex -1));
    }
    @Override
    public byte[] getBytes(String columnName) throws DataLinkException {
        JsonElement element = json.get(columnName);
        return element == null || element.isJsonNull() 
                ? null 
                : Base64.getDecoder().decode(element.getAsString());
    }
    @Override
    public Object getObject(int columnIndex) throws DataLinkException {
        return getObject(Integer.toString(columnIndex -1));
    }
    @Override
    public Object getObject(String columnName) throws DataLinkException {
        JsonElement element = json.get(columnName);
        return element == null || element.isJsonNull() 
                ? null 
                : element.getAsString();
    }
    
    @Override
    public MetaData[] getMetaData() throws DataLinkException {
        throw new UnsupportedOperationException("Not supported.");
    }    
}
