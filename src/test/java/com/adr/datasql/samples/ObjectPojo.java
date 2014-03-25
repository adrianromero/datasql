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

package com.adr.datasql.samples;

import com.adr.datasql.Kind;
import com.adr.datasql.orm.Data;
import com.adr.datasql.orm.DataPojo;
import com.adr.datasql.meta.Definition;
import com.adr.datasql.meta.Field;
import com.adr.datasql.meta.FieldKey;

/**
 *
 * @author adrian
 */
public class ObjectPojo {
    // ObjectPojo Configuration   
    public final static Data<ObjectPojo> DATA = new DataPojo(new Definition(
        "com_adr_datasql_samples_ObjectPojo",
        new FieldKey("id", Kind.STRING),
        new Field("name", Kind.STRING),            
        new Field("line", Kind.INT),
        new Field("amount", Kind.DOUBLE)));
    // Fields    
    private String id;
    private String name;
    private Integer line;
    private Double amount;

    // Access methods
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getLine() { return line; }
    public void setLine(Integer line) { this.line = line; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
}