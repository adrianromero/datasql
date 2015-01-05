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

package com.adr.datasql.link;

import com.adr.datasql.Command;
import com.adr.datasql.Parameters;
import com.adr.datasql.Results;
import java.util.List;

/**
 *
 * @author adrian
 */
public interface DataLink extends AutoCloseable {
    public <P> int exec(Command command, Parameters<P> parameters, P params) throws DataLinkException;
    public <R,P> R find(Command command, Results<R> results, Parameters<P> parameters, P params) throws DataLinkException;
    public <R,P> List<R> query(Command command, Results<R> results, Parameters<P> parameters, P params) throws DataLinkException;
    @Override
    public void close() throws DataLinkException;
}
