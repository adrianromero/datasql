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
import com.adr.datasql.orm.InsertData;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
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
public class StartTest {

    
    public StartTest() {
    }

    @Test
    public void initialTest() throws SQLException, ParseException {


        try (Connection c = DataTestSuite.getDataSource().getConnection()) {
            System.out.println(c.toString());

            Session session = new Session(c);

            session.exec(new QueryArray("insert into mytest(id, code, name) values (?, ?, ?)"), "one", "code one", "name one");

            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("id", "two");
            parameters.put("code", "two code");
            parameters.put("name", "two name");
            session.exec(new QueryMap(new NSQL("insert into mytest(id, code, name) values (:id, :code, :name)")), parameters);


            Object[] result = session.find(new QueryArray("select id, code, name from mytest where id = ?"), "two");

            System.out.println("--> " + result[0] + ", " + result[1] + ", " + result[2]);
        }
    } 
    
    @Test
    public void InsertPojo() throws SQLException {
        try (Connection c = DataTestSuite.getDataSource().getConnection()) {        
            Session session = new Session(c);            
            
            ProcExec<SamplePojo> insertSamplePojo = new InsertData<SamplePojo>(SamplePojo.DATA);
            
            SamplePojo pojo = new SamplePojo();
            pojo.setId("pojoid");
            pojo.setCode("pojocode");
            pojo.setName("pojoname");
            
            session.exec(insertSamplePojo, pojo);   
            
            
            Object[] result = session.find(new QueryArray("select id, code, name from samplepojo where id = ?"), "pojoid");
            System.out.println("--> " + result[0] + ", " + result[1] + ", " + result[2]);              
        }
    }
    
    @Test
    public void InsertJson() throws SQLException {
        try (Connection c = DataTestSuite.getDataSource().getConnection()) {        
            Session session = new Session(c);  
 
            // Metadata Definition
            Data<JsonObject> DATAJSON = new DataJson(new Definition(
                "samplepojo",
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
            
            ProcExec<JsonObject> insertJson = new InsertData<JsonObject>(DATAJSON);
            
            session.exec(insertJson, value);
        }
    }
    
    // @Test
    public void InsertMap() throws SQLException {
    }     
    
    @BeforeClass
    public static void setUpClass() throws SQLException {   
   
        try (Connection c = DataTestSuite.getDataSource().getConnection()) {        
            Session session = new Session(c);  
        
            session.exec(new QueryArray("create table mytest(id varchar(32), code varchar(128), name varchar(1024))"));
            session.exec(new QueryArray("create table samplepojo(id varchar(32), code varchar(128), name varchar(1024))"));     
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
