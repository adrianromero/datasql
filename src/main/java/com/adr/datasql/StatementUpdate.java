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
import java.sql.SQLException;

/**
 *
 * @author adrian
 */
public class StatementUpdate extends Statement {

    private int rows = 0;

    public StatementUpdate(Connection conn, String sql) {
        super(conn, sql);
    }

    public StatementUpdate(Connection conn, String sql, String... params) {
        super(conn, sql, params);
    }

    @Override
    protected final void openStmt() throws SQLException {
        rows = 0;
        rows = stmt.executeUpdate();
    }

    @Override
    protected final void closeStmt() throws SQLException {
        rows = 0;
    }
    
    public int getRows() {
        return rows;
    }

    public int exec() throws SQLException {
        open();
        return getRowsAndClose();
    }

    public int exec(Parameters params) throws SQLException {
        open(params);
        return getRowsAndClose();
    }

    public int exec(String... param) throws SQLException {
        open(param);
        return getRowsAndClose();
    }

    public int exec(KindValue... param) throws SQLException {
        open(param);
        return getRowsAndClose();
    }

    private int getRowsAndClose() throws SQLException {
        int execrows = getRows();
        close();
        return execrows;
    }
}
