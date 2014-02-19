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
import com.adr.datasql.Query;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

/**
 *
 * @author adrian
 * @param <P>
 */
public class SaveData<P> implements StatementExec<P> {
        
    private final Query<Void, P> queryinsert;
    private final Query<Void, P> queryupdate;
    private final Data<P> data;
    
    public SaveData(Data<P> data) {
        
        this.data = data;   
        queryinsert = new Query<Void, P>(data.getDefinition().getStatementInsert())
                .setParameters(data);       
        queryupdate = new Query<Void, P>(data.getDefinition().getStatementUpdate())
                .setParameters(data);
    }

    @Override
    public int exec(Connection c, P params) throws SQLException {
        
        if (data.getKey(params) == null) {
            data.setKey(params, UUID.randomUUID().toString());
            return queryinsert.exec(c, params);
        } else {       
            return queryupdate.exec(c, params);
        }
    }   
}
