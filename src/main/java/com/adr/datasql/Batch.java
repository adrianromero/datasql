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

package com.adr.datasql;

import com.adr.datasql.link.DataLink;
import com.adr.datasql.link.DataLinkException;
import java.util.List;

/**
 *
 * @author adrian
 * @param <R>
 * @param <P>
 */
public abstract class Batch <R, P> implements StatementExec<P>, StatementFind<R, P>, StatementQuery<R, P> {
    
    private Parameters<P> parameters = null;
    private Results<R> results = null;    
    
    @Override
    public int exec(DataLink link, P params) throws DataLinkException {
        throw new UnsupportedOperationException();
    }

    @Override
    public R find(DataLink link, P params) throws DataLinkException {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public List<R> query(DataLink link, P params) throws DataLinkException {
        throw new UnsupportedOperationException();
    }  

    /**
     * @return the parameters
     */
    public Parameters<P> getParameters() {
        return parameters;
    }

    /**
     * @param parameters the parameters to set
     * @return 
     */
    public Batch<R, P> setParameters(Parameters<P> parameters) {
        this.parameters = parameters;
        return this;
    }

    /**
     * @return the results
     */
    public Results<R> getResults() {
        return results;
    }

    /**
     * @param results the results to set
     * @return 
     */
    public Batch<R, P> setResults(Results<R> results) {
        this.results = results;
        return this;
    }      
}
