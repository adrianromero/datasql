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
import com.adr.datasql.Query;
import com.adr.datasql.link.SQLCommandNamed;
import com.adr.datasql.StatementExec;
import com.adr.datasql.StatementFind;
import com.adr.datasql.QueryArray;
import com.adr.datasql.QueryMap;
import com.adr.datasql.data.ParametersDouble;
import com.adr.datasql.data.ResultsInteger;
import com.adr.datasql.databases.DataBase;
import com.adr.datasql.link.DataLink;
import com.adr.datasql.link.DataLinkException;
import com.adr.datasql.link.SQLCommand;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.Instant;
import java.util.Arrays;
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
public class QueriesTest {

    public QueriesTest() {
    }

    @Test
    public void initialTest() throws DataLinkException, ParseException {
        
        try (DataLink link = DataBase.getDataLink()) {
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("id", "two");
            parameters.put("code", "two code");
            parameters.put("name", "two name");
            link.exec(new QueryMap(new SQLCommandNamed("insert into mytest(id, code, name) values (:id, :code, :name)")), parameters);

            Object[] result = link.find(new QueryArray("select id, code, name from mytest where id = ?"), "two");
            Assert.assertEquals("[two, two code, two name]", Arrays.toString(result));
        }
    }     
    
    @Test
    public void query2Test() throws DataLinkException, ParseException {   
        try (DataLink link = DataBase.getDataLink()) {
            StatementExec<Object[]> insertMYTEST = new QueryArray("insert into mytest(id, code, name) values (?, ?, ?)")
                    .setParameters(Kind.STRING, Kind.STRING, Kind.STRING);
            link.exec(insertMYTEST, "one", "code one", "name one");
        }
    } 
    
    @Test
    public void querySelectWithKinds() throws DataLinkException, ParseException {   
        try (DataLink link = DataBase.getDataLink()) {         
           StatementFind<Object[], Object[]> selectMyTest = new QueryArray(
                   "select id, code, name, startdate, weight, amount, line, active from mytest where code = ?")
                   .setParameters(Kind.STRING)
                   .setResults(Kind.STRING, Kind.STRING, Kind.STRING, Kind.TIMESTAMP, Kind.DOUBLE, Kind.DECIMAL, Kind.INT, Kind.BOOLEAN);       
           Object[] result = link.find(selectMyTest, "code 1");         
           Assert.assertEquals("[a, code 1, name one, Wed Jan 01 19:00:32 CET 2014, 12.23, 12.12, 10, true]", Arrays.toString(result));
       }
    } 
    
    @Test
    public void querySelectMetadata() throws DataLinkException, ParseException {   
        try (DataLink link = DataBase.getDataLink()) {
           StatementFind<Object[], Object[]> selectMyTest = new QueryArray(
                   "select id, code, name, startdate, weight, amount, line, active from mytest where code = ?");
           Object[] result = link.find(selectMyTest, "code 1");
           System.out.println(Arrays.toString(result)); // [a, code 1, name a, Tue Feb 11 18:37:52 CET 2014, 12.23, 12.12, 1234, 1]
        }
    }     
    
    @Test
    public void simpleStatements() throws DataLinkException {
        
        try (DataLink link = DataBase.getDataLink()) {
            // Insert a record
            link.exec(new QueryArray("insert into mytest(id, name, amount, line) values (?, ?, ?, ?)"),
                "record one", "name one", new BigDecimal("10.10"), 33);
            // Find a record
            Object[] record = link.find(new QueryArray("select id, name, line, amount from mytest where name = ?"), "name one");
            // List records
            List<Object[]> records = link.query(new QueryArray("select id, name, line, amount from mytest"));
        }    
    }

    @Test
    public void simpleStatementsTyped() throws DataLinkException {
        
        try (DataLink link = DataBase.getDataLink())  {
            // Find a record specifying types
            StatementFind<Object[], Object[]> selectTestTable = new QueryArray(
                "select id, name, line, amount from mytest where name = ?")
                .setParameters(Kind.STRING)
                .setResults(Kind.STRING, Kind.STRING, Kind.INT, Kind.DOUBLE);       
            Object[] result = link.find(selectTestTable, "name one");
        }    
    }

    @Test
    public void simpleStatementsPrimitives() throws DataLinkException {
        
        try (DataLink link = DataBase.getDataLink()) {
            // Count records
            StatementFind<Number, Number> countTestTable = new Query(
                "select count(*) from mytest where amount > ?")
                .setParameters(ParametersDouble.INSTANCE)
                .setResults(ResultsInteger.INSTANCE);       
            int countrows = link.find(countTestTable, 10.0).intValue();   
        }    
    }       
        
    @BeforeClass
    public static void setUpClass() throws DataLinkException {   
        try (DataLink link = DataBase.getDataLink()) {
        
            link.exec(new QueryArray("drop table if exists mytest"));
            
            link.exec(new QueryArray("create table mytest("
                    + "id varchar(32), "
                    + "code varchar(128), "
                    + "name varchar(1024), "
                    + "startdate timestamp, "
                    + "weight double precision, "
                    + "amount decimal(10,2), "
                    + "line integer, "
                    + "active smallint,"
                    + "primary key (id))"));    
            StatementExec<Object[]> insertMyTest = new QueryArray(
                    "insert into mytest(id, code, name, startdate, weight, amount, line, active) values (?, ?, ?, ?, ?, ?, ?, ?)")
                    .setParameters(Kind.STRING, Kind.STRING, Kind.STRING, Kind.TIMESTAMP, Kind.DOUBLE, Kind.DECIMAL, Kind.INT, Kind.BOOLEAN);
            
            link.exec(insertMyTest, "a", "code 1", "name one", new Date(Instant.parse("2014-01-01T18:00:32.212Z").toEpochMilli()), 12.23d, new BigDecimal("12.12"), 10, true);
            link.exec(insertMyTest, "b", "code x", "name two", new Date(Instant.parse("2014-01-01T18:00:32.212Z").toEpochMilli()), 12.23d, new BigDecimal("12.12"), 20, true);
            link.exec(insertMyTest, "c", "code x", "name three", new Date(Instant.parse("2014-01-01T18:00:32.212Z").toEpochMilli()), 12.23d, new BigDecimal("12.12"), 30, true);
            link.exec(insertMyTest, "d", "code x", "name four", new Date(Instant.parse("2014-01-01T18:00:32.212Z").toEpochMilli()), 12.23d, new BigDecimal("12.12"), 40, true);
            link.exec(insertMyTest, "e", "code x", "name five", new Date(Instant.parse("2014-01-01T18:00:32.212Z").toEpochMilli()), 12.23d, new BigDecimal("12.12"), 50, true);            
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
