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
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    @Override
    protected void setValue(Field f, P param, Object value) throws SQLException {
        try {          
            Method m = param.getClass().getMethod(f.getSetterName());       
            Class<?>[] types = m.getParameterTypes();
            
            if (types.length != 1) {
                throw new SQLException("Setter methods must have exactly one parameters.");
            }
            
            if (value == null && types[0].isPrimitive()) {
                return; // should not assign null to a primitive field.
            }

            m.invoke(param, value);              
        } catch (IllegalAccessException 
                |IllegalArgumentException 
                |InvocationTargetException 
                |NoSuchMethodException 
                |SecurityException ex) {
            throw new SQLException (ex);
        }          
    }  

    @Override
    protected P create() throws SQLException {
        try {
            return (P) Class.forName(getDefinition().getClassName()).newInstance();
        } catch (ClassNotFoundException 
                | InstantiationException 
                | IllegalAccessException 
                | ClassCastException ex) {
            throw new SQLException (ex);
        }
    }
}
