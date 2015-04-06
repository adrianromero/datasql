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

package com.adr.datasql.data;

import com.adr.datasql.Kind;
import java.util.Arrays;

/**
 *
 * @author adrian
 */
public class MetaData {
    
    protected Kind kind;
    protected String name;
    
    public MetaData() {
        this(null, Kind.STRING);
    }
    
    public MetaData(Kind kind) {
        this(null, kind);
    }
    
    public MetaData(String name, Kind kind) {
        this.name = name;
        this.kind = kind;
    }  
    
    public final String getName() {
        return name;
    }
    
    public final void setName(String name) {
        this.name = name;
    }
    
    public final Kind getKind() {
        return kind;
    } 
    
    public final void setKind(Kind kind) {
        this.kind = kind;
    }
    
    @Override
    public String toString() {
        return "MetaData {name: " + getName() + ", kind: " + getKind().toString() + "}";
    }
    
    public static MetaData[] fromKinds(Kind[] kinds) {
        MetaData[] metadatas = new MetaData[kinds.length];
        for (int i = 0; i < kinds.length; i++) {
            metadatas[i] = new MetaData(kinds[i]);
        }
        return metadatas;
    }
    
    public static String[] getNames(MetaData[] metadatas) {
        if (metadatas == null) {
            return null;
        } else {
            return Arrays.stream(metadatas)
                    .map(m -> m.getName())
                    .toArray(i -> new String[i]);
        }
    }
}
