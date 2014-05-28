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

import com.adr.datasql.meta.Entity;
import com.adr.datasql.Kind;
import com.adr.datasql.meta.MetaData;
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
    
    private final Class<? extends P> clazz;
    private final Map<String, Method> setters;
    private final Map<String, Method> getters;
    
    public DataPojo(Class<? extends P> clazz, Entity definition) {
        super(definition);

        this.clazz = clazz;

        setters = new HashMap<String, Method>();
        getters = new HashMap<String, Method>();
        Method[] methods = clazz.getMethods();
        for (MetaData f: definition.getMetaDatas()) {
            for (Method m: methods) {
                if (m.getParameterTypes().length == 1 && m.getName().equals(getSetterName(f))) {
                    setters.put(f.getName(), m);
                }
                if (m.getParameterTypes().length == 0 && m.getName().equals(getGetterName(f))) {
                    getters.put(f.getName(), m);
                }
            }
        }
    }

    @Override
    public Object getValue(MetaData md, P param) throws SQLException {
        
        try {          
            Method m = getters.get(md.getName()); 
            if (m == null) {
              throw new SQLException ("Getter not found for field: " + md.getName() + ".");  
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
    public void setValue(MetaData md, P param, Object value) throws SQLException {
        try {          
            Method m = setters.get(md.getName());    
            if (m == null) {
              throw new SQLException ("Setter not found for field: " + md.getName() + ".");  
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
    public P create() throws SQLException {
        try {
            return clazz.newInstance();
        } catch (InstantiationException 
                | IllegalAccessException ex) {
            throw new SQLException (ex);
        }
    }
    
    private String getSetterName(MetaData md) {
        return "set" + Character.toUpperCase(md.getName().charAt(0)) + md.getName().substring(1);
    }
    
    private String getGetterName(MetaData md) {
        return (Kind.BOOLEAN == md.getKind() ? "is" : "get") + Character.toUpperCase(md.getName().charAt(0)) + md.getName().substring(1);
    }
}
