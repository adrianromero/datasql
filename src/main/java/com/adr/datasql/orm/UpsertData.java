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
import com.adr.datasql.Query;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author adrian
 * @param <P>
 */
public class UpsertData<P> implements ProcExec<P> {
        
    private final Query<Void, P> queryinsert;
    private final Query<Void, P> queryupdate;
    
    public UpsertData(Data<P> data) {
        
        queryinsert = new Query<Void, P>(data.getDefinition().getStatementInsert())
                .setParameters(data);       
        queryupdate = new Query<Void, P>(data.getDefinition().getStatementUpdate())
                .setParameters(data);
    }

    @Override
    public int exec(Connection c, P params) throws SQLException {
        
        int i = queryupdate.exec(c, params);
        if (i == 0) {
            return queryinsert.exec(c, params);
        } else {
            return i;
        }
    }   
}
