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
import com.adr.datasql.StatementExec;
import com.adr.datasql.QueryArray;
import com.adr.datasql.Session;
import com.adr.datasql.derby.DataTestSuite;
import com.adr.datasql.orm.RecordJson;
import com.adr.datasql.meta.Entity;
import com.adr.datasql.meta.Field;
import com.adr.datasql.meta.SourceTable;
import com.adr.datasql.orm.ORMSession;
import com.google.gson.JsonObject;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author adrian
 */
public class JsonORMTest {
    
    public final static SourceTable<JsonObject> SOURCETABLE = new Entity(
            "samplejson",
            new Field("id", Kind.STRING, true),
            new Field("code", Kind.STRING),
            new Field("name", Kind.STRING),
            new Field("valdate", Kind.TIMESTAMP),
            new Field("valdouble", Kind.DOUBLE),
            new Field("valdecimal", Kind.DECIMAL),
            new Field("valinteger", Kind.INT),
            new Field("valboolean", Kind.BOOLEAN)).createSourceTable(new RecordJson());
    
    @Test
    public void insertJson() throws SQLException {
        
        try (ORMSession session = DataTestSuite.newSession()) {  
           
            JsonObject value = new JsonObject();
            value.addProperty("id", "John");
            value.addProperty("code", "Smith");
            value.addProperty("name", "pepeto");     
            value.addProperty("valdate", "2014-01-01T18:00:32.212Z");
            value.addProperty("valdouble", 12.4D);

            
            session.upsert(SOURCETABLE, value);
            
            JsonObject returnvalue = session.get(SOURCETABLE, "John");
            
            Assert.assertEquals(
                    "{\"id\":\"John\",\"code\":\"Smith\",\"name\":\"pepeto\",\"valdate\":\"2014-01-01T18:00:32.212Z\",\"valdouble\":12.4,\"valdecimal\":null,\"valinteger\":null,\"valboolean\":null}", 
                    returnvalue.toString());
        }
    }
    
    @Test
    public void filterJsonObject() throws SQLException {
        try (ORMSession session = DataTestSuite.newSession()) { 
            
            Map<String, Object> filter = new HashMap<String, Object>();
            filter.put("code", "code x");
            
            List<JsonObject> results = session.list(SOURCETABLE, filter);
            
            System.out.println(results);
        }
    }
    
    @BeforeClass
    public static void setUpClass() throws SQLException {   
   
        try (Session session = DataTestSuite.newSession()) { 
            session.exec(new QueryArray("create table samplejson("
                    + "id varchar(32), "
                    + "code varchar(128), "
                    + "name varchar(1024), "
                    + "valdate timestamp, "
                    + "valdouble double precision, "
                    + "valdecimal decimal(10,2), "
                    + "valinteger integer, "
                    + "valboolean smallint)"));    
            StatementExec<Object[]> insertMyTest = new QueryArray("insert into samplejson(id, code, name, valdate, valdouble, valdecimal, valinteger, valboolean) values (?, ?, ?, ?, ?, ?, ?, ?)")
                    .setParameters(Kind.STRING, Kind.STRING, Kind.STRING, Kind.TIMESTAMP, Kind.DOUBLE, Kind.DECIMAL, Kind.INT, Kind.BOOLEAN);
            
            session.exec(insertMyTest, "a", "code 1", "name a", new Date(Instant.parse("2014-01-01T18:00:32.212Z").toEpochMilli()), 12.23d, new BigDecimal("12.12"), 1234, true);
            session.exec(insertMyTest, "b", "code x", "name b", new Date(Instant.parse("2014-01-01T18:00:32.212Z").toEpochMilli()), 12.23d, new BigDecimal("12.12"), 1234, true);
            session.exec(insertMyTest, "c", "code x", "name c", new Date(Instant.parse("2014-01-01T18:00:32.212Z").toEpochMilli()), 12.23d, new BigDecimal("12.12"), 1234, true);
            session.exec(insertMyTest, "d", "code x", "name d", new Date(Instant.parse("2014-01-01T18:00:32.212Z").toEpochMilli()), 12.23d, new BigDecimal("12.12"), 1234, true);
            session.exec(insertMyTest, "e", "code x", "name e", new Date(Instant.parse("2014-01-01T18:00:32.212Z").toEpochMilli()), 12.23d, new BigDecimal("12.12"), 1234, true);                      
            
        }
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {  
    }
    
    @After
    public void tearDown() {
    }     
}
