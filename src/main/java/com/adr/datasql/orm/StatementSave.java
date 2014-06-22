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

import com.adr.datasql.StatementExec;
import com.adr.datasql.meta.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

/**
 *
 * @author adrian
 * @param <P>
 */
public class StatementSave<P> implements StatementExec<P> {
        
    private final StatementExec<P> queryinsert;
    private final StatementExec<P> queryupdate;
    private final Data<P> data;
    
    public StatementSave(Data<P> data) {
        
        this.data = data;   
        queryinsert = data.getDefinition().getStatementInsert(data);       
        queryupdate = data.getDefinition().getStatementUpdate(data);
    }

    @Override
    public int exec(Connection c, P params) throws SQLException {
        
        if (getKey(params) == null) {
            setKey(params, UUID.randomUUID().toString());
            return queryinsert.exec(c, params);
        } else {       
            return queryupdate.exec(c, params);
        }
    }   
    
    private Object getKey(P param) throws SQLException {
        for (Field f : data.getDefinition().getMetaDatas()) {  
            if (f.isKey()) {
                return data.getValue(f, param);
            }
        }     
        return null;
    }
    
    private void setKey(P param, Object key) throws SQLException {
        for (Field f : data.getDefinition().getMetaDatas()) {  
            if (f.isKey()) {
                data.setValue(f, param, key);
            }
        }           
    }
}
