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
import java.util.stream.Collectors;

/**
 *
 * @author adrian
 */
public class EntityList implements SourceListFactory {
    
    private String sentence = null;
    private final List<Field> fields = new ArrayList<>();
    
    public EntityList() {        
    }
    
    public EntityList(String sentence, Field... fields) {
        this.sentence = sentence;
        this.fields.addAll(Arrays.asList(fields));
    }

    public String getSentence() {
        return sentence;
    }
    
    public void setSentence(String sentence) {
        this.sentence = sentence;
    }
 
    public List<Field> getFields() {
        return fields;
    }  
    
    @Override
    public String toString() {
        return "EntityList {sentence: " + Objects.toString(sentence) + ", fields: " + Objects.toString(fields) + "}";
    }   
    
    private MetaData[] projection = null;
    @Override
    public MetaData[] defProjection() {
        if (projection == null) {
            projection = fields.toArray(new MetaData[fields.size()]);
        }
        return projection;
    }
    
    private MetaData[] projectionkeys = null;
    @Override
    public MetaData[] defProjectionKeys() {
        if (projectionkeys == null) {            
            List<Field> l = fields.stream().filter(f -> f.isKey()).collect(Collectors.toList());    
            projectionkeys = l.toArray(new MetaData[l.size()]);
        }
        return projectionkeys;
    } 
    
    @Override
    public <R, F> SourceList<R, F> createSourceList(RecordResults<R> record, RecordParameters<F> filter) {
        return new EntityListSourceList(this, record, filter);
    }
    
    private static class EntityListSourceList<R, F> implements SourceList<R, F> {
        
        private final EntityList entitylist;
        private final RecordResults<R> record;
        private final RecordParameters<F> filter;
        private MetaData[] projection;
        private MetaData[] criteria;
        private StatementOrder[] order;
        
        public EntityListSourceList(EntityList entitylist, RecordResults<R> record, RecordParameters<F> filter) {
            this.entitylist = entitylist;
            this.record = record;
            this.filter = filter;
            this.projection = entitylist.defProjection();
            this.criteria = null;
            this.order = null;
        }
        
        @Override
        public void setProjection(MetaData[] projection) {
            this.projection = projection;
        }

        @Override
        public void setCriteria(MetaData[] criteria) {
            this.criteria = criteria;
        }

        @Override
        public void setOrder(StatementOrder[] order) {
            this.order = order;
        }
        
        @Override
        public StatementQuery<R, F> getStatementList() {
            return EntityList.getStatementList(record, filter, entitylist.getSentence(), projection, criteria, order);
        }  
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
