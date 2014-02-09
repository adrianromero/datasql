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
 */
public class SQL {

    private String sql;
    private String[] paramnames;  
    
    public SQL(String sql, String... paramnames) {
        init(sql, paramnames);
    }
    
    protected SQL() {
    }
    
    protected final void init(String sql, String... paramnames) {
        this.sql = sql;
        this.paramnames = paramnames == null ? new String[0] : paramnames;        
    }
    
    public final String getSQL() {
        return sql;
    }

    public final String[] getParamNames() {
        return paramnames;
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
