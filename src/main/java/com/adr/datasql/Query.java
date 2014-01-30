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

/**
 *
 * @author adrian
 * @param <R>
 * @param <P>
 */
public abstract class Query<R, P> {
    
    protected String sql;
    protected String[] paramnames;  
    
    private Parameters<P> parameters = null;
    private Results<R> results = null;

    public final String getSQL() {
        return sql;
    }

    public final String[] getParamNames() {
        return paramnames;
    }   

    /**
     * @return the parameters
     */
    public Parameters<P> getParameters() {
        return parameters;
    }

    /**
     * @param parameters the parameters to set
     */
    public void setParameters(Parameters<P> parameters) {
        this.parameters = parameters;
    }

    /**
     * @return the results
     */
    public Results<R> getResults() {
        return results;
    }

    /**
     * @param results the results to set
     */
    public void setResults(Results<R> results) {
        this.results = results;
    }
    
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(getSQL());
        s.append('[');
        for(int i = 0; i < getParamNames().length; i++) {
            if (i > 0) {
                s.append(", ");
            }
            s.append(getParamNames()[i]);
        }
        s.append(']');
        return s.toString();
    }      
}