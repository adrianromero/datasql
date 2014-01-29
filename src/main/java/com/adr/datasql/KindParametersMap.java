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

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

/**
 *
 * @author adrian
 */
public final class KindParametersMap implements KindParameters {

    private final String[] params;
    private final PreparedStatement stmt;

    public KindParametersMap(PreparedStatement stmt, String[] params) {
        this.stmt = stmt;
        this.params = params;
    }

    @Override
    public void setInt(int paramIndex, Integer iValue) throws SQLException {
        stmt.setObject(paramIndex, iValue, Types.INTEGER);
    }
    @Override
    public void setInt(String paramName, Integer iValue) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            if (params[i] != null && params[i].equals(paramName)) {
                setInt(i + 1, iValue);
            }
        }    
    }
    @Override
    public void setString(int paramIndex, String sValue) throws SQLException {
        stmt.setString(paramIndex, sValue);
    }
    @Override
    public void setString(String paramName, String sValue) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            if (params[i] != null && params[i].equals(paramName)) {
                setString(i + 1, sValue);
            }
        }              
    }
    @Override
    public void setDouble(int paramIndex, Double dValue) throws SQLException {
        stmt.setObject(paramIndex, dValue, Types.DOUBLE);
    }
    @Override
    public void setDouble(String paramName, Double dValue) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            if (params[i] != null && params[i].equals(paramName)) {
                setDouble(i + 1, dValue);
            }
        }               
    }
    @Override
    public void setBoolean(int paramIndex, Boolean bValue) throws SQLException {
        stmt.setObject(paramIndex, bValue, Types.BOOLEAN);
    }
    @Override
    public void setBoolean(String paramName, Boolean bValue) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            if (params[i] != null && params[i].equals(paramName)) {
                setBoolean(i + 1, bValue);
            }
        }               
    }
    @Override
    public void setTimestamp(int paramIndex, java.util.Date dValue) throws SQLException {
        stmt.setObject(paramIndex, dValue == null ? null : new Timestamp(dValue.getTime()), Types.TIMESTAMP);
    }
    @Override
    public void setTimestamp(String paramName, java.util.Date dValue) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            if (params[i] != null && params[i].equals(paramName)) {
                setTimestamp(i + 1, dValue);
            }
        }               
    }
    @Override
    public void setDate(int paramIndex, java.util.Date dValue) throws SQLException {
        stmt.setObject(paramIndex, dValue == null ? null : new java.sql.Date(dValue.getTime()), Types.DATE);
    }
    @Override
    public void setDate(String paramName, java.util.Date dValue) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            if (params[i] != null && params[i].equals(paramName)) {
                setDate(i + 1, dValue);
            }
        }               
    }
    @Override
    public void setTime(int paramIndex, java.util.Date dValue) throws SQLException {
        stmt.setObject(paramIndex, dValue == null ? null : new java.sql.Date(dValue.getTime()), Types.TIME);
    }
    @Override
    public void setTime(String paramName, java.util.Date dValue) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            if (params[i] != null && params[i].equals(paramName)) {
                setTime(i + 1, dValue);
            }
        }               
    }        
    @Override
    public void setBytes(int paramIndex, byte[] value) throws SQLException {
        stmt.setBytes(paramIndex, value);
    }
    @Override
    public void setBytes(String paramName, byte[] value) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            if (params[i] != null && params[i].equals(paramName)) {
                setBytes(i + 1, value);
            }
        }               
    }

    @Override
    public int size() throws SQLException {
        return stmt.getParameterMetaData().getParameterCount();
    }
}
