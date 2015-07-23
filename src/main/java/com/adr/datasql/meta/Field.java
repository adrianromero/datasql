//    Data SQL is a light JDBC wrapper.
//    Copyright (C) 2014-2015 Adrián Romero Corchado.
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
public class Field {
    
    protected Kind kind;
    protected String name;
    private boolean load;
    private boolean save;
    
    protected boolean key;
    
    public Field() {
        this(null, null, false);
    }
    
    public Field(String name, Kind kind) {
        this(name, kind, false);
    }    
    
    public Field(String name, Kind kind, boolean key) {
        this.name = name;
        this.kind = kind;
        this.key = key;
        this.load = true;
        this.save = true;
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

    public boolean isLoad() {
        return load;
    }

    public void setLoad(boolean load) {
        this.load = load;
    }

    public boolean isSave() {
        return save;
    }

    public void setSave(boolean save) {
        this.save = save;
    }
    
    public boolean isKey() {
        return key;
    }
    
    public void setKey(boolean key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "Field {name: " + getName() + ", kind: " + getKind().toString() + "}";
    }
}
