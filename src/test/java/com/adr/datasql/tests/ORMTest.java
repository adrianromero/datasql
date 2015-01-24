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

package com.adr.datasql.tests;

import com.adr.datasql.Kind;
import com.adr.datasql.StatementExec;
import com.adr.datasql.QueryArray;
import com.adr.datasql.databases.DataBase;
import com.adr.datasql.link.DataLink;
import com.adr.datasql.link.DataLinkException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public void InsertPojo() throws DataLinkException {

        try (DataLink link = DataBase.getDataLink()) {  
            
            SamplePojo pojo = new SamplePojo();
            pojo.setId("pojoid");
            pojo.setCode("pojocode");
            pojo.setName("pojoname");
            
            link.insert(pojo);   
            
            SamplePojo returnpojo = link.get(SamplePojo.class, "pojoid");
            
            Assert.assertEquals(returnpojo.getId(), pojo.getId());  
            Assert.assertEquals(returnpojo.getCode(), pojo.getCode());  
            Assert.assertEquals(returnpojo.getName(), pojo.getName());  
            Assert.assertEquals(returnpojo.isActive(), pojo.isActive());  
            Assert.assertEquals(returnpojo.getStartdate(), pojo.getStartdate());  
            Assert.assertEquals(returnpojo.getAmount(), pojo.getAmount());  
            Assert.assertEquals(returnpojo.getWeight(), pojo.getWeight());  
            Assert.assertEquals(returnpojo.getLine(), pojo.getLine());  
        }
    }  
    
    @Test
    public void findPojo() throws DataLinkException {
        
        try (DataLink link = DataBase.getDataLink()) {        

            SamplePojo pojo = link.get(SamplePojo.class, "a");   
            
            Assert.assertEquals("a", pojo.getId());  
            Assert.assertEquals("code 1", pojo.getCode());             
            Assert.assertEquals("name a", pojo.getName());             
        }        
    }  
    
    @Test
    public void listPojo() throws DataLinkException {      

        try (DataLink link = DataBase.getDataLink()) { 
                                     
            List<SamplePojo> pojos = link.list(SamplePojo.class);   
            
            System.out.println(pojos);              
        }        
    }  
    
    @Test
    public void filterPojo() throws DataLinkException {
        
        try (DataLink link = DataBase.getDataLink()) { 
                             
            Map<String, Object> filter = new HashMap<String, Object>();
            filter.put("code", "code x");
            
            List<SamplePojo> pojos = link.list(SamplePojo.class, filter);   
            
            System.out.println("filter pojos " + pojos);          
            Assert.assertEquals("Sample pojos codex", 4, pojos.size());
        }               
    }
    
    @Test
    public void pojoStatements() throws DataLinkException {
        
        try (DataLink link = DataBase.getDataLink()) {
            // Defining a new SamplePojo
            SamplePojo pojo = new SamplePojo();
            pojo.setId("id-99");
            pojo.setName("name for object");
            pojo.setLine(10);
            pojo.setWeight(50.0);
            // Insert
            link.insert(pojo);  
            // Get an instance
            SamplePojo returnpojo = link.get(SamplePojo.class, "id-99");
            
            Assert.assertEquals(pojo.getId(), returnpojo.getId());
            Assert.assertEquals(pojo.getName(), returnpojo.getName());
            Assert.assertEquals(pojo.getLine(), returnpojo.getLine());
            Assert.assertEquals(pojo.getWeight(), returnpojo.getWeight());
        }  
    }      
    @BeforeClass
    public static void setUpClass() throws DataLinkException {   
   
        try (DataLink link = DataBase.getDataLink()) { 
            
            link.exec(new QueryArray("drop table if exists samplepojo"));
            link.exec(new QueryArray("create table samplepojo("
                    + "id varchar(32), "
                    + "code varchar(128), "
                    + "name varchar(1024), "
                    + "startdate timestamp, "
                    + "weight double precision, "
                    + "amount decimal(10,2), "
                    + "line integer, "
                    + "active smallint,"
                    + "primary key(id))"));    
            StatementExec<Object[]> insertMyTest = new QueryArray("insert into samplepojo(id, code, name, startdate, weight, amount, line, active) values (?, ?, ?, ?, ?, ?, ?, ?)")
                    .setParameters(Kind.STRING, Kind.STRING, Kind.STRING, Kind.TIMESTAMP, Kind.DOUBLE, Kind.DECIMAL, Kind.INT, Kind.BOOLEAN);
            
            link.exec(insertMyTest, "a", "code 1", "name a", new Date(Instant.parse("2014-01-01T18:00:32.212Z").toEpochMilli()), 12.23d, new BigDecimal("12.12"), 1234, true);
            link.exec(insertMyTest, "b", "code x", "name b", new Date(Instant.parse("2014-01-01T18:00:32.212Z").toEpochMilli()), 12.23d, new BigDecimal("12.12"), 1234, true);
            link.exec(insertMyTest, "c", "code x", "name c", new Date(Instant.parse("2014-01-01T18:00:32.212Z").toEpochMilli()), 12.23d, new BigDecimal("12.12"), 1234, true);
            link.exec(insertMyTest, "d", "code x", "name d", new Date(Instant.parse("2014-01-01T18:00:32.212Z").toEpochMilli()), 12.23d, new BigDecimal("12.12"), 1234, true);
            link.exec(insertMyTest, "e", "code x", "name e", new Date(Instant.parse("2014-01-01T18:00:32.212Z").toEpochMilli()), 12.23d, new BigDecimal("12.12"), 1234, true);                      
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
