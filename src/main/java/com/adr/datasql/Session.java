//    Data SQL is a light JDBC wrapper.
//    Copyright (C) 2014-2015 Adrian Romero Corchado.
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

import com.adr.datasql.link.DataLink;
import com.adr.datasql.link.DataLinkException;
import com.adr.datasql.link.DataLinkFactory;
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
 * @see Connection
 * @see DataSource
 * @since 1.0.0
 */
public class Session implements AutoCloseable {

    /**
     * Database connection instance 
     */
    private final DataLink link;
    
   
    /**
     *
     * @param factory
     * @throws DataLinkException
     */
    public Session(DataLinkFactory factory) throws DataLinkException {
        link = factory.getDataLink();
    }

    /**
     *
     * @param statement The 
     * @return Either the row count for update statements or 0 for statements
     * that return nothing
     * @throws DataLinkException
     */
    public final int exec(StatementExec<?> statement) throws DataLinkException {
        return statement.exec(link, null);
    }
    
    /**
     *
     * @param <P>
     * @param statement
     * @param params
     * @return
     * @throws DataLinkException
     */
    public final <P> int exec(StatementExec<P[]> statement, P... params) throws DataLinkException {       
        return statement.exec(link, params);
    }
    
    /**
     *
     * @param <P>
     * @param statement
     * @param params
     * @return
     * @throws DataLinkException
     */
    public final <P> int exec(StatementExec<P> statement, P params) throws DataLinkException {
        return statement.exec(link, params);
    }
    
    /**
     *
     * @param <R>
     * @param statement
     * @return
     * @throws DataLinkException
     */
    public final <R> R find(StatementFind<R, ?> statement) throws DataLinkException {
        return statement.find(link, null);
    }
    
    /**
     *
     * @param <R>
     * @param <P>
     * @param statement
     * @param params
     * @return
     * @throws DataLinkException
     */
    public final <R, P> R find(StatementFind<R, P[]> statement, P... params) throws DataLinkException {
        return statement.find(link, params);
    }
    
    /**
     *
     * @param <R>
     * @param <P>
     * @param statement
     * @param params
     * @return
     * @throws DataLinkException
     */
    public final <R, P> R find(StatementFind<R, P> statement, P params) throws DataLinkException {
        return statement.find(link, params);
    }

    /**
     *
     * @param <R>
     * @param statement
     * @return
     * @throws DataLinkException
     */
    public final <R> List<R> query(StatementQuery<R, ?> statement) throws DataLinkException {
        return statement.query(link, null);
    }
    
    /**
     *
     * @param <R>
     * @param <P>
     * @param statement
     * @param params
     * @return
     * @throws DataLinkException
     */
    public final <R, P> List<R> query(StatementQuery<R, P[]> statement, P... params) throws DataLinkException {
        return statement.query(link, params);
    }
    
    /**
     *
     * @param <R>
     * @param <P>
     * @param statement
     * @param params
     * @return
     * @throws DataLinkException
     */
    public final <R, P> List<R> query(StatementQuery<R, P> statement, P params) throws DataLinkException {
        return statement.query(link, params);
    }

    @Override
    public final void close() throws DataLinkException {
        link.close();
    }  
}
