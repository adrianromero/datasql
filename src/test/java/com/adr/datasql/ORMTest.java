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
import com.adr.datasql.orm.InsertData;
import com.adr.datasql.orm.ListData;
import com.adr.datasql.orm.UpsertData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonObject;
import org.junit.After;
import org.junit.AfterClass;
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

        try (Session session = DataTestSuite.newSession()) {        
            
            ProcExec<SamplePojo> insertSamplePojo = new InsertData<SamplePojo>(SamplePojo.DATA);
            
            SamplePojo pojo = new SamplePojo();
            pojo.setId("pojoid");
            pojo.setCode("pojocode");
            pojo.setName("pojoname");
            
            session.exec(insertSamplePojo, pojo);   
            
            
            Object[] result = session.find(new QueryArray("select id, code, name from com_adr_datasql_SamplePojo where id = ?"), "pojoid");
            System.out.println("--> " + result[0] + ", " + result[1] + ", " + result[2]);              
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
            
            // Object definition          
//            JsonReader jsonReader = Json.createReader(new StringReader("{}"));
//            JsonObject value = jsonReader.readObject();
//            jsonReader.close();     
            
            JsonObject value = Json.createObjectBuilder()
                .add("id", "John")
                .add("code", "Smith")
                .add("name", "pepeto")
                .build();            
            
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
    
    @BeforeClass
    public static void setUpClass() throws SQLException {   
   
        try (Session session = DataTestSuite.newSession()) { 
       
            session.exec(new QueryArray("create table com_adr_datasql_SamplePojo(id varchar(32), code varchar(128), name varchar(1024))"));    
            session.exec(new QueryArray("insert into com_adr_datasql_SamplePojo(id, code, name) values (?, ?, ?)"), "a", "code x", "name a");
            session.exec(new QueryArray("insert into com_adr_datasql_SamplePojo(id, code, name) values (?, ?, ?)"), "b", "code x", "name b");
            session.exec(new QueryArray("insert into com_adr_datasql_SamplePojo(id, code, name) values (?, ?, ?)"), "c", "code x", "name c");
            session.exec(new QueryArray("insert into com_adr_datasql_SamplePojo(id, code, name) values (?, ?, ?)"), "d", "code x", "name d");
            session.exec(new QueryArray("insert into com_adr_datasql_SamplePojo(id, code, name) values (?, ?, ?)"), "e", "code x", "name e");
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
