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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;

/**
 *
 * @author adrian
 * @param <P>
 */
public class DataPojo<P> extends Data<P> {
    
    public DataPojo(Definition definition) {
        super(definition);
    }

    @Override
    protected Object getValue(Field f, P param) throws SQLException {
        
        try {          
            Method m = param.getClass().getMethod(f.getGetterName());       
            return m.invoke(param);              
        } catch (IllegalAccessException 
                |IllegalArgumentException 
                |InvocationTargetException 
                |NoSuchMethodException 
                |SecurityException ex) {
            throw new SQLException (ex);
        }        
    }        
}
