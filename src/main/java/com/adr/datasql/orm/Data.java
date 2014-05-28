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

package com.adr.datasql.orm;

import com.adr.datasql.KindParameters;
import com.adr.datasql.KindResults;
import com.adr.datasql.Parameters;
import com.adr.datasql.Results;
import com.adr.datasql.meta.Entity;
import com.adr.datasql.meta.MetaData;
import java.sql.SQLException;

/**
 *
 * @author adrian
 * @param <P>
 */
public abstract class Data<P> implements Parameters<P>, Results<P> {
    
    private final Entity entity;
    
    public Data(Entity entity) {
        this.entity = entity;
    }
    
    public Entity getDefinition() {
        return entity;
    }
    
    public abstract Object getValue(MetaData f, P param) throws SQLException;
    public abstract void setValue(MetaData f, P param, Object value) throws SQLException;
    public abstract P create() throws SQLException;

    @Override
    public void write(KindParameters dp, P param) throws SQLException {
        for (MetaData md : entity.getFields()) {           
            md.getKind().set(dp, md.getName(), getValue(md, param));              
        }
    }     

    @Override
    public P read(KindResults dp) throws SQLException {
        P param = create();
        for (MetaData md : entity.getFields()) {    
            setValue(md, param, md.getKind().get(dp, md.getName()));              
        }
        return param;
    }
}
