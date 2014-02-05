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

package com.adr.datasql.data;

import com.adr.datasql.KindParameters;
import com.adr.datasql.Parameters;
import java.sql.SQLException;
import java.util.Map;

/**
 *
 * @author adrian
 */
public class ParametersMapMeta implements Parameters<Map<String, Object>> {
    
    private static ParametersMapMeta instance = null;
    
    public static ParametersMapMeta getInstance() {
        if (instance == null) {
            instance = new ParametersMapMeta();
        }
        return instance;
    }
    
    private ParametersMapMeta() {
    }
    
    @Override
    public void write(KindParameters dp, Map<String, Object> param) throws SQLException {
        Parameters<Map<String, Object>> parameters = new ParametersMap(dp.getMetaData());
        parameters.write(dp, param);
    } 
}
