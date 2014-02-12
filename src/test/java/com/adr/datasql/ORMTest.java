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

package com.adr.datasql;

import com.adr.datasql.orm.Data;
import com.adr.datasql.orm.DataJson;
import com.adr.datasql.orm.Definition;
import com.adr.datasql.orm.Field;
import com.adr.datasql.orm.FieldKey;
import com.adr.datasql.orm.GetData;
import com.adr.datasql.orm.ListData;
import com.adr.datasql.orm.ORMSession;
import com.adr.datasql.orm.UpsertData;
import com.google.gson.JsonObject;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.joda.time.Instant;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author adrian
 */
public class ORMTest {


    @Test
    public void InsertPojo() throws SQLException {

        try (ORMSession session = DataTestSuite.newSession()) {  
            
            SamplePojo pojo = new SamplePojo();
            pojo.setId("pojoid");
            pojo.setCode("pojocode");
            pojo.setName("pojoname");
            
            session.insert(pojo);   
            
            SamplePojo returnpojo = session.get(SamplePojo.class, "pojoid");
            
            Assert.assertEquals(returnpojo.getId(), pojo.getId());  
            Assert.assertEquals(returnpojo.getCode(), pojo.getCode());  
            Assert.assertEquals(returnpojo.getName(), pojo.getName());  
            Assert.assertEquals(returnpojo.getValboolean(), pojo.getValboolean());  
            Assert.assertEquals(returnpojo.getValdate(), pojo.getValdate());  
            Assert.assertEquals(returnpojo.getValdecimal(), pojo.getValdecimal());  
            Assert.assertEquals(returnpojo.getValdouble(), pojo.getValdouble());  
            Assert.assertEquals(returnpojo.getValinteger(), pojo.getValinteger());  
        }
    }
    
    @Test
    public void insertJson() throws SQLException {
        
        try (Session session = DataTestSuite.newSession()) {
 
            // Metadata Definition
            Data<JsonObject> DATAJSON = new DataJson(new Definition(
                "com_adr_datasql_SamplePojo",
                new FieldKey("id", Kind.STRING),
                new Field("code", Kind.STRING),
                new Field("name", Kind.STRING)));   
 
            
            JsonObject value = new JsonObject();
            value.addProperty("id", "John");
            value.addProperty("code", "Smith");
            value.addProperty("name", "pepeto");          
            
            ProcExec<JsonObject> insertJson = new UpsertData<JsonObject>(DATAJSON);
            
            session.exec(insertJson, value);
        }
    }
    
    // @Test
    public void insertMap() throws SQLException {
    }   
    
    @Test
    public void findPojo() throws SQLException {
        

        try (Session session = DataTestSuite.newSession()) {        
            
            ProcFind<SamplePojo, Object[]> findSamplePojo = new GetData<SamplePojo>(SamplePojo.DATA);

            SamplePojo pojo = session.find(findSamplePojo, "pojoid");   
            
            System.out.println(pojo.toString());              
        }        
        
    }  
    
    @Test
    public void listPojo() throws SQLException {
        

        try (Session session = DataTestSuite.newSession()) { 
            
            ProcList<SamplePojo, Map<String, Object>> listSamplePojo = new ListData<SamplePojo>(SamplePojo.DATA);
                             
            List<SamplePojo> pojos = session.list(listSamplePojo);   
            
            System.out.println(pojos);              
        }        
        
    }  
    
    @Test
    public void filterPojo() throws SQLException {
        

        try (Session session = DataTestSuite.newSession()) { 
            
            ProcList<SamplePojo, Map<String, Object>> listSamplePojo = new ListData<SamplePojo>(SamplePojo.DATA);
                             
            Map<String, Object> filter = new HashMap<String, Object>();
            filter.put("code", "code x");
            
            List<SamplePojo> pojos = session.list(listSamplePojo, filter);   
            
            System.out.println(pojos);              
        }           
        
    }
    
    @Test
    public void filterJsonObject() throws SQLException {
        try (Session session = DataTestSuite.newSession()) { 
             // Metadata Definition
            Data<JsonObject> DATAJSON = new DataJson(new Definition(
                "com_adr_datasql_SamplePojo",
                new FieldKey("id", Kind.STRING),
                new Field("code", Kind.STRING),
                new Field("name", Kind.STRING)));  
            
            ProcList<JsonObject, Map<String, Object>> listjson = new ListData<JsonObject>(DATAJSON);
            
            Map<String, Object> filter = new HashMap<String, Object>();
            filter.put("code", "code x");
            List<JsonObject> results = session.list(listjson, filter);
            
            System.out.println(results);
        }
    }
    
    @BeforeClass
    public static void setUpClass() throws SQLException {   
   
        try (Session session = DataTestSuite.newSession()) { 
            session.exec(new QueryArray("create table com_adr_datasql_SamplePojo("
                    + "id varchar(32), "
                    + "code varchar(128), "
                    + "name varchar(1024), "
                    + "valdate timestamp, "
                    + "valdouble double precision, "
                    + "valdecimal decimal(10,2), "
                    + "valinteger integer, "
                    + "valboolean smallint)"));    
            ProcExec<Object[]> insertMyTest = new QueryArray("insert into com_adr_datasql_SamplePojo(id, code, name, valdate, valdouble, valdecimal, valinteger, valboolean) values (?, ?, ?, ?, ?, ?, ?, ?)")
                    .setParameters(Kind.STRING, Kind.STRING, Kind.STRING, Kind.TIMESTAMP, Kind.DOUBLE, Kind.DECIMAL, Kind.INT, Kind.BOOLEAN);
            
            session.exec(insertMyTest, "a", "code 1", "name a", Instant.parse("2014-01-01T18:00:32.212").toDate(), 12.23d, new BigDecimal("12.12"), 1234, true);
            session.exec(insertMyTest, "b", "code x", "name b", Instant.parse("2014-01-01T18:00:32.212").toDate(), 12.23d, new BigDecimal("12.12"), 1234, true);
            session.exec(insertMyTest, "c", "code x", "name c", Instant.parse("2014-01-01T18:00:32.212").toDate(), 12.23d, new BigDecimal("12.12"), 1234, true);
            session.exec(insertMyTest, "d", "code x", "name d", Instant.parse("2014-01-01T18:00:32.212").toDate(), 12.23d, new BigDecimal("12.12"), 1234, true);
            session.exec(insertMyTest, "e", "code x", "name e", Instant.parse("2014-01-01T18:00:32.212").toDate(), 12.23d, new BigDecimal("12.12"), 1234, true);                      
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
