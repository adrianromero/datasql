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

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.joda.time.Instant;
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
        try (Session session = DataTestSuite.newSession()) {
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("id", "two");
            parameters.put("code", "two code");
            parameters.put("name", "two name");
            session.exec(new QueryMap(new NSQL("insert into mytest(id, code, name) values (:id, :code, :name)")), parameters);

            Object[] result = session.find(new QueryArray("select id, code, name from mytest where id = ?"), "two");
            Assert.assertEquals("[two, two code, two name]", Arrays.toString(result));
        }
    }     
    
    @Test
    public void query2Test() throws SQLException, ParseException {   
        try (Session session = DataTestSuite.newSession()) {
            ProcExec<Object[]> insertMYTEST = new QueryArray("insert into mytest(id, code, name) values (?, ?, ?)")
                    .setParameters(Kind.STRING, Kind.STRING, Kind.STRING);
            session.exec(insertMYTEST, "one", "code one", "name one");
        }
    } 
    
    @Test
    public void querySelectWithKinds() throws SQLException, ParseException {   
        try (Session session = DataTestSuite.newSession()) {         
           ProcFind<Object[], Object[]> selectMyTest = new QueryArray(
                   "select id, code, name, valdate, valdouble, valdecimal, valinteger, valboolean from mytest where code = ?")
                   .setParameters(Kind.STRING)
                   .setResults(Kind.STRING, Kind.STRING, Kind.STRING, Kind.ISODATETIME, Kind.DOUBLE, Kind.DECIMAL, Kind.INT, Kind.BOOLEAN);       
           Object[] result = session.find(selectMyTest, "code 1");         
           Assert.assertEquals("[a, code 1, name a, 2014-01-01T18:00:32.212+01:00, 12.23, 12.12, 1234, true]", Arrays.toString(result));
       }
    } 
    
    @Test
    public void querySelectMetadata() throws SQLException, ParseException {   
        try (Session session = DataTestSuite.newSession()) {
           ProcFind<Object[], Object[]> selectMyTest = new QueryArray(
                   "select id, code, name, valdate, valdouble, valdecimal, valinteger, valboolean from mytest where code = ?");
           Object[] result = session.find(selectMyTest, "code 1");
           System.out.println(Arrays.toString(result)); // [a, code 1, name a, Tue Feb 11 18:37:52 CET 2014, 12.23, 12.12, 1234, 1]
        }
    }     
    
    @BeforeClass
    public static void setUpClass() throws SQLException {   
   
        try (Session session = DataTestSuite.newSession()) {
        
            session.exec(new QueryArray("create table mytest("
                    + "id varchar(32), "
                    + "code varchar(128), "
                    + "name varchar(1024), "
                    + "valdate timestamp, "
                    + "valdouble double precision, "
                    + "valdecimal decimal(10,2), "
                    + "valinteger integer, "
                    + "valboolean smallint)"));    
            ProcExec<Object[]> insertMyTest = new QueryArray("insert into mytest(id, code, name, valdate, valdouble, valdecimal, valinteger, valboolean) values (?, ?, ?, ?, ?, ?, ?, ?)")
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
