//    Data SQL is a light JDBC wrapper.
//    Copyright (C) 2012 Adrián Romero Corchado.
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
