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

    @Override
    public <R, P> List<R> query(CommandGeneric command, Results<R> results, Parameters<P> parameters, P params) throws DataLinkException {
        if ("QUERY_VISIBLE_USERS".equals(command.getCommand())) {
            return query(new CommandSQL("select id, name, displayname, image from user where visible = true and active = true order by name"), results, parameters, params);
        } else if ("QUERY_PERMISSIONS".equals(command.getCommand())) {
            return query(new CommandSQL("select s.code, s.name from subject s join permission p on s.id = p.subject_id where p.role_id = ?", "role_id"), results, parameters, params);
        } else {
            return super.query(command, results, parameters, params); // call parent.
        }
    }  
    
    @Override
    public <P> int exec(CommandGeneric command, Parameters<P> parameters, P params) throws DataLinkException {
        if ("SAVE_USER".equals(command.getCommand())) {
            // just displayname, password, image and visible
            return exec(new CommandSQL("update user set displayname = ?, password = ?, visible = ?, image = ? where id = ?",
                "displayname", "password", "visible", "image", "id"), parameters, params);
        } else {
            return super.exec(command, parameters, params); // call parent.
        }
    }

    @Override
    public <R, P> R find(CommandGeneric command, Results<R> results, Parameters<P> parameters, P params) throws DataLinkException {
        if ("GET_USER".equals(command.getCommand())) {
            // it is a view: role_id, role, (active == true)...
            return find(new CommandSQL(               
                    "select u.id, u.name, u.displayname, u.password, u.codecard, u.role_id, r.name as role, u.visible, u.image " +
                    "from user u join role r on u.role_id = r.id " +
                    "where u.name = ? and u.active = true", "name"), results, parameters, params);
        } else {
            return super.find(command, results, parameters, params); // call parent.
        }
    }    
}
