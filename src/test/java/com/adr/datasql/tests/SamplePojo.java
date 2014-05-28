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

package com.adr.datasql.tests;

import com.adr.datasql.Kind;
import com.adr.datasql.orm.Data;
import com.adr.datasql.orm.DataPojo;
import com.adr.datasql.meta.Entity;
import com.adr.datasql.meta.Field;
import com.adr.datasql.meta.FieldKey;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author adrian
 */
public class SamplePojo {
    private String id;
    private String name;
    private String code;
    private Date valdate;
    private Double valdouble;
    private BigDecimal valdecimal;
    private Integer valinteger;
    private Boolean valboolean;
    
    public final static Data<SamplePojo> DATA = new DataPojo(SamplePojo.class,
        new Entity(
            "com_adr_datasql_tests_SamplePojo",
            new FieldKey("id", Kind.STRING),
            new Field("code", Kind.STRING),
            new Field("name", Kind.STRING),
            new Field("valdate", Kind.TIMESTAMP),
            new Field("valdouble", Kind.DOUBLE),
            new Field("valdecimal", Kind.DECIMAL),
            new Field("valinteger", Kind.INT),
            new Field("valboolean", Kind.BOOLEAN)));

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }
    
    /**
     * @return the valdate
     */
    public Date getValdate() {
        return valdate;
    }

    /**
     * @param valdate the valdate to set
     */
    public void setValdate(Date valdate) {
        this.valdate = valdate;
    }

    /**
     * @return the valdouble
     */
    public Double getValdouble() {
        return valdouble;
    }

    /**
     * @param valdouble the valdouble to set
     */
    public void setValdouble(Double valdouble) {
        this.valdouble = valdouble;
    }

    /**
     * @return the valboolean
     */
    public Boolean isValboolean() {
        return valboolean;
    }

    /**
     * @param valboolean the valboolean to set
     */
    public void setValboolean(Boolean valboolean) {
        this.valboolean = valboolean;
    }    

    /**
     * @return the valdecimal
     */
    public BigDecimal getValdecimal() {
        return valdecimal;
    }

    /**
     * @param valdecimal the valdecimal to set
     */
    public void setValdecimal(BigDecimal valdecimal) {
        this.valdecimal = valdecimal;
    }

    /**
     * @return the valinteger
     */
    public Integer getValinteger() {
        return valinteger;
    }

    /**
     * @param valinteger the valinteger to set
     */
    public void setValinteger(Integer valinteger) {
        this.valinteger = valinteger;
    }
    
    @Override
    public String toString() {
        return "(" + id + ", " + code + ", " + name + ")";
    }        
}
