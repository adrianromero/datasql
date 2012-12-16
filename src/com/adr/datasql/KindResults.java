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
    public byte[] getBytes(int columnIndex) throws SQLException;
    public byte[] getBytes(String columnName) throws SQLException;
}