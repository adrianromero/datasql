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

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author adrian
 */
public class EntityListBuilder {
    
    protected String sentence;
    protected List<MetaData> metadatas = new ArrayList<MetaData>();
    
    public static EntityListBuilder create() {
        return new EntityListBuilder();
    }
    
    public EntityList build() {
        return new EntityList(sentence, metadatas.toArray(new MetaData[metadatas.size()]));
    }  
    
    public EntityListBuilder sentence(String sentence) {
        this.sentence = sentence;
        return this;
    }
    
    public EntityListBuilder metadatas(MetaData metadata) {
        this.metadatas.add(metadata);
        return this;
    }    
}
