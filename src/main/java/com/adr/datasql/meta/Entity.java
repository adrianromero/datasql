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
import java.util.Objects;

/**
 *
 * @author adrian
 */
public class Entity extends View implements SourceTableFactory {
    
    public Entity() {
    }
    
    public Entity(String name, Field... fields) {
        super(name, fields);
    }   
    
    @Override
    public String toString() {
        return "Entity {name: " + Objects.toString(getName()) + ", fields: " + Objects.toString(getFields()) + "}";
    }
    
    @Override
    public <R> SourceTable<R> createSourceTable(Record<R> record) {
        return new EntitySourceTable(this, record);
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

