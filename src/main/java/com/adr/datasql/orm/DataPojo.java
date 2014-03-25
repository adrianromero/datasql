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
import com.adr.datasql.meta.Entity;
import com.adr.datasql.Kind;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author adrian
 * @param <P>
 */
public class DataPojo<P> extends Data<P> {
    
    private Class<P> clazz;
    private Map<String, Method> setters;
    private Map<String, Method> getters;
    
    public DataPojo(Entity definition) {
        super(definition);
        try {
            clazz = (Class<P>) Class.forName(getClassName(definition));
            
            setters = new HashMap<String, Method>();
            getters = new HashMap<String, Method>();
            Method[] methods = clazz.getMethods();
            for (Field f: definition.getFields()) {
                for (Method m: methods) {
                    if (m.getParameterTypes().length == 1 && m.getName().equals(getSetterName(f))) {
                        setters.put(f.getName(), m);
                    }
                    if (m.getParameterTypes().length == 0 && m.getName().equals(getGetterName(f))) {
                        getters.put(f.getName(), m);
                    }
                }
            }
            
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected Object getValue(Field f, P param) throws SQLException {
        
        try {          
            Method m = getters.get(f.getName()); 
            if (m == null) {
              throw new SQLException ("Getter not found for field: " + f.getName() + ".");  
            }
            return m.invoke(param);              
        } catch (IllegalAccessException 
                |IllegalArgumentException 
                |InvocationTargetException  
                |SecurityException ex) {
            throw new SQLException (ex);
        }        
    } 
    
    @Override
    protected void setValue(Field f, P param, Object value) throws SQLException {
        try {          
            Method m = setters.get(f.getName());    
            if (m == null) {
              throw new SQLException ("Setter not found for field: " + f.getName() + ".");  
            }            
            Class<?>[] types = m.getParameterTypes();
            
            if (value == null && types[0].isPrimitive()) {
                return; // should not assign null to a primitive field.
            }

            m.invoke(param, value);              
        } catch (IllegalAccessException 
                |IllegalArgumentException 
                |InvocationTargetException 
                |SecurityException ex) {
            throw new SQLException (ex);
        }          
    }  

    @Override
    protected P create() throws SQLException {
        try {
            return clazz.newInstance();
        } catch (InstantiationException 
                | IllegalAccessException ex) {
            throw new SQLException (ex);
        }
    }
    
    private String getClassName(Entity definition) {
        return definition.getName().replaceAll("_", ".");
    }
    
    private String getSetterName(Field f) {
        return "set" + Character.toUpperCase(f.getName().charAt(0)) + f.getName().substring(1);
    }
    
    private String getGetterName(Field f) {
        return (Kind.BOOLEAN == f.getKind() ? "is" : "get") + Character.toUpperCase(f.getName().charAt(0)) + f.getName().substring(1);
    }
}
