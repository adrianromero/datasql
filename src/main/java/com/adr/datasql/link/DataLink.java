//    Data SQL is a light JDBC wrapper.
//    Copyright (C) 2015 Adrián Romero Corchado.
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

package com.adr.datasql.link;

import com.adr.datasql.Parameters;
import com.adr.datasql.Results;
import com.adr.datasql.meta.StatementExec;
import com.adr.datasql.meta.StatementFind;
import com.adr.datasql.meta.StatementQuery;
import com.adr.datasql.adaptor.sql.SQLCommand;
import com.adr.datasql.data.MetaData;
import com.adr.datasql.meta.CommandEntityDelete;
import com.adr.datasql.meta.CommandEntityGet;
import com.adr.datasql.meta.CommandEntityInsert;
import com.adr.datasql.meta.CommandEntityList;
import com.adr.datasql.meta.CommandEntityUpdate;
import com.adr.datasql.meta.SourceList;
import com.adr.datasql.meta.SourceListFactory;
import com.adr.datasql.meta.SourceTable;
import com.adr.datasql.meta.SourceTableFactory;
import com.adr.datasql.meta.StatementOrder;
import com.adr.datasql.orm.RecordMap;
import com.adr.datasql.orm.RecordPojo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author adrian
 */
public abstract class DataLink implements AutoCloseable {

    public <P> int exec(CommandEntityDelete cmd, Parameters<P> parameters, P params) throws DataLinkException {
        throw new DataLinkException("CommandEntityDelete not supported.");
    }
    public <P> int exec(CommandEntityInsert cmd, Parameters<P> parameters, P params) throws DataLinkException {
        throw new DataLinkException("CommandEntityInsert not supported.");
    }
    public <P> int exec(CommandEntityUpdate cmd, Parameters<P> parameters, P params) throws DataLinkException {
        throw new DataLinkException("CommandEntityUpdate not supported.");
    }
    public <P> int exec(SQLCommand cmd, Parameters<P> parameters, P params) throws DataLinkException {
        throw new DataLinkException("SQLCommand not supported.");
    }
    public <R, P> R find(CommandEntityGet command, Results<R> results, Parameters<P> parameters, P params) throws DataLinkException {
        throw new DataLinkException("CommandEntityGet not supported.");
    }
    public <R, P> R find(CommandEntityList command, Results<R> results, Parameters<P> parameters, P params) throws DataLinkException {
        throw new DataLinkException("CommandEntityList not supported.");
    }
    public <R, P> R find(SQLCommand command, Results<R> results, Parameters<P> parameters, P params) throws DataLinkException {
        throw new DataLinkException("SQLCommand not supported.");
    }
    public <R, P> List<R> query(CommandEntityList command, Results<R> results, Parameters<P> parameters, P params) throws DataLinkException {
        throw new DataLinkException("CommandEntityList not supported.");
    }
    public <R, P> List<R> query(SQLCommand command, Results<R> results, Parameters<P> parameters, P params) throws DataLinkException {
        throw new DataLinkException("SQLCommand not supported.");
    }           
    
    @Override
    public void close() throws DataLinkException {        
    }
    
    /**
     *
     * @param statement The 
     * @return Either the row count for update statements or 0 for statements
     * that return nothing
     * @throws DataLinkException
     */
    public final int exec(StatementExec<?> statement) throws DataLinkException {
        return statement.exec(this, null);
    }
    
    /**
     *
     * @param <P>
     * @param statement
     * @param params
     * @return
     * @throws DataLinkException
     */
    public final <P> int exec(StatementExec<P[]> statement, P... params) throws DataLinkException {       
        return statement.exec(this, params);
    }
    
    /**
     *
     * @param <P>
     * @param statement
     * @param params
     * @return
     * @throws DataLinkException
     */
    public final <P> int exec(StatementExec<P> statement, P params) throws DataLinkException {
        return statement.exec(this, params);
    }
    
    /**
     *
     * @param <R>
     * @param statement
     * @return
     * @throws DataLinkException
     */
    public final <R> R find(StatementFind<R, ?> statement) throws DataLinkException {
        return statement.find(this, null);
    }
    
    /**
     *
     * @param <R>
     * @param <P>
     * @param statement
     * @param params
     * @return
     * @throws DataLinkException
     */
    public final <R, P> R find(StatementFind<R, P[]> statement, P... params) throws DataLinkException {
        return statement.find(this, params);
    }
    
    /**
     *
     * @param <R>
     * @param <P>
     * @param statement
     * @param params
     * @return
     * @throws DataLinkException
     */
    public final <R, P> R find(StatementFind<R, P> statement, P params) throws DataLinkException {
        return statement.find(this, params);
    }

    /**
     *
     * @param <R>
     * @param statement
     * @return
     * @throws DataLinkException
     */
    public final <R> List<R> query(StatementQuery<R, ?> statement) throws DataLinkException {
        return statement.query(this, null);
    }
    
    /**
     *
     * @param <R>
     * @param <P>
     * @param statement
     * @param params
     * @return
     * @throws DataLinkException
     */
    public final <R, P> List<R> query(StatementQuery<R, P[]> statement, P... params) throws DataLinkException {
        return statement.query(this, params);
    }
    
    /**
     *
     * @param <R>
     * @param <P>
     * @param statement
     * @param params
     * @return
     * @throws DataLinkException
     */
    public final <R, P> List<R> query(StatementQuery<R, P> statement, P params) throws DataLinkException {
        return statement.query(this, params);
    } 
    
    
    private static <P> SourceTable<P> getSourceTable(Class<? extends P> clazz) throws DataLinkException {
        try {
            return ((SourceTableFactory) clazz.getField("SOURCEFACTORY").get(null))
                    .createSourceTable(new RecordPojo(clazz));
        } catch (NoSuchFieldException 
                |IllegalArgumentException
                |IllegalAccessException
                |SecurityException ex) {
            throw new DataLinkException(ex);
        }
    }
    
    private static SourceListFactory getSourceListFactory(Class<?> clazz) throws DataLinkException {
        try {
            return ((SourceListFactory) clazz.getField("SOURCEFACTORY").get(null));
        } catch (NoSuchFieldException 
                |IllegalArgumentException
                |IllegalAccessException
                |SecurityException ex) {
            throw new DataLinkException(ex);
        }
    }
    
    private static MetaData[] getMetaDatas(MetaData[] metadatas, Set<String> fieldsname) {
        ArrayList<MetaData> keys = new ArrayList<>();
        for (MetaData m: metadatas) {
            if (fieldsname.contains(m.getName())) {
                keys.add(m);
            }
        }        
        return keys.toArray(new MetaData[keys.size()]);
    }    
    
    public final <P> int insert(P value) throws DataLinkException {  
        return insert(getSourceTable((Class<? extends P>) value.getClass()), value);
    }
    
    public final <P> int delete(P value) throws DataLinkException {
        return delete(getSourceTable((Class<? extends P>) value.getClass()), value);
    }
    
    public final <P> int update(P value) throws DataLinkException {
        return update(getSourceTable((Class<? extends P>) value.getClass()), value); 
    }
    
    public final <P> P get(Class<? extends P> clazz, Object... key) throws DataLinkException {      
        return get(getSourceTable(clazz), key);
    }
    
    public final <P> List<P> list(Class<? extends P> clazz) throws DataLinkException {
        SourceListFactory sourcelistfactory = getSourceListFactory(clazz);
        SourceList<P, Map<String, Object>> sourcelist = sourcelistfactory.createSourceList(new RecordPojo(clazz), new RecordMap());
        sourcelist.setCriteria(null);
        return list(sourcelist);
    }  
    
    public final <P> List<P> list(Class<? extends P> clazz, StatementOrder[] order) throws DataLinkException {
        SourceListFactory sourcelistfactory = getSourceListFactory(clazz);
        SourceList<P, Map<String, Object>> sourcelist = sourcelistfactory.createSourceList(new RecordPojo(clazz), new RecordMap());
        sourcelist.setCriteria(null);
        sourcelist.setOrder(order);
        return list(sourcelist);
    }  
    
    public final <P> List<P> list(Class<? extends P> clazz, Map<String, Object> filter) throws DataLinkException {
        SourceListFactory sourcelistfactory = getSourceListFactory(clazz);
        SourceList<P, Map<String, Object>> sourcelist = sourcelistfactory.createSourceList(new RecordPojo(clazz), new RecordMap());
        sourcelist.setCriteria(getMetaDatas(sourcelistfactory.defProjection(), filter.keySet()));
        return list(sourcelist, filter);
    }  
    
    public final <P> List<P> list(Class<? extends P> clazz, Map<String, Object> filter, StatementOrder[] order) throws DataLinkException {
        SourceListFactory sourcelistfactory = getSourceListFactory(clazz);
        SourceList<P, Map<String, Object>> sourcelist = sourcelistfactory.createSourceList(new RecordPojo(clazz), new RecordMap());
        sourcelist.setCriteria(getMetaDatas(sourcelistfactory.defProjection(), filter.keySet()));
        sourcelist.setOrder(order);
        return list(sourcelist, filter);
    }  
    
    public final <P> int insert(SourceTable<P> sourcetable, P value) throws DataLinkException {
        return exec(sourcetable.getStatementInsert(), value);
    }
    
    public final <P> int delete(SourceTable<P> sourcetable, P value) throws DataLinkException {
        return exec(sourcetable.getStatementDelete(), value);
    }

    public final <P> int update(SourceTable<P> sourcetable, P value) throws DataLinkException {
        return exec(sourcetable.getStatementUpdate(), value);
    }
    
    public final <P> P get(SourceTable<P> sourcetable, Object... key) throws DataLinkException { 
        return find(sourcetable.getStatementGet(), key);
    }
    
    public final <P> List<P> list(SourceList<P, ?> sourcelist) throws DataLinkException {
        return query(sourcelist.getStatementList());
    } 

    public final <P, F> List<P> list(SourceList<P, F> sourcelist, F filter) throws DataLinkException {
        return query(sourcelist.getStatementList(), filter);
    }
}
