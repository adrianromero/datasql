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

package com.adr.datasql;

import com.adr.datasql.meta.Field;
import com.adr.datasql.meta.MetaData;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.UUID;

/**
 *
 * @author adrian
 */
public final class KindResultsNew implements KindResults {
    
    private final Field[] fields;
    
    public KindResultsNew(Field[] fields) {
        this.fields = fields;
    }
    
    @Override
    public Integer getInt(int columnIndex) throws SQLException {
        return null;
    }
    @Override
    public Integer getInt(String columnName) throws SQLException {
        return null;
    }
    @Override
    public String getString(int columnIndex) throws SQLException {
        return fields[columnIndex - 1].isKey() ? UUID.randomUUID().toString().replaceAll("-", "") : null;
    }
    @Override
    public String getString(String columnName) throws SQLException {
        for (Field f : fields) {
            if (f.getName().equals(columnName)) {
                return f.isKey() ? UUID.randomUUID().toString().replaceAll("-", "") : null;
            }
        }
        return null;
    }
    @Override
    public Double getDouble(int columnIndex) throws SQLException {
        return null;
    }
    @Override
    public Double getDouble(String columnName) throws SQLException {
        return null;
    }
    @Override
    public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
        return null;
    }
    @Override
    public BigDecimal getBigDecimal(String columnName) throws SQLException {
        return null;
    }
    @Override
    public Boolean getBoolean(int columnIndex) throws SQLException {
        return false;
    }
    @Override
    public Boolean getBoolean(String columnName) throws SQLException {
        return false;
    }
    @Override
    public java.util.Date getTimestamp(int columnIndex) throws SQLException {
        return null;
    }
    @Override
    public java.util.Date getTimestamp(String columnName) throws SQLException {
        return null;
    }
    @Override
    public java.util.Date getDate(int columnIndex) throws SQLException {
        return null;
    }
    @Override
    public java.util.Date getDate(String columnName) throws SQLException {
        return null;
    }
    @Override
    public java.util.Date getTime(int columnIndex) throws SQLException {
        return null;
    }
    @Override
    public java.util.Date getTime(String columnName) throws SQLException {
        return null;
    }        
    @Override
    public byte[] getBytes(int columnIndex) throws SQLException {
        return null;
    }
    @Override
    public byte[] getBytes(String columnName) throws SQLException {
        return null;
    }
    @Override
    public Object getObject(int columnIndex) throws SQLException {
        return null;
    }
    @Override
    public Object getObject(String columnName) throws SQLException {
        return null;
    }
    
    @Override
    public MetaData[] getMetaData() throws SQLException {       
        return fields;       
    }    
}
