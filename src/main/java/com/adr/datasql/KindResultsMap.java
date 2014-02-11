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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 *
 * @author adrian
 */
public final class KindResultsMap implements KindResults {
    
    private final ResultSet resultset;
    
    public KindResultsMap(ResultSet resultset) {
        this.resultset = resultset;
    }
    
    @Override
    public Integer getInt(int columnIndex) throws SQLException {
        int iValue = resultset.getInt(columnIndex);
        return resultset.wasNull() ? null : new Integer(iValue);
    }
    @Override
    public Integer getInt(String columnName) throws SQLException {
        int iValue = resultset.getInt(columnName);
        return resultset.wasNull() ? null : new Integer(iValue);
    }
    @Override
    public String getString(int columnIndex) throws SQLException {
        return resultset.getString(columnIndex);
    }
    @Override
    public String getString(String columnName) throws SQLException {
        return resultset.getString(columnName);
    }
    @Override
    public Double getDouble(int columnIndex) throws SQLException {
        double dValue = resultset.getDouble(columnIndex);
        return resultset.wasNull() ? null : new Double(dValue);
    }
    @Override
    public Double getDouble(String columnName) throws SQLException {
        double dValue = resultset.getDouble(columnName);
        return resultset.wasNull() ? null : new Double(dValue);
    }
    @Override
    public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
        return resultset.getBigDecimal(columnIndex);
    }
    @Override
    public BigDecimal getBigDecimal(String columnName) throws SQLException {
        return resultset.getBigDecimal(columnName);
    }
    @Override
    public Boolean getBoolean(int columnIndex) throws SQLException {
        boolean bValue = resultset.getBoolean(columnIndex);
        return resultset.wasNull() ? null : bValue;
    }
    @Override
    public Boolean getBoolean(String columnName) throws SQLException {
        boolean bValue = resultset.getBoolean(columnName);
        return resultset.wasNull() ? null : bValue;
    }
    @Override
    public java.util.Date getTimestamp(int columnIndex) throws SQLException {
        java.sql.Timestamp ts = resultset.getTimestamp(columnIndex);
        return ts == null ? null : new java.util.Date(ts.getTime());
    }
    @Override
    public java.util.Date getTimestamp(String columnName) throws SQLException {
        java.sql.Timestamp ts = resultset.getTimestamp(columnName);
        return ts == null ? null : new java.util.Date(ts.getTime());
    }
    @Override
    public java.util.Date getDate(int columnIndex) throws SQLException {
        java.sql.Date da = resultset.getDate(columnIndex);
        return da == null ? null : new java.util.Date(da.getTime());
    }
    @Override
    public java.util.Date getDate(String columnName) throws SQLException {
        java.sql.Date da = resultset.getDate(columnName);
        return da == null ? null : new java.util.Date(da.getTime());
    }
    @Override
    public java.util.Date getTime(int columnIndex) throws SQLException {
        java.sql.Time da = resultset.getTime(columnIndex);
        return da == null ? null : new java.util.Date(da.getTime());
    }
    @Override
    public java.util.Date getTime(String columnName) throws SQLException {
        java.sql.Time da = resultset.getTime(columnName);
        return da == null ? null : new java.util.Date(da.getTime());
    }        
    @Override
    public byte[] getBytes(int columnIndex) throws SQLException {
        return resultset.getBytes(columnIndex);
    }
    @Override
    public byte[] getBytes(String columnName) throws SQLException {
        return resultset.getBytes(columnName);
    }
    @Override
    public Object getObject(int columnIndex) throws SQLException {
        return resultset.getObject(columnIndex);
    }
    @Override
    public Object getObject(String columnName) throws SQLException {
        return resultset.getObject(columnName);
    }
    
    @Override
    public MetaData[] getMetaData() throws SQLException {
        
        ResultSetMetaData meta = resultset.getMetaData();
        int size = meta.getColumnCount();
        MetaData[] metadata = new MetaData[size];
        
        for (int i = 0; i < size; i++) {
            metadata[i] = new MetaData(meta.getColumnName(i + 1), Kind.getKind(meta.getColumnType(i + 1)));
        }
        
        return metadata;        
    }    
}
