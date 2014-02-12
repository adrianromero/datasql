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

import com.adr.datasql.ProcExec;
import com.adr.datasql.ProcFind;
import com.adr.datasql.ProcList;
import com.adr.datasql.Session;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 *
 * @author adrian
 */
public class ORMSession extends Session {
    
    private static final Logger logger = Logger.getLogger(ORMSession.class.getName());   
    
    public ORMSession(Connection c) {
        super(c);
    }
    
    private <P> Data<P> getData(Class<? extends P> clazz) throws SQLException {
        try {
            return (Data<P>) clazz.getField("DATA").get(null);
        } catch (NoSuchFieldException 
                |IllegalArgumentException
                |IllegalAccessException
                |SecurityException ex) {
            throw new SQLException(ex);
        }
    }
    
    public <P> int insert(P value) throws SQLException {      
        return insert(getData((Class<? extends P>) value.getClass()), value);
    }
    
    public <P> int delete(P value) throws SQLException {
        return delete(getData((Class<? extends P>) value.getClass()), value);
    }
    
    public <P> int save(P value) throws SQLException {
        return save(getData((Class<? extends P>) value.getClass()), value);
    }
    
    public <P> int upsert(P value) throws SQLException {
        return upsert(getData((Class<? extends P>) value.getClass()), value);
    }
    
    public <P> int update(P value) throws SQLException {
        return update(getData((Class<? extends P>) value.getClass()), value); 
    }
    
    public <P> P get(Class<? extends P> clazz, Object... key) throws SQLException {       
        return get(getData(clazz), key);
    }
    
    public <P> List<P> list(Class<? extends P> clazz, Map<String, Object> filter) throws SQLException {
        return list(getData(clazz), filter);
    }  
    
    public <P> int insert(Data<P> data, P value) throws SQLException {      
        ProcExec<P> insert = new InsertData<P>(data); 
        return insert.exec(c, value);
    }
    
    public <P> int delete(Data<P> data, P value) throws SQLException {
        ProcExec<P> delete = new DeleteData<P>(data); 
        return delete.exec(c, value);
    }
    
    public <P> int save(Data<P> data, P value) throws SQLException {
        ProcExec<P> save = new SaveData<P>(data); 
        return save.exec(c, value);
    }
    
    public <P> int upsert(Data<P> data, P value) throws SQLException {
        ProcExec<P> upsert = new UpsertData<P>(data); 
        return upsert.exec(c, value);
    }
    
    public <P> int update(Data<P> data, P value) throws SQLException {
        ProcExec<P> update = new UpdateData<P>(data); 
        return update.exec(c, value);
    }
    
    public <P> P get(Data<P> data, Object... key) throws SQLException {       
        ProcFind<P, Object[]> get = new GetData<P>(data);
        return get.find(c, key);
    }
    
    public <P> List<P> list(Data<P> data, Map<String, Object> filter) throws SQLException {
        ProcList<P, Map<String, Object>> list = new ListData<P>(data);
        return list.list(c, filter);
    }  
}
