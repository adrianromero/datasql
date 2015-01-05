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

import com.adr.datasql.KindParameters;
import com.adr.datasql.data.MetaData;
import java.math.BigDecimal;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

/**
 *
 * @author adrian
 */
class SQLKindParameters implements KindParameters {

    private final String[] params;
    private final PreparedStatement stmt;

    public SQLKindParameters(PreparedStatement stmt, String[] params) {
        this.stmt = stmt;
        this.params = params;
    }
    
    @FunctionalInterface
    private interface Setter {
        void set(int index) throws DataLinkException;
    }
    
    private void set(String paramName, Setter s) throws DataLinkException {
        for (int i = 0; i < params.length; i++) {
            if (params[i] != null && params[i].equals(paramName)) {
                s.set(i + 1);
            }
        } 
    }     

    @Override
    public void setInt(int paramIndex, Integer value) throws DataLinkException {
        try {
            stmt.setObject(paramIndex, value, Types.INTEGER);
        } catch (SQLException ex) {
            throw new DataLinkException(ex);
        }
    }
    @Override
    public void setInt(String paramName, Integer value) throws DataLinkException {
        set(paramName, index -> setInt(index, value));  
    }
    @Override
    public void setString(int paramIndex, String value) throws DataLinkException {
        try {
            stmt.setString(paramIndex, value);
        } catch (SQLException ex) {
            throw new DataLinkException(ex);
        }
    }
    @Override
    public void setString(String paramName, String value) throws DataLinkException {
        set(paramName, index -> setString(index, value));              
    }
    @Override
    public void setDouble(int paramIndex, Double value) throws DataLinkException {
        try {
            stmt.setObject(paramIndex, value, Types.DOUBLE);
        } catch (SQLException ex) {
            throw new DataLinkException(ex);
        }
    }
    @Override
    public void setDouble(String paramName, Double value) throws DataLinkException {
        set(paramName, index -> setDouble(index, value));               
    }
    @Override
    public void setBigDecimal(int paramIndex, BigDecimal value) throws DataLinkException {
        try {
            stmt.setBigDecimal(paramIndex, value);
        } catch (SQLException ex) {
            throw new DataLinkException(ex);
        }
    }
    @Override
    public void setBigDecimal(String paramName, BigDecimal value) throws DataLinkException {
        set(paramName, index -> setBigDecimal(index, value));                          
    }
    @Override
    public void setBoolean(int paramIndex, Boolean value) throws DataLinkException {
        try {
            stmt.setObject(paramIndex, value, Types.BOOLEAN);
        } catch (SQLException ex) {
            throw new DataLinkException(ex);
        }
    }
    @Override
    public void setBoolean(String paramName, Boolean value) throws DataLinkException {
        set(paramName, index -> setBoolean(index, value));                         
    }
    @Override
    public void setTimestamp(int paramIndex, java.util.Date value) throws DataLinkException {
        try {
            stmt.setObject(paramIndex, value == null ? null : new Timestamp(value.getTime()), Types.TIMESTAMP);
        } catch (SQLException ex) {
            throw new DataLinkException(ex);
        }
    }
    @Override
    public void setTimestamp(String paramName, java.util.Date value) throws DataLinkException {
        set(paramName, index -> setTimestamp(index, value));
    }
    @Override
    public void setDate(int paramIndex, java.util.Date value) throws DataLinkException {
        try {
            stmt.setObject(paramIndex, value == null ? null : new java.sql.Date(value.getTime()), Types.DATE);
        } catch (SQLException ex) {
            throw new DataLinkException(ex);
        }
    }
    @Override
    public void setDate(String paramName, java.util.Date value) throws DataLinkException {
        set(paramName, index -> setDate(index, value));     
    }
    @Override
    public void setTime(int paramIndex, java.util.Date value) throws DataLinkException {
        try {
            stmt.setObject(paramIndex, value == null ? null : new java.sql.Date(value.getTime()), Types.TIME);
        } catch (SQLException ex) {
            throw new DataLinkException(ex);
        }
    }
    @Override
    public void setTime(String paramName, java.util.Date value) throws DataLinkException {
        set(paramName, index -> setTime(index, value));     
    }        
    @Override
    public void setBytes(int paramIndex, byte[] value) throws DataLinkException {
        try {
            stmt.setBytes(paramIndex, value);
        } catch (SQLException ex) {
            throw new DataLinkException(ex);
        }
    }
    @Override
    public void setBytes(String paramName, byte[] value) throws DataLinkException {
        set(paramName, index -> setBytes(index, value));     
    }
    @Override
    public void setObject(int paramIndex, Object value) throws DataLinkException {
        try {
            stmt.setObject(paramIndex, value);
        } catch (SQLException ex) {
            throw new DataLinkException(ex);
        }
    }
    @Override
    public void setObject(String paramName, Object value) throws DataLinkException {
        set(paramName, index -> setObject(index, value));     
    }
    
    @Override
    public MetaData[] getMetaData() throws DataLinkException {
        
        try {
            ParameterMetaData meta = stmt.getParameterMetaData();
            int size = meta.getParameterCount();
            MetaData[] metadata = new MetaData[size];
            
            for (int i = 0; i < size; i++) {
                metadata[i] = new MetaData(i < params.length ? params[i] : null, SQLDataLinkFactory.getKind(meta.getParameterType(i + 1)));
            }
            
            return metadata;
        } catch (SQLException ex) {
            throw new DataLinkException(ex);
        }
    }
}
