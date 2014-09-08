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

import com.adr.datasql.Kind;
import com.adr.datasql.Parameters;
import com.adr.datasql.Query;
import com.adr.datasql.Results;
import com.adr.datasql.SQL;
import com.adr.datasql.StatementExec;
import com.adr.datasql.StatementFind;
import com.adr.datasql.StatementQuery;
import com.adr.datasql.data.ParametersArray;
import com.adr.datasql.data.ParametersMap;
import com.adr.datasql.data.Record;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

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
    public <R> SourceList<R> createSourceList(Record<R> record) {
        return new EntitySourceTable(record);
    }
    
    @Override
    public <R> SourceTable<R> createSourceTable(Record<R> record) {
        return new EntitySourceTable(record);
    }
    
    private class EntitySourceTable<R> implements SourceTable<R> {
        
        private final Record<R> record;
        private final MetaData[] metadatas;
        private final MetaData[] filtermetadatas;
        
        public EntitySourceTable(Record<R> record) {
            this.record = record;
            this.metadatas = fields.toArray(new MetaData[fields.size()]);
            this.filtermetadatas = Entity.this.getFilterMetaDatas();
        }
        
        @Override
        public final MetaData[] getMetaDatas() {
            return metadatas;
        }
        
        @Override 
        public final MetaData[] getFilterMetaDatas() {
            return filtermetadatas;
        }

        @Override
        public StatementFind<R, Object[]> getStatementGet() {
            MetaData[] fieldskey = getKeyMetaDatas();
            return Entity.this.getStatementList(record.createResults(metadatas), new ParametersArray(fieldskey), fieldskey, null);           
        }
        
        @Override
        public StatementQuery<R, Map<String, Object>> getStatementList(MetaData[] filter, StatementOrder[] order) {
            return Entity.this.getStatementList(record.createResults(metadatas), filter == null ? null : new ParametersMap(filter), filter, order);
        }  

        @Override
        public StatementExec<R> getStatementDelete() {
            return Entity.this.getStatementDelete(record.createParams(metadatas));
        }

        @Override
        public StatementExec<R> getStatementUpdate() {
            return Entity.this.getStatementUpdate(record.createParams(metadatas));
        }

        @Override
        public StatementExec<R> getStatementInsert() {
            return Entity.this.getStatementInsert(record.createParams(metadatas));
        }

        @Override
        public R createNew() {
            return Entity.this.createNew(record);
        }
    }
    
    private MetaData[] getKeyMetaDatas() {
        ArrayList<MetaData> keys = new ArrayList<MetaData>();
        for (Field f: fields) {
            if (f.isKey()) {
                keys.add(f);
            }
        }        
        return keys.toArray(new MetaData[keys.size()]);
    }
    
    private MetaData[] getFilterMetaDatas() {
        ArrayList<MetaData> keys = new ArrayList<MetaData>();
        for (Field f: fields) {
            if (f.isFilter()) {
                keys.add(f);
                if (Kind.STRING.equals(f.getKind())) {
                    keys.add(new MetaData(f.getName() + "_LIKE", f.getKind()));
                }
            }
        }        
        return keys.toArray(new MetaData[keys.size()]);
    }    
 
    public <P> StatementExec<P> getStatementDelete(Parameters<P> parameters) {
        
        StringBuilder sentence = new StringBuilder();
        StringBuilder sentencefilter = new StringBuilder();
        ArrayList<String> keyfields = new ArrayList<String>();
        
        sentence.append("DELETE FROM ");
        sentence.append(getName());
        
        for (Field f: fields) {
            if (f.isKey()) {
                sentencefilter.append(sentencefilter.length() == 0 ? " WHERE " : " AND ");
                sentencefilter.append(f.getName());
                sentencefilter.append(" = ?");
                keyfields.add(f.getName());
            }
        }
        sentence.append(sentencefilter);
            
        SQL sql = new SQL(sentence.toString(), keyfields.toArray(new String[keyfields.size()]));  
        return new Query<Void, P>(sql).setParameters(parameters);
    }
    
    public <P> StatementExec<P> getStatementUpdate(Parameters<P> parameters) {
        
        StringBuilder sentence = new StringBuilder();
        ArrayList<String> keyfields = new ArrayList<String>();
        
        sentence.append("UPDATE ");
        sentence.append(getName());
               
        boolean filter = false;
        for (Field f: fields) {
            sentence.append(filter ? ", " : " SET ");
            sentence.append(f.getName());
            sentence.append(" = ?");    
            keyfields.add(f.getName());
            filter = true;
        }  
        
        filter = false;
        for (Field f: fields) {
            if (f.isKey()) {
                sentence.append(filter ? " AND " : " WHERE ");
                sentence.append(f.getName());
                sentence.append(" = ?");
                keyfields.add(f.getName());
                filter = true;
            }
        }  
            
        SQL sql =  new SQL(sentence.toString(), keyfields.toArray(new String[keyfields.size()]));   
        return new Query<Void, P>(sql).setParameters(parameters);
    }
    
    public <P> StatementExec<P> getStatementInsert(Parameters<P> parameters) {
        
        StringBuilder sentence = new StringBuilder();
        StringBuilder values = new StringBuilder();
        ArrayList<String> fieldslist = new ArrayList<String>();
        
        sentence.append("INSERT INTO ");
        sentence.append(getName());
        sentence.append("(");
               
        boolean filter = false;
        for (Field f: fields) {
            sentence.append(filter ? ", " : "");
            sentence.append(f.getName());

            values.append(filter ? ", ?": "?");
            fieldslist.add(f.getName());

            filter = true;
        }  
        
        sentence.append(") VALUES (");
        sentence.append(values);
        sentence.append(")");
            
        SQL sql = new SQL(sentence.toString(), fieldslist.toArray(new String[fieldslist.size()]));     
        return new Query<Void, P>(sql).setParameters(parameters);
    }

    public <R, P> Query<R, P> getStatementList(Results<R> results, Parameters<P> parameters, MetaData[] filter, StatementOrder[] order) {
        
        StringBuilder sqlsent = new StringBuilder();
        List<String> fieldslist = new ArrayList<String>();
        
        sqlsent.append("SELECT ");
        boolean comma = false;
        for (Field f: fields) {
            if (comma) {
                sqlsent.append(", ");
            } else {
                comma = true;       
            }
            sqlsent.append(f.getName()); 
        }    
        
        sqlsent.append(" FROM ");       
        sqlsent.append(getName());
        
        // WHERE CLAUSE
        if (filter != null) {
            comma = false;
            for (MetaData m: filter) {
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
                    sqlsent.append(" LIKE ?");
                    fieldslist.add(realname);                      
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
        return new Query<R, P>(sql).setResults(results).setParameters(parameters);
    }
    
    public <R> R createNew(Record<R> record) {
        try {
            R r = record.create();
            for (Field f: fields) {
                if (f.isKey()) {
                    record.setValue(f, r, UUID.randomUUID().toString().replaceAll("-", ""));
                }
            }
            return r;
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

