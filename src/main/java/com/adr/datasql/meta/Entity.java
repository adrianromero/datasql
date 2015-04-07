//    Data SQLCommand is a light JDBC wrapper.
//    Copyright (C) 2012-2015 Adrián Romero Corchado.
//
//    This file is part of Data SQLCommand
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
import com.adr.datasql.orm.Record;
import com.adr.datasql.orm.RecordArray;
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

    private MetaData[] projection = null;
    @Override
    public MetaData[] defProjection() {
        if (projection == null) {
            List<MetaData> l =  fields.stream().map(f -> new MetaData(f.getName(), f.getKind())).collect(Collectors.toList()); 
            projection = l.toArray(new MetaData[l.size()]);
        }
        return projection;
    }
    
    private MetaData[] projectionkeys = null;
    @Override
    public MetaData[] defProjectionKeys() {
        if (projectionkeys == null) {            
            List<MetaData> l = fields.stream().filter(f -> f.isKey()).map(f -> new MetaData(f.getName(), f.getKind())).collect(Collectors.toList());    
            projectionkeys = l.toArray(new MetaData[l.size()]);
        }
        return projectionkeys;
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
            CommandEntityList command = new CommandEntityList(entity.getName(), MetaData.getNames(projection), MetaData.getNames(criteria), order);
            return new BasicStatementQuery<R, F>(command).setResults(record.createResults(projection)).setParameters(filter.createParams(criteria));
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
        public StatementFind<R, Object[]> getStatementGet() {
            CommandEntityGet command = new CommandEntityGet(entity.getName(), MetaData.getNames(entity.defProjectionKeys()), MetaData.getNames(entity.defProjection()));
            return new BasicStatementFind<R, Object[]>(command).setResults(record.createResults(entity.defProjection())).setParameters(new RecordArray().createParams(entity.defProjectionKeys()));
        }

        @Override
        public StatementExec<R> getStatementDelete() {
            CommandEntityDelete command = new CommandEntityDelete(entity.getName(), MetaData.getNames(entity.defProjectionKeys()), MetaData.getNames(entity.defProjection()));
            return new BasicStatementExec<R>(command).setParameters(record.createParams(entity.defProjection()));
        }

        @Override
        public StatementExec<R> getStatementUpdate() {
            CommandEntityUpdate command = new CommandEntityUpdate(entity.getName(), MetaData.getNames(entity.defProjectionKeys()), MetaData.getNames(entity.defProjection()));
            return new BasicStatementExec<R>(command).setParameters(record.createParams(entity.defProjection()));            
        }

        @Override
        public StatementExec<R> getStatementInsert() {
            CommandEntityInsert command = new CommandEntityInsert(entity.getName(), MetaData.getNames(entity.defProjectionKeys()), MetaData.getNames(entity.defProjection()));
            return new BasicStatementExec<R>(command).setParameters(record.createParams(entity.defProjection()));            
        }
    }
}

