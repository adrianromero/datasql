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

package com.adr.datasql.meta;

import com.adr.datasql.data.MetaData;
import com.adr.datasql.StatementQuery;

/**
 *
 * @author adrian
 * @param <R>
 * @param <F>
 */
public interface SourceList<R, F> {
    
    public MetaData[] defProjection();
    public MetaData[] defCriteria();
    public StatementOrder[] defOrder();
    
    public void setProjection(MetaData[] projection);
    public void setCriteria(MetaData[] criteria);
    public void setOrder(StatementOrder[] order);   
    public StatementQuery<R, F> getStatementList(); 
}
