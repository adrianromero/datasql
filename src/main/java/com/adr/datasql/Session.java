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

import com.adr.datasql.data.ParametersStringArray;
import com.adr.datasql.data.ParametersStringMap;
import com.adr.datasql.data.ResultsStringArray;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    
    public int exec(Query query) throws SQLException {
        return exec(c, query, null, null);
    }
    
    public int exec(Query query, String... params) throws SQLException {       
        return exec(c, query, new ParametersStringArray(), params);
    }
    
    public int exec(Query query, Map<String, String> params) throws SQLException {
        return exec(c, query, new ParametersStringMap(), params);
    }
    
    public String[] find(Query query) throws SQLException {
        return find(c, query, new ResultsStringArray(), null, null);
    }
    
    public String[] find(Query query, String... params) throws SQLException {
        return find(c, query, new ResultsStringArray(), new ParametersStringArray(), params);
    }
    
    public String[] find(Query query, Map<String, String> params) throws SQLException {
        return find(c, query, new ResultsStringArray(), new ParametersStringMap(), params);
    }
    
    public List<String[]> list(Query query) throws SQLException {
        return list(c, query, new ResultsStringArray(), null, null);
    }
    
    public List<String[]> list(Query query, String... params) throws SQLException {
        return list(c, query, new ResultsStringArray(), new ParametersStringArray(), params);
    }
    
    public List<String[]> list(Query query, Map<String, String> params) throws SQLException {
        return list(c, query, new ResultsStringArray(), new ParametersStringMap(), params);
    }
    
    public static final <P> int exec(Connection conn, Query sql, Parameters<P> paramwrite, P params) throws SQLException {
        
        logger.log(Level.INFO, "Executing prepared SQL: {0}", sql.getSQL());

        try (PreparedStatement stmt = conn.prepareStatement(sql.getSQL())) {
            KindParameters kp = new KindParametersMap(stmt, sql.getParamNames());

            if (paramwrite != null) {
                paramwrite.write(kp, params);
            }  
            return stmt.executeUpdate();
        }
    }
    
    public static final <P, T> T find(Connection conn, Query sql, Results<T> resultread, Parameters<P> paramwrite, P params) throws SQLException {
        
        logger.log(Level.INFO, "Executing prepared SQL: {0}", sql.getSQL());

        try (PreparedStatement stmt = conn.prepareStatement(sql.getSQL())) {
            KindParameters kp = new KindParametersMap(stmt, sql.getParamNames());

            if (paramwrite != null) {
                paramwrite.write(kp, params);
            }  
            try (ResultSet resultset = stmt.executeQuery()) {
                KindResults kr = new KindResultsMap(resultset);
                
                if (resultset.next()) {
                    return resultread.read(kr);
                } else {
                    return null;
                }
            }
        }        
    }
    
    public static final <P, T> List<T> list(Connection conn, Query sql, Results<T> resultread, Parameters<P> paramwrite, P params) throws SQLException {
        
        logger.log(Level.INFO, "Executing prepared SQL: {0}", sql.getSQL());

        try (PreparedStatement stmt = conn.prepareStatement(sql.getSQL())) {
            KindParameters kp = new KindParametersMap(stmt, sql.getParamNames());

            if (paramwrite != null) {
                paramwrite.write(kp, params);
            }  
            try (ResultSet resultset = stmt.executeQuery()) {
                KindResults kr = new KindResultsMap(resultset);
                
                List<T> l = new ArrayList<T>();
                while (resultset.next()) {
                    l.add(resultread.read(kr));
                }
                return l;
            }
        }        
    }      
}
