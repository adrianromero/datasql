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

import com.adr.datasql.data.Record;
import com.adr.datasql.meta.MetaData;
import com.google.gson.JsonObject;
import java.sql.SQLException;

/**
 *
 * @author adrian
 */
public class RecordJson extends Record<JsonObject> {

    @Override
    public Object getValue(MetaData md, JsonObject param) throws SQLException {
        return md.getKind().get(new KindResultsJson(param), md.getName());  
    }        

    @Override
    public void setValue(MetaData md, JsonObject param, Object value) throws SQLException {
        md.getKind().set(new KindParametersJson(param), md.getName(), value);
    }

    @Override
    public JsonObject create() throws SQLException {
        return new JsonObject();
    }
}
