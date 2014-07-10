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

package com.adr.datasql.meta;

import com.adr.datasql.Query;
import com.adr.datasql.Results;
import com.adr.datasql.SQL;
import com.adr.datasql.StatementQuery;
import com.adr.datasql.data.ParametersMap;
import com.adr.datasql.data.Record;
import java.util.Map;

/**
 *
 * @author adrian
 */
public class EntityList implements SourceListFactory {
    
    private final String sentence;
    private final MetaData[] metadatas;
    
    public EntityList(String sentence, MetaData[] metadatas) {
        this.sentence = sentence;
        this.metadatas = metadatas;
    }
    
    @Override
    public <R> SourceList<R> createSourceList(Record<R> record) {
        return new EntityListSourceList(record);
    }
    
    private class EntityListSourceList<R> implements SourceList<R> {
        
        private final Record<R> record;
        
        public EntityListSourceList(Record<R> record) {
            this.record = record;
        }
        
        @Override
        public MetaData[] getMetaDatas() {
            return metadatas;
        }

        @Override
        public StatementQuery<R, Map<String, Object>> getStatementList(MetaData[] filter, StatementOrder[] order) {
            return EntityList.this.getStatementList(record.createResults(metadatas), filter, order);
        }  
    }

    private <R> StatementQuery<R, Map<String, Object>> getStatementList(Results<R> results, MetaData[] filter, StatementOrder[] order) {
        
        StringBuilder sqlsent = new StringBuilder(sentence);
        
        // the-filter-too.
        
       // ORDER BY CLAUSE
        boolean comma = false;
        for (StatementOrder o: order) {
            if (comma) {
                sqlsent.append(", ");
            } else {
                sqlsent.append(" ORDER BY ");
                comma = true;
            }           
            sqlsent.append(o.getName());
            sqlsent.append(o.getOrder().toSQL());               
        }

        // build statement
        SQL sql = new SQL(sqlsent.toString());     
        return new Query<R, Map<String, Object>>(sql).setResults(results).setParameters(filter == null ? null : new ParametersMap(filter));
    }
    
}
