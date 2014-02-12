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
import java.util.logging.Logger;

/**
 *
 * @author adrian
 */
public class Session implements AutoCloseable {
    
    private static final Logger logger = Logger.getLogger(Session.class.getName());   
    
    protected final Connection c;
    
    public Session(Connection c) {
        this.c = c;
    }
    
    public final Connection getConnection() {
        return c;
    }
    
    public final int exec(ProcExec<?> proc) throws SQLException {
        return proc.exec(c, null);
    }
    
    public final <P> int exec(ProcExec<P[]> proc, P... params) throws SQLException {       
        return proc.exec(c, params);
    }
    
    public final <P> int exec(ProcExec<P> proc, P params) throws SQLException {
        return proc.exec(c, params);
    }
    
    public final <R> R find(ProcFind<R, ?> proc) throws SQLException {
        return proc.find(c, null);
    }
    
    public final <R, P> R find(ProcFind<R, P[]> proc, P... params) throws SQLException {
        return proc.find(c, params);
    }
    
    public final <R, P> R find(ProcFind<R, P> proc, P params) throws SQLException {
        return proc.find(c, params);
    }

    public final <R> List<R> list(ProcList<R, ?> proc) throws SQLException {
        return proc.list(c, null);
    }
    
    public final <R, P> List<R> list(ProcList<R, P[]> proc, P... params) throws SQLException {
        return proc.list(c, params);
    }
    
    public final <R, P> List<R> list(ProcList<R, P> proc, P params) throws SQLException {
        return proc.list(c, params);
    }

    @Override
    public final void close() throws SQLException {
        c.close();
    }  
}
