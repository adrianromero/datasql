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

package com.adr.datasql.link;

import com.adr.datasql.KindResults;
import com.adr.datasql.data.MetaData;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 *
 * @author adrian
 */
final class SQLKindResults implements KindResults {
    
    private final ResultSet resultset;
    
    public SQLKindResults(ResultSet resultset) {
        this.resultset = resultset;
    }
    
    @Override
    public Integer getInt(int columnIndex) throws DataLinkException {
        try {
            int iValue = resultset.getInt(columnIndex);
            return resultset.wasNull() ? null : iValue;
        } catch (SQLException ex) {
            throw new DataLinkException(ex);
        }
    }
    @Override
    public Integer getInt(String columnName) throws DataLinkException {
        try {
            int iValue = resultset.getInt(columnName);
            return resultset.wasNull() ? null : iValue;
        } catch (SQLException ex) {
            throw new DataLinkException(ex);
        }
    }
    @Override
    public String getString(int columnIndex) throws DataLinkException {
        try {
            return resultset.getString(columnIndex);
        } catch (SQLException ex) {
            throw new DataLinkException(ex);
        }
    }
    @Override
    public String getString(String columnName) throws DataLinkException {
        try {
            return resultset.getString(columnName);
        } catch (SQLException ex) {
            throw new DataLinkException(ex);
        }
    }
    @Override
    public Double getDouble(int columnIndex) throws DataLinkException {
        try {
            double dValue = resultset.getDouble(columnIndex);
            return resultset.wasNull() ? null : dValue;
        } catch (SQLException ex) {
            throw new DataLinkException(ex);
        }
    }
    @Override
    public Double getDouble(String columnName) throws DataLinkException {
        try {
            double dValue = resultset.getDouble(columnName);
            return resultset.wasNull() ? null : dValue;
        } catch (SQLException ex) {
            throw new DataLinkException(ex);
        }
    }
    @Override
    public BigDecimal getBigDecimal(int columnIndex) throws DataLinkException {
        try {
            return resultset.getBigDecimal(columnIndex);
        } catch (SQLException ex) {
            throw new DataLinkException(ex);
        }
    }
    @Override
    public BigDecimal getBigDecimal(String columnName) throws DataLinkException {
        try {
            return resultset.getBigDecimal(columnName);
        } catch (SQLException ex) {
            throw new DataLinkException(ex);
        }
    }
    @Override
    public Boolean getBoolean(int columnIndex) throws DataLinkException {
        try {
            boolean bValue = resultset.getBoolean(columnIndex);
            return resultset.wasNull() ? null : bValue;
        } catch (SQLException ex) {
            throw new DataLinkException(ex);
        }
    }
    @Override
    public Boolean getBoolean(String columnName) throws DataLinkException {
        try {
            boolean bValue = resultset.getBoolean(columnName);
            return resultset.wasNull() ? null : bValue;
        } catch (SQLException ex) {
            throw new DataLinkException(ex);
        }
    }
    @Override
    public java.util.Date getTimestamp(int columnIndex) throws DataLinkException {
        try {
            java.sql.Timestamp ts = resultset.getTimestamp(columnIndex);
            return ts == null ? null : new java.util.Date(ts.getTime());
        } catch (SQLException ex) {
            throw new DataLinkException(ex);
        }
    }
    @Override
    public java.util.Date getTimestamp(String columnName) throws DataLinkException {
        try {
            java.sql.Timestamp ts = resultset.getTimestamp(columnName);
            return ts == null ? null : new java.util.Date(ts.getTime());
        } catch (SQLException ex) {
            throw new DataLinkException(ex);
        }
    }
    @Override
    public java.util.Date getDate(int columnIndex) throws DataLinkException {
        try {
            java.sql.Date da = resultset.getDate(columnIndex);
            return da == null ? null : new java.util.Date(da.getTime());
        } catch (SQLException ex) {
            throw new DataLinkException(ex);
        }
    }
    @Override
    public java.util.Date getDate(String columnName) throws DataLinkException {
        try {
            java.sql.Date da = resultset.getDate(columnName);
            return da == null ? null : new java.util.Date(da.getTime());
        } catch (SQLException ex) {
            throw new DataLinkException(ex);
        }
    }
    @Override
    public java.util.Date getTime(int columnIndex) throws DataLinkException {
        try {
            java.sql.Time da = resultset.getTime(columnIndex);
            return da == null ? null : new java.util.Date(da.getTime());
        } catch (SQLException ex) {
            throw new DataLinkException(ex);
        }
    }
    @Override
    public java.util.Date getTime(String columnName) throws DataLinkException {
        try {
            java.sql.Time da = resultset.getTime(columnName);
            return da == null ? null : new java.util.Date(da.getTime());
        } catch (SQLException ex) {
            throw new DataLinkException(ex);
        }
    }        
    @Override
    public byte[] getBytes(int columnIndex) throws DataLinkException {
        try {
            return resultset.getBytes(columnIndex);
        } catch (SQLException ex) {
            throw new DataLinkException(ex);
        }
    }
    @Override
    public byte[] getBytes(String columnName) throws DataLinkException {
        try {
            return resultset.getBytes(columnName);
        } catch (SQLException ex) {
            throw new DataLinkException(ex);
        }
    }
    @Override
    public Object getObject(int columnIndex) throws DataLinkException {
        try {
            return resultset.getObject(columnIndex);
        } catch (SQLException ex) {
            throw new DataLinkException(ex);
        }
    }
    @Override
    public Object getObject(String columnName) throws DataLinkException {
        try {
            return resultset.getObject(columnName);
        } catch (SQLException ex) {
            throw new DataLinkException(ex);
        }
    }
    
    @Override
    public MetaData[] getMetaData() throws DataLinkException {
        
        try {
            ResultSetMetaData meta = resultset.getMetaData();
            int size = meta.getColumnCount();
            MetaData[] metadata = new MetaData[size];
            
            for (int i = 0; i < size; i++) {
                metadata[i] = new MetaData(meta.getColumnName(i + 1), SQLDataLinkFactory.getKind(meta.getColumnType(i + 1)));
            }
            
            return metadata;
        } catch (SQLException ex) {
            throw new DataLinkException(ex);
        }
    }    
}
