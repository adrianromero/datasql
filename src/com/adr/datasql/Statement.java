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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author adrian
 */
public abstract class Statement {

    private static final Logger logger = Logger.getLogger(Statement.class.getName());

    private String sql;

    private KindParameters kp;

    protected Connection conn = null;
    protected PreparedStatement stmt = null;

    public Statement(Connection conn, String sql) {
        this.conn = conn;
        this.sql = sql;
        this.kp = new KindParametersMap();
    }

    public Statement(Connection conn, String sql, String... params) {
        this.conn = conn;
        this.sql = sql;
        this.kp = new KindParametersMap(params);
    }

    protected abstract void openStmt() throws SQLException;
    protected abstract void closeStmt() throws SQLException;

    private final class KindParametersMap implements KindParameters {

        private String[] params;

        public KindParametersMap(String[] params) {
            this.params = params;
        }
        public KindParametersMap() {
            params = new String[0];
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
    }

    public final void open() throws SQLException {
        open((Parameters) null);
    }

    public final void open(Parameters params) throws SQLException {

        logger.log(Level.INFO, "Executing prepared SQL: {0}", sql);

        stmt = conn.prepareStatement(sql);

        if (params != null) {
            params.write(kp);
        }

        openStmt();
    }

    public final void open(final String... param) throws SQLException {
        open(new Parameters() {
            @Override
            public void write(KindParameters dp) throws SQLException {
                for(int i = 0; i < param.length; i++) {
                    dp.setString(i + 1, param[i]);
                }
            }
        });
    }

    public final void open(final KindValue... param) throws SQLException {
        open(new Parameters() {
            @Override
            public void write(KindParameters dp) throws SQLException {
                for(int i = 0; i < param.length; i++) {
                    param[i].getKind().set(dp, i + 1, param[i].getValue());
                }
            }
        });
    }

    public final void close() throws SQLException {

        closeStmt();
        if (stmt != null) {
            stmt.close();
            stmt = null;
        }
     }
}
