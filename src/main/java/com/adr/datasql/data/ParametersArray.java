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

package com.adr.datasql.data;

import com.adr.datasql.Kind;
import com.adr.datasql.KindParameters;
import com.adr.datasql.Parameters;
import com.adr.datasql.link.DataLinkException;

/**
 *
 * @author adrian
 */
public class ParametersArray implements Parameters<Object[]> {
    
    private final MetaData[] metadatas;
    
    public ParametersArray(MetaData... metadatas) {
        this.metadatas = metadatas;
    }
    
    public ParametersArray(Kind... kinds) {
        this.metadatas = MetaData.fromKinds(kinds);
    }
    
    @Override
    public void write(KindParameters dp, Object[] param) throws DataLinkException {      
        for(int i = 0; i < metadatas.length; i++) {
            metadatas[i].getKind().set(dp, i + 1, i < param.length ? param[i] : null);
        }
    }
}

