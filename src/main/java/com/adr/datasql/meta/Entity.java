//    Data SQL is a light JDBC wrapper.
//    Copyright (C) 2012-2014 Adrián Romero Corchado.
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
import com.adr.datasql.Kind;
import com.adr.datasql.Query;
import com.adr.datasql.Results;
import com.adr.datasql.SQL;
import com.adr.datasql.StatementExec;
import com.adr.datasql.StatementFind;
import com.adr.datasql.StatementQuery;
import com.adr.datasql.orm.Record;
import com.adr.datasql.orm.RecordArray;
import com.adr.datasql.orm.RecordParameters;
import com.adr.datasql.orm.RecordResults;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 *
 * @author adrian
 */
public class Entity implements SourceTableFactory, SourceListFactory {
    
    private String name = null;
    private final List<Field> fields = new ArrayList<Field>();
    
    public Entity() {
    }
    
    public Entity(String name, Field... fields) {
        this.name = name;
        this.fields.addAll(Arrays.asList(fields));
    }   

    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
 
    public List<Field> getFields() {
        return fields;
    }
    
    @Override
    public String toString() {
        return "Entity {name: " + Objects.toString(name) + ", fields: " + Objects.toString(fields) + "}";
    }
    
    @Override
    public <R, F> SourceList<R, F> createSourceList(RecordResults<R> record, RecordParameters<F> filter) {
        return new EntitySourceList(this, record, filter);
    }
    
    @Override
    public <R> SourceTable<R> createSourceTable(Record<R> record) {
        return new EntitySourceTable(this, record);
    }
    
    private static class EntitySourceList<R, F> implements SourceList<R, F> {
        
        private final Entity entity;
        private final RecordResults<R> record;
        private final RecordParameters<F> filter;
        private MetaData[] projection;
        private MetaData[] criteria;
        private StatementOrder[] order;
        
        public EntitySourceList(Entity entity, RecordResults<R> record, RecordParameters<F> filter) {
            this.entity = entity;
            this.record = record;
            this.filter = filter;
            this.projection = entity.defProjection();
            this.criteria = null;
            this.order = null;
        }
        
        @Override
        public final MetaData[] defProjection() {
            return entity.defProjection();
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
            return Entity.getStatementList(record, filter, entity.getName(), projection, criteria, order);
        }  
    }
    
    private static class EntitySourceTable<R> implements SourceTable<R> {
        
        private final Entity entity;
        private final Record<R> record;
        
        public EntitySourceTable(Entity entity, Record<R> record) {
            this.entity = entity;            
            this.record = record;
        }
        
        @Override
        public final MetaData[] defProjection() {
            return entity.defProjection();
        }

        @Override
        public StatementFind<R, Object[]> getStatementGet() {
            return Entity.getStatementList(record, new RecordArray(), entity.getName(), entity.defProjection(), entity.defProjectionKeys(), null);           
        }

        @Override
        public StatementExec<R> getStatementDelete() {
            return Entity.getStatementDelete(record, entity.getName(), entity.defProjection(), entity.defProjectionKeys());
        }

        @Override
        public StatementExec<R> getStatementUpdate() {
            return Entity.getStatementUpdate(record, entity.getName(), entity.defProjection(), entity.defProjectionKeys());
        }

        @Override
        public StatementExec<R> getStatementInsert() {
            return Entity.getStatementInsert(record, entity.getName(), entity.defProjection(), entity.defProjectionKeys());
        }

        @Override
        public R createNew() {
            return Entity.createNew(record, entity.getName(), entity.defProjection(), entity.defProjectionKeys());
        }
    }
    
    private MetaData[] projection = null;
    public MetaData[] defProjection() {
        if (projection == null) {
            projection = fields.toArray(new MetaData[fields.size()]);
        }
        return projection;
    }
    
    private MetaData[] projectionkeys = null;
    public MetaData[] defProjectionKeys() {
        if (projectionkeys == null) {            
            List<Field> l = fields.stream().filter(f -> f.isKey()).collect(Collectors.toList());    
            projectionkeys = l.toArray(new MetaData[l.size()]);
        }
        return projectionkeys;
    }
    
    public static <P> StatementExec<P> getStatementDelete(RecordParameters<P> parameters, String name, MetaData[] projection, MetaData[] keys) {
        
        StringBuilder sentence = new StringBuilder();
        StringBuilder sentencefilter = new StringBuilder();
        ArrayList<String> keyfields = new ArrayList<String>();
        
        sentence.append("DELETE FROM ");
        sentence.append(name);
        
        for (MetaData m: keys) {
                sentencefilter.append(sentencefilter.length() == 0 ? " WHERE " : " AND ");
                sentencefilter.append(m.getName());
                sentencefilter.append(" = ?");
                keyfields.add(m.getName());
        }
        sentence.append(sentencefilter);
            
        SQL sql = new SQL(sentence.toString(), keyfields.toArray(new String[keyfields.size()]));  
        return new Query<Void, P>(sql).setParameters(parameters.createParams(projection));
    }
    
    public static <P> StatementExec<P> getStatementUpdate(RecordParameters<P> parameters, String name, MetaData[] projection, MetaData[] keys) {
        
        StringBuilder sentence = new StringBuilder();
        ArrayList<String> keyfields = new ArrayList<String>();
        
        sentence.append("UPDATE ");
        sentence.append(name);
               
        boolean filter = false;
        for (MetaData m: projection) {
            sentence.append(filter ? ", " : " SET ");
            sentence.append(m.getName());
            sentence.append(" = ?");    
            keyfields.add(m.getName());
            filter = true;
        }  
        
        filter = false;
        for (MetaData m: keys) {
                sentence.append(filter ? " AND " : " WHERE ");
                sentence.append(m.getName());
                sentence.append(" = ?");
                keyfields.add(m.getName());
                filter = true;
        }  
            
        SQL sql =  new SQL(sentence.toString(), keyfields.toArray(new String[keyfields.size()]));   
        return new Query<Void, P>(sql).setParameters(parameters.createParams(projection));
    }
    
    public static <P> StatementExec<P> getStatementInsert(RecordParameters<P> parameters, String name, MetaData[] projection, MetaData[] keys) {
        
        StringBuilder sentence = new StringBuilder();
        StringBuilder values = new StringBuilder();
        ArrayList<String> fieldslist = new ArrayList<String>();
        
        sentence.append("INSERT INTO ");
        sentence.append(name);
        sentence.append("(");
               
        boolean filter = false;
        for (MetaData m: projection) {
            sentence.append(filter ? ", " : "");
            sentence.append(m.getName());

            values.append(filter ? ", ?": "?");
            fieldslist.add(m.getName());

            filter = true;
        }  
        
        sentence.append(") VALUES (");
        sentence.append(values);
        sentence.append(")");
            
        SQL sql = new SQL(sentence.toString(), fieldslist.toArray(new String[fieldslist.size()]));     
        return new Query<Void, P>(sql).setParameters(parameters.createParams(projection));
    }

    private static <R, P> Query<R, P> getStatementList(RecordResults<R> results, RecordParameters<P> parameters, String name, MetaData[] projection, MetaData[] criteria, StatementOrder[] order) {
        
        StringBuilder sqlsent = new StringBuilder();
        List<String> fieldslist = new ArrayList<String>();
        
        sqlsent.append("SELECT ");
        boolean comma = false;
        for (MetaData m: projection) {
            if (comma) {
                sqlsent.append(", ");
            } else {
                comma = true;       
            }
            sqlsent.append(m.getName()); 
        }    
        
        sqlsent.append(" FROM ");       
        sqlsent.append(name);
        
        // WHERE CLAUSE
        if (criteria != null) {
            comma = false;
            for (MetaData m: criteria) {
                if (comma) {
                    sqlsent.append(" AND ");
                } else {
                    sqlsent.append(" WHERE ");
                    comma = true;
                }
                
                String realname;
                if (m.getName().endsWith("_LIKE")) {
                    realname = m.getName().substring(0, m.getName().length() - 5);
                    sqlsent.append(realname);
                    sqlsent.append(" LIKE ? {escape '$'}");
                    fieldslist.add(m.getName());                      
                } else {
                    sqlsent.append(m.getName());
                    sqlsent.append(" = ?");
                    fieldslist.add(m.getName());    
                }
            }
        }
        
        // ORDER BY CLAUSE
        if (order != null) {
            comma = false;
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
        }

        // build statement
        SQL sql = new SQL(sqlsent.toString(), fieldslist.toArray(new String[fieldslist.size()]));     
        return new Query<R, P>(sql).setResults(results.createResults(projection)).setParameters(parameters.createParams(criteria));
    }
    
    public static <R> R createNew(RecordResults<R> record, String name, MetaData[] projection, MetaData[] keys) {
        try {
            Results<R> r = record.createResults(projection);
            return r.read(new KindResultsNew(projection, keys));
        } catch (SQLException e) {         
            throw new RuntimeException(e); // Never happens with the instanciated objects
        }
    }
     
//    
//    public SQL getStatementCreateTable(Database db) {
//        
//    }
//    
//    public SQL getStatementDropTable(Database db) {
//        
//    }
}

