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
import com.adr.datasql.Kind;
import com.adr.datasql.data.MetaData;
import com.adr.datasql.data.ParametersArray;
import com.adr.datasql.data.ParametersArrayMeta;
import com.adr.datasql.data.ResultsArray;
import com.adr.datasql.data.ResultsArrayMeta;

/**
 *
 * @author adrian
 */
public class SQLStatementArray extends SQLStatement<Object[], Object[]> {

    public SQLStatementArray(String command, String... paramnames) {
        super(command, paramnames);
        init();
    }

    public SQLStatementArray(CommandSQL command) {
        super(command);
        init();
    }
    
    private void init() {
        this.setParameters(ParametersArrayMeta.getInstance());
        this.setResults(ResultsArrayMeta.getInstance());    
    }
    
    public SQLStatementArray setParameters(Kind... kinds) {
        setParameters(new ParametersArray(kinds));
        return this;
    }    
    
    public SQLStatementArray setParameters(MetaData... metadatas) {
        setParameters(new ParametersArray(metadatas));
        return this;
    }       
    
    public SQLStatementArray setResults(Kind... kinds) {
        setResults(new ResultsArray(kinds));
        return this;
    }    
    
    public SQLStatementArray setResults(MetaData... metadatas) {
        setResults(new ResultsArray(metadatas));
        return this;
    }      
}
