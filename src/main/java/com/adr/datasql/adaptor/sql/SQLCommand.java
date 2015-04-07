//    Data SQLCommand is a light JDBC wrapper.
//    Copyright (C) 2014-2015 Adrián Romero Corchado.
//
//    This file is part of Data SQLCommand
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
import com.adr.datasql.link.DataLink;
import com.adr.datasql.link.DataLinkException;
import com.adr.datasql.meta.CommandExec;
import com.adr.datasql.meta.CommandFind;
import com.adr.datasql.meta.CommandQuery;
import java.util.List;

/**
 *
 * @author adrian
 */
public class SQLCommand implements CommandQuery, CommandFind, CommandExec {

    private String command;
    private String[] paramnames;  
    
    public SQLCommand(String command, String... paramnames) {
        init(command, paramnames);
    }
    
    protected SQLCommand() {
    }
    
    protected final void init(String command, String... paramnames) {
        this.command = command;
        this.paramnames = paramnames == null ? new String[0] : paramnames;        
    }
    
    public final String getCommand() {
        return command;
    }

    public final String[] getParamNames() {
        return paramnames;
    } 

    @Override
    public <R, P> List<R> query(DataLink link, Results<R> results, Parameters<P> parameters, P params) throws DataLinkException {
        return link.query(this, results, parameters, params);
    }
    
    @Override
    public <R, P> R find(DataLink link, Results<R> results, Parameters<P> parameters, P params) throws DataLinkException {
        return link.find(this, results, parameters, params);
    } 

    @Override
    public <P> int exec(DataLink link, Parameters<P> parameters, P params) throws DataLinkException {
        return link.exec(this, parameters, params);
    }
    
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(getCommand());
        s.append('[');
        for(int i = 0; i < getParamNames().length; i++) {
            if (i > 0) {
                s.append(", ");
            }
            s.append(getParamNames()[i]);
        }
        s.append(']');
        return s.toString();
    }         
}
