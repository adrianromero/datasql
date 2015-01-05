//    Data SQL is a light JDBC wrapper.
//    Copyright (C) 2014-2015 Adrián Romero Corchado.
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
import com.adr.datasql.link.DataLink;
import com.adr.datasql.link.DataLinkException;
import com.adr.datasql.meta.SourceTable;

/**
 *
 * @author adrian
 * @param <P>
 */
public class StatementUpsert<P> implements StatementExec<P> {
        
    private final StatementExec<P> queryinsert;
    private final StatementExec<P> queryupdate;
    
    public StatementUpsert(SourceTable<P> sourcetable) {
        
        queryinsert = sourcetable.getStatementInsert();       
        queryupdate = sourcetable.getStatementUpdate();
    }

    @Override
    public int exec(DataLink link, P params) throws DataLinkException {
        
        int i = queryupdate.exec(link, params);
        if (i == 0) {
            return queryinsert.exec(link, params);
        } else {
            return i;
        }
    }   
}
