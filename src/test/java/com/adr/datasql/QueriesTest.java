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

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author adrian
 */
public class QueriesTest {

    
    public QueriesTest() {
    }

    @Test
    public void initialTest() throws SQLException, ParseException {

        try (Session session = new Session(DataTestSuite.getDataSource().getConnection())) {

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
    public void query2Test() throws SQLException, ParseException {   
        
        try (Session session = new Session(DataTestSuite.getDataSource().getConnection())) {
            
            ProcExec<Object[]> insertMYTEST = new QueryArray("insert into mytest(id, code, name) values (?, ?, ?)")
                    .setParameters(Kind.STRING, Kind.STRING, Kind.STRING);
            
            session.exec(insertMYTEST, "one", "code one", "name one");
        }
    } 
    
    @BeforeClass
    public static void setUpClass() throws SQLException {   
   
        try (Session session = new Session(DataTestSuite.getDataSource().getConnection())) {
        
            session.exec(new QueryArray("create table mytest(id varchar(32), code varchar(128), name varchar(1024))"));
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
