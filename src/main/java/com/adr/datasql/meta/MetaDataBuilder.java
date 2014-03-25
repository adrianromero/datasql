//    Data SQL is a light JDBC wrapper.
//    Copyright (C) 2012-2014 Adrián Romero Corchado.
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

import com.adr.datasql.Kind;

/**
 *
 * @author adrian
 */
public class MetaDataBuilder {
    
    protected String name = null;
    protected Kind kind = Kind.STRING;
    
    public static MetaDataBuilder create() {
        return new MetaDataBuilder();
    }
    
    public MetaData build() {
        return new MetaData(name, kind);
    }
 
    public MetaDataBuilder name(String name) {
        this.name = name;
        return this;
    }
    
    public MetaDataBuilder kind(Kind kind) {
        this.kind = kind;
        return this;
    }      
}
