//    Data SQL is a light JDBC wrapper.
//    Copyright (C) 2015 Adrián Romero Corchado.
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

package com.adr.datasql.adaptor.sql;

import com.adr.datasql.meta.CommandSQL;
import com.adr.datasql.data.MetaData;
import com.adr.datasql.data.ParametersMap;
import com.adr.datasql.data.ParametersMapMeta;
import com.adr.datasql.data.ResultsMap;
import com.adr.datasql.data.ResultsMapMeta;
import java.util.Map;

/**
 *
 * @author adrian
 */
public class SQLStatementMap extends SQLStatement<Map<String, Object>, Map<String, Object>> {

    public SQLStatementMap(String command, String... paramnames) {
        super(command, paramnames);
        init();
    }

    public SQLStatementMap(CommandSQL command) {
        super(command);
        init();
    }
    
    private void init() {
        this.setParameters(ParametersMapMeta.getInstance());
        this.setResults(ResultsMapMeta.getInstance());
    }   
    
    public SQLStatementMap setParameters(MetaData... metadatas) {
        setParameters(new ParametersMap(metadatas));
        return this;
    }       

    public SQLStatementMap setResults(MetaData... metadatas) {
        setResults(new ResultsMap(metadatas));
        return this;
    }     
    
}
