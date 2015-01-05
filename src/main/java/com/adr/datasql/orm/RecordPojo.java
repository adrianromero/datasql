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

package com.adr.datasql.orm;

import com.adr.datasql.Kind;
import com.adr.datasql.data.MetaData;
import com.adr.datasql.link.DataLinkException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author adrian
 * @param <P>
 */
public class RecordPojo<P> extends RecordAbstract<P> {
    
    private final static Object NULLMETHOD = new Object();
    
    private final Class<? extends P> clazz;
    private final Map<String, Object> setters = new HashMap<>();
    private final Map<String, Object> getters = new HashMap<>();
    
    public RecordPojo(Class<? extends P> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object getValue(MetaData md, P param) throws DataLinkException {
        
        try {          
            return findGetter(md).invoke(param);              
        } catch (IllegalAccessException 
                |IllegalArgumentException 
                |InvocationTargetException  
                |SecurityException ex) {
            throw new DataLinkException (ex);
        }        
    } 
    
    @Override
    public void setValue(MetaData md, P param, Object value) throws DataLinkException {
        try {          
            Method m = findSetter(md);               
            Class<?>[] types = m.getParameterTypes();           
            if (value == null && types[0].isPrimitive()) {
                return; // should not assign null to a primitive field.
            }
            m.invoke(param, value);              
        } catch (IllegalAccessException 
                |IllegalArgumentException 
                |InvocationTargetException 
                |SecurityException ex) {
            throw new DataLinkException (ex);
        }          
    }  

    @Override
    public P create() throws DataLinkException {
        try {
            return clazz.newInstance();
        } catch (InstantiationException 
                | IllegalAccessException ex) {
            throw new DataLinkException (ex);
        }
    }
    
    private Method findSetter(MetaData md) throws DataLinkException {
        Object method = setters.get(md.getName()); 
        if (method == NULLMETHOD) {
            throw new DataLinkException ("Setter not found for field: " + md.getName() + ".");  
        } else if (method == null) {
            for (Method m: clazz.getMethods()) {              
                if (m.getParameterTypes().length == 1 && m.getName().equals(getSetterName(md))) {
                    setters.put(md.getName(), m);
                    return m;
                }
            }
            setters.put(md.getName(), NULLMETHOD);
            throw new DataLinkException ("Setter not found for field: " + md.getName() + ".");
        } else {
            return (Method) method;
        }
    }
    
    private String getSetterName(MetaData md) {
        return "set" + Character.toUpperCase(md.getName().charAt(0)) + md.getName().substring(1);
    }
    
    private Method findGetter(MetaData md) throws DataLinkException {
        Object method = getters.get(md.getName()); 
        if (method == NULLMETHOD) {
            throw new DataLinkException ("Getter not found for field: " + md.getName() + ".");  
        } else if (method == null) {
            for (Method m: clazz.getMethods()) {
                if (m.getParameterTypes().length == 0 && m.getName().equals(getGetterName(md))) {
                    getters.put(md.getName(), m);
                    return m;
                }
            }
            getters.put(md.getName(), NULLMETHOD);
            throw new DataLinkException ("Getter not found for field: " + md.getName() + ".");
        } else {
            return (Method) method;
        }
    }
    
    private String getGetterName(MetaData md) {
        return (Kind.BOOLEAN == md.getKind() ? "is" : "get") + Character.toUpperCase(md.getName().charAt(0)) + md.getName().substring(1);
    }
}
