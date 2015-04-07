//    Data SQL is a light JDBC wrapper.
//    Copyright (C) 2015 Adrián Romero Corchado.
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
import com.adr.datasql.meta.StatementExec;
import com.adr.datasql.meta.StatementFind;
import com.adr.datasql.meta.StatementQuery;
import com.adr.datasql.link.DataLink;
import com.adr.datasql.link.DataLinkException;
import java.util.List;

/**
 *
 * @author adrian
 */
public class SQLStatement<R, P> implements StatementFind<R, P>, StatementQuery<R, P>, StatementExec<P> {
   
    private Parameters<P> parameters = null;
    private Results<R> results = null;    
    private final SQLCommand command; 
    
    public SQLStatement(SQLCommand command) {
        this.command = command;
    }
    
    public SQLStatement(String command, String... paramnames) {
        this.command = new SQLCommand(command, paramnames);
    }    
    
    public Parameters<P> getParameters() {
        return parameters;
    }

    public SQLStatement<R, P> setParameters(Parameters<P> parameters) {
        this.parameters = parameters;
        return this;
    }

    public Results<R> getResults() {
        return results;
    }

    public SQLStatement<R, P> setResults(Results<R> results) {
        this.results = results;
        return this;
    }     
    
    @Override
    public final int exec(DataLink link, P params) throws DataLinkException {
        return link.exec(command, getParameters(), params);
    }

    @Override
    public final R find(DataLink link, P params) throws DataLinkException {
        return link.find(command, getResults(), getParameters(), params);
    }
    
    @Override
    public final List<R> query(DataLink link, P params) throws DataLinkException {
        return link.query(command, getResults(), getParameters(), params);
    }     
}
