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

/**
 *
 * @author adrian
 * @param <P>
 */
public class DeleteData<P> implements StatementExec<P> {
        
    private final Query<Void, P> querydelete;
    
    public DeleteData(Data<P> data) {
        
        querydelete = new Query<Void,P>(data.getDefinition().getStatementDelete())
                .setParameters(data);
    }

    @Override
    public int exec(Connection c, P params) throws SQLException {
        return querydelete.exec(c, params);
    }
    
}
