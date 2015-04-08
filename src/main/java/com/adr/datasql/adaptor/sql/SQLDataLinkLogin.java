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

package com.adr.datasql.adaptor.sql;

import com.adr.datasql.Parameters;
import com.adr.datasql.Results;
import com.adr.datasql.link.DataLinkException;
import com.adr.datasql.meta.CommandGeneric;
import com.adr.datasql.meta.CommandSQL;
import java.util.List;

/**
 *
 * @author adrian
 */
public class SQLDataLinkLogin extends SQLDataLink {
    
    public static final int QUERY_VISIBLE_USERS = 1230001;
    public static final int FIND_USER_PASWORD = 1230002;
    public static final int QUERY_PERMISSIONS = 1230003;
    
    @Override
    public <R, P> List<R> query(CommandGeneric command, Results<R> results, Parameters<P> parameters, P params) throws DataLinkException {
        if ("QUERY_VISIBLE_USERS".equals(command.getCommand())) {
            return query(new CommandSQL("SELECT ID, NAME, DISPLAYNAME, IMAGE FROM USER WHERE VISIBLE = TRUE AND ACTIVE = TRUE ORDER BY NAME"), results, parameters, params);
        } else if ("QUERY_PERMISSIONS".equals(command.getCommand())) {
            return query(new CommandSQL("SELECT S.CODE, S.NAME FROM SUBJECT S JOIN PERMISSION P ON S.ID = P.SUBJECT_ID WHERE P.ROLE_ID = ?", "ROLE_ID"), results, parameters, params);
        } else {
            return super.query(command, results, parameters, params); // call parent.
        }
    }  
}
