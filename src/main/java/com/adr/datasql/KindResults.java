//    Data SQL is a light JDBC wrapper.
//    Copyright (C) 2012-2015 Adrián Romero Corchado.
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

import com.adr.datasql.data.MetaData;
import com.adr.datasql.link.DataLinkException;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author  adrian
 */
public interface KindResults {

    public Integer getInt(int columnIndex) throws DataLinkException;
    public Integer getInt(String columnName) throws DataLinkException;
    public String getString(int columnIndex) throws DataLinkException;
    public String getString(String columnName) throws DataLinkException;
    public Double getDouble(int columnIndex) throws DataLinkException;
    public Double getDouble(String columnName) throws DataLinkException;
    public BigDecimal getBigDecimal(int columnIndex) throws DataLinkException;
    public BigDecimal getBigDecimal(String columnName) throws DataLinkException;
    public Boolean getBoolean(int columnIndex) throws DataLinkException;
    public Boolean getBoolean(String columnName) throws DataLinkException;
    public Date getTimestamp(int columnIndex) throws DataLinkException;
    public Date getTimestamp(String columnName) throws DataLinkException;
    public Date getDate(int columnIndex) throws DataLinkException;
    public Date getDate(String columnName) throws DataLinkException;
    public Date getTime(int columnIndex) throws DataLinkException;
    public Date getTime(String columnName) throws DataLinkException;
    public byte[] getBytes(int columnIndex) throws DataLinkException;
    public byte[] getBytes(String columnName) throws DataLinkException;
    public Object getObject(int columnIndex) throws DataLinkException;
    public Object getObject(String columnName) throws DataLinkException;
    
    public MetaData[] getMetaData() throws DataLinkException;
}