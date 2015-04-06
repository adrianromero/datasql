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
public abstract class CommandEntityAbstract {
    
    private final String name;
    private final String[] keys;
    private final String[] fields;
    
    public CommandEntityAbstract(String name, String[] keys, String[] fields) {
        this.name = name;
        this.keys = keys;
        this.fields = fields;
    }

    /**
     * @return the name
     */
    public final String getName() {
        return name;
    }

    /**
     * @return the keys
     */
    public final String[] getKeys() {
        return keys;
    }

    /**
     * @return the fields
     */
    public final String[] getFields() {
        return fields;
    }
     
}
