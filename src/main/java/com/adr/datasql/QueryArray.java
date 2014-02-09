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

package com.adr.datasql;

import com.adr.datasql.data.ParametersArray;
import com.adr.datasql.data.ParametersArrayMeta;
import com.adr.datasql.data.ResultsArray;
import com.adr.datasql.data.ResultsArrayMeta;

/**
 *
 * @author adrian
 */
public class QueryArray extends Query<Object[], Object[]> {

    public QueryArray(String sql, String... paramnames) {
        super(sql, paramnames);
        init();
    }

    public QueryArray(SQL sql) {
        super(sql);
        init();
    }
    
    private void init() {
        this.setParameters(ParametersArrayMeta.getInstance());
        this.setResults(ResultsArrayMeta.getInstance());    
    }
    
    public QueryArray setParameters(Kind... kinds) {
        setParameters(new ParametersArray(kinds));
        return this;
    }    
    
    public QueryArray setParameters(MetaData... metadatas) {
        setParameters(new ParametersArray(metadatas));
        return this;
    }       
    
    public QueryArray setResults(Kind... kinds) {
        setResults(new ResultsArray(kinds));
        return this;
    }    
    
    public QueryArray setResults(MetaData... metadatas) {
        setResults(new ResultsArray(metadatas));
        return this;
    }      
}
