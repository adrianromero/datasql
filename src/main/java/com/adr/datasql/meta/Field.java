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

import com.adr.datasql.data.MetaData;
import com.adr.datasql.Kind;

/**
 *
 * @author adrian
 */
public class Field extends MetaData {
    
    private boolean key = false;
    private boolean filter = false;
    
    public Field() {    
    }
    
    public Field(String name, Kind kind) {
        super(name, kind);
    }    
    
    public Field(String name, Kind kind, boolean key) {
        super(name, kind);
        this.key = key;
    }
    
    public Field(String name, Kind kind, boolean key, boolean filter) {
        super(name, kind);
        this.key = key;
        this.filter = filter;
    }

    public boolean isKey() {
        return key;
    }
    
    public void setKey(boolean key) {
        this.key = key;
    }
    
    public boolean isFilter() {
        return filter;
    }
    
    public void setFilter(boolean filter) {
        this.filter = filter;
    }
    
    @Override
    public String toString() {
        return "Field {name: " + getName() + ", kind: " + getKind().toString() + "}";
    }
}
