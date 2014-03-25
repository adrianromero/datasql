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
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author adrian
 */
public class DataMap extends Data<Map<String, Object>> {
    
    public DataMap(Entity definition) {
        super(definition);
    }

    @Override
    protected Object getValue(Field f, Map<String, Object> param) throws SQLException {
        return param.get(f.getName());      
    }        

    @Override
    protected void setValue(Field f, Map<String, Object> param, Object value) throws SQLException {
        param.put(f.getName(), value);
    }

    @Override
    protected Map<String, Object> create() throws SQLException {
        return new HashMap<String, Object>();
    }
}
