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

import com.adr.datasql.KindParameters;
import com.adr.datasql.Parameters;
import com.adr.datasql.link.DataLinkException;
import java.util.Map;

/**
 *
 * @author adrian
 */
public class ParametersMap implements Parameters<Map<String, Object>> {
    
    private final MetaData[] metadatas;
    
    public ParametersMap(MetaData... metadatas) {
        this.metadatas = metadatas;
    }
    
    @Override
    public void write(KindParameters dp, Map<String, Object> param) throws DataLinkException {
        for (MetaData metadata : metadatas) {
            metadata.getKind().set(dp, metadata.getName(), param.get(metadata.getName()));
        }
    } 
}
