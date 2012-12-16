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
