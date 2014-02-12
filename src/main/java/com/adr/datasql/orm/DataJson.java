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

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.math.BigInteger;
import java.sql.SQLException;

/**
 *
 * @author adrian
 */
public class DataJson extends Data<JsonObject> {
    
    public DataJson(Definition definition) {
        super(definition);
    }

    @Override
    protected Object getValue(Field f, JsonObject param) throws SQLException {
        
        JsonElement value = param.get(f.getName());
        if (value == null) {
            return null;
        }
        
        if (value.isJsonNull()) {
            return null;
        } else if (value.isJsonPrimitive()) {
            JsonPrimitive primitive = value.getAsJsonPrimitive();
            if (primitive.isBoolean()) {
                return primitive.getAsBoolean();
            } else if (primitive.isNumber()) {
                Number n = primitive.getAsNumber();           
                if (isIntegral(n)) {
                    return n.intValue();
                } else {
                    return n.doubleValue();
                }
            } else if (primitive.isString()) {
                return primitive.getAsString();
            } else {
                throw new SQLException("Not valid Json primitive value.");
            }
        } else {
            throw new SQLException("Not valid Json value.");
        }  
    }        

    @Override
    protected void setValue(Field f, JsonObject param, Object value) throws SQLException {
        
        if (value == null) {
            param.add(f.getName(), JsonNull.INSTANCE);            
        } else if (value instanceof Boolean) {
            param.add(f.getName(), new JsonPrimitive((Boolean)value));                        
        } else if (value instanceof Number) {
            param.add(f.getName(), new JsonPrimitive((Number)value));
        } else {
            param.add(f.getName(), new JsonPrimitive(value.toString()));
        }
    }

    @Override
    protected JsonObject create() throws SQLException {
        return new JsonObject();
    }
    
    private boolean isIntegral(Number number) {
      return number instanceof BigInteger || number instanceof Long || number instanceof Integer
          || number instanceof Short || number instanceof Byte;
    }
}
