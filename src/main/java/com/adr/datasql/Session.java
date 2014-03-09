//    Data SQL is a light JDBC wrapper.
//    Copyright (C) 2014 Adrián Romero Corchado.
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
import java.util.List;

/**
 * A Session with a specific database. All functionality provided by Data SQL is
 * provided by a Session instance. It wraps a database connection that must be 
 * provided at the moment of creating a new Session object and follows the same 
 * life cycle as the database connection wrapped.
 * <p>
 * The following code shows an example of how to create and use a new Session
 * instance using a poolable DataSource. 
 * <pre>
 * try (Session session = new Session(getDataSource().getConnection())) {
 *   // Here goes your database code
 * }
 * </pre>
 *
 * @author adrianromero
 * @see ORMSession
 * @see Connection
 * @see DataSource
 * @since 1.0.0
 */
public class Session implements AutoCloseable {

    /**
     * Database connection instance 
     */
    protected final Connection c;
    
    protected int maxfetch = 500;
    
    /**
     *
     * @param c
     */
    public Session(Connection c) {
        this.c = c;
    }
    
    /**
     *
     * @return The database connection
     */
    public final Connection getConnection() {
        return c;
    }
    
    public final int getMaxFetch() {
        return maxfetch;
    }
    
    public final void setMaxFetch(int maxfetch) {
        this.maxfetch = maxfetch;
    }
    
    /**
     *
     * @param statement The 
     * @return Either the row count for update statements or 0 for statements
     * that return nothing
     * @throws SQLException
     */
    public final int exec(StatementExec<?> statement) throws SQLException {
        return statement.exec(c, null);
    }
    
    /**
     *
     * @param <P>
     * @param statement
     * @param params
     * @return
     * @throws SQLException
     */
    public final <P> int exec(StatementExec<P[]> statement, P... params) throws SQLException {       
        return statement.exec(c, params);
    }
    
    /**
     *
     * @param <P>
     * @param statement
     * @param params
     * @return
     * @throws SQLException
     */
    public final <P> int exec(StatementExec<P> statement, P params) throws SQLException {
        return statement.exec(c, params);
    }
    
    /**
     *
     * @param <R>
     * @param statement
     * @return
     * @throws SQLException
     */
    public final <R> R find(StatementFind<R, ?> statement) throws SQLException {
        return statement.find(c, null);
    }
    
    /**
     *
     * @param <R>
     * @param <P>
     * @param statement
     * @param params
     * @return
     * @throws SQLException
     */
    public final <R, P> R find(StatementFind<R, P[]> statement, P... params) throws SQLException {
        return statement.find(c, params);
    }
    
    /**
     *
     * @param <R>
     * @param <P>
     * @param statement
     * @param params
     * @return
     * @throws SQLException
     */
    public final <R, P> R find(StatementFind<R, P> statement, P params) throws SQLException {
        return statement.find(c, params);
    }

    /**
     *
     * @param <R>
     * @param statement
     * @return
     * @throws SQLException
     */
    public final <R> List<R> query(StatementQuery<R, ?> statement) throws SQLException {
        return statement.query(c, null);
    }
    
    /**
     *
     * @param <R>
     * @param <P>
     * @param statement
     * @param params
     * @return
     * @throws SQLException
     */
    public final <R, P> List<R> query(StatementQuery<R, P[]> statement, P... params) throws SQLException {
        return statement.query(c, params);
    }
    
    /**
     *
     * @param <R>
     * @param <P>
     * @param statement
     * @param params
     * @return
     * @throws SQLException
     */
    public final <R, P> List<R> query(StatementQuery<R, P> statement, P params) throws SQLException {
        return statement.query(c, params);
    }

    @Override
    public final void close() throws SQLException {
        c.close();
    }  
}
