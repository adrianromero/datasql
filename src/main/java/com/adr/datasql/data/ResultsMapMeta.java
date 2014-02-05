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

import com.adr.datasql.KindResults;
import com.adr.datasql.Results;
import java.sql.SQLException;
import java.util.Map;

/**
 *
 * @author adrian
 */
public class ResultsMapMeta  implements Results<Map<String, Object>> {
    
    private static ResultsMapMeta instance = null;
    
    public static ResultsMapMeta getInstance() {
        if (instance == null) {
            instance = new ResultsMapMeta();
        }
        return instance;
    }
    
    private ResultsMapMeta() {
    }
    
    @Override
    public Map<String, Object> read(KindResults kr) throws SQLException {
        Results<Map<String, Object>> results = new ResultsMap(kr.getMetaData());
        return results.read(kr);
    }
}
