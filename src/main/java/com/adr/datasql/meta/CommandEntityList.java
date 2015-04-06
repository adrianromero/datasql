//    Data Command is a light JDBC wrapper.
//    Copyright (C) 2015 Adrián Romero Corchado.
//
//    This file is part of Data Command
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
public class CommandEntityList {
    
    private final String name;
    private final String[] fields;    
    private final String[] criteria;    
    private final StatementOrder[] order;    
    
    public CommandEntityList(String name, String[] fields) {
        this(name, fields, null, null);
    }
    
    public CommandEntityList(String name, String[] fields, String[] criteria, StatementOrder[] order) {
        this.name = name;
        this.fields = fields;
        this.criteria = criteria;
        this.order = order;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the fields
     */
    public String[] getFields() {
        return fields;
    }

    /**
     * @return the criteria
     */
    public String[] getCriteria() {
        return criteria;
    }

    /**
     * @return the order
     */
    public StatementOrder[] getOrder() {
        return order;
    }
}
