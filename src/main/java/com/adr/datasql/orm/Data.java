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

package com.adr.datasql.orm;

import com.adr.datasql.meta.Field;
import com.adr.datasql.meta.Definition;
import com.adr.datasql.KindParameters;
import com.adr.datasql.KindResults;
import com.adr.datasql.Parameters;
import com.adr.datasql.Results;
import java.sql.SQLException;

/**
 *
 * @author adrian
 * @param <P>
 */
public abstract class Data<P> implements Parameters<P>, Results<P> {
    
    private final Definition definition;
    
    public Data(Definition definition) {
        this.definition = definition;
    }
    
    public Definition getDefinition() {
        return definition;
    }
    
    protected abstract Object getValue(Field f, P param) throws SQLException;
    protected abstract void setValue(Field f, P param, Object value) throws SQLException;
    protected abstract P create() throws SQLException;
    
    public Object getKey(P param) throws SQLException {
        for (Field f : definition.getFields()) {  
            if (f.isKey()) {
                return getValue(f, param);
            }
        }     
        return null;
    }
    
    public void setKey(P param, Object key) throws SQLException {
        for (Field f : definition.getFields()) {  
            if (f.isKey()) {
                setValue(f, param, key);
            }
        }           
    }

    @Override
    public void write(KindParameters dp, P param) throws SQLException {
        for (Field f : definition.getFields()) {           
            f.getKind().set(dp, f.getName(), getValue(f, param));              
        }
    }     

    @Override
    public P read(KindResults dp) throws SQLException {
        P param = create();
        for (Field f : definition.getFields()) {           
            setValue(f, param, f.getKind().get(dp, f.getName()));              
        }
        return param;
    }
}
