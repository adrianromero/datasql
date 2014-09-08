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

package com.adr.datasql.orm;

import com.adr.datasql.Session;
import com.adr.datasql.StatementExec;
import com.adr.datasql.meta.MetaData;
import com.adr.datasql.meta.SourceList;
import com.adr.datasql.meta.SourceTable;
import com.adr.datasql.meta.SourceTableFactory;
import com.adr.datasql.meta.StatementOrder;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author adrian
 */
public class ORMSession extends Session {
    
    private static final Logger logger = Logger.getLogger(ORMSession.class.getName());   
    
    public ORMSession(Connection c) {
        super(c);
    }

    public ORMSession(DataSource ds) throws SQLException {
        super(ds);
    }
    
    private <P> SourceTable<P> getSourceTable(Class<? extends P> clazz) throws SQLException {
        try {
            return ((SourceTableFactory) clazz.getField("SOURCETABLEFACTORY").get(null))
                    .createSourceTable(new RecordPojo(clazz));
        } catch (NoSuchFieldException 
                |IllegalArgumentException
                |IllegalAccessException
                |SecurityException ex) {
            throw new SQLException(ex);
        }
    }
    
    public <P> int insert(P value) throws SQLException {      
        return insert(getSourceTable((Class<? extends P>) value.getClass()), value);
    }
    
    public <P> int delete(P value) throws SQLException {
        return delete(getSourceTable((Class<? extends P>) value.getClass()), value);
    }
    
    public <P> int upsert(P value) throws SQLException {
        return upsert(getSourceTable((Class<? extends P>) value.getClass()), value);
    }
    
    public <P> int update(P value) throws SQLException {
        return update(getSourceTable((Class<? extends P>) value.getClass()), value); 
    }
    
    public <P> P get(Class<? extends P> clazz, Object... key) throws SQLException {       
        return get(getSourceTable(clazz), key);
    }
    
    public <P> List<P> list(Class<? extends P> clazz) throws SQLException {
        return list(getSourceTable(clazz));
    }  
    
    public <P> List<P> list(Class<? extends P> clazz, StatementOrder[] order) throws SQLException {
        return list(getSourceTable(clazz), order);
    }  
    
    public <P> List<P> list(Class<? extends P> clazz, Map<String, Object> filter) throws SQLException {
        return list(getSourceTable(clazz), filter);
    }  
    
    public <P> List<P> list(Class<? extends P> clazz, Map<String, Object> filter, StatementOrder[] order) throws SQLException {
        return list(getSourceTable(clazz), filter, order);
    }  
    
    public <P> int insert(SourceTable<P> sourcetable, P value) throws SQLException {
        return sourcetable.getStatementInsert().exec(c, value);
    }
    
    public <P> int delete(SourceTable<P> sourcetable, P value) throws SQLException {
        return sourcetable.getStatementDelete().exec(c, value);
    }
    
    public <P> int upsert(SourceTable<P> sourcetable, P value) throws SQLException {
        StatementExec<P> upsert = new StatementUpsert<P>(sourcetable); 
        return upsert.exec(c, value);
    }
    
    public <P> int update(SourceTable<P> sourcetable, P value) throws SQLException {
        return sourcetable.getStatementUpdate().exec(c, value);
    }
    
    public <P> P get(SourceTable<P> sourcetable, Object... key) throws SQLException { 
        return sourcetable.getStatementGet().find(c, key);
    }
    
    public <P> List<P> list(SourceList<P> sourcelist) throws SQLException {
        return sourcelist.getStatementList(null, null).query(c, null);
    } 
    
    public <P> List<P> list(SourceList<P> sourcelist, Map<String, Object> filter) throws SQLException {
        return sourcelist.getStatementList(getMetaDatas(sourcelist.getMetaDatas(), filter.keySet()), null).query(c, filter);
    } 

    public <P> List<P> list(SourceList<P> sourcelist, StatementOrder[] order) throws SQLException {
        return sourcelist.getStatementList(null, order).query(c, null);
    }
    
    public <P> List<P> list(SourceList<P> sourcelist, Map<String, Object> filter, StatementOrder[] order) throws SQLException {
        return sourcelist.getStatementList(getMetaDatas(sourcelist.getMetaDatas(), filter.keySet()), order).query(c, filter);
    }
    
    public <P> List<P> list(SourceList<P> sourcelist, MetaData[] filtermetadatas, Map<String, Object>  filter, StatementOrder[] order) throws SQLException {
        return sourcelist.getStatementList(filtermetadatas, order).query(c, filter);
    }
    
    private MetaData[] getMetaDatas(MetaData[] metadatas, Set<String> fieldsname) {
        ArrayList<MetaData> keys = new ArrayList<MetaData>();
        for (MetaData m: metadatas) {
            if (fieldsname.contains(m.getName())) {
                keys.add(m);
            }
        }        
        return keys.toArray(new MetaData[keys.size()]);
    }    
}
