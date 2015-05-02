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

package com.adr.datasql.meta;

import com.adr.datasql.Parameters;
import com.adr.datasql.Results;
import com.adr.datasql.link.CommandType;
import com.adr.datasql.link.DataLink;
import com.adr.datasql.link.DataLinkException;

/**
 *
 * @author adrian
 * @param <R>
 * @param <P>
 */
public class BasicStatementFind<R, P> implements StatementFind<R, P> {
    
    private Parameters<P> parameters = null;
    private Results<R> results = null;    
    private final CommandFind command;

    public BasicStatementFind(CommandFind command) {
        this.command = command;
    }
    public BasicStatementFind(String command) {
        this(new CommandGeneric(command));
    }   
    public BasicStatementFind(String command, CommandType type) {
        this(new CommandGeneric(command, type));
    }

    @Override
    public final R find(DataLink link, P params) throws DataLinkException {
        return command.find(link, results, parameters, params);
    }

    public Parameters<P> getParameters() {
        return parameters;
    }

    public BasicStatementFind<R, P> setParameters(Parameters<P> parameters) {
        this.parameters = parameters;
        return this;
    }

    public Results<R> getResults() {
        return results;
    }

    public BasicStatementFind<R, P> setResults(Results<R> results) {
        this.results = results;
        return this;
    }  
}
