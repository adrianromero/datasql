//    Data SQL is a light JDBC wrapper.
//    Copyright (C) 2012-2014 Adrián Romero Corchado.
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

import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author  adrian
 */
public interface KindResults {

    public Integer getInt(int columnIndex) throws SQLException;
    public Integer getInt(String columnName) throws SQLException;
    public String getString(int columnIndex) throws SQLException;
    public String getString(String columnName) throws SQLException;
    public Double getDouble(int columnIndex) throws SQLException;
    public Double getDouble(String columnName) throws SQLException;
    public Boolean getBoolean(int columnIndex) throws SQLException;
    public Boolean getBoolean(String columnName) throws SQLException;
    public Date getTimestamp(int columnIndex) throws SQLException;
    public Date getTimestamp(String columnName) throws SQLException;
    public Date getDate(int columnIndex) throws SQLException;
    public Date getDate(String columnName) throws SQLException;
    public Date getTime(int columnIndex) throws SQLException;
    public Date getTime(String columnName) throws SQLException;
    public byte[] getBytes(int columnIndex) throws SQLException;
    public byte[] getBytes(String columnName) throws SQLException;
    public Object getObject(int columnIndex) throws SQLException;
    public Object getObject(String columnName) throws SQLException;
    
    public MetaData[] getMetaData() throws SQLException;
}