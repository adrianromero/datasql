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
public interface KindParameters {

    public void setInt(int paramIndex, Integer iValue) throws DataLinkException;
    public void setInt(String paramName, Integer iValue) throws DataLinkException;
    public void setString(int paramIndex, String sValue) throws DataLinkException;
    public void setString(String paramName, String sValue) throws DataLinkException;
    public void setDouble(int paramIndex, Double dValue) throws DataLinkException;
    public void setDouble(String paramName, Double dValue) throws DataLinkException;
    public void setBigDecimal(int paramIndex, BigDecimal value) throws DataLinkException;
    public void setBigDecimal(String paramName, BigDecimal value) throws DataLinkException;
    public void setBoolean(int paramIndex, Boolean bValue) throws DataLinkException;
    public void setBoolean(String paramName, Boolean bValue) throws DataLinkException;
    public void setTimestamp(int paramIndex, Date dValue) throws DataLinkException;
    public void setTimestamp(String paramName, Date dValue) throws DataLinkException;
    public void setDate(int paramIndex, Date dValue) throws DataLinkException;
    public void setDate(String paramName, Date dValue) throws DataLinkException;
    public void setTime(int paramIndex, Date dValue) throws DataLinkException;
    public void setTime(String paramName, Date dValue) throws DataLinkException;
    public void setBytes(int paramIndex, byte[] value) throws DataLinkException;
    public void setBytes(String paramName, byte[] value) throws DataLinkException;
    public void setObject(int paramIndex, Object value) throws DataLinkException;
    public void setObject(String paramName, Object value) throws DataLinkException;
    
    public MetaData[] getMetaData() throws DataLinkException;
}