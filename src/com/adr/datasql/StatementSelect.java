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

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author  adrianromero
 */
public class StatementSelect<T> extends Statement {

    private ResultSet resultset;
    private KindResults kr = new KindResultsMap();

    public StatementSelect(Connection conn, String sql) {
        super(conn, sql);
    }
    public StatementSelect(Connection conn, String sql, String... params) {
        super(conn, sql, params);
    }

    private final class KindResultsMap implements KindResults {
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
        public byte[] getBytes(int columnIndex) throws SQLException {
            return resultset.getBytes(columnIndex);
        }
        @Override
        public byte[] getBytes(String columnName) throws SQLException {
            return resultset.getBytes(columnName);
        }
    }

    @Override
    protected void openStmt() throws SQLException {
        resultset = stmt.executeQuery();
    }

    @Override
    protected void closeStmt() throws SQLException {
        resultset.close();
        resultset = null;
     }

    public final List<T> readall(Results<T> results) throws SQLException {

        List<T> l = new ArrayList<T>();
        while (resultset.next()) {
            l.add(results.read(kr));
        }
        return l;
    }

    public final T read(Results<T> results) throws SQLException {

        if (resultset.next()) {
            return results.read(kr);
        } else {
            return null;
        }
    }

    public final T find(Results<T> results) throws SQLException {
        open();
        return readAndClose(results);
    }

    public final T find(Results<T> results, Parameters params) throws SQLException {
        open(params);
        return readAndClose(results);
    }

    public final T find(Results<T> results, String... param) throws SQLException {
        open(param);
        return readAndClose(results);
    }

    public final T find(Results<T> results, KindValue... param) throws SQLException {
        open(param);
        return readAndClose(results);
    }

    private T readAndClose(Results<T> results) throws SQLException {
        T value = read(results);
        close();
        return value;
    }

    public final List<T> list(Results<T> results) throws SQLException {
        open();
        return readallAndClose(results);
    }

    public final List<T> list(Results<T> results, Parameters params) throws SQLException {
        open(params);
        return readallAndClose(results);
    }

    public final List<T> list(Results<T> results, String... param) throws SQLException {
        open(param);
        return readallAndClose(results);
    }

    public final List<T> list(Results<T> results, KindValue... param) throws SQLException {
        open(param);
        return readallAndClose(results);
    }

    private List<T> readallAndClose(Results<T> results) throws SQLException {
        List<T> values = readall(results);
        close();
        return values;
    }
}

