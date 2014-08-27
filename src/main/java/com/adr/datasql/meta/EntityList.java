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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author adrian
 */
public class EntityList implements SourceListFactory {
    
    private String sentence = null;
    private final List<MetaData> metadatas = new ArrayList<MetaData>();
    
    public EntityList() {        
    }
    
    public EntityList(String sentence, MetaData... metadatas) {
        this.sentence = sentence;
        this.metadatas.addAll(Arrays.asList(metadatas));
    }

    public String getSentence() {
        return sentence;
    }
    
    public void setSentence(String sentence) {
        this.sentence = sentence;
    }
 
    public List<MetaData> getMetaDatas() {
        return metadatas;
    }  
    
    @Override
    public String toString() {
        return "EntityList {sentence: " + Objects.toString(sentence) + ", metadatas: " + Objects.toString(metadatas) + "}";
    }   
    
    @Override
    public <R> SourceList<R> createSourceList(Record<R> record) {
        return new EntityListSourceList(record);
    }
    
    private class EntityListSourceList<R> implements SourceList<R> {
        
        private final Record<R> record;
        private final MetaData[] metadatasSource;
        
        public EntityListSourceList(Record<R> record) {
            this.record = record;
            this.metadatasSource = metadatas.toArray(new MetaData[metadatas.size()]);            
        }
        
        @Override
        public MetaData[] getMetaDatas() {
            return metadatasSource;
        }

        @Override
        public StatementQuery<R, Map<String, Object>> getStatementList(MetaData[] filter, StatementOrder[] order) {
            return EntityList.this.getStatementList(record.createResults(metadatasSource), filter, order);
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
