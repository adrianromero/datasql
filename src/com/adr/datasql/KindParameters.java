//    Data SQL is a light JDBC wrapper.
//    Copyright (C) 2011 Adrián Romero Corchado.
//
//    This file is part of Data SQL
//
//    Data SQL is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//    Data SQL is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with Task Executor. If not, see <http://www.gnu.org/licenses/>.

package com.adr.datasql;

import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author  adrian
 */
public interface KindParameters {

    public void setInt(int paramIndex, Integer iValue) throws SQLException;
    public void setInt(String paramName, Integer iValue) throws SQLException;
    public void setString(int paramIndex, String sValue) throws SQLException;
    public void setString(String paramName, String sValue) throws SQLException;
    public void setDouble(int paramIndex, Double dValue) throws SQLException;
    public void setDouble(String paramName, Double dValue) throws SQLException;
    public void setBoolean(int paramIndex, Boolean bValue) throws SQLException;
    public void setBoolean(String paramName, Boolean bValue) throws SQLException;
    public void setTimestamp(int paramIndex, Date dValue) throws SQLException;
    public void setTimestamp(String paramName, Date dValue) throws SQLException;
    public void setDate(int paramIndex, Date dValue) throws SQLException;
    public void setDate(String paramName, Date dValue) throws SQLException;
    public void setBytes(int paramIndex, byte[] value) throws SQLException;
    public void setBytes(String paramName, byte[] value) throws SQLException;
}