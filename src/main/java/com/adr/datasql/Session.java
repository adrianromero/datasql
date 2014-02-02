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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author adrian
 */
public class Session {
    
    private static final Logger logger = Logger.getLogger(Session.class.getName());   
    
    private final Connection c;
    
    public Session(Connection c) {
        this.c = c;
    }
    
    public Connection getConnection() {
        return c;
    }
    
    public int exec(Query<?, ?> query) throws SQLException {
        return exec(c, query, null);
    }
    
    public <P> int exec(Query<?, P[]> query, P... params) throws SQLException {       
        return exec(c, query, params);
    }
    
    public <P> int exec(Query<?, P> query, P params) throws SQLException {
        return exec(c, query, params);
    }
    
    public <R> R find(Query<R, ?> query) throws SQLException {
        return find(c, query, null);
    }
    
    public <R, P> R find(Query<R, P[]> query, P... params) throws SQLException {
        return find(c, query, params);
    }
    
    public <R, P> R find(Query<R, P> query, P params) throws SQLException {
        return find(c, query, params);
    }

    
    public <R> List<R> list(Query<R, ?> query) throws SQLException {
        return list(c, query, null);
    }
    
    public <R, P> List<R> list(Query<R, P[]> query, P... params) throws SQLException {
        return list(c, query, params);
    }
    
    public <R, P> List<R> list(Query<R, P> query, P params) throws SQLException {
        return list(c, query, params);
    }
    
    public static final <P> int exec(Connection conn, Query<?, P> query, P params) throws SQLException {
        
        logger.log(Level.INFO, "Executing prepared SQL: {0}", query.getSQL());

        try (PreparedStatement stmt = conn.prepareStatement(query.getSQL())) {
            KindParameters kp = new KindParametersMap(stmt, query.getParamNames());

            if (query.getParameters() != null) {
                query.getParameters().write(kp, params);
            }  
            return stmt.executeUpdate();
        }
    }
    
    public static final <R, P> R find(Connection conn, Query<R, P> query, P params) throws SQLException {
        
        logger.log(Level.INFO, "Executing prepared SQL: {0}", query.getSQL());

        try (PreparedStatement stmt = conn.prepareStatement(query.getSQL())) {
            KindParameters kp = new KindParametersMap(stmt, query.getParamNames());

            if (query.getParameters() != null) {
                query.getParameters().write(kp, params);
            }  
            try (ResultSet resultset = stmt.executeQuery()) {
                KindResults kr = new KindResultsMap(resultset);
                
                if (resultset.next()) {
                    return query.getResults().read(kr);
                } else {
                    return null;
                }
            }
        }        
    }
    
    public static final <R, P> List<R> list(Connection conn, Query<R, P> query, P params) throws SQLException {
        
        logger.log(Level.INFO, "Executing prepared SQL: {0}", query.getSQL());

        try (PreparedStatement stmt = conn.prepareStatement(query.getSQL())) {
            KindParameters kp = new KindParametersMap(stmt, query.getParamNames());

            if (query.getParameters() != null) {
                query.getParameters().write(kp, params);
            }  
            try (ResultSet resultset = stmt.executeQuery()) {
                KindResults kr = new KindResultsMap(resultset);
                
                List<R> l = new ArrayList<R>();
                while (resultset.next()) {
                    l.add(query.getResults().read(kr));
                }
                return l;
            }
        }        
    }      
}
