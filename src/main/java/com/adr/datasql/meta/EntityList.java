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

import com.adr.datasql.data.MetaData;
import com.adr.datasql.Query;
import com.adr.datasql.SQL;
import com.adr.datasql.StatementQuery;
import com.adr.datasql.orm.RecordParameters;
import com.adr.datasql.orm.RecordResults;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
 
    public List<MetaData> getMetadatas() {
        return metadatas;
    }  
    
    @Override
    public String toString() {
        return "EntityList {sentence: " + Objects.toString(sentence) + ", metadatas: " + Objects.toString(metadatas) + "}";
    }   
    
    @Override
    public <R, F> SourceList<R, F> createSourceList(RecordResults<R> record, RecordParameters<F> filter) {
        return new EntityListSourceList(this, record, filter);
    }
    
    private class EntityListSourceList<R, F> implements SourceList<R, F> {
        
        private final RecordResults<R> record;
        private final RecordParameters<F> filter;
        private final String sentence;
        private MetaData[] projection;
        private MetaData[] criteria;
        private StatementOrder[] order;
        
        public EntityListSourceList(EntityList entitylist, RecordResults<R> record, RecordParameters<F> filter) {
            this.sentence = entitylist.getSentence();
            this.record = record;
            this.filter = filter;
            this.projection = entitylist.getProjection();
            this.criteria = null;
            this.order = null;
        }
        
        @Override
        public final MetaData[] getProjection() {
            return projection;
        }

        @Override
        public void setProjection(MetaData[] projection) {
            this.projection = projection;
        }
        
        @Override 
        public final MetaData[] getCriteria() {
            return criteria;
        }

        @Override
        public void setCriteria(MetaData[] criteria) {
            this.criteria = criteria;
        }
        
        @Override
        public StatementOrder[] getOrder() {
            return order;
        }

        @Override
        public void setOrder(StatementOrder[] order) {
            this.order = order;
        }
        
        @Override
        public StatementQuery<R, F> getStatementList() {
            return EntityList.getStatementList(record, filter, sentence, projection, criteria, order);
        }  
    }
    
    private MetaData[] projection = null;
    public MetaData[] getProjection() {
        if (projection == null) {
            projection = metadatas.toArray(new MetaData[metadatas.size()]);
        }
        return projection;
    }
    
    private static<R, P> StatementQuery<R, P> getStatementList(RecordResults<R> results, RecordParameters<P> parameters, String sentence, MetaData[] projection, MetaData[] criteria, StatementOrder[] order) {
        
        StringBuilder sqlsent = new StringBuilder(sentence);
        
        // the-filter-too. TO-DO
        
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
        return new Query<R, P>(sql).setResults(results.createResults(projection)).setParameters(parameters.createParams(criteria));
    }   
}
