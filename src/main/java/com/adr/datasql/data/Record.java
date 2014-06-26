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
import com.adr.datasql.KindResults;
import com.adr.datasql.Parameters;
import com.adr.datasql.Results;
import com.adr.datasql.meta.MetaData;
import java.sql.SQLException;

/**
 *
 * @author adrian
 */
public abstract class Record<P> {

    public abstract Object getValue(MetaData f, P param) throws SQLException;
    public abstract void setValue(MetaData f, P param, Object value) throws SQLException;
    public abstract P create() throws SQLException;
    
    public final Results<P> createResults(MetaData[] metadatas) {
        return new RecordParametersResults(metadatas);
    }
    
    public final Parameters<P> createParams(MetaData[] metadatas) {
        return new RecordParametersResults(metadatas);
    }
    
    private class RecordParametersResults implements Parameters<P>, Results<P> {
        private final MetaData[] metadatas;

        public RecordParametersResults(MetaData[] metadatas) {
            this.metadatas = metadatas;
        }
        
        @Override
        public final P read(KindResults dp) throws SQLException {
            P param = create();
            for (MetaData md : metadatas) {    
                setValue(md, param, md.getKind().get(dp, md.getName()));              
            }
            return param;
        }    
        @Override
        public final void write(KindParameters dp, P param) throws SQLException {
            for (MetaData md : metadatas) {           
                md.getKind().set(dp, md.getName(), getValue(md, param));              
            }
        }         
    }
}
