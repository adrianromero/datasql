//    Data Command is a light JDBC wrapper.
//    Copyright (C) 2015 Adrián Romero Corchado.
//
//    This file is part of Data Command
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

package com.adr.datasql.meta;

import com.adr.datasql.Parameters;
import com.adr.datasql.Results;
import com.adr.datasql.link.CommandType;
import com.adr.datasql.link.DataLink;
import com.adr.datasql.link.DataLinkException;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author adrian
 */
public class CommandGeneric implements CommandQuery, CommandFind, CommandExec {

    private final String command;
    private final CommandType type;  
    
    public CommandGeneric(String command, CommandType type) {
        this.command = command;
        this.type = type;
    }
    
    public CommandGeneric(String command) {
        this(command, CommandType.STATIC);
    }
    
    public final String getCommand() {
        return command;
    }
    
    public final CommandType getType() {
        return type;
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
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.command);
        hash = 37 * hash + Objects.hashCode(this.type);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CommandGeneric other = (CommandGeneric) obj;
        if (!Objects.equals(this.command, other.command)) {
            return false;
        }
        return this.type == other.type;
    }    
}
