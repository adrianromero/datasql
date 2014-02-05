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
 * @param <R>
 * @param <P>
 */
public class Query<R, P> implements ProcExec<P>, ProcFind<R, P>, ProcList<R, P> {
    
    private static final Logger logger = Logger.getLogger(Query.class.getName()); 

    protected String sql;
    protected String[] paramnames;  
    
    private Parameters<P> parameters = null;
    private Results<R> results = null;
    
    public Query(NSQL nsql) {
        this.sql = nsql.getSQL();
        this.paramnames = nsql.getParamNames();
    }
    
    public Query(String sql, String... paramnames) {
        this.sql = sql;
        this.paramnames = paramnames == null ? new String[0] : paramnames;
    }
    
    @Override
    public final int exec(Session s, P params) throws SQLException {
        logger.log(Level.INFO, "Executing prepared SQL: {0}", sql);

        try (PreparedStatement stmt = s.getConnection().prepareStatement(sql)) {
            KindParameters kp = new KindParametersMap(stmt, paramnames);

            if (parameters != null) {
                parameters.write(kp, params);
            }  
            return stmt.executeUpdate();
        }
    }

    @Override
    public R find(Session s, P params) throws SQLException {
        
        logger.log(Level.INFO, "Executing prepared SQL: {0}", sql);

        try (PreparedStatement stmt = s.getConnection().prepareStatement(sql)) {
            KindParameters kp = new KindParametersMap(stmt, paramnames);

            if (parameters != null) {
                parameters.write(kp, params);
            }  
            try (ResultSet resultset = stmt.executeQuery()) {
                KindResults kr = new KindResultsMap(resultset);
                
                if (resultset.next()) {
                    return results.read(kr);
                } else {
                    return null;
                }
            }
        } 
    }
    
    @Override
    public List<R> list(Session s, P params) throws SQLException {
        logger.log(Level.INFO, "Executing prepared SQL: {0}", sql);

        try (PreparedStatement stmt = s.getConnection().prepareStatement(sql)) {
            KindParameters kp = new KindParametersMap(stmt, paramnames);

            if (parameters != null) {
                parameters.write(kp, params);
            }  
            try (ResultSet resultset = stmt.executeQuery()) {
                KindResults kr = new KindResultsMap(resultset);
                
                List<R> l = new ArrayList<R>();
                while (resultset.next()) {
                    l.add(results.read(kr));
                }
                return l;
            }
        }
    }  

    /**
     * @return the parameters
     */
    public Parameters<P> getParameters() {
        return parameters;
    }

    /**
     * @param parameters the parameters to set
     * @return 
     */
    public Query<R, P> setParameters(Parameters<P> parameters) {
        this.parameters = parameters;
        return this;
    }

    /**
     * @return the results
     */
    public Results<R> getResults() {
        return results;
    }

    /**
     * @param results the results to set
     * @return 
     */
    public Query<R, P> setResults(Results<R> results) {
        this.results = results;
        return this;
    }      
}