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

package com.adr.datasql.meta;

/**
 *
 * @author adrian
 */
public class StatementOrder {
    
    public static enum Sort {
        ASC(" ASC"),
        DESC(" DESC");
        
        private final String sql;
        Sort(String sql) {
            this.sql = sql;
        }
        public String toSQL() {
            return sql;
        }
        @Override
        public String toString() {
            return sql;
        }  
    }
    
    private final String name;
    private final Sort sort;
    
    public StatementOrder(String name, Sort sort) {
        this.name = name;
        this.sort = sort;
    }
    public String getName() {
        return name;
    }
    public Sort getSort() {
        return sort;
    }    
}
