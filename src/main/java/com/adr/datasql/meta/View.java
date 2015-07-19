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
public class View implements SourceListFactory {
    
    protected String name = null;
    protected final List<Field> fields = new ArrayList<>();
    
    public View() {
    }
    
    public View(String name, Field... fields) {
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
        return "View {name: " + Objects.toString(getName()) + ", fields: " + Objects.toString(getFields()) + "}";
    }

    private MetaData[] projection = null;
    @Override
    public MetaData[] defProjection() {
        if (projection == null) {
            List<MetaData> l =  fields.stream().filter(f -> f.isLoad()).map(f -> new MetaData(f.getName(), f.getKind())).collect(Collectors.toList()); 
            projection = l.toArray(new MetaData[l.size()]);
        }
        return projection;
    }
    
    private MetaData[] keys = null;
    @Override
    public MetaData[] defProjectionKeys() {
        if (keys == null) {            
            List<MetaData> l = fields.stream().filter(f -> f.isKey()).map(f -> new MetaData(f.getName(), f.getKind())).collect(Collectors.toList());    
            keys = l.toArray(new MetaData[l.size()]);
        }
        return keys;
    }     
    
    @Override
    public <R, F> SourceList<R, F> createSourceList(RecordResults<R> record, RecordParameters<F> filter) {
        return new EntitySourceList(this, record, filter);
    }
    
    private static class EntitySourceList<R, F> implements SourceList<R, F> {
        
        private final View view;
        private final RecordResults<R> record;
        private final RecordParameters<F> filter;
        private MetaData[] criteria;
        private StatementOrder[] order;
        
        public EntitySourceList(View view, RecordResults<R> record, RecordParameters<F> filter) {
            this.view = view;
            this.record = record;
            this.filter = filter;
            this.criteria = null;
            this.order = null;
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
            CommandEntityList command = new CommandEntityList(view.getName(), MetaData.getNames(view.defProjection()), MetaData.getNames(criteria), order);
            return new BasicStatementQuery<R, F>(command).setResults(record.createResults(view.defProjection())).setParameters(filter.createParams(criteria));
        }  

        @Override
        public StatementFind<R, Object[]> getStatementFind() {
            CommandEntityGet command = new CommandEntityGet(view.getName(), MetaData.getNames(view.defProjectionKeys()), MetaData.getNames(view.defProjection()));
            return new BasicStatementFind<R, Object[]>(command).setResults(record.createResults(view.defProjection())).setParameters(new RecordArray().createParams(view.defProjectionKeys()));
        }       
    } 
}

